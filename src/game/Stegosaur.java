package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

import java.util.Random;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.
	private Behaviour behaviour;

	/** 
	 * Instantiates a Stegosaur with and randomises its gender. It calls the Dinosaur class constructor which in turn calls the Actor class constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 */
	public Stegosaur() {
		super("stegosaur", 'd', 100);
		Random rand = new Random();
		int num = rand.nextInt(2) + 1; //either 1 or 2
		if (num == 1){
			setGender("female");
		} else {
			setGender("male");
		}
		this.setStage("adult");
		
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
}
