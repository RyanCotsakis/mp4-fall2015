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
    private static final int INITIAL_ENERGY = 450;
    private static final int MAX_ENERGY = 600;
    private static final int STRENGTH = 500;
    private static final int COOLDOWN = 1;
    private static final ImageIcon motocycleImage = Util.loadImage("motorcycles.gif");

    private Location location;
    private int energy;

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

        this.energy = INITIAL_ENERGY;
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
        this.energy-=energy;
    }

    @Override
    public boolean isDead() {
        return energy <=0;
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

    //runs over rabbits??
    @Override
    public Command getNextAction(World world) {
        Set<Item> surroundings = new HashSet<Item>();
        surroundings = world.searchSurroundings(location, 3);
        for(Item item : surroundings){
        }
        return new WaitCommand();
    }

}
