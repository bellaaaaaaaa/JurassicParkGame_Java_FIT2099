package edu.monash.fit2099.engine;

import game.Fruit;
import game.Player;

import java.util.Random;

/**
 * This action allows players to attempt to pick fruit from a tree.
 */
public class PickFruitFromTreeAction extends Action{

    /**
     * A player may attempt to pick fruit from a tree with 60% of success
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return A message indicating whether a player successfully or unsuccessfully picked fruit from a tree
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String msg = "";
        if(actor instanceof Player){
            Random rand = new Random();
            int num = rand.nextInt(10) + 1; //either 1 or 2
            if (num > 6){
                actor.addItemToInventory(new Fruit());
                msg = "You successfully picked fruit from the tree";
            } else {
                msg = "You search the tree for fruit, but you can't find any ripe ones";
            }
        }
        return msg;
    }

    /**
     * A string indicating the type of action
     * @param actor The actor performing the action.
     * @return a string: Pick fruit from the tree
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Pick fruit from the tree";
    }
}
