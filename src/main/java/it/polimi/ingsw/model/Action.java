package it.polimi.ingsw.model;

/**
 * Represents the actions of the players
 * Every action is composed by some moves that can be 1 or more steps, and the possibility to collect, shoot or reload.
 * Every player has 2 or 3 actions available at the same time, depending of his status.
 * Contains an override of equals useful for the tests.
 *
 * @author  davidealde
 */

public final class Action {

    private int steps;
    private boolean collect;
    private boolean shoot;
    private boolean reload;


    /**
     * Constructor
     *
     * @param steps   maximum number of possible steps
     * @param collect possibility to collect
     * @param shoot   possibility to shoot
     * @param reload  possibility to reload
     */
    public Action(int steps, boolean collect, boolean shoot, boolean reload) {
        this.steps = steps;
        this.collect = collect;
        this.shoot = shoot;
        this.reload = reload;
    }


    /**
     * Getters
     */
    public int getSteps() {
        return steps;
    }

    public boolean isCollect() {
        return collect;
    }

    public boolean isShoot() {
        return shoot;
    }

    public boolean isReload() {
        return reload;
    }


    /**
     * Override of equals(Object) to be able to confront two equal Actions.
     *
     * @param o     the object to compare the action with.
     */
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Action)) {
            return false;
        }

        Action a = (Action) o;

        return a.getSteps() == getSteps() &&
                a.isCollect() == isCollect() &&
                a.isShoot() == isShoot() &&
                a.isReload() == isReload();

    }


    /**
     * Override of hashcode(Object).
     */
    @Override
    public int hashCode() {

        int result = 0;

        result += 117 * steps;
        if (collect) result += 249;
        if (shoot) result += 134;
        if (reload) result += 795;

        return result;

    }

}