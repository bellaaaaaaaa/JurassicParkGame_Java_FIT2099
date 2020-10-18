package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.LaserGun;

import java.util.ArrayList;

public class VendingMachine extends Ground {
    ArrayList<Item> merchandise;
    /**
     * Constructor.
     *
     */
    public VendingMachine() {
        super('V');
        this.merchandise = new ArrayList<Item>();
        this.merchandise = new ArrayList<Item>();
        this.merchandise.add(new Hay());
        this.merchandise.add(new Fruit());
        this.merchandise.add(new MealKit("vegetarian"));
        this.merchandise.add(new MealKit("carnivore"));
        this.merchandise.add(new Egg("stegosaur"));
        this.merchandise.add(new Egg("allosaur"));
        this.merchandise.add(new LaserGun());
    }

    /**
     *
     * @param actor the Actor to check. Only players can enter a vending machine as they can purchase from it. Everything else can't.
     * @return boolean type.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    public ArrayList<Item> getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(ArrayList<Item> merchandise) {
        this.merchandise = merchandise;
    }
}
