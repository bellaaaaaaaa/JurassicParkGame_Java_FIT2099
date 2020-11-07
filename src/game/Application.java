package game;

import java.util.*;
import java.util.stream.IntStream;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) throws ErrorInputException{
		boolean selected_quit = false;
		while (!selected_quit) {
			System.out.println("Select your preferred game mode");
			System.out.println("1: Challenge mode");
			System.out.println("2: Sandbox mode");
			System.out.println("3: Exit this game");
			Scanner choice = new Scanner(System.in);
			System.out.println();
			System.out.println("Enter \"1\", \"2\" or \"3\"");
			try{
				int choiceEntry = choice.nextInt();

				if (choiceEntry == 1) {
					System.out.println("Choose the number of moves: ");
					int numOfMoves = choice.nextInt();
					if (numOfMoves <= 0){
						System.out.println("Number of moves must be greater than 0 !");
						throw new ErrorInputException("Input must greater than 0");
					}
					System.out.println("Choose the number of Eco Points: ");
					int ecoPoints = choice.nextInt();
					if (ecoPoints <= 250){
						System.out.println("EcoPoints must be greater than 250 !");
						throw new ErrorInputException("Input must greater than 250");
					}
					System.out.println(numOfMoves + ", e: " + ecoPoints);
					Player.TargetEco = ecoPoints;
					World.TargetTurn = numOfMoves;
					reset(1);}
				else if (choiceEntry == 2) {
					reset(2);

				}
				else if (choiceEntry == 3) {
					selected_quit = true;
				}

			}
			catch (InputMismatchException e){
				System.out.println("Input must be integer !");
			}

			catch (ErrorInputException e) {
				System.out.println("Input is out of bound !");
			}

		}
	}

	public static void reset(int choice){
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new Grass(), new VendingMachine(), new Water());


		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		".....#######....................................................................",
		".....#_____#....................................................................",
		".....#_____#....................................................................",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++.......................................",
		".......................................++++.....................................",
		"...................................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		".....................................+++........................................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		"........................................................................~~~~~~~~",
		"........................................................................~~~~~~~~",
		"........................................................................~~~~~~~.");

		List<String> secondMap = Arrays.asList(
		"................................................................................",
		".....................................................++++++.....................",
		".....###.................................................+++....................",
		".....#_#..............................................+++++.....................",
		".....###.............................................++++++.....................",
		"................................................................................",
		"................................................................................",
		"...........+++..................................................................",
		".......................................++++.....................................",
		"...................................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		".....................................+++........................................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"................................++.......................+++++..................",
		"................................+++.............................................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		"........................................................................~~~~~~~~",
		"........................+++.............................................~~~~~~~~",
		"........................................................................~~~~~~~.");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);

		// Second gameMap added to world
		GameMap secondGameMap = new GameMap(groundFactory, secondMap);
		world.addGameMap(secondGameMap);

		Actor player = new Player("Player", '@', 100);
		player.addItemToInventory(new LaserGun());
		player.addItemToInventory(new MealKit("carnivore"));
		world.addPlayer(player, gameMap.at(0, 0));
		VendingMachine v = new VendingMachine();

		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(79, 24).addActor(new Archaeopteryx(gameMap));
		gameMap.at(72, 24).addActor(new Stegosaur());
		gameMap.at(40, 12).setGround(v);


		// Each square of dirt has a small 2% chance to grow grass.

		Grass g = new Grass();
		gameMap.growInitialGrass(g.getDisplayChar(), gameMap.getXRange(), gameMap.getYRange());
		secondGameMap.growInitialGrass(g.getDisplayChar(), gameMap.getXRange(), gameMap.getYRange());

		//Each square of dirt has a 1% chance to create pool of water.
		Water w = new Water();
		gameMap.createPool(w.getDisplayChar(),gameMap.getXRange(),gameMap.getYRange());
		secondGameMap.createPool(w.getDisplayChar(),gameMap.getXRange(),gameMap.getYRange());

		world.run();

		if (choice == 1){
		world.runChallenge();}
		else if (choice == 2){
			world.run();}
		}
		public static class ErrorInputException extends Exception{
		public ErrorInputException(String errorMessage){
			super(errorMessage);
		}
		}
	}





