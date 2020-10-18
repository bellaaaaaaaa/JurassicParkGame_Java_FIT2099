package edu.monash.fit2099.engine;

import game.Player;

/**
 * An action which allows the player to end the game
 */
public class EndGameAction extends Action{

    /**
     * If successful, this action will remove the current player from the game and the game should come to an end.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @throws IllegalArgumentException if the actor parameter is not of type Player
     * @return an empty string
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Remove player from the map
        if (actor instanceof Player){
            map.removeActor(actor);
        } else {
            throw new IllegalArgumentException("Actor must be an instance of type player to perform this action");
        }
        return "";
    }

    /**
     * A string command the player can view and potentially select to end the game
     * @param actor The actor performing the action.
     * @return a string: End the game
     */
    @Override
    public String menuDescription(Actor actor) {
        return "End the game";
    }
}
