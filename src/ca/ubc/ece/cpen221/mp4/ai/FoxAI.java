package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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
	private Set<Item> itemsInRange; //set of item's in fox's view range
	private Location foxLocation;
	
	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
	    foxLocation= animal.getLocation(); // get current location of the fox
	    itemsInRange= world.searchSurroundings(animal);
	    
	    //if there is a rabbit around and the fox's energy is less than the maximum the priority is to pursue it
	    if(numOf(animal, world, "Rabbit")>0 && animal.getEnergy()<animal.getMaxEnergy()){
	        int tagetRabbitDistance=15;
	        Item targetRabbit=null;; //the rabbit that the fox will decide to chase

	        //get the rabbit that after the next step in the world will be closer to the fox
	        for(Item item: itemsInRange){
	            if(item.getName().equals("Rabbit")){
	                int rabbitDistance=foxLocation.getDistance(nextRabbitMovement(animal, world,item));

	                if(rabbitDistance<tagetRabbitDistance){
	                       tagetRabbitDistance=rabbitDistance;
	                       targetRabbit=item;
	                }
	                
	                //if there is rabbit adjacent to fox eat it
	                if(item.getLocation().getDistance(foxLocation)== 1){
	                    return new EatCommand(animal, item);
	                }

	            }
	        }
	        
	        //if there are no obstacles or other foxes around it would be hard to catch the rabbit so breed first
	        //if it has minimum energy
	        if(numOf(animal, world, "Fox")==0 && numOf(animal, world, "Grass")==0 
	                && numOf(animal, world, "Gnat")==0 && animal.getEnergy()>animal.getMinimumBreedingEnergy()){
	            Location breedLocation=getBreedingLocation(animal, world);
	            if(breedLocation!=null)
	                return new BreedCommand(animal, breedLocation);
	        }
	        
	        int foxX=foxLocation.getX();
	        int foxY=foxLocation.getY();
	        
	        int rabX=targetRabbit.getLocation().getX();
	        int rabY=targetRabbit.getLocation().getY();
	        
	        Direction targetDirectionX;
	        Direction targetDirectionY;
	        
	      //get direction of rabbit in X
	        if(rabX > foxX)
                targetDirectionX = Direction.EAST;
            else
                targetDirectionX = Direction.WEST;
	        
	      //get direction of rabbit in Y
            if(rabY < foxY) //north is decreasing y
                targetDirectionY = Direction.NORTH;
            else
                targetDirectionY = Direction.SOUTH;
	        
            
	     // if fox is closer to rabbit in x direction and it can move towards rabbit in x direction do so
            if(Math.abs(rabX - foxX) > Math.abs(rabY - foxY)){
                Location newLocation = new Location(foxLocation, targetDirectionX);
                if(this.isLocationEmpty(world, animal, newLocation))
                    return new MoveCommand(animal, newLocation);
                    
                    //try y direction if that is not empty
                else{
                    newLocation = new Location(foxLocation, targetDirectionY);
                    if(this.isLocationEmpty(world, animal, newLocation)){
                        return new MoveCommand(animal, newLocation);
                    }
                }
            }
            
            //otherwise move towards rabbit in y direction first and if that is not empty try x direction
            else{
                Location newLocation = new Location(foxLocation, targetDirectionY);
                newLocation = new Location(foxLocation, targetDirectionY);
                if(this.isLocationEmpty(world, animal, newLocation)){
                    return new MoveCommand(animal, newLocation);
                }
                
                else{
                    newLocation = new Location(foxLocation, targetDirectionX);
                    if(this.isLocationEmpty(world, animal, newLocation)){
                        return new MoveCommand(animal, newLocation);
                    }
                }
            }
	            
	    }

	    //if there was no Rabbit to catch breed in any location that is empty
	   if(animal.getEnergy() >= animal.getMinimumBreedingEnergy() && numOf(animal, world, "Rabbit")>0){
	       
	       Location breedLocation=getBreedingLocation(animal, world);
	       if(breedLocation!=null)
	           return new BreedCommand(animal, breedLocation);
           
	   }
	   
       return MoveInRandomDirection(animal, world);
	}
	
	/**Get the preferred breeding location of animal
	 * 
	 * @param animal the animal that wants to breed, fox
	 * @param world
	 * @return Location: breedingLocation that is an empty location that is adjacent to fox's current location
	 *         if all adjacent locations are full return null
	 */
	private Location getBreedingLocation(ArenaAnimal animal, ArenaWorld world){
	    Location breedLocation = new Location(animal.getLocation(), Direction.NORTH);
	    
	    
        if(this.isLocationEmpty(world, animal, breedLocation) && isRabbitAdjacent(breedLocation)){
            return breedLocation;
        }
        
        breedLocation = new Location(animal.getLocation(), Direction.EAST);
        if(this.isLocationEmpty(world, animal, breedLocation )&& isRabbitAdjacent(breedLocation)){
            return breedLocation;
        }
        
        breedLocation = new Location(animal.getLocation(), Direction.SOUTH);
        if(this.isLocationEmpty(world, animal, breedLocation)&& isRabbitAdjacent(breedLocation)){
            return breedLocation;
        }
        
        
        breedLocation = new Location(animal.getLocation(), Direction.WEST);
        if(this.isLocationEmpty(world, animal, breedLocation)){
            return breedLocation;
        }
        
        if(this.isLocationEmpty(world, animal, breedLocation)){
            return breedLocation;
        }
        
        breedLocation = new Location(animal.getLocation(), Direction.EAST);
        if(this.isLocationEmpty(world, animal, breedLocation )){
            return breedLocation;
        }
        
        breedLocation = new Location(animal.getLocation(), Direction.SOUTH);
        if(this.isLocationEmpty(world, animal, breedLocation)){
            return breedLocation;
        }
        
        
        breedLocation = new Location(animal.getLocation(), Direction.WEST);
        if(this.isLocationEmpty(world, animal, breedLocation)){
            return breedLocation;
        }
        return null;
	    
	}
	
	/**
	 * Checks to see if there is a Rabbit adjacent to the given location
	 * @param location
	 * @return
	 */
	private boolean isRabbitAdjacent(Location location){
	    for(Item item: itemsInRange){
	        if(item.getName().equals("Rabbit")){
	            
                if(item.getLocation().getDistance(location)== 1){
                    return true;
                }
	        }
	    }
	    return false;
	}
    
    /**
     * Predict the next movement of a given Rabbit; assumes the rabbit are smart and will runaway from 
     * the closest fox
     * @param Item thisRabbit
     * @return Location , the predicted next location of rabbit 
     */
    private Location nextRabbitMovement(ArenaAnimal animal, ArenaWorld world, Item thisRabbit){
        Direction dangerDirection=null;
        
        Location nextLocation;
        
        //check to see if there are other foxes that this fox can see who might be chasing this rabbit
        //assumes that the rabbit will run away from the closest fox 
        if(numOf(animal, world ,"Fox")>0){
            Item dangerFox=getClosest("Fox", thisRabbit, closest, world, animal);
            
            dangerDirection=Util.getDirectionTowards(thisRabbit.getLocation(), dangerFox.getLocation());
            
            nextLocation = new Location(thisRabbit.getLocation(), this.oppositeDir(dangerDirection));
            
            //if the predicted next location of rabbit, that is in opposite direction of its closest fox, is empty
            //go there
            if(this.isLocationEmpty(world,animal, nextLocation))
                return nextLocation; 
            
            //otherwise just return the current location of the rabbit
            else
                return thisRabbit.getLocation(); 
        }
        
        //if no other foxes chasing the rabbit return its current location 
        else
            return nextLocation=thisRabbit.getLocation();        
 
    }

}
