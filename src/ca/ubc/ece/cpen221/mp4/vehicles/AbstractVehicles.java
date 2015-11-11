package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;

public abstract class AbstractVehicles implements MoveableItem, Actor {
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
    
    public int accelerate(){
        return 0;
    }
    

}
