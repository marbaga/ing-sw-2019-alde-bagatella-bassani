package it.polimi.ingsw.view.CLIRenderer;

import it.polimi.ingsw.view.ClientModel;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static it.polimi.ingsw.view.CLIRenderer.MainRenderer.RESET;


public class WeaponRenderer {

    private static final Logger LOGGER = Logger.getLogger("clientLogger");
    private static final int WEAPON_HEIGHT = 8;
    private static final int WEAPON_LENGTH = 55;

    /**
     * Class creating a bidimensional String array containing data about weapons on the ground
     */
    private WeaponRenderer(){}

    public static String[][] get(ClientModel clientModel){

        int rows = clientModel.getSquares().stream().filter(x->!x.getWeapons().isEmpty()).collect(Collectors.toList()).size();

        String[][] box = new String[rows+WEAPON_HEIGHT][WEAPON_LENGTH];
        for(int i=0; i<box.length; i++){
            for(int j=0; j<box[i].length; j++){
                box[i][j] = " ";
            }
        }

        try {
            String[] weapons = new String[rows];
            int ind = 0;
            for (ClientModel.SimpleSquare s : clientModel.getSquares()) {
                if (!s.getWeapons().isEmpty()) {
                    weapons[ind] = "Room " + s.getId() + ": ";
                    List<ClientModel.SimpleWeapon> list = s.getWeapons();
                    for (int i = 0; i < list.size(); i++) {
                        weapons[ind] = weapons[ind].concat(list.get(i).getName());
                        if (i != list.size() - 1) {
                            weapons[ind] = weapons[ind].concat(", ");
                        }
                    }
                    ind++;
                }
            }
            for (int i = 0; i < weapons.length; i++) {
                for (int j = 0; j < weapons[i].length() && j + 3 < WEAPON_LENGTH; j++) {
                    box[1 + i][3 + j] = String.valueOf(weapons[i].charAt(j));
                }
            }
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Error while listing weapons in rooms", ex);
        }

        try {
            List<ClientModel.SimplePlayer> shooters = clientModel.getKillShotTrack();
            String[] killshotTrack = new String[shooters.size()+clientModel.getSkullsLeft()];
            for (int i = 0; i < shooters.size(); i++) {
                killshotTrack[i] = ClientModel.getEscapeCode(shooters.get(i).getColor()) + "♱" + RESET;
            }
            for(int i=shooters.size(); i<shooters.size()+clientModel.getSkullsLeft(); i++){
                killshotTrack[i] = "X";
            }
            for (int i = 0; i < "KillShot: ".length(); i++) {
                box[rows + 2][i + 3] = String.valueOf("Killshot: ".charAt(i));
            }
            for (int i = 0; i < killshotTrack.length; i++) {
                box[rows + 2][i + 14] = String.valueOf(killshotTrack[i]);
            }
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Error while printing killshot track", ex);
        }

        String weaponDeck = "Weapons left in deck: " + clientModel.getWeaponCardsLeft();
        for(int i=0; i<weaponDeck.length(); i++){
            box[rows+4][i+3] = String.valueOf(weaponDeck.charAt(i));
        }

        String powerUpDeck = "PowerUps left in deck: " + clientModel.getPowerUpCardsLeft();
        for(int i=0; i<powerUpDeck.length(); i++){
            box[rows+6][i+3] = String.valueOf(powerUpDeck.charAt(i));
        }
        return box;
    }

}
