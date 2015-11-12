package ca.ubc.ece.cpen221.mp4.vehicles;

import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Bomber extends AbstractVehicles {
    private static final int INITIAL_FUEL = 200;
    private static final int MAX_ENERGY = 350;
    private static final int SLOW_COOLDOWN = 1;
    private static final int FAST_COOLDOWN = 3;
    private static final ImageIcon BomberImage = Util.loadImage("bomber.jpg");

    /**
     * Create a new "Motorcycle" at
     *      initialLocation. The initialLocation must be
     * valid and empty
     *
     * @param initialLocation
     *            the location where this Motorcycle will be created
     */
    public Bomber(Location initialLocation) {
        setLocation(initialLocation);

        setEnergy(INITIAL_FUEL);
    }

    @Override
    public int getMovingRange() {
        return 3;
    }

    @Override
    public ImageIcon getImage() {
        return BomberImage;
    }

    @Override
    public String getName() {
        return "Bomber";
    }


    @Override
    public int getCoolDownPeriod() {
        //the cooldown period changes according to the bomber's energy; cools down faster
        //if it has more energy
        if (this.getEnergy()<MAX_ENERGY-80)
            return SLOW_COOLDOWN;
        
        return FAST_COOLDOWN;
    }
    
    @Override
    public Command getNextAction(World world) {
        //bomber targets lions,boxfoxes and foxes so it only explodes where it is surrounded
        //by those items
        Direction direction;
        Location nextLocation; 
        
        int count=0;
        
        Location location=this.getLocation();
        int energy=this.getEnergy();
        
        Set<Item> surroundings =new HashSet<Item>();
        surroundings=world.searchSurroundings(location, 8);
        
        for(Item item : surroundings){
            if(item.getName().equals("BoxFox") || item.getName().equals("Fox") 
                    || item.getName().equals("Lion")){
                
                if(location.getDistance(item.getLocation())==1)
                    count++;
                
              //move towards either of its targets
                direction=Util.getDirectionTowards(location, item.getLocation());
                nextLocation = new Location(this.getLocation(), direction);
                if  (Util.isLocationEmpty(world, nextLocation) && count!=3) {
                    return new MoveCommand(this, nextLocation);
                }
            }   
        }
        
        //if in at least 3 of its adjacent locations there is either a fox, boxfox or lion them explode and
        //kill them all
        if(count==3){
            this.setEnergy(0);
            
            for(Item item : surroundings){
                
                if(location.getDistance(item.getLocation())==1)
                    item.loseEnergy(Integer.MAX_VALUE);
            }
        }
            
        
        //gets a random direction and moves there if it is empty and the motorcycle's energy is more than 30
        //it looses 5 energies when it moves around
        if  (energy>=30) {
            this.setEnergy(energy-5);
            return MoveCommand.moveInRandomDirection(this, world);
            }
        
        return new WaitCommand();
            

    }
    

}
