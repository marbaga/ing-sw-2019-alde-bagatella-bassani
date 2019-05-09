package it.polimi.ingsw.network.server;
import it.polimi.ingsw.controller.GameEngine;
import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.model.board.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class responsible for the connection between to clients.
 *
 * @author marcobaga
 */

public abstract class PlayerController implements Runnable{
    private GameEngine game;
    protected String name;
    boolean suspended;
    private Player model;
    static final Logger LOGGER = Logger.getLogger("serverLogger");
    List<String> incoming;
    List<String> outgoing;

    PlayerController(){
        this.game = null;
        this.name = null;
        this.suspended = false;
        this.model = null;
        this.incoming = new ArrayList<>();
        this.outgoing = new ArrayList<>();
    }

    /**
     * Manages login communication with the client
     */
    public void run(){
        send("Select a name");
        LOGGER.log(Level.FINE, "Name request sent");
        name = receive();
        LOGGER.log(Level.INFO, "Login procedure initiated for {0}", name);

        while(!ServerMain.getInstance().login(name, this)){
            if(ServerMain.getInstance().canResume(name)){
                send("Do you want to resume?");
                String ans = receive();
                if(ans.equals("yes")) {
                    if(ServerMain.getInstance().resume(name, this)){
                        break;
                    } else {
                        send("Somebody already resumed.");
                    }
                }
            }
            send("Name already taken. Try another one");
            name = receive();
        }
        send("Name accepted.");
    }

    /**
     * Checks for connection with client and forwards messages
     */
    public void refresh(){}

    /**
     * Sends a message through the connection to the client
     *
     * @param in            the message to be sent
     */
    private void send(String in){
        LOGGER.log(Level.FINE, "Message added to outgoing: {0}", in);
        outgoing.add(in);
        refresh();
    }

    /**
     * Receives a message from the client (BLOCKING)
     *
     * @return              the message received
     */
    private String receive() {
        while(incoming.isEmpty()){
            try {
                refresh();
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(InterruptedException ex){
                LOGGER.log(Level.INFO,"Skipped waiting time.");
                Thread.currentThread().interrupt();
            }
        }
        String message = incoming.remove(0);
        LOGGER.log(Level.FINE, "Message added to incoming: {0}", message);
        return message;
    }

    /**
     * Sends a message to the client containing a question and some options.
     *
     * @param message           the text of the question.
     * @param options           the available options.
     */
    public void send(String message, List<String> options){
        //method to be called by GameController to ask the player to make a choice, providing the options.
        System.out.println(message + "\n\n");
        for (String s: options){
            System.out.println(s + "\t");
        }
        System.out.println("\n\n");

    }

    /**
     * Returns a random number between 1 and max.
     * To be substituted with a method that returns the user choice.
     *
     * @param max           the number of options.
     * @param timeout       the time given to make a choice.
     * @return              the user choice (now a random value).
     */
    public int receive (int max, int timeout){
        //method called by game controller to retrieve the answer to said question
        return (1 + (new Random()).nextInt(max));
    }

    /**
     * Getters and Setters
     */

    public String getName() {
        return name;
    }
    public GameEngine getGame() { return game;  }

    public Player getModel() {return model; }

    public boolean isSuspended() {
        return suspended;
    }

    public void setPlayer(Player model) {
        this.model = model;
    }

    public void suspend() {
        this.suspended = true;
        LOGGER.log(Level.INFO,"Player {0} was suspended", name);
    }

}