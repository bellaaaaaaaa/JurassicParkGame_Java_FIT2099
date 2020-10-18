package edu.monash.fit2099.engine;

import game.Dirt;
import game.Hay;
import game.Player;

/**
 * An action which allows users to harvest hay from a Grass Ground type.
 */
public class HarvestGrassAction extends Action {

    /**
     * This method checks whether a player is standing on Grass. If a player is, they can choose to harvest the grass
     * producing hay in their inventory, and leaving bare dirt behind on the ground at the current location
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A string message indicating that the player has performed the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String msg = menuDescription(actor);
        if (actor instanceof Player) {
            map.locationOf(actor).setGround(new Dirt()); // Set ground to dirt
            actor.addItemToInventory(new Hay()); // Add hay to player inventory
            ((Player) actor).setEcoPoints(((Player) actor).getEcoPoints()+1); //gains 1 ecopoint
            msg =  "Player harvests grass";
        } else {
            throw new IllegalArgumentException("Actor must be an instance of type player to perform this action");
        }
        return msg;
    }

    /**
     * A message indicating if a player would like to harvest the grass they are currently standing on.
     * @param actor The actor performing the action.
     * @return A string
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Harvest grass at current location";
    }
}
