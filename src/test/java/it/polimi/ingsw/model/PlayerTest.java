package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.*;

import static it.polimi.ingsw.model.Color.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests all methods of the class Player, covering all the instructions.
 */

class PlayerTest {

    @BeforeEach
    public void setup() {

        BoardConfigurer.getInstance().configureMap(1);
        BoardConfigurer.getInstance().configureDecks();
    }


    /**
     * Tests addMarks()
     */
    @Test
    void addMarks() {

        //instantiates the player who takes damage and two shooters
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //adds 2 marks from shooter
        player.addMarks(2, shooter);

        //checks that player has only 2 marks from shooter
        assertTrue(frequency(player.getMarks(), shooter) == 2 && player.getMarks().size() == 2);
    }


    /**
     * Tests addMarks()
     */
    @Test
    void addMarksMultipleShooters() {

        //instantiates the player who takes damage and two shooters
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter1 = new Player(2, Player.HeroName.D_STRUCT_OR);
        Player shooter2 = new Player(3, Player.HeroName.DOZER);

        //adds 1 marks from shooter1 and 1 from shooter2
        player.addMarks(1, shooter1);
        player.addMarks(1, shooter2);

        //checks that player has only 1 marks from shooter1 and 1 from shooter2
        assertEquals(1,frequency(player.getMarks(), shooter1));
        assertEquals(1,frequency(player.getMarks(), shooter2));
        assertEquals(2,player.getMarks().size());

    }


    /**
     * Tests addMarks()
     */
    @Test
    void addMarksMaximum3() {

        //instantiates the player who takes damage and two shooters
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //adds 3 marks from shooter
        player.addMarks(4, shooter);

        //checks that player has only 3 marks from shooter
        assertTrue(frequency(player.getMarks(), shooter) == 3 && player.getMarks().size() == 3);
    }


    /**
     * Tests powerUp()
     */
    @Test
    void drawPowerUp() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);


        //checks that poweruplist size is 0
        assertEquals(0,player.getPowerUpList().size());
        //draws powerUp
        player.drawPowerUp();

        //checks that poweruplist size is 1
        assertEquals(1,player.getPowerUpList().size());

    }


    /**
     * Tests powerUp()
     */
    @Test
    void drawPowerUpMultiple() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);


        //checks that poweruplist size is 0
        assertEquals(0,player.getPowerUpList().size());
        //draws powerUp
        player.drawPowerUp();

        //checks that poweruplist size is 1
        assertEquals(1,player.getPowerUpList().size());

        //saves this power up for a later confrontation
        Card confr = player.getPowerUpList().get(0);

        //draws another powerUp
        player.drawPowerUp();

        //checks that poweruplist size is 2
        assertEquals(2,player.getPowerUpList().size());

        //checks that the previous powerUp did not changed
        assertEquals(confr,player.getPowerUpList().get(0));

    }


    /**
     * Tests discardPowerUp()
     */
    @Test
    void discardPowerUp() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //draws 2 times
        player.drawPowerUp();
        player.drawPowerUp();

        //saves what powerUp that there are
        Card powerUp1;
        Card powerUp2;
        powerUp1 = player.getPowerUpList().get(0);
        powerUp2 = player.getPowerUpList().get(1);

        //discards powerUp1
        player.discardPowerUp(powerUp1);

        //checks that powerUpList contains only powerUp2
        assertTrue(player.getPowerUpList().contains(powerUp2) && player.getPowerUpList().size() == 1);

    }


    /**
     * Tests addPoints()
     */
    @Test
    void addPoints() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //adds 2 point
        player.addPoints(2);

        //checks that player has 2 point
        assertEquals(2,player.getPoints());

    }


    /**
     * Tests addPoints()
     */
    @Test
    void addPointsMultiple() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //adds 1 point
        player.addPoints(1);

        //checks that player has 1 point
        assertEquals(1,player.getPoints());

        //adds 2 point
        player.addPoints(2);

        //checks that player has 3 point
        assertEquals(3,player.getPoints() );
    }


    /**
     * Tests addWeapon()
     */
    @Test
    void addWeapon() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //instantiates a weapon
        Weapon weapon = WeaponFactory.createWeapon(Weapon.WeaponName.THOR);

        //adds weapon
        player.addWeapon(weapon);

        //checks that weaponsList contains only weapon
        assertTrue(player.getWeaponList().contains(weapon) && player.getWeaponList().size() == 1);

    }


    /**
     * Tests addWeapon()
     */
    @Test
    void addWeaponMultiple() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //instantiates 2 weapons
        Weapon weapon1 = WeaponFactory.createWeapon(Weapon.WeaponName.THOR);
        Weapon weapon2 = WeaponFactory.createWeapon(Weapon.WeaponName.SHOTGUN);

        //adds weapons
        player.addWeapon(weapon1);
        player.addWeapon(weapon2);

        //checks that weaponsList contains only the rights wepons
        assertTrue(player.getWeaponList().contains(weapon1) &&
                player.getWeaponList().contains(weapon2) &&
                player.getWeaponList().size() == 2);

    }


    /**
     * Tests discardWeapon()
     */
    @Test
    void discardWeapon() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //instantiates 2 weapons
        Weapon weapon1 = WeaponFactory.createWeapon(Weapon.WeaponName.THOR);
        Weapon weapon2 = WeaponFactory.createWeapon(Weapon.WeaponName.SHOTGUN);

        //adds weapons
        player.addWeapon(weapon1);
        player.addWeapon(weapon2);

        //discards weapon2
        player.discardWeapon(weapon2);

        //checks that weaponslist contains only weapon1
        assertTrue(player.getWeaponList().contains(weapon1) && player.getWeaponList().size() == 1);
    }


    /**
     * Tests updateAwards()
     */
    @Test
    void updateAwards() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //calls updateAwards
        player.updateAwards();

        //checks that pointsToGive is 6
        assertEquals(6,player.getPointsToGive());

        //calls updateAwards
        player.updateAwards();

        //checks that pointsToGive is 4
        assertEquals(4,player.getPointsToGive());

        //calls updateAwards
        player.updateAwards();

        //checks that pointsToGive is 2
        assertEquals(2,player.getPointsToGive());

        //calls updateAwards
        player.updateAwards();

        //checks that pointsToGive is 1
        assertEquals(1,player.getPointsToGive());

        //calls updateAwards
        player.updateAwards();

        //checks that pointsToGive is 1
        assertEquals(1,player.getPointsToGive());

    }


    /**
     * Tests useAsAmmo()
     */
    @Test
    void useAsAmmo() {
        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //draws a poerUp
        player.drawPowerUp();

        //save the powerUp
        PowerUp powerUp;
        powerUp = player.getPowerUpList().get(0);
        Color color;
        color = powerUp.getColor();

        //calls useAsAmmo
        player.useAsAmmo(powerUp);

        //checks that powerUpList is empty
        assertTrue(player.getPowerUpList().isEmpty());

        //checks that myAmmoPack contains only the ammo of the right color
        if (color == RED) {
            assertTrue(player.getAmmopack().getRedAmmo() == 1 && player.getAmmopack().getYellowAmmo() == 0 && player.getAmmopack().getBlueAmmo() == 0);
        } else if (color == YELLOW) {
            assertTrue(player.getAmmopack().getRedAmmo() == 0 && player.getAmmopack().getYellowAmmo() == 1 && player.getAmmopack().getBlueAmmo() == 0);
        } else {
            assertTrue(player.getAmmopack().getRedAmmo() == 0 && player.getAmmopack().getYellowAmmo() == 0 && player.getAmmopack().getBlueAmmo() == 1);
        }

    }


    /**
     * Tests addAmmoPack()
     */
    @Test
    void addAmmoPackPlayer() {

        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //instantiates an AmmoPack
        AmmoPack ammoPack = new AmmoPack(1, 2, 3);

        //calls addAmmoPack
        player.addAmmoPack(ammoPack);

        //checks that myAmmoPacks contains the right amount of ammos of every color
        assertTrue(player.getAmmopack().getRedAmmo() == 1 && player.getAmmopack().getBlueAmmo() == 2 && player.getAmmopack().getYellowAmmo() == 3);
    }


    /**
     * Tests useAmmo()
     */
    @Test
    void useAmmo() {
        //instantiates the player
        Player player = new Player(1, Player.HeroName.VIOLET);

        //instantiates 2 AmmoPacks
        AmmoPack ammoPack1 = new AmmoPack(1, 2, 3);
        AmmoPack ammoPack2 = new AmmoPack(1, 1, 2);

        //adds ammos
        player.addAmmoPack(ammoPack1);

        //calls useAmmo
        player.useAmmo(ammoPack2);

        //checks that myAmmoPacks contains the right amount of ammos of every color
        assertTrue(player.getAmmopack().getRedAmmo() == 0 && player.getAmmopack().getBlueAmmo() == 1 && player.getAmmopack().getYellowAmmo() == 1);
    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamage() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(2, shooter);

        //checks that damages are done and right
        assertEquals(shooter,player.getDamages().get(0));
        assertEquals(shooter,player.getDamages().get(1));
        assertEquals(2,player.getDamages().size());

    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageMultipleShooters() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter1 = new Player(2, Player.HeroName.D_STRUCT_OR);
        Player shooter2 = new Player(3, Player.HeroName.DOZER);

        //calls sufferDamage
        player.sufferDamage(1, shooter1);
        player.sufferDamage(2, shooter2);

        //checks that the damages and the damages.size() are right
        assertEquals(shooter1,player.getDamages().get(0));
        assertEquals(shooter2,player.getDamages().get(1));
        assertEquals(shooter2,player.getDamages().get(2));
        assertEquals(3,player.getDamages().size());

    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageJustDamaged() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(1, shooter);

        //checks that JustDamaged is right
        assertTrue(player.isJustDamaged());

    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageNotOverkilled() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(1, shooter);

        //checks that overkilled is right
        assertFalse(player.isOverkilled());

    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageNotDead() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(1, shooter);

        //checks that dead is right
        assertFalse(player.isDead());

    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageStatus1() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(1, shooter);

        //checks that status is right
        assertEquals(Player.Status.BASIC,player.getStatus());
    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageStatus2() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);


        //calls sufferDamage
        player.sufferDamage(3, shooter);

        //checks that status is right
        assertEquals(Player.Status.ADRENALINE_1,player.getStatus());

    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageStatus3() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(7, shooter);


        //checks that status is right
        assertEquals(Player.Status.ADRENALINE_2,player.getStatus());
    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageAndMarks() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //adds marks
        player.addMarks(3, shooter);

        //calls sufferDamage
        player.sufferDamage(7, shooter);

        //checks that the damages and marks amount are correct
        assertEquals(10, player.getDamages().size());
        assertEquals(0, player.getMarks().size());
    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageDeath() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(11, shooter);

        //checks that the damages and marks amount are correct
        assertTrue(player.isDead());
        assertFalse(player.isOverkilled());
    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageOverkilled() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(12, shooter);

        //checks that the damages and marks amount are correct
        assertTrue(player.isOverkilled());
    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageMarksToOverkiller() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(12, shooter);

        //checks that overkiller has only the right mark
        assertTrue(shooter.getMarks().get(0) == player && shooter.getMarks().size() == 1);
    }


    /**
     * Tests sufferDamage()
     */
    @Test
    void sufferDamageMax12Dmgs() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //calls sufferDamage
        player.sufferDamage(13, shooter);

        //checks that the size of damages is 12
        assertEquals(12,player.getDamages().size());
    }



    /**
     * Tests rewardKillers()
     */
    @Test
    void rewardKillers() {

        BoardConfigurer.getInstance().configurePlayerOptions(3);

        //instantiates the players
        Player player1 = Board.getInstance().getPlayers().get(0);
        Player shooter = Board.getInstance().getPlayers().get(1);
        Player player2 = Board.getInstance().getPlayers().get(2);


        //puts a damage from shooter in damages of player
        player1.sufferDamage(11, shooter);

        //rewards the killers
        player1.rewardKillers();

        //check that gives awards only to the killer, first blood included
        assertEquals(9, shooter.getPoints());
        assertEquals(0, player1.getPoints());
        assertEquals(0, player2.getPoints());
    }


    /**
     * Tests rewardKillers()
     */
    @Test
    void rewardKillersManyShooters1() {

        BoardConfigurer.getInstance().configurePlayerOptions(5);

        //initializes the killShotTrack and five players, with 0 points

        Player player = Board.getInstance().getPlayers().get(0);
        Player shooter1 = Board.getInstance().getPlayers().get(1);
        Player shooter2 = Board.getInstance().getPlayers().get(2);
        Player shooter3 = Board.getInstance().getPlayers().get(3);
        Player shooter4 = Board.getInstance().getPlayers().get(4);



        //adds damages
        player.sufferDamage(1, shooter1);
        player.sufferDamage(2, shooter2);
        player.sufferDamage(3, shooter3);
        player.sufferDamage(5, shooter4);

        //rewards the killers
        player.rewardKillers();


        //checks that gives awards right
        assertEquals(3,shooter1.getPoints());
        assertEquals(4,shooter2.getPoints());
        assertEquals(6,shooter3.getPoints());
        assertEquals(8,shooter4.getPoints());
    }


    /**
     * Tests rewardKillers()
     */
    @Test
    void rewardKillersManyShooters2() {

        BoardConfigurer.getInstance().configurePlayerOptions(5);

        Player player = Board.getInstance().getPlayers().get(0);
        Player shooter1 = Board.getInstance().getPlayers().get(1);
        Player shooter2 = Board.getInstance().getPlayers().get(2);
        Player shooter3 = Board.getInstance().getPlayers().get(3);
        Player shooter4 = Board.getInstance().getPlayers().get(4);

        //simulates 2 deaths of the player
        player.updateAwards();
        player.updateAwards();

        //adds damages
        player.sufferDamage(1, shooter1);
        player.sufferDamage(2, shooter2);
        player.sufferDamage(3, shooter3);
        player.sufferDamage(5, shooter4);

        //rewards the killers
        player.rewardKillers();

        //checks that gives awards right
        assertEquals(2,shooter1.getPoints());
        assertEquals(1,shooter2.getPoints());
        assertEquals(2,shooter3.getPoints());
        assertEquals(4,shooter4.getPoints());
    }


    /**
     * Tests refreshActionList()
     */
    @Test
    void refreshActionListBASIC() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);

        //calls refreshActionList
        player.refreshActionList();

        //checks that she has only the rights abilities
        assertTrue(player.getActionList().get(0).equals(new Action(3, false, false, false))
                        &&player.getActionList().get(1).equals(new Action(1, true, false, false))
                        &&player.getActionList().get(2).equals(new Action(0, false, true, false))
                        &&player.getActionList().size() == 3);
    }


    /**
     * Tests refreshActionList()
     */
    @Test
    void refreshActionListFRENZY_1() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);

        //set the status FRENZY_1
        player.setStatusFrenzy(Player.Status.FRENZY_1);

        //calls refreshActionList
        player.refreshActionList();

        //checks that she has only the rights abilities
        assertTrue(player.getActionList().get(0).equals(new Action(1, false, true, true))
                &&player.getActionList().get(1).equals(new Action(4, false, false, false))
                &&player.getActionList().get(2).equals(new Action(2, true, false, false))
                && player.getActionList().size() == 3);
    }


    /**
     * Tests refreshActionList()
     */
    @Test
    void refreshActionListFRENZY_2() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);

        //set the status FRENZY_2
        player.setStatusFrenzy(Player.Status.FRENZY_2);

        //calls refreshActionList
        player.refreshActionList();

        //checks that she has only the rights abilities
        assertTrue(player.getActionList().get(0).equals(new Action(2, false, true, true)) &&
                player.getActionList().get(1).equals(new Action(3, true, false, false)) &&
                player.getActionList().size() == 2);
    }


    /**
     * Tests refreshActionList()
     */
    @Test
    void refreshActionListADRENALINE_1() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //set the status ADRENALINE_1
        player.sufferDamage(3, shooter);

        //calls refreshActionList
        player.refreshActionList();

        //checks that she has only the rights abilities
        assertTrue(player.getActionList().get(0).equals(new Action(3, false, false, false)) &&
                player.getActionList().get(1).equals(new Action(2, true, false, false)) &&
                player.getActionList().get(2).equals(new Action(0, false, true, false)) &&
                player.getActionList().size() == 3);
    }


    /**
     * Tests refreshActionList()
     */
    @Test
    void refreshActionListADRENALINE_2() {

        //instantiates the players
        Player player = new Player(1, Player.HeroName.VIOLET);
        Player shooter = new Player(2, Player.HeroName.D_STRUCT_OR);

        //set the status ADRENALINE_2
        player.sufferDamage(6, shooter);

        //calls refreshActionList
        player.refreshActionList();

        //checks that she has only the rights abilities
        assertTrue(player.getActionList().get(0).equals(new Action(3, false, false, false)) &&
                player.getActionList().get(1).equals(new Action(2, true, false, false)) &&
                player.getActionList().get(2).equals(new Action(1, false, true, false)) &&
                player.getActionList().size() == 3);
    }


}