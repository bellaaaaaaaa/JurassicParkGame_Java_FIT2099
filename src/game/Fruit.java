package game;
import edu.monash.fit2099.engine.Food;

public class Fruit extends Food {
    int expiryTurns;

    /**
     * A constructor for fruit, where fruit extends Food and calls the super constructor method.
     */
    public Fruit(){
        super(30, 30, 15, "Fruit", 'f', true);
        this.expiryTurns = 20;
    }

    /**
     * Returns the number of turns until the fruit expires. Fruits on the ground for more than 20 turns will rot.
     * @return integer
     */
    public int getExpiryTurns() { return expiryTurns; }

    /**
     * Increases by 1 every term and sets the number of times the fruit has stayed on the ground.
     */
    public void setExpiryTurns(int expiryTurns) { this.expiryTurns = expiryTurns; }
}
