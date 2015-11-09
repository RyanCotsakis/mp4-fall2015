package ca.ubc.ece.cpen221.mp4.items.misc;

import java.util.*;
import javax.swing.ImageIcon;
import ca.ubc.ece.cpen221.mp4.*;
import ca.ubc.ece.cpen221.mp4.commands.*;
import ca.ubc.ece.cpen221.mp4.items.*;

public class Catapult implements Item, Actor{
	private static final ImageIcon catapultImage = Util.loadImage("catapult.gif");
	private Location location;
	
	public Catapult(Location initialLocation){
		this.location = initialLocation;
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
	public ImageIcon getImage ( ){
		return catapultImage;
	}

	@Override
	public String getName ( ){
		return "Catapult";
	}

	@Override
	public Location getLocation ( ){
		return location;
	}

	@Override
	public int getStrength ( ){
		return Integer.MAX_VALUE;
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
	public int getCoolDownPeriod ( ){
		return 1;
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
		return new WaitCommand();
	}
}
