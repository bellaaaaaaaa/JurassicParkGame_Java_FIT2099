package game;

import edu.monash.fit2099.engine.*;

/**
 * A class which inherits from and represents a type of Dinosaur available in the game
 */
public class Allosaur extends Dinosaur {

    /**
     * This constructor instantiates a new Allosaur by calling the Dinosaur class constructor which in turn calls the Actor class constructor.
     */
    public Allosaur() {
        super("allosaur", 'a', 100);
        this.setGender(this.randomiseGender());

    }
}
