package game;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

import java.util.Random;

/**
 * A herbivorous dinosaur.
 */
public class Stegosaur extends Dinosaur {

	/** 
	 * Instantiates a Stegosaur with and randomises its gender. It calls the Dinosaur class constructor which in turn calls the Actor class constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 */
	public Stegosaur() {
		super("stegosaur", 'd', 100);
		this.setGender(this.randomiseGender());
		this.setStage("adult");
	}
}
