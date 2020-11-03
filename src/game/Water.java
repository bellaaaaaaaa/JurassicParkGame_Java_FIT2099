package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * A class represents water which actor cannot pass through
 */
public class Water extends Ground {
    /**
     * Constructor for water class extends from ground
     */
    public Water() {
        super('~');
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    @Override
    public boolean blocksThrownObjects() {
        return true;
    }
}
