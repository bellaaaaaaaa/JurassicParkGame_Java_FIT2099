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
    boolean edible = true; // Only changes for allosaur eggs

    /**
     * Constructor for Egg which extends Food.
     * @param type Indicates whether the egg is a stegosaur or allosaur egg
     * @throws IllegalArgumentException if the egg type is neither stegosaur nor allosaur
     */
    public Egg(String type) {
        super(10, 200, 100, "Stegosaur Egg", 'e', true);
        if(type.equals("allosaur") || type.equals("stegosaur") || type.equals("agilisaurus") || type.equals("archaeopteryx")){
            this.type = type;
            this.setPrice(200);
            if(this.type.equals("allosaur")){
                this.setName("Allosaur Egg");
                this.setPrice(1000);
            } else if (this.type.equals("agilisaurus")){ // price of agilisaurus egg should be between stegosaur and allosaur price
                this.setName("Agilisaurus Egg");
                this.setPrice(500);
            } else if (this.type.equals("archaeopteryx")) { // price of archaeopteryx egg closer to agilisaurus
                this.setName("Archaeopteryx Egg");
                this.setPrice(600);
            }
        } else {
            throw new IllegalArgumentException("Incorrect egg type given");
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

        // Edible is normally true except for allosaur eggs, which we dont want to be eaten as soon as the mother allosaur lays it
        if(this.getNumTurnsOnGround() > 5){
            this.setEdible(true);
        }
        if (this.getNumTurnsOnGround() > 15){
            location.removeItem(this);
            Dinosaur baby;
            if (this.getType().equals("stegosaur")){
                baby = new Stegosaur();
            } else if (this.getType().equals("allosaur")) {
                baby = new Allosaur();
            } else if (this.getType().equals("agilisaurus")) {
                baby = new Agilisaurus(); // baby agilisaurus can be born
            } else {
                baby = new Archaeopteryx(map); // baby archaeopteryx can be born
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
            } else if (e.getType().equals("agilisaur")){
                p.setEcoPoints(p.getEcoPoints() + 500); // reward for birth of agilisaur is between that of stegosaur and allosaur
            } else if (e.getType().equals("archaeopteryx")){
                p.setEcoPoints(p.getEcoPoints() + 600); // reward for birth of archaeopteryx, slightly higher than agilisaur
            }
        }
    }

    /**
     * This method is only applicable to allosaur eggs
     * @return a boolean indicating whether the egg can be eaten
     */
    public boolean isEdible() {
        return edible;
    }

    /**
     * Set to false initially for allosaur eggs to ensure that an adult allosaur doesnt automatically eat an egg if it has just lain one
     * @param edible a boolean indicating whether the egg can be eaten
     */
    public void setEdible(boolean edible) {
        this.edible = edible;
    }
}
