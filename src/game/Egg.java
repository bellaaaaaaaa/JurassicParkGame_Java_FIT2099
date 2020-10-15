package game;

import edu.monash.fit2099.engine.Food;

public class Egg extends Food {
    String type;
    int numTurnsOnGround;

    public Egg(int foodLvl, int price, int gainPoints, String name, char displayChar, boolean portable, String type) {
        super(foodLvl, price, gainPoints, name, displayChar, portable);
        this.type = type;
    }

    public int getNumTurnsOnGround() {
        return numTurnsOnGround;
    }

    public void setNumTurnsOnGround(int numTurnsOnGround) {
        this.numTurnsOnGround = numTurnsOnGround;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
