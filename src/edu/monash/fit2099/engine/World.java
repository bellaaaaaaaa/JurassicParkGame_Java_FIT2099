package edu.monash.fit2099.engine;

import game.*;

import java.util.*;

/**
 * Class representing the game world, including the locations of all Actors, the
 * player, and the playing grid.
 */
public class World {
	public static int TargetTurn;
	protected Display display;
	protected ArrayList<GameMap> gameMaps = new ArrayList<GameMap>();
	protected ActorLocations actorLocations = new ActorLocations();
	protected Actor player; // We only draw the particular map this actor is on.
	protected Map<Actor, Action> lastActionMap = new HashMap<Actor, Action>();

	/**
	 * Constructor.
	 *
	 * @param display the Display that will display this World.
	 */
	public World(Display display) {
		Objects.requireNonNull(display);
		this.display = display;
	}

	/**
	 * Add a GameMap to the World.
	 *
	 * @param gameMap the GameMap to add
	 */
	public void addGameMap(GameMap gameMap) {
		Objects.requireNonNull(gameMap);
		gameMaps.add(gameMap);
		gameMap.actorLocations = actorLocations;
	}

	/**
	 * Set an actor as the player. The map is drawn just before this Actor's turn
	 *
	 * @param player   the player to add
	 * @param location the Location where the player is to be added
	 */
	public void addPlayer(Actor player, Location location) {
		this.player = player;
		actorLocations.add(player, location.map().at(location.x(), location.y()));
		actorLocations.setPlayer(player);
	}


	/**
	 * Run the game.
	 * <p>
	 * On each iteration the gameloop does the following: - displays the player's
	 * map - processes the actions of every Actor in the game, regardless of map
	 * <p>
	 * We could either only process the actors on the current map, which would make
	 * time stop on the other maps, or we could process all the actors. We chose to
	 * process all the actors.
	 *
	 * @throws IllegalStateException if the player doesn't exist
	 */
	public void runChallenge() {

		if (player == null)
			throw new IllegalStateException();

		// initialize the last action map to nothing actions;
		for (Actor actor : actorLocations) {
			lastActionMap.put(actor, new DoNothingAction());
		}
		// This loop is basically the whole game
		int turnCount = 1;


		while (stillRunning()) {
			String count = Integer.toString(turnCount);
			display.println("Number of Moves: " + count);
			GameMap playersMap = actorLocations.locationOf(player).map();
			playersMap.draw(display);
			// Process all the actors.
			for (Actor actor : actorLocations) {
				if (stillRunning()) {

					processActorTurn(actor);

					if (getTargetTurn() <= turnCount && actor instanceof Player) {
						display.println("Current ecoPoints: " + Player.ecoPoints +"; Target ecoPoints: " + Player.TargetEco);
						display.println("You have used up " + turnCount + " moves. \r\nYou lose !");
						playersMap.removeActor(actor);
					} else if (turnCount <= getTargetTurn() && Player.ecoPoints >= Player.TargetEco) {
						display.println ("You have successfully earned " + Player.ecoPoints + " ecoPoints in " + turnCount + " moves.");
						display.println("You win !");

						playersMap.removeActor(actor);
					} else {
						if (actor instanceof Player) {
							turnCount += 1;

							continue;
						}
					}
				}
			}


			// Tick over all the maps. For the map stuff.
			for (GameMap gameMap : gameMaps) {
				gameMap.tick();
			}

		}


		display.println(endGameMessage());
	}

	public void run() {

		if (player == null)
			throw new IllegalStateException();

		// initialize the last action map to nothing actions;
		for (Actor actor : actorLocations) {
			lastActionMap.put(actor, new DoNothingAction());
		}
		// This loop is basically the whole game
		while (stillRunning()) {

			GameMap playersMap = actorLocations.locationOf(player).map();
			playersMap.draw(display);
			// Process all the actors.
			for (Actor actor : actorLocations) {
				if (stillRunning()) {

					processActorTurn(actor);
				}
			}


			// Tick over all the maps. For the map stuff.
			for (GameMap gameMap : gameMaps) {
				gameMap.tick();
			}

		}
		display.println(endGameMessage());
	}


	/**
	 * Gives an Actor its turn.
	 *
	 * The Actions an Actor can take include:
	 * <ul>
	 * <li>those conferred by items it is carrying</li>
	 * <li>movement actions for the current location and terrain</li>
	 * <li>actions that can be done to Actors in adjacent squares</li>
	 * <li>actions that can be done using items in the current location</li>
	 * <li>skipping a turn</li>
	 * </ul>
	 *
	 * @param actor the Actor whose turn it is.
	 */
	protected void processActorTurn(Actor actor) {
		Location here = actorLocations.locationOf(actor);
		GameMap map = here.map();

		Actions actions = new Actions();
		if(actor instanceof Player){
			// Player can harvest grass if standing on some
			if(here.getGround() instanceof Grass){
				actions.add(new HarvestGrassAction());
			}

			// Player can pick fruit from a tree if beside one
			if(here.getGround() instanceof Tree){
				actions.add(new PickFruitFromTreeAction());
			}

			ArrayList<Location> validLocations = actorLocations.locationOf(actor).validAdjacentLocations();
			for(Location vl : validLocations){

				// Vending machine action
				if(vl.getGround() instanceof VendingMachine){
					VendingMachine vm = new VendingMachine();
					ArrayList<Item> merchandise = vm.getMerchandise();
					for(Item i : merchandise){
						buyItemAction bia = new buyItemAction(i);
						actions.add(bia);
					}
				}

				if(vl.getActor() instanceof Dinosaur){
					// Feed dinosaur action
					List<Item> items = actor.getInventory();
					for(Item i : items){
						if(i instanceof Food){
							feedDinosaurAction fda = new feedDinosaurAction((Food) i, (Dinosaur) vl.getActor(), vl);
							actions.add(fda);
						}
					}

					// Laser stegosaur action - if player standing next to a LIVING stegosaur and also owns a laser gun
					if((vl.getActor() instanceof Stegosaur) && (actor.getWeapon() instanceof LaserGun)){
						if(!((Stegosaur) vl.getActor()).isDead()){
							laserStegosaurAction lsa = new laserStegosaurAction();
							lsa.setStegosaur((Stegosaur) vl.getActor());
							lsa.setStegosaurMap(map);
							actions.add(lsa);
						}
					}
				}

			}
			actions.add(new EndGameAction());
		}
		for (Item item : actor.getInventory()) {
			actions.add(item.getAllowableActions());
			// Game rule. If you're carrying it, you can drop it.
			actions.add(item.getDropAction());
		}
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();

			// Game rule. You don't get to interact with the ground if someone is standing
			// on it.
			if (actorLocations.isAnActorAt(destination)) {
				actions.add(actorLocations.getActorAt(destination).getAllowableActions(actor, exit.getName(), map));
			} else {
				actions.add(destination.getGround().allowableActions(actor, destination, exit.getName()));
			}
			actions.add(destination.getMoveAction(actor, exit.getName(), exit.getHotKey()));
		}

		for (Item item : here.getItems()) {
			actions.add(item.getAllowableActions());
			// Game rule. If it's on the ground you can pick it up.
			actions.add(item.getPickUpAction());
		}
		actions.add(new DoNothingAction());
		Action action = actor.playTurn(actions, lastActionMap.get(actor), map, display);

		lastActionMap.put(actor, action);

		String result = action.execute(actor, map);

		display.println(result);

	}
	public void setTargetTurn (int TargetTurn){this.TargetTurn = TargetTurn;}
	public int getTargetTurn(){return TargetTurn;}

	/**
	 * Returns true if the game is still running.
	 *
	 * The game is considered to still be running if the player is still around.
	 *
	 * @return true if the player is still on the map.
	 */
	protected boolean stillRunning() {
		return actorLocations.contains(player);
	}

	/**
	 * Return a string that can be displayed when the game ends.
	 *
	 * @return the string "Game Over"
	 */
	protected String endGameMessage() {
		return "Game Over";
	}
}
