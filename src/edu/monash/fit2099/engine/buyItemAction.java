package edu.monash.fit2099.engine;

import game.Player;

public class buyItemAction extends Action {
    Item item;

    public buyItemAction(Item item){
        this.item = item;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        Item i = this.getItem();
        actor.addItemToInventory(i);
        if(actor instanceof Player){
            ((Player) actor).setEcoPoints(((Player) actor).getEcoPoints() - i.getPrice());
        }
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        String msg = "Buy ";
        msg += item.getName() + " from Vending Machine (" + Integer.toString(item.getPrice()) + " points)";
        return msg;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
