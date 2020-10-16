package game;

import edu.monash.fit2099.engine.Food;

public class Egg extends Food {
    String type;
    int numTurnsOnGround;

    /**
     * Constructor for Egg which extends Food.
     * @param foodLvl How much eating this will increase a dinosaurs food level by
     * @param price The cost to purchase this item from the vending machine.
     * @param gainPoints Number of eco points players gain when an egg hatches
     * @param name Name of egg
     * @param displayChar How Eggs can be visualised on a map
     * @param portable Whether egg items are portable and can be added to the players inventory
     * @param type Indicates whether the egg is a stegosaur or allosaur egg
     */
    public Egg(int foodLvl, int price, int gainPoints, String name, char displayChar, boolean portable, String type) {
        super(foodLvl, price, gainPoints, name, displayChar, portable);
        this.type = type;
    }

    /**
     * Indicates how long the egg has been on the ground since lain
     * @return integer
     */
    public int getNumTurnsOnGround() { return numTurnsOnGround; }

    /**
     * Changes the number of turns an egg has been on the ground
     * @param numTurnsOnGround the new number of turns the egg has been on the ground
     */
    public void setNumTurnsOnGround(int numTurnsOnGround) { this.numTurnsOnGround = numTurnsOnGround; }

    /**
     * Returns whether the egg is a stegosaur or allosaur type
     * @return string
     */
    public String getType() { return type; }

    /**
     * System will set the type of egg depending on the type of dinosaur that laid it.
     * @param type string type
     */
    public void setType(String type) { this.type = type; }
}
