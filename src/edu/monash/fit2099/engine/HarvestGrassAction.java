package edu.monash.fit2099.engine;

import game.Dirt;
import game.Hay;
import game.Player;

public class HarvestGrassAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player) {
            Dirt d = new Dirt();
            map.locationOf(actor).setGround(d); // Set ground to dirt
            Hay h = new Hay();
            actor.addItemToInventory(h); // Add hay to player inventory
        }
        return "Player harvests grass";
    }


    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
