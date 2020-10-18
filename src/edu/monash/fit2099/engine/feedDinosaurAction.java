package edu.monash.fit2099.engine;

import game.Dinosaur;
import game.Player;

/**
 * Allows a player to feed food type items to a dinosaur
 */
public class feedDinosaurAction extends Action{
    Food food;
    Dinosaur dinosaur;
    Location location;

    /**
     * A constructor for this action.
     * @param food The food that a dinosaur may be fed
     * @param dinosaur The dinosaur in which to feed the food to
     * @param location The current location of the dinosaur at any given turn
     */
    public feedDinosaurAction(Food food, Dinosaur dinosaur, Location location){
        this.food = food;
        this.dinosaur = dinosaur;
        this.location = location;
    }

    /**
     * If a player chooses to feed a dinosaur, the food item will be removed from its inventory.
     * The dinosaurs food level will be raised based on the type of food chosen.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @throws IllegalArgumentException if the actor parameter is not of type Player
     * @return A string indicating the chosen dinosaur to feed and its location
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player){
            actor.removeItemFromInventory(this.getFood());
            int foodLvlPoints = this.getFood().gainPoints;
            this.getDinosaur().setFoodLvl(this.getDinosaur().getFoodLvl() + foodLvlPoints);
        } else {
            throw new IllegalArgumentException("Actor must be an instance of type player to perform this action");
        }
        return menuDescription(actor);
    }

    /**
     * Returns information on the food item to be fed to the dinosaur at a specific location
     * @param actor The actor performing the action.
     * @return A string type
     */
    @Override
    public String menuDescription(Actor actor) {
        String msg = "Feed " + food.getName() + " to " + this.getDinosaur().name + " at (" + this.location.x() + ", " + this.location.y() + ")";
        return msg;
    }

    /**
     * Returns the Food type attribute belonging to this action
     * @return a Food child instance
     */
    public Food getFood() {
        return food;
    }

    /**
     * Sets the food for this specific action. It is based off of the food the player has in its possession
     * @param food an instance of type food eg. Fruit, Hay
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * The dinosaur to be fed
     * @return A dinosaur child instance (stegosaur)
     */
    public Dinosaur getDinosaur() {
        return dinosaur;
    }

    /**
     * Sets the dinosaur child instance (stegosaur) to be fed.
     * @param dinosaur A dinosaur child instance (stegosaur)
     */
    public void setDinosaur(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
    }
}
