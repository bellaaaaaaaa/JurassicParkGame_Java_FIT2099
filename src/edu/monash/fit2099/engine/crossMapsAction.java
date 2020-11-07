package edu.monash.fit2099.engine;

import java.util.ArrayList;

public class crossMapsAction extends Action {
    World world;
    ArrayList<GameMap> worldMaps;

    @Override
    public String execute(Actor actor, GameMap map) {
        String msg = "";

        GameMap mapOne = this.getWorldMaps().get(0);
        GameMap mapTwo = this.getWorldMaps().get(1);
        Location current = world.actorLocations.locationOf(actor);
        Location newLocation;
        // If player at northern border of map 1
        if(world.actorLocations.locationOf(actor).map().equals(mapOne) && world.actorLocations.locationOf(actor).y() == 0){
            // Remove actor from map1
            mapOne.removeActor(actor);

            // Same x at southern border of map2
            newLocation = mapTwo.at(current.x(), mapTwo.getYRange().max());
            if(newLocation.canActorEnter(actor)){
                mapOne.removeActor(actor);
                mapTwo.addActor(actor, newLocation);
                world.actorLocations = mapTwo.actorLocations;
                world.playersMap = world.actorLocations.locationOf(actor).map();
            }

            // If player at southern border of map2
        } else if (world.actorLocations.locationOf(actor).map().equals(mapTwo) && world.actorLocations.locationOf(actor).y() == 24){
            // Remove actor from map2
            mapTwo.removeActor(actor);

            // Same x at northern border of map1
            newLocation = mapOne.at(current.x(), mapTwo.getYRange().min());
            if (newLocation.canActorEnter(actor)){
                mapTwo.removeActor(actor);
                mapOne.addActor(actor, newLocation);
                world.actorLocations = mapOne.actorLocations;
                world.playersMap = world.actorLocations.locationOf(actor).map();
            }
        }
        return msg;
    }


    @Override
    public String menuDescription(Actor actor) {
        return "Cross over to other map";
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public ArrayList<GameMap> getWorldMaps() {
        return worldMaps;
    }

    public void setWorldMaps(ArrayList<GameMap> worldMaps) {
        this.worldMaps = worldMaps;
    }
}
