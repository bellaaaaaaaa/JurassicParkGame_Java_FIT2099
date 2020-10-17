package edu.monash.fit2099.engine;

import game.Player;

public class buyItemAction extends Action {
    Item item;
    public buyItemAction(Item item){
        this.item = item;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        if(actor instanceof Player){
            Item i = this.getItem();
            if (((Player) actor).getEcoPoints() >= i.getPrice()){
                actor.addItemToInventory(i);
                ((Player) actor).setEcoPoints(((Player) actor).getEcoPoints() - i.getPrice());
            } else {
                return ("Not enough eco points to purchase " + i.getName());
            }
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
