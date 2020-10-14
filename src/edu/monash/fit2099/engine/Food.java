package edu.monash.fit2099.engine;

public class Food extends Item {
    int foodLvl;
    int price;
    int gainPoints;

    public Food(int foodLvl, int price, int gainPoints, String name, char displayChar, boolean portable){
        super(name, displayChar, portable);
        this.foodLvl = foodLvl;
        this.price = price;
        this.gainPoints = gainPoints;
    }
}
