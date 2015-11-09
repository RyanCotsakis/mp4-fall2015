package ca.ubc.ece.cpen221.mp4.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;

public class Helicopter implements MoveableItem, Actor {
    private static final int INITIAL_ENERGY = 450;
    private static final int MAX_ENERGY = 600;
    private static final int STRENGTH = 500;
    private static final int COOLDOWN = 2;
    private static final ImageIcon helicopterImage = Util.loadImage("fox.gif");

    private Location location;
    private int energy;

    /**
     * Create a new "Helicopter" at
     *      initialLocation. The initialLocation must be
     * valid and empty
     *
     * @param foxAI
     *            the AI designed for foxes
     * @param initialLocation
     *            the location where this Fox will be created
     */
    public Helicopter(Location initialLocation) {
        this.location = initialLocation;

        this.energy = INITIAL_ENERGY;
    }

    @Override
    public ImageIcon getImage() {
        return helicopterImage;
    }

    @Override
    public String getName() {
        return "Helicopter";
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
        // should it die?
        return false;
    }

    @Override
    public int getPlantCalories() {
        // not a plant
        return 0;
    }

    @Override
    public int getMeatCalories() {
        // not an animal
        return 0;
    }

    @Override
    public void moveTo(Location targetLocation) {
        this.location=targetLocation;

    }

    @Override
    public int getMovingRange() {
        return 2;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public Command getNextAction(World world) {
        // TODO Auto-generated method stub
        return null;
    }

}
