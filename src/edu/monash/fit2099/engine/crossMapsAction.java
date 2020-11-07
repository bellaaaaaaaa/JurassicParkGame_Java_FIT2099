package edu.monash.fit2099.engine;

import java.util.ArrayList;

/**
 * This action allows for players to cross between maps in the World.
 * If they are at the Northern border of Map1, they will be able to cross to the Southern most border of Map2
 * And vice versa.
 */
public class crossMapsAction extends Action {
    World world;
    ArrayList<GameMap> worldMaps;

    /**
     * This method checks whether a player is at the Northern border of Map1 or the Southern border of Map2. If so the player
     * will then be able to cross between these maps.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string type indicating the action performed
     */
    @Override
    public String execute(Actor actor, GameMap map) {
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
        return menuDescription(actor);
    }


    /**
     * An string denoting the type of action a player can perform
     * @param actor The actor performing the action.
     * @return a string type
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Cross over to the other map";
    }

    /**
     * This method retrieves the world in which the player resides with
     * @return an instance of the World class
     */
    public World getWorld() {
        return world;
    }

    /**
     * This method sets the world that contain two maps which a player can traverse
     * @param world an instance of the World class
     */
    public void setWorld(World world) {
        this.world = world;
    }

    /**
     * This method returns an arraylist of gameMaps that the player can traverse across
     * @return an arraylist of gameMaps
     */
    public ArrayList<GameMap> getWorldMaps() {
        return worldMaps;
    }

    /**
     * This method sets all the gamemaps contained within the specified world instance variable
     * @param worldMaps an array list of GameMaps belonging to this world
     */
    public void setWorldMaps(ArrayList<GameMap> worldMaps) {
        this.worldMaps = worldMaps;
    }
}
