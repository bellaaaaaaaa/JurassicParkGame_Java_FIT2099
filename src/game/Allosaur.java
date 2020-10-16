package game;

import edu.monash.fit2099.engine.*;

public class Allosaur extends Dinosaur {
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Allosaur(String name, char displayChar, int hitPoints) {
        super("allosaur", 'a', hitPoints);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    public void eatStegosaur(Stegosaur s){
        this.setFoodLvl(50);
    }
}
