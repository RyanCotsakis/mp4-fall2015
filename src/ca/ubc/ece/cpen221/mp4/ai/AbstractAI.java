package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
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
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.*;

public class AbstractAI implements AI {

	public Direction oppositeDir(Direction dir) { // returns opposite direction
													// of direction dir
		if (dir == Direction.EAST) {
			return Direction.WEST;
		} else if (dir == Direction.WEST) {
			return Direction.EAST;
		} else if (dir == Direction.SOUTH) {
			return Direction.NORTH;
		} else {
			return Direction.SOUTH;
		}
	}

	public boolean isLocationEmpty(ArenaWorld world, ArenaAnimal animal, Location location) { 
	    // return true if location is empty
																								
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(animal);
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		return new WaitCommand();
	}
	
    /**
    * Get the number of an item present in the animal's view range
    * 
    * @param itemName the name of the item that we want to find 
    * @return number of occurrences of the specified item in fox's view range, if no item of that 
    *         type present return 0
    */
   public int numOf(ArenaAnimal animal, ArenaWorld world, String itemName){
       int numOfType = 0;
       Set<Item> itemsInRange = world.searchSurroundings(animal);
       
       for(Item item : itemsInRange){
           if(item.getName().equals(itemName)){
               numOfType++;
           }
       }
       return numOfType;
   }
   
   
   /**
    * Moves an item in a random direction. If the direction is not available, it does not move the
    * item.
    * 
    * @param animal
    *            the item to be moved.
    * @param world
    *            the world that contains the item.
    */
   public Command MoveInRandomDirection (ArenaAnimal animal, ArenaWorld world){
       Direction dir = Util.getRandomDirection();
       Location newLocation = new Location(animal.getLocation(), dir);
       if (Util.isValidLocation(world, newLocation) && this.isLocationEmpty(world, animal, newLocation)) {
           return new MoveCommand(animal,newLocation);
       }
       return new WaitCommand();
   }
   
   /**
    *  Find the itemToFind that minimizes the distance between myItem and itemToFind
    *  Precondition: itemToFind must be in fox's view range
    * @param itemToFind the item type that we want to find the closest one  of it
    * @param myItem the item form which we measure the distance
    * @return the closets item to myItem of the type itemToFind
    */
   public Item getClosest(String itemToFind, Item myItem, int closest, ArenaWorld world, ArenaAnimal animal){
       int smallestDistance=closest;
       Item closestItem=null;
       Set<Item> itemsInRange = world.searchSurroundings(animal);
       
          for(Item item: itemsInRange){
              if(item.getName().equals(itemToFind)){
                  int itemDistance=myItem.getLocation().getDistance(item.getLocation()); //gets distance of this item
                                                                                // from my location
                  if(itemDistance<smallestDistance){
                      smallestDistance=itemDistance;
                      closestItem=item;
                      }   
                  }
              }
          
           if(closestItem == null){
               throw new IllegalArgumentException();
           }
           
           return closestItem;
   }
   
}
