package ca.ubc.ece.cpen221.mp4.vehicles;

import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public class Motorcycle extends AbstractVehicles {
    private static final int INITIAL_FUEL = 180;
    private static final int MAX_ENERGY = 250;
    private static final int STRENGTH = 200;
    private static final int COOLDOWN = 1;
    private static final ImageIcon motocycleImage = Util.loadImage("motorcycles.gif");

    private Location location;
    private int energy;

    /**
     * Create a new "Motorcycle" at
     *      initialLocation. The initialLocation must be
     * valid and empty
     *
     * @param initialLocation
     *            the location where this Motorcycle will be created
     */
    public Motorcycle(Location initialLocation) {
        this.location = initialLocation;

        this.energy = INITIAL_FUEL;
    }

    @Override
    public ImageIcon getImage() {
        return motocycleImage;
    }

    @Override
    public String getName() {
        return "Motorcycle";
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public int getMovingRange() {
        return 1;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public Command getNextAction(World world) {
        //Motorcycle runs lions over making them lose energy and it gains energy itself
        //its fuel decreases when it is moving around
        
        Direction direction;
        Location nextLocation; 
        
        Set<Item> surroundings = new HashSet<Item>();
        surroundings = world.searchSurroundings(location, 4);
        
        for(Item item : surroundings){
            //if it hits a lion, the lion looses 20 of its energy
            //the fuel of motorcycle increases by 10
            if(item.getName().equals("Lion")){
                if(location.getDistance(item.getLocation())==1){
                    item.loseEnergy(40);
                    
                    if(this.energy<=MAX_ENERGY)
                        this.energy+=10;
                }
                
                //goes towards the lion if it can
                direction=Util.getDirectionTowards(this.location, item.getLocation());
                nextLocation = new Location(this.getLocation(), direction);
                
                if  (Util.isLocationEmpty(world, nextLocation) && Util.isValidLocation(world, nextLocation)) {
                    return new MoveCommand(this, nextLocation);
                }
                        
            }
            
            else{
                //gets a random direction and moves there if it is empty
                direction = Util.getRandomDirection();
                nextLocation = new Location(this.getLocation(), direction);
                if  (Util.isLocationEmpty(world, nextLocation) && Util.isValidLocation(world, nextLocation)) {
                    return new MoveCommand(this, nextLocation);
                }
            }
                
        }
        return new WaitCommand();
    }

}
