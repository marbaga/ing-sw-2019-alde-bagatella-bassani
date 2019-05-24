package it.polimi.ingsw.network.server;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.GameEngine;
import it.polimi.ingsw.controller.ServerMain;
import it.polimi.ingsw.model.board.Player;
import it.polimi.ingsw.model.exceptions.SlowAnswerException;
import it.polimi.ingsw.view.ClientModel;

import java.util.ArrayList;
import java.util.Arrays;
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

public abstract class VirtualView implements Runnable{
    protected GameEngine game;
    protected String name;
    boolean suspended;
    private Player model;
    static final Logger LOGGER = Logger.getLogger("serverLogger");

    VirtualView(){
        this.game = null;
        this.name = null;
        this.suspended = false;
        this.model = null;
    }

    /**
     * Manages login communication with the client
     */
    public void run(){
        name = getInputNow("Select a name", 16);
        LOGGER.log(Level.INFO, "Login procedure initiated for {0}", name);

        while(!ServerMain.getInstance().login(name, this)){
            if(ServerMain.getInstance().canResume(name)){
                int ans = chooseNow("Do you want to resume?", Arrays.asList("yes", "no"));

                if(ans==1) {
                    if(ServerMain.getInstance().resume(name, this)){
                        break;
                    } else {
                        display("Somebody already resumed.");
                    }
                }
            }
            name=getInputNow("Name already taken. Try another one", 16);
        }
        display("Name accepted.");
    }

    /**
     * Checks for connection with client and forwards messages
     */
    public void refresh(){}

    /**
     * Getters and Setters
     */

    public String getName() {
        return name;
    }

    public GameEngine getGame() { return game;  }

    public void setGame(GameEngine game){this.game = game;}

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

    abstract public void choose(String msg, List<?> options);

    abstract public void display(String msg);

    abstract public void getInput(String msg, int max);

    abstract String getInputNow(String msg, int max);

    abstract int chooseNow(String msg, List<?> options);

    abstract public void update(ClientModel clientModel);
}