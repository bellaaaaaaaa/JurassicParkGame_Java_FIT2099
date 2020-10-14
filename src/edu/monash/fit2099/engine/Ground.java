package edu.monash.fit2099.engine;

import edu.monash.fit2099.interfaces.GroundInterface;
import game.Dirt;
import game.Grass;
import game.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing terrain type
 */
public abstract class Ground implements GroundInterface, Capable, Printable {

	private Capabilities capabilities = new Capabilities();
	protected char displayChar;

	/**
	 * Constructor.
	 *
	 * @param displayChar character to display for this type of terrain
	 */
	public Ground(char displayChar) {
		this.displayChar = displayChar;
	}

	public char getDisplayChar() {
		return displayChar;
	}

	/**
	 * Returns an empty Action list.
	 *
	 * @param actor the Actor acting
	 * @param location the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return a new, empty collection of Actions
	 */
	public Actions allowableActions(Actor actor, Location location, String direction){
		return new Actions();
	}

	/**
	 * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
	 *
	 * @param actor the Actor to check
	 * @return true
	 */
	public boolean canActorEnter(Actor actor) {
		return true;
	}

	/**
	 * Ground can also experience the joy of time.
	 * @param location The location of the Ground 
	 */
	public void tick(Location location) {
		Grass grass = new Grass();
		int numAdjGrass = 0;
		// Get adjacent locations
		ArrayList<Location> adjacents = new ArrayList();

		// Append adjacent locations
		if (location.locationValid(location.x(), location.y()-1)== true){
			adjacents.add(location.getNorth());
		}
		if (location.locationValid(location.x(), location.y()+1) == true){
			adjacents.add(location.getSouth());
		}
		if (location.locationValid(location.x() + 1, location.y()) == true){
			adjacents.add(location.getEast());
		}
		if (location.locationValid(location.x() - 1, location.y()) ==true){
			adjacents.add(location.getWest());
		}

		if (location.getGround() instanceof Dirt){
			// Check has tree
			for (Location l : adjacents){
				Random rand = new Random();
				int num = rand.nextInt(100) + 1;
				if (l.getGround() instanceof Tree){
					if(num <= 2){
						System.out.println("hits here");
						location.setGround(grass);
					}
				} else if (l.getGround() instanceof Grass){
					numAdjGrass += 1;
				}
			}
			if(numAdjGrass > 2){
				Random rand = new Random();
				int num = rand.nextInt(100) + 1;
				if (num <= 10){
					location.setGround(grass);
				}
			}
		}
	}
	
	/**
	 * Override this to implement terrain that blocks thrown objects but not movement, or vice versa
	 * @return true
	 */
	public boolean blocksThrownObjects() {
		return false;
	}

	/**
	 * Check whether this Ground type has the given Capability.
	 * 
	 * @param capability the Capability to check
	 * @return true if and only if this Ground has the given capability.
	 */
	public boolean hasCapability(Enum<?> capability) {
		return capabilities.hasCapability(capability);
	}

	/**
	 * Add the given Capability to this Ground.
	 * 
	 * @param capability the Capability to add
	 */
	public void addCapability(Enum<?> capability) {
		capabilities.addCapability(capability);
	}

	/**
	 * Remove the given Capability from this Ground.
	 * 
	 * @param capability the Capability to remove.
	 */
	public void removeCapability(Enum<?> capability) {
		capabilities.removeCapability(capability);
	}
}
