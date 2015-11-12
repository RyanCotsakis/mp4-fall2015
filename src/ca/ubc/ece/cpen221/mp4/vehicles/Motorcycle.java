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

public class Motorcycle implements MoveableItem, Actor {
    private static final int INITIAL_FUEL = 180;
    private static final int MAX_ENERGY = 250;
    private static final int STRENGTH = 200;
    private static final int COOLDOWN = 1;
    private static final ImageIcon motocycleImage = Util.loadImage("motorcycles.gif");

    private Location location;
    private int fuel;

    /**
     * Create a new "Motorcycle" at
     *      initialLocation. The initialLocation must be
     * valid and empty
     *
     * @param foxAI
     *            the AI designed for foxes
     * @param initialLocation
     *            the location where this Fox will be created
     */
    public Motorcycle(Location initialLocation) {
        this.location = initialLocation;

        this.fuel = INITIAL_FUEL;
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
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energy) {
        this.fuel-=energy;
    }

    @Override
    public boolean isDead() {
        return fuel <=0;
    }

    @Override
    public int getPlantCalories() {
        //not food
        return 0;
    }

    @Override
    public int getMeatCalories() {
        //not food
        return 0;
    }

    @Override
    public void moveTo(Location targetLocation){
        this.location=targetLocation;

    }

    @Override
    public int getMovingRange() {
        return 1;
    }

    @Override
    public int getCoolDownPeriod() {
        return 1;
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
                    item.loseEnergy(20);
                    this.fuel+=10;
                }
                
                //goes towards the lion if it can
                direction=Util.getDirectionTowards(this.location, item.getLocation());
                nextLocation = new Location(this.getLocation(), direction);
                
                if  (Util.isLocationEmpty(world, nextLocation) && Util.isValidLocation(world, nextLocation)) {
                    return new MoveCommand(this, nextLocation);
                }
                        
            }
        }
        return MoveCommand.moveInRandomDirection(this, world);
    }

}
