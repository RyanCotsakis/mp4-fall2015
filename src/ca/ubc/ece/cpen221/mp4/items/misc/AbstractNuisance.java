package ca.ubc.ece.cpen221.mp4.items.misc;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public abstract class AbstractNuisance implements LivingItem {
    
    protected Location location;

    @Override
    public int getStrength ( ){
        return 0;
    }

    @Override
    public void loseEnergy (int energy){
        return;
    }

    @Override
    public boolean isDead ( ){
        return false;
    }

    @Override
    public int getPlantCalories ( ){
        return 0;
    }

    @Override
    public int getMeatCalories ( ){
        return 0;
    }

    @Override
    public int getCoolDownPeriod ( ){
        return 1;
    }

    @Override
    public int getEnergy ( ){
        return 0;
    }

    @Override
    public LivingItem breed ( ){
        return null;
    }

    @Override
    public void eat (Food food){
        return;
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

}
