package ca.ubc.ece.cpen221.mp4.items.misc;

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
import ca.ubc.ece.cpen221.mp4.items.*;

public class BoxFox extends AbstractNuisance{

	private static final ImageIcon boxFoxImage = Util.loadImage("boxFox.gif");
	
	private boolean isDead;
	
	public BoxFox (Location loc){
	    setLocation(loc);
		isDead = false;
	}
	
	
	@Override
	public ImageIcon getImage ( ){
		return boxFoxImage;
	}

	@Override
	public String getName ( ){
		return "BoxFox";
	}

	@Override
	public void loseEnergy (int energy){
	    //BoxFox dies when it loses energy
	    isDead = true;
	}

	@Override
	public boolean isDead ( ){
		return false;
	}

    @Override
    public Command getNextAction (World world){
        Location location=this.getLocation();
        
        Set<Item> surroundings = new HashSet<Item>();
        surroundings = world.searchSurroundings(location, world.getWidth()/2);
        int distance = 0;
        Item closestFox = null;
        for(Item item : surroundings){
            if(item.getName().equals("Fox")){
                int actualDistance = item.getLocation().getDistance(location);
                if(actualDistance == 1){
                    return new WaitCommand();
                }
                if(distance == 0 || actualDistance < distance){
                    distance = actualDistance;
                    closestFox = item;
                }
            }
        }
        
        if(distance != 0){
            Direction direction=Util.getDirectionTowards(location, closestFox.getLocation());

            Location targetLocation = new Location(this.getLocation(), direction);
            if (Util.isLocationEmpty(world, targetLocation)) {
                return new MoveCommand(this, targetLocation);
            }
        }
        
        return MoveCommand.moveInRandomDirection(this, world);
    }
}
