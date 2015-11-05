package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {

	private static final int DANGER_DISTANCE = 2;
	
	private int closest = 10; // max number; greater than rabbit's view range.
	private int temp;
	
	private boolean foxFound;
	private Item closestFox;
	private Direction dangerDirection;
	
	private int myX;
	private int myY;
	Set<Item> visibleItems;

	public RabbitAI() {
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		
		visibleItems = world.searchSurroundings(animal);
		myX = animal.getLocation().getX();
		myY = animal.getLocation().getY();
		
		if(numOf("Fox") > 0){
			foxFound = true;
			closestFox = getClosest("Fox",animal);
			int distance = closestFox.getLocation().getDistance(animal.getLocation());
			if(distance <= DANGER_DISTANCE){
				int foxX = closestFox.getLocation().getX();
				int foxY = closestFox.getLocation().getY();
				if(Math.abs(myX - foxX) > Math.abs(myY - foxY)){
					if(foxX > myX)
						dangerDirection = Direction.EAST;
					else
						dangerDirection = Direction.WEST;
				}
				else{
					if(foxY > myY)
						dangerDirection = Direction.NORTH;
					else
						dangerDirection = Direction.SOUTH;
				}
				Location newLocation = new Location(animal.getLocation(), this.oppositeDir(dangerDirection));
				if(this.isLocationEmpty(world, animal, newLocation)){
					return new MoveCommand(animal, newLocation);
				}
			}
		}
		
		return new WaitCommand();
	}
	
	private int numOf(String itemName){
		int numOfType = 0;
		for(Item item : visibleItems){
			if(item.getName().equals(itemName)){
				numOfType++;
			}
		}
		return numOfType;
	}
	
	/**
	 * finds the closest item of some type to the rabbit.
	 * @param itemName must be in visible range
	 * @return the item with the name "itemName" that is closest to the rabbit
	 */
	private Item getClosest(String itemName, ArenaAnimal animal){
		int smallestDistance = closest;
		Item closestItem = null;
		for(Item item : visibleItems){
			if(item.getName().equals(itemName)){
				Location myLocation = animal.getLocation();
				int distance = item.getLocation().getDistance(myLocation);
				if(distance < smallestDistance){
					smallestDistance = distance;
					closestItem = item;
				}
			}
		}
		if(closestItem == null){
			throw new IllegalArgumentException();
		}
		return closestItem;
	}
	
	
	
}
