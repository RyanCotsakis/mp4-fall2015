package ca.ubc.ece.cpen221.mp4.items.misc;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public abstract class AbstractNuisance implements MoveableItem, Actor {
    private static final int STRENGTH = 0; // all items have strength of 200
    
    protected Location location;


    @Override
    public int getCoolDownPeriod ( ){
        return 1;
    }

    @Override
    public void moveTo (Location targetLocation){
        this.location = targetLocation;
        
    }

    @Override
    public int getMovingRange ( ){
        return 1;
    }


    @Override
    public Location getLocation ( ){
        return this.location;
    }

    @Override
    public Command getNextAction (World world){
        return new WaitCommand();
    }
    
    /**
     * Set the location of the item to the specified location
     * Precondition: Location must be a valid location in the world
     * @param loc to set the location of item to
     */
    public void setLocation(Location loc){
        this.location=loc;
    }
    
    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energy) {
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public abstract ImageIcon getImage();

    @Override
    public abstract String getName();

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return 0;
    }

}
