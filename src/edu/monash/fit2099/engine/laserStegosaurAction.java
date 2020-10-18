package edu.monash.fit2099.engine;

import game.Player;
import game.Stegosaur;

/**
 * An action which gives players the choice to use their laser gun on a stegosaur to hurt/kill it.
 */
public class laserStegosaurAction extends Action {
    Stegosaur stegosaur;
    GameMap stegosaurMap;

    /**
     * This action will attack a stegosaur at a specific location. Each zap to the stegosaur takes away 50 hit points from its life.
     * This ensures that any stegosaur will be killed in one or two turns since stegosaurs have a max hitPoint (health) of 100 points
     * If a Stegosaur gets killed in one blow, its carcass will remain at the location it was last at.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A String message indicating whether a stegosaur was killed or simply hurt. If hurt the Stegosaurs new hitPoint value will be shown.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player) {
            // Kill stegosaur in one or two hits. laser attack will be 50, where Dinosaur max health is 100.
            this.getStegosaur().hurt(50);
            if(this.getStegosaur().hitPoints <= 0){
                this.getStegosaur().setDead(true);
                this.getStegosaur().setNumTurnsDead(0);
                this.getStegosaur().setUnconscious(true);
                return "Player killed Stegosaur";
            }
        }
        return "Player attacked Stegosaur, it has " + this.getStegosaur().hitPoints + " health remaining";
    }

    /**
     * This method returns a string indicating which stegosaur at which location a player may want to zap with its laser gun
     * @param actor The actor performing the action.
     * @return A string type such as: Zap Stegosaur at (20, 4)
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Zap Stegosaur at (" + this.getStegosaurMap().locationOf(this.getStegosaur()).x() + ", " + this.getStegosaurMap().locationOf(this.getStegosaur()).y() + ")";
    }

    /**
     * This method accesses the laserStegosaurAction class's instance variable: stegosaur
     * @return A stegosaur instance
     */
    public Stegosaur getStegosaur() {
        return stegosaur;
    }

    /**
     * This method sets the laserStegosaurAction class's instance variable: stegosaur
     * @param stegosaur a stegosaur instance currently existing on the GameMap
     */
    public void setStegosaur(Stegosaur stegosaur) {
        this.stegosaur = stegosaur;
    }

    /**
     * Accesses the instance variable 'stegosaurMap' for this action class.
     * @return A GameMap instance which the stegosaur exists within
     */
    public GameMap getStegosaurMap() {
        return stegosaurMap;
    }

    /**
     * Sets the instance variable 'stegosaurMap' for this action class.
     * @param stegosaurMap A GameMap instance that is currently in use.
     */
    public void setStegosaurMap(GameMap stegosaurMap) {
        this.stegosaurMap = stegosaurMap;
    }
}
