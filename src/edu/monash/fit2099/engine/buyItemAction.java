package edu.monash.fit2099.engine;

import game.Player;

/**
 * A class extending from Action which allows players to purchase items sold from the vending machine
 */
public class buyItemAction extends Action {
    Item item;
    public buyItemAction(Item item){
        this.item = item;
    }

    /**
     * This method allows a player to purchase a specific item. It ensures that the player has enough eco points to make the purchase.
     * If so, the chose item will be added to its inventory.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @throws IllegalArgumentException if the actor parameter is not of type Player
     * @return a String type indicating either the bought item or that the user has insufficient funds.
     */
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
        } else {
            throw new IllegalArgumentException("Actor must be an instance of type player to perform this action");
        }
        return menuDescription(actor);
    }

    /**
     * This method returns a string indicating the item a player may wish to buy along with its cost in eco points.
     * @param actor The actor performing the action.
     * @return a String type
     */
    @Override
    public String menuDescription(Actor actor) {
        String msg = "Buy ";
        msg += item.getName() + " from Vending Machine (" + Integer.toString(item.getPrice()) + " ecopoints)";
        return msg;
    }

    /**
     * This method returns the item from the vending machine a player may choose to buy.
     * @return Item type
     */
    public Item getItem() {
        return item;
    }

    /**
     * This method sets the item for this action.
     * @param item of Item type
     */
    public void setItem(Item item) {
        this.item = item;
    }
}
