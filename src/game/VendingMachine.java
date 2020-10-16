package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

public class VendingMachine extends Ground {
    /**
     * Constructor.
     *
     */
    public VendingMachine() {
        super('V');
    }

    /**
     *
     * @param actor the Actor to check. Only players can enter a vending machine as they can purchase from it. Everything else can't.
     * @return boolean type.
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if(actor instanceof Player){
            return true;
        } else {
            return false;
        }
    }
}
