package it.polimi.ingsw.view;

//TODO: implement
/**
 * Graphical user interface for I/O operations
 *
 * @author  marcobaga
 */

public class GUI implements UI, Runnable {

    private ClientMain clientMain;

    public GUI(ClientMain clientMain){
        this.clientMain = clientMain;
    }

    /**
     * Displays content
     * @param message   message to be displayed
     */
    @Override
    public void display(String message) { }

    /**
     * Queries the user for input
     * @return      the user's input
     */
    @Override
    public String get() {
        return null;
    }

    /**
     * Main GUI loop
     */
    public void run(){}
}