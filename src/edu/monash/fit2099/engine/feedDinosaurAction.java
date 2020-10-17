package edu.monash.fit2099.engine;

import game.Dinosaur;
import game.Player;

public class feedDinosaurAction extends Action{
    Food food;
    Dinosaur dinosaur;
    Location location;

    public feedDinosaurAction(Food food, Dinosaur dinosaur, Location location){
        this.food = food;
        this.dinosaur = dinosaur;
        this.location = location;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof Player){
            actor.removeItemFromInventory(this.getFood());
            int foodLvlPoints = this.getFood().gainPoints;
            this.getDinosaur().setFoodLvl(this.getDinosaur().getFoodLvl() + foodLvlPoints);
        }
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        String msg = "Feed " + food.getName() + " to " + this.getDinosaur().name + " at (" + this.location.x() + ", " + this.location.y() + ")";
        return msg;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Dinosaur getDinosaur() {
        return dinosaur;
    }

    public void setDinosaur(Dinosaur dinosaur) {
        this.dinosaur = dinosaur;
    }
}
