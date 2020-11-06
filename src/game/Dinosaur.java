package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class is abstract and inherits and overrides some methods from the Actor class.
 * This class includes all shared behaviours and functions between Stegosaur and Allosaur instances
 */
public abstract class Dinosaur extends Actor {
    private Behaviour behaviour;
    GameMap map;
    int foodLvl = 50;
    int maxFoodLvl = 100;
    int waterLvl = 50;
    int maxWaterLvl = 100;
    int numTurnsUnconscious = 0;
    boolean isUnconscious = false;
    boolean isDead = false;
    int numTurnsDead = 0;
    String gender;
    int numTurnsPregnant = 0;
    boolean isPregnant = false;
    String stage = "adult"; // default
    int numTurnsAlive = 0; // Only needed for babies for now, assuming adult dinosaurs never die unless killed or starving.
    private int carcassFoodLvl = 50;

    /**
     * This calls the Actor class constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        behaviour = new WanderBehaviour();
    }

    /**
     * The current foodLvl of the dinosaur
     * @return a integer type representing this.
     */
    public int getFoodLvl() {
        return foodLvl;
    }

    /**
     * This method increases (eats food) or decreases the foodLvl of a dinosaur. This also is how we decrement the dinosaurs foodLvl by 1 each turn.
     * @param foodRaisingPoints an integer representing a dinosaurs new food level
     */
    public void setFoodLvl(int foodRaisingPoints) {
        int checkLevel = foodRaisingPoints + this.getFoodLvl();
        this.foodLvl = Math.min(checkLevel, maxFoodLvl);
    }
    /**
     * The current waterLvl of the dinosaur
     * @return a integer type representing this.
     */
    public int getWaterLvl() {
        return waterLvl;
    }

    /**
     * This method increases (drinks water) or decreases the waterLvl of a dinosaur. This also is how we decrement the dinosaurs waterLvl by 1 each turn.
     * @param waterRaisingPoints an integer representing a dinosaurs new water level
     */
    public void setWaterLvl(int waterRaisingPoints) {
        int checkLevel = waterRaisingPoints + this.getWaterLvl();
        this.waterLvl = Math.min(checkLevel, maxWaterLvl);
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
     * This method ticks all the dinosaurs across the gamemap. This includes ageing a baby dinosaur, setting the new food levels, breeding, attacks etc.
     * @param d The current dinosaur being ticked
     * @param l The location of the dinosaur at the current moment
     * @param gameMap The gameMap in which the dinosaur resides in
     */
    public static void newTick(Dinosaur d, Location l, GameMap gameMap){
        int currentFoodLvl = d.getFoodLvl();
        d.setFoodLvl(currentFoodLvl - 1);
        int currentWaterLvl = d.getWaterLvl();
        d.setWaterLvl(currentWaterLvl - 1);
        ArrayList<Location> adjacents = l.validAdjacentLocations();

        if(d instanceof Archaeopteryx){
            ((Archaeopteryx) d).flyAround();
        }

        // Baby dinosaurs grow up
        if(d.getStage().equals("baby")){
            d.babyDinosaurGrows();
        }

        if (currentFoodLvl > 0 && currentWaterLvl> 0) {


            // If dinosaur getting hungry
            if (d.getFoodLvl() <= 10) {
                System.out.println(d.name + " at (" + l.x() + ", " + l.y() + ") is getting hungry!");
            }
            if (d.getWaterLvl() <= 10) {
                System.out.println(d.name + " at (" + l.x() + ", " + l.y() + ") is getting thirsty!");
            }

            if (l.getGround() instanceof Water){
                d.setWaterLvl(d.getWaterLvl() + 99);
            }

            // Stegosaurs/Agilisaurus eats the grass it grazes. Ground resumes to dirt
            if((d instanceof Stegosaur) || d instanceof Agilisaurus){
                if(l.getGround() instanceof Grass){
                    d.setFoodLvl(d.getFoodLvl() + 5);
                    l.setGround(new Dirt());
                }
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
                // Eat an egg currently on the ground
                for (Item i : l.getItems()){
                    if (i instanceof Egg){
                        Egg e = (Egg) i;
                        if(e.isEdible()){
                            d.setFoodLvl(d.getFoodLvl() + e.getFoodLvl());
                        }
                    }
                }

                boolean hasKilled = false;
                for (Location adj : adjacents) {
                    if (hasKilled) {
                        break; // To only eat 1 dead dinosaur per turn.
                    }
                    if (adj.containsAnActor()) {
                        if ((adj.getActor() instanceof Stegosaur) || (adj.getActor() instanceof Agilisaurus)) {
                            Dinosaur dinoToAttack = (Dinosaur) adj.getActor();
                            if(dinoToAttack instanceof Stegosaur){
                                dinoToAttack = (Stegosaur) adj.getActor();
                            } else if (dinoToAttack instanceof Agilisaurus){
                                dinoToAttack = (Agilisaurus) adj.getActor();
                                dinoToAttack.setDead(true); // Agilisaurus should be killed in one hit
                            }
                            while (!dinoToAttack.isDead()) {
                                int[] array = {50, 100}; // Allosaur kills stegosaur in 1 or 2 hits.
                                Random generator = new Random();
                                int randomIndex = generator.nextInt(array.length);
                                int hitPoints = array[randomIndex];
                                dinoToAttack.hurt(hitPoints);dinoToAttack.setDead(true);
                            }
                            // Jumps here - Eat carcass of dead stegosaur/agilisaur
                            ((Allosaur) d).eatCarcass(dinoToAttack.getCarcassFoodLvl());
                            gameMap.removeActor(dinoToAttack);
                            hasKilled = true;
                        } else if (adj.getActor() instanceof Allosaur) {
                            Allosaur ar2 = (Allosaur) adj.getActor();
                            if (ar2.isDead()) {
                                // Eat dead allosaur
                                ((Allosaur) d).eatCarcass(ar2.getCarcassFoodLvl());
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

                // Carcass of dead dinosaur still remains for 20 turns
                if(d.getNumTurnsDead() >= 20){
                    gameMap.removeActor(d);
                    gameMap.at(l.x(), l.y()).setGround(new Dirt());
                }
            }
        }
    }

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

    /**
     * This method checks whether both dinosaurs meet the breeding requirements. If they do, the female will get pregnant and eventually lay an egg of the same type.
     * @param d1 A dinosaur of the same type as d2
     * @param d2 A dinosaur of the same type as d1
     * @return A boolean to indicate whether breeding was successful or not.
     */
    public static boolean breed(Dinosaur d1, Dinosaur d2){
        if(d1.getFoodLvl() > 50 && d2.getFoodLvl() > 50 && (!d1.getGender().equals(d2.getGender())) && (d1.name.equals(d2.name)) && (!d1.getStage().equals("baby")) && (!d2.getStage().equals("baby"))){
            if(d1.getGender().equals("female")){
                d1.setNumTurnsPregnant(d1.getNumTurnsPregnant() + 1);
                d1.setPregnant(true);
            } else if (d2.getGender().equals("female")){
                d2.setNumTurnsPregnant(d2.getNumTurnsPregnant() + 1);
                d2.setPregnant(true);
            }
            System.out.println("A pair of " + d1.name + "have just bred");
            return true;
        }
        return false;
    }

    /**
     * Sets isDead to true if a dinosaur has had its foodLvl be 0 for 20 or more turns. Lets us now that a dinosaur should be deleted from the game.
     * @param dead boolean to indicate if a dinosaur is dead or not.
     */
    public void setDead(boolean dead) {
        isDead = dead;
        this.hitPoints = 0;
        // Change display char for dead dinosaurs
        this.displayChar = 'X';
    }

    /**
     * This method checks that if a dinosaur has been pregnant for more than 10 turns, it will lay an egg of its same type at its current location.
     * @param d The pregnant dinosaur
     * @param l The current location of the dinosaur where it will lay its egg
     */
    private static void layEgg(Dinosaur d, Location l){
        d.setNumTurnsPregnant(d.getNumTurnsPregnant() + 1);
        if (d.getNumTurnsPregnant() >= 10){
            Egg e = new Egg(d.name);
            if(e.getType().equals("allosaur")){
                e.setEdible(false);
            }
            l.addItem(e); // Lays egg at current location
        }
    }

    /**
     * This method returns the number of turns a dinosaur has been alive. This is only relevant for baby dinosaurs and so the starting herd of stegosaurs ignore this attribute entirely.
     * @return an integer indicating how long a dinosaur has been alive. Only applicable to baby dinosaurs which have hatched from eggs.
     */
    public int getNumTurnsAlive() {
        return numTurnsAlive;
    }

    /**
     * This method increases each turn since a baby dinosaur was born. This attribute only applies to baby dinosaurs which hatched from an egg.
     * @param numTurnsAlive an incremental integer which increases by 1 each turn
     */
    public void setNumTurnsAlive(int numTurnsAlive) {
        this.numTurnsAlive = numTurnsAlive;
    }

    /**
     * This method checks if a current baby dinosaur is >= 30 turns in age. If so, this baby grows into an adult dinosaur.
     */
    private void babyDinosaurGrows(){
        this.setNumTurnsAlive(this.getNumTurnsAlive() + 1);
        if(this.getNumTurnsAlive() >= 30){
            this.setStage("adult");
        }
    }

    /**
     * This method randomises the given gender for a stegosaur or allosaur instance
     * @return a string of either male or female
     */
    public String randomiseGender(){
        Random rand = new Random();
        int num = rand.nextInt(2) + 1; //either 1 or 2
        if (num == 1){
            return "female";
        } else {
            return "male";
        }
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    public int getCarcassFoodLvl() {
        return carcassFoodLvl;
    }

    public void setCarcassFoodLvl(int carcassFoodLvl) {
        this.carcassFoodLvl = carcassFoodLvl;
    }

    /**
     * Figure out what to do next.
     *
     * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
     * just stands there.  That's boring.
     *
     * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
    }
}
