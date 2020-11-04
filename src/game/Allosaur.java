package game;

import edu.monash.fit2099.engine.*;

/**
 * A class which inherits from and represents a type of Dinosaur available in the game
 */
public class Allosaur extends Dinosaur {

    /**
     * This constructor instantiates a new Allosaur by calling the Dinosaur class constructor which in turn calls the Actor class constructor.
     */
    public Allosaur() {
        super("allosaur", 'a', 100);
        this.setGender(this.randomiseGender());
    }


    /**
     * This method returns the current actions that an allosaur can perform at a given turn
     * @param actions The allowed actions for an allosaur instance
     * @param lastAction The last action the allosaur performed
     * @param map The current map the allosaur exists on
     * @param display The display of the world the allosaur exists in. Depicts the terrain type and existing actors
     * @return A collection of actions
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    /**
     * This method increases an Allosaur instance's food level by 50 each time it eats a dead dinosaur
     */
    public void eatCarcass(int increase){
        this.setFoodLvl(this.getFoodLvl() + increase);
    }
}
