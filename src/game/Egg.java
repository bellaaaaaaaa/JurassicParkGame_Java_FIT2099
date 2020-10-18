package game;

import edu.monash.fit2099.engine.Food;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;

/**
 * This Egg class inherits methods and behaviours from Food
 * Eggs represent the offspring of Stegosaurs and Allosaurs.
 * If not eaten, an egg will hatch after some time and a baby dinosaur of the same type as the egg will be born
 */
public class Egg extends Food {
    String type;
    int numTurnsOnGround;

    /**
     * Constructor for Egg which extends Food.
     * @param type Indicates whether the egg is a stegosaur or allosaur egg
     * @throws IllegalArgumentException if the egg type is neither stegosaur nor allosaur
     */
    public Egg(String type) {
        super(10, 200, 100, "Stegosaur Egg", 'e', true);
        if(!type.equals("allosaur") || !type.equals("stegosaur")){
            throw new IllegalArgumentException("Eggs must be of type stegosaur or allosaur");
        } else {
            this.type = type;
            this.setPrice(200);
            if(this.type.equals("allosaur")){
                this.setName("Allosaur Egg");
                this.setPrice(1000);
            }
        }
    }

    /**
     * Indicates how long the egg has been on the ground since lain.
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
     * @return a string indicating the type of egg
     */
    public String getType() { return type; }

    /**
     * System will set the type of egg depending on the type of dinosaur that laid it
     * @param type string type which will match the type of the mother and father dinosaur
     */
    public void setType(String type) { this.type = type; }

    /**
     * This method checks whether a specific egg is ready to hatch based on the number or turns its been since it was lain,
     * If an egg is ready to hatch, it will be removed from a locations items and a baby dinosaur will be born at that location
     * If an egg has been hatched, the player is rewarded with some eco points
     * @param location The current location in which the egg is sitting
     * @param map The GameMap which the egg and location belong to
     * @return A boolean indicating whether an egg has been hatched or not
     */
    public boolean anyEggsToHatch(Location location, GameMap map){
        this.setNumTurnsOnGround(this.getNumTurnsOnGround() + 1);
        if (this.getNumTurnsOnGround() > 15){
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
            eggsHatchRewards(map, this);
            location.removeItem(this); // Get rid of the eggshells
            return true;
        } else {
            return false;
        }
    }

    /**
     * This is a private method which rewards players across the map with a number of eco points based on the type of egg that hatched
     * @param map The GameMap in which the players and the hatched egg belong to
     * @param e The egg which has just hatched
     */
    private void eggsHatchRewards(GameMap map, Egg e){
        ArrayList<Player> players = map.getPlayers();
        for(Player p : players){
            if(e.getType().equals("stegosaur")){
                p.setEcoPoints(p.getEcoPoints() + 100);
            } else if (e.getType().equals("allosaur")){
                p.setEcoPoints(p.getEcoPoints() + 1000);
            }
        }
    }
}
