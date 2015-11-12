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

public class BoxFox extends Nuisance{

	private static final ImageIcon boxFoxImage = Util.loadImage("boxFox.gif");
	
	public BoxFox (Location loc){
		super.location = loc;
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
	public Command getNextAction (World world){
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
			int foxX = closestFox.getLocation().getX();
			int foxY = closestFox.getLocation().getY();
			int myX = getLocation().getX();
			int myY = getLocation().getY();
			Direction direction;
			if(Math.abs(myX - foxX) > Math.abs(myY - foxY)){
				if(foxX > myX)
					direction = Direction.EAST;
				else
					direction = Direction.WEST;
			}
			
			else{
				if(foxY < myY) //north is decreasing y
					direction = Direction.NORTH;
				else
					direction = Direction.SOUTH;
			}
			Location targetLocation = new Location(this.getLocation(), direction);
			if (Util.isLocationEmpty(world, targetLocation)) {
				return new MoveCommand(this, targetLocation);
			}
		}
		
		return MoveCommand.moveInRandomDirection(this, world);
	}
}
