package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

/**
 * A MoveCommand is a {@link Command} which represents a {@link MovableItem}
 * moving. This Command moves that Item from one space in the world to another.
 */
public final class MoveCommand implements Command {

	private final MoveableItem item;
	private final Location targetLocation;

	/**
	 * Construct a {@link MoveCommand}, where <code>item</code> is the moving
	 * item and <code>targetLocation</code> is the location that
	 * <code> item </code> is moving to. The target location must be within
	 * <code>item</code>'s moving range and the target location must be empty
	 * and valid.
	 *
	 * @param item
	 *            the Item that is moving
	 * @param targetLocation
	 *            the location that Item is moving to
	 */
	public MoveCommand(MoveableItem item, Location targetLocation) {
		this.item = item;
		this.targetLocation = targetLocation;
	}
	
	/**
	 * Moves an item in a random direction. If the direction is not available, it does not move the
	 * item.
	 * 
	 * @param item
	 *            the item to be moved.
	 * @param world
	 *            the world that contains the item.
	 */
	public static Command moveInRandomDirection (MoveableItem item, World world){
		Direction dir = Util.getRandomDirection();
		Location newLocation = new Location(item.getLocation(), dir);
		if (Util.isValidLocation(world, newLocation) && Util.isLocationEmpty(world, newLocation)) {
			return new MoveCommand(item,newLocation);
		}
		return new WaitCommand();
	}
    
	

	@Override
	public void execute(World world) throws InvalidCommandException {
		// If the item is dead, then it will not move.
		if (item.isDead()) {
			return;
		}
		if (!Util.isValidLocation(world, targetLocation) || !Util.isLocationEmpty(world, targetLocation)) {
			throw new InvalidCommandException("Invalid MoveCommand: Invalid/non-empty target location");
		}
		if (item.getMovingRange() < targetLocation.getDistance(item.getLocation())) {
			throw new InvalidCommandException("Invalid MoveCommand: Target location farther than moving range");
		}

		item.moveTo(targetLocation);
	}

}