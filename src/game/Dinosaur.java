package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

public class Dinosaur extends Actor {
    private Behaviour behaviour;
    int foodLvl = 50;
    int maxFoodLvl = 100;
    int numTurnsUnconscious = 0;
    boolean isUnconscious = false;
    boolean isDead = false;
    int numTurnsDead = 0;
    String gender;
    int numTurnsPregnant = 0;
    boolean isPregnant = false;
    String stage;
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    public int getFoodLvl() {
        return foodLvl;
    }

    public void setFoodLvl(int foodLvl) {
        if (foodLvl >= maxFoodLvl){
            this.foodLvl = maxFoodLvl;
        } else {
            this.foodLvl = foodLvl;
        }
    }

    /**
     * Once the dinosaurs food level is below a certain number it turns unconscious. This tracks how many turns the dinosaur has been unconscious. Once this hits 0, the dinosaur will die.
     * @return integer
     */
    public int getNumTurnsUnconscious() {
        return numTurnsUnconscious;
    }

    /**
     * Sets the number of turns since the dinosaur fell unconscious. Increases by 1 each turn the dinosaur has been unconscious.
     * @param numTurnsUnconcious integer type
     */
    public void setNumTurnsUnconscious(int numTurnsUnconcious) {
        this.numTurnsUnconscious = numTurnsUnconcious;
    }

    /**
     * A Boolean which indicates whether a dinosaur is unconscious or not. Will help us know when a dinosaur will die.
     * @return boolean type.
     */
    public boolean isUnconscious() {
        return isUnconscious;
    }

    /**
     * Sets isUnconscious to true once a dinosaurs food level hits 0.
     * @param unconscious boolean type
     */
    public void setUnconscious(boolean unconscious) {
        isUnconscious = unconscious;
    }

    /**
     * Returns a boolean indicating whether a dinosaur is dead.
     * @return boolean type.
     */
    public boolean isDead() {
        if(this.hitPoints <= 0){
            this.setDead(true);
        }
        return isDead;
    }

    /**
     * Sets isDead to true if a dinosaur has had its foodLvl be 0 for 20 or more turns. Lets us now that a dinosaur should be deleted from the game.
     * @param dead boolean to indicate if a dinosaur is dead or not.
     */
    public void setDead(boolean dead) { isDead = dead; }

    /**
     * Tells us how many turns a dinosaur has been dead. Initially this is 0 and increases each turn since being unconscious for 20 or more turns.
     * @return integer
     */
    public int getNumTurnsDead() { return numTurnsDead; }

    /**
     * Sets the number of turns a dinosaur carcass has been on the ground. Since a dinosaur carcass is still edible for a certain number of turns, we need to track this.
     * @param numTurnsDead updated number of times carcass has remained on ground
     */
    public void setNumTurnsDead(int numTurnsDead) { this.numTurnsDead = numTurnsDead; }

    /**
     * Indicates the dinosaurs gender to let us know whether two dinosaurs can mate or not.
     * @return string type indicating dinosaur gender
     */
    public String getGender() { return gender; }

    /**
     * This is randomised and theres a 50% chance of the dinosaur either being male or female.
     * @param gender integer type where 1 = female and 2 = male
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * Used on female dinosaurs indicating how long its been since breeding with another dinosaur. Once this hits 10, the female dinosaur will lay an egg.
     * @return integer type
     */
    public int getNumTurnsPregnant() { return numTurnsPregnant; }

    /**
     * Increases by 1 each turn since the female dinosaur has mated
     * @param numTurnsPregnant new value to be set to
     */
    public void setNumTurnsPregnant(int numTurnsPregnant) { this.numTurnsPregnant = numTurnsPregnant; }

    /**
     * Returns true or false whether or not a female dinosaur is expected to lay an egg.
     * @return boolean type
     */
    public boolean isPregnant() { return isPregnant; }

    /**
     * This is set to true for a female dinosaur once it has mated with another male dinosaur.
     * @param pregnant boolean type
     */
    public void setPregnant(boolean pregnant) { isPregnant = pregnant; }

    /**
     * This indicates the stage of life a dinosaur is currently at. Either baby or adult.
     * @return String type
     */
    public String getStage() { return stage; }

    /**
     * Sets the stage of the dinosaur. This is initially adult for the starting herd of the game. But for a hatching dinosaur egg, the dinosaur born will have its stage set as baby.
     * @param stage String type.
     */
    public void setStage(String stage) { this.stage = stage; }

    public static boolean breed(Dinosaur d1, Dinosaur d2){
        if(d1.getFoodLvl() > 50 && d2.getFoodLvl() > 50 && (!d1.getGender().equals(d2.getGender())) && (d1.name.equals(d2.name))){
            if(d1.getGender().equals("female")){
                d1.setNumTurnsPregnant(d1.getNumTurnsPregnant() + 1);
                d1.setPregnant(true);
            } else if (d2.getGender().equals("female")){
                d2.setNumTurnsPregnant(d2.getNumTurnsPregnant() + 1);
                d2.setPregnant(true);
            }
            return true;
        }
        return false;
    }

    public static void newTick(Dinosaur d, Location l, GameMap gameMap){
        d.setFoodLvl(d.getFoodLvl() - 1);
        int currentFoodLvl = d.getFoodLvl();
        ArrayList<Location> adjacents = l.validAdjacentLocations();
        if (currentFoodLvl > 0) {

            // If stegosaur getting hungry
            if (d.getFoodLvl() <= 10) {
                System.out.println(d.name + " at (" + l.x() + ", " + l.y() + ") is getting hungry!");
            }

            // If dinosaur can breed
            if (d.getFoodLvl() > 50) {
                // Check locations for breedable stegosaurs
                for (Location adj : adjacents) {
                    if (l.containsAnActor()) {
                        Actor a = adj.getActor();
                        if (a instanceof Dinosaur) {
                            boolean bred = breed((Dinosaur) a, d);
                            if (bred) {
                                break;
                            }
                        }
                    }
                }
            }

            // Potentially lay egg
            if (d.isPregnant()) {
                layEgg(d, l);
            }

            // Allosaur Attacks Stegosaur
            if (d instanceof Allosaur) {
                boolean hasKilled = false;
                for (Location adj : adjacents) {
                    if (hasKilled) {
                        break; // To only eat 1 dead dinosaur per turn.
                    }
                    if (adj.containsAnActor()) {
                        if (adj.getActor() instanceof Stegosaur) {
                            Stegosaur s = (Stegosaur) adj.getActor();
                            while (!s.isDead()) {
                                int[] array = {50, 100}; // Allosaur kills stegosaur in 1 or 2 hits.
                                Random generator = new Random();
                                int randomIndex = generator.nextInt(array.length);
                                int hitPoints = array[randomIndex];
                                s.hurt(hitPoints);
                            }
                            // Jumps here if stegosaur already dead or if allosaur attacked and killed stegosaur.
                            ((Allosaur) d).eatCarcass();
                            gameMap.removeActor(s);
                            hasKilled = true;
                        } else if (adj.getActor() instanceof Allosaur) {
                            Allosaur ar2 = (Allosaur) adj.getActor();
                            if (ar2.isDead()) {
                                // Eat dead allosaur
                                ((Allosaur) d).eatCarcass();
                                gameMap.removeActor(ar2);
                            }
                        }
                    }
                }
            }
        }
        // For unconscious dinosaurs
        else {
            d.setUnconscious(true);
            d.setNumTurnsUnconscious(d.getNumTurnsUnconscious() + 1);
            if (d.getNumTurnsUnconscious() >= 20){
                d.setDead(true);
                d.setNumTurnsDead(d.getNumTurnsDead() + 1);
                if(d.getNumTurnsDead() >= 20){
                    gameMap.removeActor(d);
                    gameMap.at(l.x(), l.y()).setGround(new Dirt());
                }
            }
        }
    }

    private static void layEgg(Dinosaur d, Location l){
        d.setNumTurnsPregnant(d.getNumTurnsPregnant() + 1);
        if (d.getNumTurnsPregnant() >= 10){
            Egg e = new Egg(d.name);
            l.addItem(e); // Lays egg at current location
        }
    }
}
