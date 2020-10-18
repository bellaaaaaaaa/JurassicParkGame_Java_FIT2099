package edu.monash.fit2099.engine;

import game.Fruit;
import game.Player;

import java.util.Random;

public class PickFruitFromTreeAction extends Action{
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

    @Override
    public String menuDescription(Actor actor) {
        return "Pick fruit from the tree";
    }
}
