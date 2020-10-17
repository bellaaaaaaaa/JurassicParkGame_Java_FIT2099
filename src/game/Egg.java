package game;

import edu.monash.fit2099.engine.Food;
import edu.monash.fit2099.engine.Location;

public class Egg extends Food {
    String type;
    int numTurnsOnGround;

    /**
     * Constructor for Egg which extends Food.
     * @param type Indicates whether the egg is a stegosaur or allosaur egg
     */
    public Egg(String type) {
        super(10, 200, 100, "Stegosaur Egg", 'e', true);
        this.type = type;
        this.setPrice(200);
        if(this.type.equals("allosaur")){
            this.setName("Allosaur Egg");
            this.setPrice(1000);
        }
    }

    /**
     * Indicates how long the egg has been on the ground since lain
     * @return integer
     */
    public int getNumTurnsOnGround() { return numTurnsOnGround; }

    /**
     * Changes the number of turns an egg has been on the ground
     * @param numTurnsOnGround the new number of turns the egg has been on the ground
     */
    public void setNumTurnsOnGround(int numTurnsOnGround) { this.numTurnsOnGround = numTurnsOnGround; }

    /**
     * Returns whether the egg is a stegosaur or allosaur type
     * @return string
     */
    public String getType() { return type; }

    /**
     * System will set the type of egg depending on the type of dinosaur that laid it.
     * @param type string type
     */
    public void setType(String type) { this.type = type; }

    public void manageEggs(Location location){
        this.setNumTurnsOnGround(this.getNumTurnsOnGround() + 1);
        if (this.getNumTurnsOnGround() > 10){
            location.removeItem(this);
            Dinosaur baby;
            if (this.getType().equals("stegosaur")){
                baby = new Stegosaur();
            } else {
                baby = new Allosaur();
            }
            baby.setStage("baby");
            baby.setFoodLvl(10);
            location.addActor(baby);
        }
    }
}
