package edu.monash.fit2099.engine;

import game.*;

import java.util.*;


/**
 * Class representing a location in the game map.  This includes keeping track of exits,
 * character representation, terrain type, and other game data.
 */
public class Location implements Printable {

	private GameMap map;
	private int x;
	private int y;

	private List<Item> items = new ArrayList<>();
	private Ground ground;
	private List<Exit> exits = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 * Locations know which map they are part of, and where.
	 *
	 * @param map the map that contains this location
	 * @param x x coordinate of this location within the map
	 * @param y y coordinate of this location within the map
	 */
	public Location(GameMap map, int x, int y) {
		this.map = map;
		this.x = x;
		this.y = y;
	}

	/**
	 * Accessor for the Location's map.
	 *
	 * @return the map that contains this Location
	 */
	public GameMap map() {
		return map;
	}

	/**
	 * Accessor for the x coordinate.
	 *
	 * @return the x coordinate
	 */
	public int x() {
		return x;
	}

	/**
	 * Accessor for the y coordinate.
	 *
	 * @return the y coordinate
	 */
	public int y() {
		return y;
	}

	/**
	 * Returns a list of items at this location.
	 *
	 * This list uses Collections.unmodifiableList() to prevent privacy leaks.
	 *
	 * @return an unmodifiable List of items at this location
	 */
	public List<Item> getItems() {
		return Collections.unmodifiableList(items);
	}

	/** Add an item to this location.
	 *
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		Objects.requireNonNull(item);
		items.add(item);
	}

	/**
	 * Remove an item from this location, if it is here.
	 *
	 * @param item the item to remove
	 */
	public void removeItem(Item item) {
		Objects.requireNonNull(item);
		items.remove(item);
	}

	/**
	 * Accessor for the ground at this location.
	 * @return the ground at this location
	 */
	public Ground getGround() {
		return ground;
	}

	/**
	 * Set the Ground type at the given Location
	 * 
	 * @param ground Ground type to set
	 */
	public void setGround(Ground ground) {
		this.ground = ground;
	}
	
	/**
	 * Called once per turn, so that Locations can experience the passage time. If that's
	 * important to them.
	 */
	public void tick() {
		ground.tick(this);
		for(Item item :  new ArrayList<>(items)) {
			item.tick(this);

			// Manage eggs
			if (item instanceof Egg){
				Egg e = (Egg) item;
				e.anyEggsToHatch(this, map); //Check for eggs to hatch, if yes, a baby dinosaur is born.
			}

			// Tick fruits
			if (item instanceof Fruit){
				int expiry = ((Fruit) item).getExpiryTurns();
				expiry -= 1;

				if(expiry == 0){
					this.removeItem(item);
				} else {
					((Fruit) item).setExpiryTurns(expiry);
				}
			}
		}
	}

	public Location getNorthEast(){ return map.at(x+1, y - 1); }
	public Location getNorthWest(){ return map.at(x-1, y - 1); }
	public Location getSouthEast(){ return map.at(x+1, y + 1); }
	public Location getSouthWest(){ return map.at(x-1, y + 1); }
	/**
	 * This method returns the location instance directly north of the current one
	 * @return a location instance
	 */
	public Location getNorth(){ return map.at(x, y - 1); }

	/**
	 * This method returns the location instance directly south of the current one
	 * @return a location instance
	 */
	public Location getSouth(){ return map.at(x, y+1); }

	/**
	 * This method returns the location instance directly east of the current one
	 * @return a location instance
	 */
	public Location getEast(){ return map.at(x+1, y); }

	/**
	 * This method returns the location instance directly west of the current one
	 * @return a location instance
	 */
	public Location getWest(){ return map.at(x-1, y); }

	/**
	 * This method checks the locations north, south, east and west of the current one.
	 * If one of these locations is out of bounds of the gameMap area, it will not be returned
	 * @param x The x coordinate of the location you are trying to retrieve
	 * @param y The y coordinate of the location you are trying to retrieve
	 * @return A boolean value indicating whether the location you want to retrieve exists within the game map
	 */
	public boolean locationValid(int x, int y){
		return (x >= 0) && (y >= 0) && (x <= map.getXRange().max()) && (y <= map.getYRange().max());
	}

	/**
	 * Generates an array list of locations N,S,E and W of the current one. Only returned if they are valid
	 * @return An array list of location instances which are valid and exist within the game map and world
	 */
	public ArrayList<Location> validAdjacentLocations(){
		ArrayList<Location> locations = new ArrayList();
		if (locationValid(x, y - 1)){
			locations.add(this.getNorth());
		}
		if (locationValid(x, y + 1)){
			locations.add(this.getSouth());
		}
		if (locationValid(x + 1, y)){
			locations.add(this.getEast());
		}
		if (locationValid(x - 1, y)){
			locations.add(this.getWest());
		}
		return locations;
	}

	/**
	 * Accessor to determine whether there is an Actor at this location.
	 *
	 * @return true if and only if there is an Actor at this location.
	 */
	public boolean containsAnActor() {
		return map.isAnActorAt(this);
	}

	/**
	 * Accessor to retrieve the Actor at this location.
	 *
	 * @return the Actor at this location, if there is one
	 */
	public Actor getActor() {
		return map.getActorAt(this);
	}
	
	/**
	 * Add an Actor to the Location.
	 * This is really only here for consistency for the Location API.
	 *
	 * @param actor the Actor to add
	 */
	public void addActor(Actor actor) {
		Objects.requireNonNull(actor);
		map.addActor(actor, this);
	}
	
	/**
	 * Returns a MoveActorAction that will move actor to location if the terrain type allows.
	 * @param actor the Actor to move
	 * @param direction the direction of the destination from actor
	 * @param hotKey the character that will trigger the movement command
	 *
	 * @return a MoveActorAction that allows actor to move to location if allowed; otherwise null
	 */
	public MoveActorAction getMoveAction(Actor actor, String direction, String hotKey) {
		if(canActorEnter(actor)) {
			return new MoveActorAction(this, direction, hotKey);
		}
		return null;
	}

	/**
	 * Returns true if an Actor can enter this location.
	 *
	 * Actors can enter a location if the terrain is passable and there isn't an Actor there already.
	 * Game rule. One actor per location.
	 * @param actor the Actor who might be moving
	 * @return true if the Actor can enter this location
	 */
	public boolean canActorEnter(Actor actor) {
		return !map.isAnActorAt(this) && ground.canActorEnter(actor);
	}

	/**
	 * Returns the character to display for this location.
	 *
	 * If there is an Actor here, they are drawn.  If there is no Actor, then any items present are drawn.
	 * If there is no Actor and no item, then the ground symbol is drawn.
	 *
	 * @return the symbol to draw for this location
	 */
	@Override
	public char getDisplayChar() {
		Printable thing;
		
		if(this.containsAnActor()) 
			thing = this.getActor();
		else if (items.size() > 0)
			thing = items.get(items.size() - 1);
		else
			thing = ground;
		
		return thing.getDisplayChar();
	}

	/**
	 * Compare two Locations for equality.
	 *
	 * @param obj the object (presumably a Location) to compare
	 * @return true if obj is a reference to the current Location
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof Location))
			return false;

		if (obj == this)
			return true;

		Location that = (Location) obj;

		return this.map == that.map && this.y() == that.y() && this.x() == that.x();
	}

	/**
	 * Computes a hash for the current Location.
	 * @return the hash
	 */
	@Override
	public int hashCode() {
		return map.hashCode() ^ y() << 16 ^ x();
	}

	/**
	 * Returns an unmodifiable list of exits.
	 *
	 * @return an unmodifiable list of exits
	 */
	public List<Exit> getExits() {
		return Collections.unmodifiableList(exits);
	}

	/**
	 * Add an exit to this Location.
	 *
	 * This method is used in GameMap to initialize the Location's exits.
	 * @param exit the exit to add
	 */
	public void addExit(Exit exit) {
		exits.add(exit);
	}
	
	/**
	 * Remove an exit from this Location.
	 *
	 * This method is used in GameMap to initialize the Location's exits.
	 * @param exit the exit to remove
	 */
	public void removeExit(Exit exit) {
		exits.remove(exit);
	}
}
