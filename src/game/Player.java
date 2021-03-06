package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor {


	private Menu menu = new Menu();
	public static int ecoPoints = 250; // Lets say starting ecoPooints.
	public static int TargetEco;

	/**
	 * Constructor for player class
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		return menu.showMenu(this, actions, display);

	}

	/**
	 * gets the number of EcoPoints that the player currently has
	 *
	 * @return ecoPoints
	 */
	public int getEcoPoints() {
		return ecoPoints;
	}

	/**
	 * Sets the number of EcoPoints that the player has
	 *
	 * @param ecoPoints initially the number of starting ecopoints a player will have. Then this can succeed based on occurences in the game.
	 */
	public void setEcoPoints(int ecoPoints) {
		this.ecoPoints = ecoPoints;
	}


	public boolean sandboxEco(int targetEco) {
		boolean state = false;
		if (targetEco > ecoPoints)
			state = true;
		return state;
	}

	public void setTargetEco(int TargetEco) {
		this.TargetEco = TargetEco;
	}

	public int getTargetEco() {
		return TargetEco;
	}
}
