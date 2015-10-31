package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Gnawty implements LivingItem{
	private static final ImageIcon gnawtyImage = Util.loadImage("gnawty.gif");

	private static final int MEAT_CALORIES = 10;
	private static final int STRENGTH = 100;

	private Location location;
	private boolean isDead;

	/**
	 * Create a new Gnat at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the Gnat will be created
	 */
	public Gnawty(Location initialLocation) {
		this.location = initialLocation;
		this.isDead = false;
	}

	@Override
	public ImageIcon getImage() {
		return gnawtyImage;
	}

	@Override
	public String getName() {
		return "Gnawty";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getPlantCalories() {
		return 0;
	}

	@Override
	public int getMeatCalories() {
		return MEAT_CALORIES;
	}

	@Override
	public void loseEnergy(int energy) {
		isDead = true; // Dies if it loses energy.
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public void moveTo(Location targetLocation) {
		location = targetLocation;
	}

	@Override
	public int getCoolDownPeriod() {
		// Each Gnawty acts every 1-3 steps randomly.
		return Util.RAND.nextInt(3) + 1;
	}

	@Override
	public Command getNextAction(World world) {
		// The Gnawty selects a random direction and check if the next location at
		// the direction is valid and empty. If yes, then it moves to the
		// location, otherwise it waits.
		Direction dir = Util.getRandomDirection();
		Location targetLocation = new Location(this.getLocation(), dir);
		if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
			return new MoveCommand(this, targetLocation);
		}

		return new WaitCommand();
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public int getEnergy() {
		// doesn't every die, except when run over by a Vehicle
		return MEAT_CALORIES;
	}

	@Override
	public LivingItem breed() {
		return null;
	}

	@Override
	public void eat(Food food) {
		// Never eats.
	}

	@Override
	public int getMovingRange() {
		return 1; // Can only move to adjacent locations.
	}
}
