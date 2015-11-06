package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Lion implements LivingItem {

    private static final int INITIAL_ENERGY = 100;
    private static final int MAX_ENERGY = 120;
    private static final int STRENGTH = 350;
    private static final int VIEW_RANGE = 5;
    private static final int MIN_BREEDING_ENERGY = 20;
    private static final ImageIcon LionImage = Util.loadImage("heyena.gif");
    
    private final AI ai;
    
    private Location location;
    private int energy;
    
    
    public Lion (AI HyenaAI, Location initialLocation){
        this.location = initialLocation;
        this.ai=HyenaAI;
    }
    
    @Override
    public void moveTo(Location targetLocation) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getMovingRange() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ImageIcon getImage() {
        return LionImage;
    }

    @Override
    public String getName() {
        return "Lion";
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
        this.energy = this.energy - energy;

    }

    @Override
    public boolean isDead() {
        
    }

    @Override
    public int getPlantCalories() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMeatCalories() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getCoolDownPeriod() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Command getNextAction(World world) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getEnergy() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public LivingItem breed() {
        Lion child = new Lion(ai, location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        // TODO Auto-generated method stub

    }

}
