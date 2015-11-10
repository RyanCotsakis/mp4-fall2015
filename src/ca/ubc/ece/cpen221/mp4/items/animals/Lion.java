package ca.ubc.ece.cpen221.mp4.items.animals;

import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Lion implements LivingItem {

    private static final int INITIAL_ENERGY = 100;
    private static final int MAX_ENERGY = 150;
    private static final int STRENGTH = 200;
    private static final int VIEW_RANGE = 10;
    private static final int MAX_NUM_OFFSPRINGS=2;
    private static final int MIN_BREEDING_ENERGY=130;
    private static final ImageIcon LionImage = Util.loadImage("lion.jpg");
    
    
    private Location location;
    private int energy;
    
    private int numOfChildren=0;
    
    public Lion (Location initialLocation){
        this.location = initialLocation;
        
        this.energy=INITIAL_ENERGY;
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
        //only loses energy by 10 if it is hit by a vehicle
        this.energy -= energy;

    }

    @Override
    public boolean isDead() {
        return energy<=0;
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
    public int getCoolDownPeriod() {
        //cool down faster if it has more energy
        if(this.energy>=MAX_ENERGY+30)
            return 3;
        
        else return 5;
    }

    @Override
    public Command getNextAction(World world) {
        Set<Item> surroundings = new HashSet<Item>();
        surroundings = world.searchSurroundings(location, VIEW_RANGE);
        
        for(Item item: surroundings){
            if(item.getName().equals("Rabbit") || item.getName().equals("Crocodile") 
                    ||item.getName().equals("Fox")){
                if(this.getLocation().getDistance(item.getLocation())==1)
                    return new EatCommand(this, item);
                    
            }
        }
        
        //if it has less than the maximum children it can have in its life time and also if it has minimum required
        //energy it breeds in a random adjacent location that is empty;
        Location breedLocation=Util.getRandomEmptyAdjacentLocation(world, location);
        if(this.getEnergy()>=MIN_BREEDING_ENERGY && this.numOfChildren<MAX_NUM_OFFSPRINGS && breedLocation!=null)
            return new BreedCommand(this,breedLocation);
        
        //gets a random direction and moves there if it is empty
        Direction direction = Util.getRandomDirection();
        Location nextLocation = new Location(this.getLocation(), direction);
        if  (Util.isLocationEmpty(world, nextLocation) && Util.isValidLocation(world, nextLocation)) {
            return new MoveCommand(this, nextLocation);
        }

        return new WaitCommand();
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public LivingItem breed() {
        
        Lion child = new Lion(location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        numOfChildren++;
        
        return child;
    }

    @Override
    public void eat(Food food) {
        // Note that energy does not exceed energy limit.
        energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
    }

}
