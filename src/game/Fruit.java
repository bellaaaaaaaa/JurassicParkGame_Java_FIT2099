package game;
import edu.monash.fit2099.engine.Food;

/**
 * This class represents fruit which can be bought from the vending machine or dropped from a tree
 * Fruit can be fed to dinosaurs
 */
public class Fruit extends Food {
    int expiryTurns;

    /**
     * A constructor for fruit, where fruit extends Food and calls the super constructor method.
     */
    public Fruit(){
        super(30, 30, 15, "Fruit", 'f', true);
        this.expiryTurns = 20;
        this.setPrice(30);
    }

    /**
     * Returns the number of turns until the fruit expires. Fruits on the ground for more than 20 turns will rot and disappear
     * @return integer indicating how many turns it takes for the fruit to expire
     */
    public int getExpiryTurns() { return expiryTurns; }

    /**
     * Increases by 1 every term and sets the number of times the fruit has stayed on the ground.
     * @param expiryTurns the new updated expiry turns for a current fruit
     */
    public void setExpiryTurns(int expiryTurns) { this.expiryTurns = expiryTurns; }
}
