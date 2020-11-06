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
        GameMap map = this.map;
        Location current = map.locationOf(this);
        boolean foundLand = false;
        Random randomGenerator = new Random();

        //Get valid surrounding directions (NSEW)
        ArrayList<Location> surroundings = current.validAdjacentLocations();
        ArrayList<String> directions = new ArrayList<>();

        //randomly pick one and remove it from list
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
        int step;
        directionToMoveIn = "West";

        Location starting = map.locationOf(this);
        if (directionToMoveIn.equals("North")) {
            step = starting.y();
            while (step > map.getYRange().min()) {
                Location next = map.at(starting.x(), step);
                if ((!next.containsAnActor()) && (next.getGround() instanceof Water == false)) {
                    map.removeActor(this);
                    map.getActorLocations().move(this, next);
                    break;
                }
                step -= 1;
            }
        } else if (directionToMoveIn.equals("South")) {
            step = starting.y();
            while (step < map.getYRange().max()) {
                Location next = map.at(starting.x(), step);
                if ((!next.containsAnActor()) && (next.getGround() instanceof Water == false)) {
                    map.removeActor(this);
                    map.getActorLocations().move(this, next);
                    break;
                }
                step += 1;
            }
        } else if (directionToMoveIn.equals("East")) {
            step = starting.x();
            while (step < map.getXRange().max()) {
                Location next = map.at(step, starting.y());
                if ((!next.containsAnActor()) && (next.getGround() instanceof Water == false)) {
                    map.removeActor(this);
                    map.getActorLocations().move(this, next);
                    break;
                }
                step += 1;
            }
        } else if (directionToMoveIn.equals("West")) {
            step = starting.x();
            while (step > map.getXRange().min()) {
                Location next = map.at(step, starting.y());
                if ((!next.containsAnActor()) && (next.getGround() instanceof Water == false)) {
                    map.removeActor(this);
                    map.getActorLocations().move(this, next);
                    break;
                }
                step -= 1;
            }
        }
    }
    //public void flyTo()
}
