package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Actor {
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.
	private Behaviour behaviour;
	int foodLvl = 50;
	int maxFoodLvl = 100;
	int numTurnsUnconscious = 0;
	boolean isUnconscious = false;

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 * 
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name) {
		super(name, 'd', 100);
		
		behaviour = new WanderBehaviour();
	}

	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new AttackAction(this));
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

	// BELLA ADDED METHODS
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
	 * Decrease Stegosaur food level by 1 each turn.
	 */
	public void tick(){
		int foodLvl = this.getFoodLvl();
		foodLvl -= 1;
		this.setFoodLvl(foodLvl);
	}

	public int getNumTurnsUnconscious() {
		return numTurnsUnconscious;
	}

	public void setNumTurnsUnconscious(int numTurnsUnconcious) {
		this.numTurnsUnconscious = numTurnsUnconcious;
	}

	public boolean isUnconscious() {
		return isUnconscious;
	}

	public void setUnconscious(boolean unconscious) {
		isUnconscious = unconscious;
	}

}
