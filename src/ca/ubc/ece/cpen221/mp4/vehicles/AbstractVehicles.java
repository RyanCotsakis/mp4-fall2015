package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public abstract class AbstractVehicles implements MoveableItem, Actor {
    private static final int STRENGTH = 200; // all vehicles have strength of 200
    
    private Location location;
    private int energy;

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void loseEnergy(int energy) {
        this.energy-=energy;

    }

    @Override
    public boolean isDead() {
        return this.energy <=0;
    }


    @Override
    public Command getNextAction(World world) {
        return new WaitCommand();
    }

    @Override
    public void moveTo(Location targetLocation) {
        this.location=targetLocation;

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
    public int getStrength() {
        return STRENGTH;
    }
    
    /**
     * Set the location of the vehicle to the specified location
     * Precondition: Location must be a valid location in the world
     * @param loc to set the location of vehicle to
     */
    public void setLocation(Location loc){
        this.location=loc;
    }
    
    //set the energy of the vehicle
    public void setEnergy(int energy){
        this.energy=energy;
    }
    
    //return the energy of vehicle
    public int getEnergy() {
        return this.energy;
    }

    @Override
    public abstract ImageIcon getImage();

    @Override
    public abstract String getName();

    @Override
    public abstract int getCoolDownPeriod();

    @Override
    public abstract int getMovingRange();
    

}
