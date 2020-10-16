package game;

import edu.monash.fit2099.engine.Food;

public class Hay extends Food {
    public Hay() {
        super(20, 20, 10, "hay", 'h', true);
        this.setPrice(20);
    }
}
