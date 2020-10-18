package edu.monash.fit2099.engine;

/**
 * Food for dinosaurs
 */
public abstract class Food extends Item {
    int foodLvl;
    int price;
    int gainPoints;
    String name;

    /**
     * This method is for a type of food based on the Item constructor.
     * @param foodLvl The amount of points that when eaten, increases a dinosaurs foodLvl by
     * @param price The number of eco points required to purchase this item from a vending machine
     * @param gainPoints The number of eco points a player gains each time they feed a specific food to a dinosaur
     * @param name The name used to identify the type of food. Eg. Fruit.
     * @param displayChar The character used to display the food
     * @param portable A boolean indicating whether an instance can be stored in the players inventory/ moved around
     */
    public Food(int foodLvl, int price, int gainPoints, String name, char displayChar, boolean portable){
        super(name, displayChar, portable);
        this.foodLvl = foodLvl;
        this.price = price;
        this.gainPoints = gainPoints;
    }
}
