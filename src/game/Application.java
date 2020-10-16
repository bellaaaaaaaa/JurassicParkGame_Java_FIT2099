package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.*;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree(), new Grass(), new VendingMachine());
		
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
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, gameMap.at(9, 4));
		VendingMachine v = new VendingMachine();
		
		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(79, 24).addActor(new Stegosaur("Stegosaur"));
		gameMap.at(32, 12).addActor(new Stegosaur("Stegosaur"));
		gameMap.at(40, 12).setGround(v);

		// Each square of dirt has a small 2% chance to grow grass.
		Grass g = new Grass();
		gameMap.growInitialGrass(g.getDisplayChar(), gameMap.getXRange(), gameMap.getYRange());
		world.run();
	}
}
