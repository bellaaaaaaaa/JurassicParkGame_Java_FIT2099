package edu.monash.fit2099.engine;

import game.Dirt;
import game.Hay;
import game.Player;

public class HarvestGrassAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player) {
            map.locationOf(actor).setGround(new Dirt()); // Set ground to dirt
            actor.addItemToInventory(new Hay()); // Add hay to player inventory
            ((Player) actor).setEcoPoints(((Player) actor).getEcoPoints()+1); //gains 1 ecopoint
        }
        return "Player harvests grass";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Harvest grass at current location";
    }
}
