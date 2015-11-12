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
	    if(numOf("Rabbit")>0 && animal.getEnergy()<animal.getMaxEnergy()){
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
	        if(numOf("Fox")==0 && numOf("Grass")==0 && numOf("Gnat")==0 && animal.getEnergy()>animal.getMinimumBreedingEnergy()){
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
	   if(animal.getEnergy() >= animal.getMinimumBreedingEnergy() && numOf("Rabbit")>0){
	       
	       Location breedLocation=getBreedingLocation(animal, world);
	       if(breedLocation!=null)
	           return new BreedCommand(animal, breedLocation);
           
	   }
	   
       return MoveCommand.moveInRandomDirection(animal, (World) world);
	}
	
	/**Get the preferred breeding location of animal
	 * 
	 * @param animal the animal that wants to breed, fox
	 * @param world
	 * @return Location: breedingLocation that is an empty location that is adjacent to fox's current location
	 *         if all adjacent locations are full return null
	 */
	private Location getBreedingLocation(ArenaAnimal animal, ArenaWorld world){
	    //we should definitely make this nicer but the ideas is you prefer to breed somewhere if there is a rabbit
	    //adjacent to that breeding location so check that for all directions first and if neither of them have
	    //adjacent rabbits just breed anywhere that is empty
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
    private Location nextRabbitMovement(ArenaAnimal fox, ArenaWorld world, Item thisRabbit){
        Direction dangerDirection=null;
        
        Location nextLocation;
        
        //check to see if there are other foxes that this fox can see who might be chasing this rabbit
        //assumes that the rabbit will run away from the closest fox 
        if(numOf("Fox")>0){
            Item dangerFox=getClosest("Fox", thisRabbit);
            
            dangerDirection=Util.getDirectionTowards(thisRabbit.getLocation(), dangerFox.getLocation());
            
            nextLocation = new Location(thisRabbit.getLocation(), this.oppositeDir(dangerDirection));
            
            //if the predicted next location of rabbit, that is in opposite direction of its closest fox, is empty
            //go there
            if(this.isLocationEmpty(world,fox, nextLocation))
                return nextLocation; 
            
            //otherwise just return the current location of the rabbit
            else
                return thisRabbit.getLocation(); 
        }
        
        //if no other foxes chasing the rabbit return its current location 
        else
            return nextLocation=thisRabbit.getLocation();        
 
    }
	
//    /**
//     * Get the shortest possible path from my location to the target location
//     * @param myLocation
//     * @param targetLocation of the rabbit
//     * @return
//     */
//    private int getShortestPath(Location myLocation, Location targetLocation){
//        int shortestPath=0;
//        
//      //get x and y coordinates of the target
//        int targetX=targetLocation.getX();
//        int targetY=targetLocation.getY();
//        
//        //get my x and y coordinates
//        int myX=myLocation.getX();
//        int myY=myLocation.getX();
//        
//        //get a list of all the obstacles in my way that are also in my view range
//        //only grass is considered an obstacle
//        LinkedList<Item> obstacles= new LinkedList<Item>();
//        for(Item item:itemsInRange){
//            if(item.getName().equals("Grass")){
//                
//                //check to see if there are obstacles in the path from my location to the target location
//                if((item.getLocation().getX()>=targetX && item.getLocation().getX()<=myX)
//                        || (item.getLocation().getX()<=targetX && item.getLocation().getX()>=myX)){
//                    
//                    if((item.getLocation().getY()>=targetY && item.getLocation().getY()<=myY)
//                        || (item.getLocation().getY()<=targetY && item.getLocation().getY()>=myY)){
//                        obstacles.add(item);
//                    }
//                }
//            }
//        }
//        if(obstacles.isEmpty()){
//            shortestPath=myLocation.getDistance(targetLocation);
//            return shortestPath;
//            
//        }
//        
//        else{
//            return shortestPath; //need to change this...
//        }
//    }
    
    /**
    * Get the number of an item present in the fox's view range
    * 
    * @param itemName the name of the item that we want to find 
    * @return number of occurrences of the specified item in fox's view range, if no item of that 
    *         type present return 0
    */
   private int numOf(String itemName){
       int numOfType = 0;
       for(Item item : itemsInRange){
           if(item.getName().equals(itemName)){
               numOfType++;
           }
       }
       return numOfType;
   }
	
   /**
    *  Find the itemToFind that minimizes the distance between myItem and itemToFind
    *  Precondition: itemToFind must be in fox's view range
    * @param itemToFind the item type that we want to find the closest one  of it
    * @param myItem the item form which we measure the distance
    * @return the closets item to myItem of the type itemToFind
    */
   private Item getClosest(String itemToFind, Item myItem){
       int smallestDistance=closest;
       Item closestItem=null;
       
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
