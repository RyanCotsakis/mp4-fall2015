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

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {
	private int closest = 15; // max number; greater than fox's view range
	private Set<Item> ItemsInRange; //set of item's in fox's view range
	private Location foxLocation;
	
	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
	    foxLocation= animal.getLocation(); // get current location of the fox
	    ItemsInRange= world.searchSurroundings(animal);
	    
        Item targetRabbit=null; 
        Location targetLocation=foxLocation;

	    if(isItemAround("Rabbit")){
	        targetRabbit=getClosest("Rabbit", animal);   
	        targetLocation=targetRabbit.getLocation();
	        
	    }
	    
        if(this.isLocationEmpty(world, animal, targetLocation)){
            return new MoveCommand(animal, targetLocation);
        }
        
	    else
	        return new WaitCommand();
	}
	
	/**
	 *  Find the closets itemToFind in fox's view range 
	 *  Precondition: the item must be in fox's view range
	 * @param itemToFind
	 * @param animal the fox
	 * @return
	 */
	private Item getClosest(String itemToFind, ArenaAnimal animal){
	    int smallestDistance=closest;
	    Item closestItem=null;
	    
	       for(Item item: ItemsInRange){
	           if(item.getName().equals(itemToFind)){
	               int itemDistance=foxLocation.getDistance(item.getLocation()); //gets distance of this item
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
	
	/**
	 * Checks to see if an item is in the view range of the fox
	 * @param item
	 * @return true if item is in the fox's view range and false otherwise
	 */
	private boolean isItemAround(String itemName){
        for(Item item: ItemsInRange){
            
            if(item.getName().equals(itemName))
                return true;
            }
        
        return false;
	}
	
	


}
