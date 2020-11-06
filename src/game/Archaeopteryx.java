package game;
import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

public class Archaeopteryx extends Dinosaur{
    public Archaeopteryx(GameMap map) {
        super("archaeopteryx", 'r', 100);
        this.setGender(this.randomiseGender());
        this.setStage("adult");
        this.map = map;
    }

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

    public boolean checkLocation(Location next){
        if ((!next.containsAnActor()) && (next.getGround() instanceof Water == false)) {
            this.map.removeActor(this);
            this.map.getActorLocations().move(this, next);
            return true;
        }
        return false;
    }
}
