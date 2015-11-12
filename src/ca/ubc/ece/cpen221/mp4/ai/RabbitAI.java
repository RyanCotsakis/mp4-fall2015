package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Set;
import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI{

	private int closest = 10; // max number; greater than rabbit's view range.

	private boolean foxFound;
	private Item closestFox;
	private Direction dangerDirection;
	Set<Item> visibleItems;

	public RabbitAI ( ){
	}

	@Override
	public Command getNextAction (ArenaWorld world, ArenaAnimal animal){

		visibleItems = world.searchSurroundings(animal);
		foxFound = false;

		if (numOf(animal, world, "Fox") > 0) {
			foxFound = true;
			closestFox = getClosest("Fox", animal); // get the closest fox
			dangerDirection = Util.getDirectionTowards(animal.getLocation(), closestFox.getLocation());
			Location newLocation = new Location(animal.getLocation(), this.oppositeDir(dangerDirection));
			if (this.isLocationEmpty(world, animal, newLocation)) {
				return new MoveCommand(animal, newLocation);
			}
		}

		if (animal.getEnergy() >= animal.getMinimumBreedingEnergy()) {
			Location breedLocation = new Location(animal.getLocation(), Direction.NORTH);
			if (this.isLocationEmpty(world, animal, breedLocation)) {
				if (!foxFound || (foxFound && !dangerDirection.equals(Direction.NORTH))) {
					if (numOf(animal, world, "grass") > numOf(animal, world, "Rabbit") * 2)
						return new BreedCommand(animal, breedLocation);
				}
			}
			breedLocation = new Location(animal.getLocation(), Direction.EAST);
			if (this.isLocationEmpty(world, animal, breedLocation)) {
				if (!foxFound || (foxFound && !dangerDirection.equals(Direction.EAST))) {
					if (numOf(animal, world, "grass") > numOf(animal, world, "Rabbit") * 2)
						return new BreedCommand(animal, breedLocation);
				}
			}
			breedLocation = new Location(animal.getLocation(), Direction.SOUTH);
			if (this.isLocationEmpty(world, animal, breedLocation)) {
				if (!foxFound || (foxFound && !dangerDirection.equals(Direction.SOUTH))) {
					if (numOf(animal, world, "grass") > numOf(animal, world, "Rabbit") * 2)
						return new BreedCommand(animal, breedLocation);
				}
			}
			breedLocation = new Location(animal.getLocation(), Direction.WEST);
			if (this.isLocationEmpty(world, animal, breedLocation)) {
				if (!foxFound || (foxFound && !dangerDirection.equals(Direction.WEST))) {
					if (numOf(animal, world, "grass") > numOf(animal, world, "Rabbit") * 2)
						return new BreedCommand(animal, breedLocation);
				}
			}
		}

		if (numOf(animal, world, "grass") > 0) {
			Item closestGrass = getClosest("grass", animal);
			if (closestGrass.getLocation().getDistance(animal.getLocation()) == 1) {
				if (numOf(animal, world, "grass") >= numOf(animal, world, "Rabbit")
						&& animal.getEnergy() <= animal.getMaxEnergy() - 10)
					return new EatCommand(animal, closestGrass);
				// return new WaitCommand();
			}
			Direction grassDirection = Util.getDirectionTowards(animal.getLocation(), closestGrass.getLocation());
			Location grassLocation = new Location(animal.getLocation(), grassDirection);
			if (this.isLocationEmpty(world, animal, grassLocation)) {
				if (!foxFound || (foxFound && !dangerDirection.equals(grassDirection)))
					return new MoveCommand(animal, grassLocation);
			}

		}

		return MoveCommand.moveInRandomDirection(animal, (World) world);
	}

	/**
	 * finds the closest item of some type to the rabbit.
	 * 
	 * @param itemName
	 *            must be in visible range
	 * @return the item with the name "itemName" that is closest to the rabbit
	 */
	private Item getClosest (String itemName, ArenaAnimal animal){
		int smallestDistance = closest;
		Item closestItem = null;
		for (Item item : visibleItems){
			if (item.getName().equals(itemName)) {
				Location myLocation = animal.getLocation();
				int distance = item.getLocation().getDistance(myLocation);
				if (distance < smallestDistance && distance != 0) {
					smallestDistance = distance;
					closestItem = item;
				}
			}
		}
		if (closestItem == null) {
			throw new IllegalArgumentException();
		}
		return closestItem;
	}
}
