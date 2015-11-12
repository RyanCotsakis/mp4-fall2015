package ca.ubc.ece.cpen221.mp4.items.misc;

import java.util.*;
import javax.swing.ImageIcon;
import ca.ubc.ece.cpen221.mp4.*;
import ca.ubc.ece.cpen221.mp4.commands.*;
import ca.ubc.ece.cpen221.mp4.items.*;

public class Catapult extends AbstractNuisance{
	private static final ImageIcon catapultImage = Util.loadImage("catapult.gif");
	
	public Catapult(Location loc){
	    setLocation(loc);
	}
	
	@Override
	public ImageIcon getImage ( ){
		return catapultImage;
	}

	@Override
	public String getName ( ){
		return "Catapult";
	}

	@Override
	public Command getNextAction (World world){
		Set<Item> surroundings = new HashSet<Item>();
		surroundings = world.searchSurroundings(location, 1);
		if(surroundings.size() > 1){
			for(Item item : surroundings){
				if(!item.getLocation().equals(this.location) && item instanceof MoveableItem){
					MoveableItem itemCopy = (MoveableItem) item;
					itemCopy.moveTo(Util.getRandomEmptyLocation(world));
				}
			}
		}
		return MoveCommand.moveInRandomDirection(this, world);
	}

}
