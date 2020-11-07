package game;
import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is a type of carnivorous flying dinosaur which is able to traverse water.
 */
public class Archaeopteryx extends Dinosaur{
    /**
     * Instantiates an Archaeopteryx, abbreviation (ax) and makes use of the constructor in the super class
     * @param map the map within the Archaeopteryx resides in
     */
    public Archaeopteryx(GameMap map) {
        super("archaeopteryx", 'r', 100);
        this.setGender(this.randomiseGender());
        this.setStage("adult");
        this.map = map;
        this.isCarnivorous = true;
    }

    /**
     * This method randomises the direction an (ax) may fly in. It ensures that the (ax) flies in a VALID location
     * ie. If an (ax) is at the bottom of the map, it ensures it does not fly further south.
     * Then the (ax) will fly in the specified direction
     * If there is no direction for the (ax) to fly, say the map is made ENTIRELY of water, then the (ax) will stay at its current position
     */
    public void flyAround() {
        //Get location of arch
        Location current = this.map.locationOf(this);
        boolean foundLand = false;
        Random randomGenerator = new Random();

        //Get valid surrounding directions (NSEW)
        ArrayList<Location> surroundings = current.validAdjacentLocations();
        ArrayList<String> directions = new ArrayList<>();

        //Add valid directions
        for (Location l : surroundings) {
            if (l.y() == current.y() - 1) {
                directions.add("North");
            } else if (l.y() == current.y() + 1) {
                directions.add("South");
            } else if (l.x() == current.x() + 1) {
                directions.add("East");
            } else if (l.x() == current.x() - 1) {
                directions.add("West");
            }
        }
        int index = randomGenerator.nextInt(directions.size());
        String directionToMoveIn = directions.get(index);

        Location starting = this.map.locationOf(this);
        if (directionToMoveIn.equals("North")) {
            flyTo(directionToMoveIn, starting.y(), -1, this.map.getYRange().min(), starting.x());
        } else if (directionToMoveIn.equals("South")) {
            flyTo(directionToMoveIn, starting.y(), 1, this.map.getYRange().max(), starting.x());
        } else if (directionToMoveIn.equals("East")) {
            flyTo(directionToMoveIn, starting.x(), 1, this.map.getXRange().max(), starting.y());
        } else if (directionToMoveIn.equals("West")) {
            flyTo(directionToMoveIn, starting.x(), -1, this.map.getXRange().min(), starting.y());
        }
    }

    /**
     * This function allows an (ax) to fly in a specific direction, ensuring it does not fly out of the bounds of the gamemap.
     * @param directionToMoveIn either North, South, East or West
     * @param step the current relevant coordinate (either x or y) that the (ax) is traversing
     * @param increment this could be either +1 to move south or east or -1 to move west or north
     * @param rangeLimit the x or y bounds based on the gamemap
     * @param constCo the coordinate which remains constant during the (ax) flight. Eg if flying north, the x coordinate remains constant while the y coordinate changes
     */
    public void flyTo(String directionToMoveIn, Integer step, Integer increment, Integer rangeLimit, Integer constCo){
        if(directionToMoveIn.equals("East") || directionToMoveIn.equals("West")){
            while (step != rangeLimit) {
                Location next = this.map.at(step, constCo);
                if(checkLocation(next)){
                    break;
                }
                step += increment;
            }
        } else if (directionToMoveIn.equals("North") || directionToMoveIn.equals("South")){
            while (step != rangeLimit) {
                Location next = this.map.at(constCo, step);
                if(checkLocation(next)){
                    break;
                }
                step += increment;
            }
        }
    }

    /**
     * This function checks whether a location should be traversed by an archaeopteryx (ax)
     * @param next the following location the (ax) may want to enter
     * @return a boolean indicating whether the (ax) can enter the given location
     */
    public boolean checkLocation(Location next){
        // The idea is that (ax) can land on anything except water eg. perch on trees or walls etc.
        if ((!next.containsAnActor()) && (next.getGround() instanceof Water == false)) {
            this.map.removeActor(this);
            this.map.getActorLocations().move(this, next);
            return true;
        }
        return false;
    }
}
