package game;

import edu.monash.fit2099.engine.Food;

/**
 * Hay is edible and can be harvested from grass. Hay may be fed to stegosaurs
 */
public class Hay extends Food {
    /**
     * This method constructs and instantiates a new hay instance every time a player harvests grass or purchases hay from the vending machine
     */
    public Hay() {
        super(20, 20, 10, "hay", 'h', true);
        this.setPrice(20);
    }
}
