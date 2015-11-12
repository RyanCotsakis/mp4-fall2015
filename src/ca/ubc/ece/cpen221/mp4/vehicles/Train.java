package ca.ubc.ece.cpen221.mp4.vehicles;

import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;

public class Train extends AbstractVehicles {
    private static final int INITIAL_FUEL = 380;
    private static final int MAX_ENERGY = 400;
    private static final int COOLDOWN = 3;
    private static final ImageIcon TrainImage = Util.loadImage("train.gif");
    

    /**
     * Create a new "Train" at
     *      initialLocation. The initialLocation must be
     * valid and empty
     *
     * @param initialLocation
     *            the location where this Motorcycle will be created
     */
    public Train(Location initialLocation) {
        setLocation(initialLocation);
        setEnergy(INITIAL_FUEL);
    }

    @Override
    public ImageIcon getImage() {
        return TrainImage;
    }

    @Override
    public String getName() {
        return "Train";
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public int getMovingRange() {
        return 1;
    }
    
    @Override
    public Command getNextAction(World world) {
        
        //Train hits rabbits and kills them , gaining energy itself
        //its fuel decreases when it is moving around
        Direction direction;
        Location nextLocation; 
        
        Location location=this.getLocation();
        int energy=this.getEnergy();
        
        Set<Item> surroundings = new HashSet<Item>();
        surroundings = world.searchSurroundings(location, 8);
        
        for(Item item : surroundings){
            //if it hits a lion, the lion looses 20 of its energy
            //the fuel of motorcycle increases by 10
            if(item.getName().equals("Rabbit")){
                
                if(location.getDistance(item.getLocation())==1){
                    item.loseEnergy(Integer.MAX_VALUE);
                    
                    if(energy<=MAX_ENERGY)
                        this.setEnergy(energy+10);
                    
                    //goes towards the lion if it can
                    direction=Util.getDirectionTowards(location, item.getLocation());
                    nextLocation = new Location(this.getLocation(), direction);
                    
                    if  (Util.isLocationEmpty(world, nextLocation)) {
                        return new MoveCommand(this, nextLocation);
                        }
                    }
            }
                
        }
        
        //gets a random direction and moves there if it is empty and the motorcycle's energy is more than 30
        //it looses 10 energies when it moves around
        if  (energy>=30) {
            this.setEnergy(energy-10);
            return MoveCommand.moveInRandomDirection(this, world);
            }
        
        return new WaitCommand();
        }

}
