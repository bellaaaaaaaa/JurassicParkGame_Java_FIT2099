package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.LaserGun;

import java.util.ArrayList;

/**
 * A player can purchase items from a vending machine.
 * Only one of these will exist on the map at any give time.
 */
public class VendingMachine extends Ground {
    ArrayList<Item> merchandise;
    /**
     * Constructor for vending machine. Initially created with merandise that is a list of items it can sell.
     */
    public VendingMachine() {
        super('V');
        this.merchandise = new ArrayList<Item>();
        this.merchandise.add(new Hay());
        this.merchandise.add(new Fruit());
        this.merchandise.add(new MealKit("vegetarian"));
        this.merchandise.add(new MealKit("carnivore"));
        this.merchandise.add(new Egg("stegosaur"));
        this.merchandise.add(new Egg("allosaur"));
        this.merchandise.add(new Egg("agilisaurus")); // agilisaurus eggs available for purchase from VM.
        this.merchandise.add(new Egg("archaeopteryx")); // archaeopteryx eggs available for purchase from VM.
        this.merchandise.add(new LaserGun());
    }

    /**
     * Checks whether an actor can enter this specific ground location or not
     * @param actor the Actor to check. Only players can enter a vending machine as they can purchase from it. Everything else can't.
     * @return boolean type.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * This method accesses a vending machines merchandise
     * @return All of the items which are available for purchase at a vending machine
     */
    public ArrayList<Item> getMerchandise() {
        return merchandise;
    }

    /**
     * Sets the arraylist of items that will be sold at a vending machine
     * @param merchandise an array list of Items to be sold
     */
    public void setMerchandise(ArrayList<Item> merchandise) {
        this.merchandise = merchandise;
    }
}
