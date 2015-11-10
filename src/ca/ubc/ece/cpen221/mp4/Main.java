package ca.ubc.ece.cpen221.mp4;

import javax.swing.SwingUtilities;

import ca.ubc.ece.cpen221.mp4.ai.*;
import ca.ubc.ece.cpen221.mp4.items.Gardener;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.items.misc.*;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.staff.WorldUI;
import ca.ubc.ece.cpen221.mp4.vehicles.Motorcycle;

/**
 * The Main class initialize a world with some {@link Grass}, {@link Rabbit}s,
 * {@link Fox}es, {@link Gnat}s, {@link Gardener}, etc.
 *
 * You may modify or add Items/Actors to the World.
 *
 */
public class Main {

	static final int X_DIM = 40;
	static final int Y_DIM = 40;
	static final int SPACES_PER_GRASS = 7;
	static final int INITIAL_GRASS = X_DIM * Y_DIM / SPACES_PER_GRASS;
	static final int INITIAL_GNATS = INITIAL_GRASS / 4;
	static final int INITIAL_RABBITS = INITIAL_GRASS / 10;
	static final int INITIAL_FOXES = INITIAL_GRASS / 20;
	static final int INITIAL_TIGERS = INITIAL_GRASS / 32;
	static final int INITIAL_BEARS = INITIAL_GRASS / 40;
	static final int INITIAL_HYENAS = INITIAL_GRASS / 32;
	static final int INITIAL_CARS = INITIAL_GRASS / 100;
	static final int INITIAL_TRUCKS = INITIAL_GRASS / 150;
	static final int INITIAL_MOTORCYCLES = INITIAL_GRASS / 64;
	static final int INITIAL_MANS = INITIAL_GRASS / 150;
	static final int INITIAL_WOMANS = INITIAL_GRASS / 100;
	static final int INITIAL_HUNTERS = INITIAL_GRASS / 150;
	static final int INITIAL_CROCODILES = INITIAL_GRASS / 20;
	static final int INITIAL_GNAWTYS = INITIAL_GRASS / 30;
	static final int INITIAL_BOXFOXES = 8;
	static final int INITIAL_LIONS = 10;
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main().createAndShowWorld();
			}
		});
	}

	public void createAndShowWorld() {
		World world = new WorldImpl(X_DIM, Y_DIM);
		initialize(world);
		new WorldUI(world).show();
	}

	public void initialize(World world) {
		addGrass(world);
		world.addActor(new Gardener());
		addCatapult(world);
		addGnats(world);
		addRabbits(world);
		addFoxes(world);
		addCrocodiles(world);
		addGnawtys(world);
		addPortaGrass(world);
		addBoxFoxes(world);
		addLions(world);
		addMotorcycles(world);
	}

	private void addGrass(World world) {
		for (int i = 0; i < INITIAL_GRASS; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			world.addItem(new Grass(loc));
		}
	}

	private void addGnats(World world) {
		for (int i = 0; i < INITIAL_GNATS; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Gnat gnat = new Gnat(loc);
			world.addItem(gnat);
			world.addActor(gnat);
		}
	}
	
	private void addCrocodiles(World world) {
		for (int i = 0; i < INITIAL_CROCODILES; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Crocodile croc = new Crocodile(loc);
			world.addItem(croc);
			world.addActor(croc);
		}
	}
	
	private void addGnawtys(World world) {
		for (int i = 0; i < INITIAL_GNAWTYS; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Gnawty gnawty = new Gnawty(loc);
			world.addItem(gnawty);
			world.addActor(gnawty);
		}
	}

	private void addFoxes(World world) {
		FoxAI foxAI = new FoxAI();
		for (int i = 0; i < INITIAL_FOXES; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Fox fox = new Fox(foxAI, loc);
			world.addItem(fox);
			world.addActor(fox);
		}
	}

	private void addRabbits(World world) {
		RabbitAI rabbitAI = new RabbitAI();
		for (int i = 0; i < INITIAL_RABBITS; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			Rabbit rabbit = new Rabbit(rabbitAI, loc);
			world.addItem(rabbit);
			world.addActor(rabbit);
		}
	}
	
	private void addCatapult(World world){
		Location loc = Util.getRandomEmptyLocation(world);
		Catapult catapult = new Catapult(loc);
		world.addItem(catapult);
		world.addActor(catapult);
	}
	
	private void addPortaGrass (World world){
		Location loc = Util.getRandomEmptyLocation(world);
		PortaGrass portaGrass = new PortaGrass(loc);
		world.addItem(portaGrass);
		world.addActor(portaGrass);
	}
	
	private void addBoxFoxes(World world) {
		for (int i = 0; i < INITIAL_BOXFOXES; i++) {
			Location loc = Util.getRandomEmptyLocation(world);
			BoxFox boxFox = new BoxFox(loc);
			world.addItem(boxFox);
			world.addActor(boxFox);
		}
	}
	
	private void addLions(World world) {
	        for (int i = 0; i < INITIAL_LIONS; i++) {
	            Location loc = Util.getRandomEmptyLocation(world);
	            Lion lion = new Lion(loc);
	            world.addItem(lion);
	            world.addActor(lion);
	        }
	    }
	
	private void addMotorcycles(World world) {
           for (int i = 0; i < INITIAL_MOTORCYCLES; i++) {
               Location loc = Util.getRandomEmptyLocation(world);
               Motorcycle motor = new Motorcycle(loc);
               world.addItem(motor);
               world.addActor(motor);
           }
       }
}