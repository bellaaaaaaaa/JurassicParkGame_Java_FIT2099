package game;
import edu.monash.fit2099.engine.Food;

public class Fruit extends Food {
    int expiryTurns;

    public Fruit(){
        super(30, 30, 15, "Fruit", 'f', true);
        this.expiryTurns = 20;
    }
    public int getExpiryTurns() {
        return expiryTurns;
    }

    public void setExpiryTurns(int expiryTurns) {
        this.expiryTurns = expiryTurns;
    }
}
