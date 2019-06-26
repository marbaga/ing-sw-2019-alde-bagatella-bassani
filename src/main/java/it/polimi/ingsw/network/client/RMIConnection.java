package it.polimi.ingsw.network.client;

import com.google.gson.JsonParser;
import it.polimi.ingsw.network.server.RemoteController;
import it.polimi.ingsw.network.server.RemoteServer;
import it.polimi.ingsw.view.ClientMain;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static it.polimi.ingsw.controller.ServerMain.SLEEP_TIMEOUT;

/**
 * Implementation of RMI connection to server
 *
 * @author marcobaga
 */
public class RMIConnection implements Runnable, RemoteView {

    private RemoteController playerStub;
    private ClientMain clientMain;
    private static final Logger LOGGER = Logger.getLogger("clientLogger");
    private JsonParser jsonParser;
    private boolean shutdown;


    /**
     * Constructor retrieving remote objects
     *
     * @param clientMain        reference to the main class
     * @param address           IP to connect to
     * @param port              port to connect to
     */
    public RMIConnection(ClientMain clientMain, String address, int port){
        this.clientMain = clientMain;
        this.jsonParser = new JsonParser();
        try {
            Registry reg = LocateRegistry.getRegistry(address, port);
            RemoteServer serverStub = (RemoteServer) reg.lookup("RMIServer");
            String pcLookup = serverStub.getPlayerController((RemoteView) UnicastRemoteObject.exportObject(this, 0));
            //LOGGER.log(Level.INFO, "Name received for RMI PC lookup: " + pcLookup);
            playerStub = (RemoteController) reg.lookup(pcLookup);

            ExecutorService executor = Executors.newCachedThreadPool();
            executor.submit(()->{
                while(Thread.currentThread().isAlive()&&!shutdown){
                    try {
                        playerStub.ping();
                    }catch(RemoteException ex){
                        LOGGER.log(Level.SEVERE, "Unable to ping RMI server", ex);
                        shutdown();
                        clientMain.showDisconnection();
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(SLEEP_TIMEOUT);
                    }catch(InterruptedException ex){
                        LOGGER.log(Level.INFO,"Skipped waiting time.");
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }catch(NotBoundException ex){
            LOGGER.log(Level.SEVERE, "Could not find stubs in registry", ex);
        }catch(RemoteException ex){
            LOGGER.log(Level.SEVERE, "RMI server disconnected, shutting down", ex);
            System.exit(0);
        }
    }

    public void run(){}

    /**
     * Asks clientMain to carry out a decision
     * @param msg       message to display
     * @param options   options between which to choose
     * @return          int corresponding to the choice
     * @throws RemoteException
     */
    public int choose(String msg, List<String> options) throws RemoteException{
        return clientMain.choose(msg, options);
    }

    /**
     * Asks clientMain to display a message
     *
     * @param msg           message to display
     * @throws RemoteException
     */
    public void display(String msg) throws RemoteException{
        clientMain.display(msg);
    }

    /**
     * Asks clientMain to provide a String
     *
     * @param msg       message to display
     * @param max       max length of the answer
     * @return          clientMain's response
     * @throws RemoteException
     */
    public String getInput(String msg, int max) throws RemoteException{
        return clientMain.getInput(msg, max);
    }

    /**
     * Remote methos to be called by the server to assert the state of the connection
     * @throws RemoteException
     */
    public void ping() throws RemoteException{
        //empty method useful for checking whether the server can callback remote functions from the client
    }

    /**
     * Passes an update to clientMain
     *
     * @param jsonObject        encoded update
     * @throws RemoteException
     */
    public void update(String jsonObject) throws RemoteException{
        try {
            clientMain.update(jsonParser.parse(jsonObject).getAsJsonObject());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Exception in parsing jsonObject", ex);
        }
    }

    public void shutdown(){
        shutdown = true;
        try {
            UnicastRemoteObject.unexportObject(this, false);
        }catch(NoSuchObjectException ex){
            LOGGER.log(Level.SEVERE, "issue while closing connection", ex);
        }
    }

    public void showSuspension() throws RemoteException{
        shutdown();
        clientMain.showSuspension();
    }

    public void showEnd(String message) throws  RemoteException{
        shutdown();
        clientMain.showEnd(message);
    }


}
