package game;

import edu.monash.fit2099.engine.*;

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
    public boolean isDead() { return isDead; }

    /**
     * Sets isDead to true if a dinosaur has had its foodLvl be 0 for 20 or more turns. Lets us now that a dinosaur should be deleted from the game.
     * @param dead
     */
    public void setDead(boolean dead) { isDead = dead; }

    /**
     * Tells us how many turns a dinosaur has been dead. Initially this is 0 and increases each turn since being unconscious for 20 or more turns.
     * @return integer
     */
    public int getNumTurnsDead() { return numTurnsDead; }

    /**
     * Sets the number of turns a dinosaur carcass has been on the ground. Since a dinosaur carcass is still edible for a certain number of turns, we need to track this.
     * @param numTurnsDead
     */
    public void setNumTurnsDead(int numTurnsDead) { this.numTurnsDead = numTurnsDead; }

    /**
     * Indicates the dinosaurs gender to let us know whether two dinosaurs can mate or not.
     * @return
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
     * @param numTurnsPregnant
     */
    public void setNumTurnsPregnant(int numTurnsPregnant) { this.numTurnsPregnant = numTurnsPregnant; }

    /**
     * Returns true or false whether or not a female dinosaur is expected to lay an egg.
     * @return boolean type
     */
    public boolean isPregnant() { return isPregnant; }

    /**
     * This is set to true for a female dinosaur once it has mated with another male dinosaur.
     * @param pregnant
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
}
