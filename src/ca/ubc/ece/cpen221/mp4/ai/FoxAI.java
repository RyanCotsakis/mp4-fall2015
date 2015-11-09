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
	        
	        int foxX=foxLocation.getX();
	        int foxY=foxLocation.getY();
	        
	        int rabX=targetRabbit.getLocation().getX();
	        int rabY=targetRabbit.getLocation().getY();
	        
	        Direction targetDirectionX=null;
	        Direction targetDirectionY=null;
	        
	        //get direction of Rabbit in x
            if(rabX > foxX)
                targetDirectionX = Direction.EAST;
            else
                targetDirectionX = Direction.WEST;
            
            //get direction of rabbit in Y
            if(rabY > foxY)
                targetDirectionY = Direction.NORTH;
            else
                targetDirectionY = Direction.SOUTH;
            
	           // if fox can move towards rabbit in x direction do so
	           Location newLocation = new Location(foxLocation, targetDirectionX);
	            if(this.isLocationEmpty(world, animal, newLocation)){
	                return new MoveCommand(animal, newLocation);
	            }
	            
	            //otherwise move towards rabbit in y direction
	            else{
	                newLocation = new Location(foxLocation, targetDirectionY);
	                if(this.isLocationEmpty(world, animal, newLocation)){
	                    return new MoveCommand(animal, newLocation);
	                }
	            }
	            
	    }

	    //if there was no Rabbit to catch breed in any location that is empty
	   if((animal.getEnergy() >= animal.getMinimumBreedingEnergy() && numOf("Rabbit")>0) ||
	           (animal.getEnergy()<animal.getMinimumBreedingEnergy()+10 && numOf("Rabbit")==0)){
           Location breedLocation = new Location(animal.getLocation(), Direction.NORTH);
           if(this.isLocationEmpty(world, animal, breedLocation)){
               return new BreedCommand(animal, breedLocation);
           }
           
           breedLocation = new Location(animal.getLocation(), Direction.EAST);
           if(this.isLocationEmpty(world, animal, breedLocation)){
               return new BreedCommand(animal, breedLocation);
           }
           
           breedLocation = new Location(animal.getLocation(), Direction.SOUTH);
           if(this.isLocationEmpty(world, animal, breedLocation)){
               return new BreedCommand(animal, breedLocation);
           }
           
           
           breedLocation = new Location(animal.getLocation(), Direction.WEST);
           if(this.isLocationEmpty(world, animal, breedLocation)){

               return new BreedCommand(animal, breedLocation);
           }
	   }
	   
       Direction dir = Util.getRandomDirection();
       Location targetLocation = new Location(animal.getLocation(), dir);
       if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty((World) world, targetLocation)) {
           return new MoveCommand(animal, targetLocation);
       }
       
       
       return new WaitCommand();
	}
	
	
    
    /**
     * Predict the next movement of a given Rabbit; assumes the rabbit is pretty smart
     * @param targetRabbit
     * @return
     */
    private Location nextRabbitMovement(ArenaAnimal fox, ArenaWorld world, Item targetRabbit){
        Direction dangerDirection=null;
        
        if(numOf("Fox")>0){
            Item dangerFox=getClosest("Fox", targetRabbit);
            
            dangerDirection=Util.getDirectionTowards(targetRabbit.getLocation(), dangerFox.getLocation());
            
            Location nextLocation = new Location(targetRabbit.getLocation(), this.oppositeDir(dangerDirection));
            
            if(this.isLocationEmpty(world,fox, nextLocation))
                return nextLocation; 
            
            else
                return targetRabbit.getLocation();
            //currently I don't check if this is an empty location, I should do that 
        }
        
        else
            return targetRabbit.getLocation();        
 
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
    * get the number of an item present in the Rabbit's view range
    * 
    * @param itemName the name of the item that we want to find 
    * @return number of occurrences of the specified item in Rabbit's view range
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
    *  Find the closets itemToFind in fox's view range 
    *  Precondition: the item must be in fox's view range
    * @param itemToFind
    * @param me
    * @return
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
