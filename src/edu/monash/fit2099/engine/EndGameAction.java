package edu.monash.fit2099.engine;

import game.Player;

public class EndGameAction extends Action{
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

    @Override
    public String menuDescription(Actor actor) {
        return "End the game";
    }
}
