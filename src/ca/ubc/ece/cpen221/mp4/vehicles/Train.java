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
    private static final int INITIAL_FUEL = 200;
    private static final int MAX_ENERGY = 280;
    private static final int COOLDOWN = 3;
    private static final ImageIcon TrainImage = Util.loadImage("train.jpg");

    /**
     * Create a new "Motorcycle" at
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
        //train sticks to the end of other train if it sees it
        //explodes if there are more than 8 trains sticking together
        Direction targetDirection;
        Location nextLocation;
        Location itemLocation;

        Location location=this.getLocation();
        int energy=this.getEnergy();
        
        Set<Item> surroundings =new HashSet<Item>();
        surroundings=world.searchSurroundings(location, world.getWidth());
        
        for(Item item : surroundings){
            if(item.getName().equals("Train")){
                
                itemLocation=item.getLocation();
                if(isAtTrainHead(itemLocation, world)){
                    
                    targetDirection=Util.getDirectionTowards(location, itemLocation);
                    nextLocation = new Location(this.getLocation(), targetDirection);
                    
                    if  (Util.isLocationEmpty(world, nextLocation)) {
                        return new MoveCommand(this, nextLocation);
                    }
                }

            }
            
        }
        
        //gets a random direction and moves there if it is empty and the train's energy is more than 30
        //it looses 5 energies when it moves around
        if  (energy>=30) {
            this.setEnergy(energy-5);
            return MoveCommand.moveInRandomDirection(this, world);
            }
        
        return new WaitCommand();
            

    }
    
    public boolean isAtTrainHead(Location loc, World world){
        int count=0;
        
        Set<Item> surroundings =new HashSet<Item>();
        surroundings=world.searchSurroundings(loc, 1);
        
        for(Item item: surroundings){
            if(item.getName().equals("Train"))
                count++;
        }
        
        if(count==1 || count==0)
            return true;
        
        return false;
    }
    

}
