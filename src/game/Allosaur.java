package game;

import edu.monash.fit2099.engine.*;

public class Allosaur extends Dinosaur {
    /**
     * Constructor.
     *
     */
    public Allosaur() {
        super("allosaur", 'a', 100);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    public void eatCarcass(){
        this.setFoodLvl(50);
    }
}
