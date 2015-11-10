package ca.ubc.ece.cpen221.mp4.items.misc;

import java.util.*;
import javax.swing.ImageIcon;
import ca.ubc.ece.cpen221.mp4.*;
import ca.ubc.ece.cpen221.mp4.commands.*;
import ca.ubc.ece.cpen221.mp4.items.*;

public class PortaGrass implements LivingItem{

	Location location;
	
	public PortaGrass (Location location){
		this.location = location;
	}
	
	@Override
	public ImageIcon getImage ( ){
		return Grass.grassImage;
	}

	@Override
	public String getName ( ){
		return "grass";
	}

	@Override
	public Location getLocation ( ){
		return location;
	}

	@Override
	public int getStrength ( ){
		return 5;
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
	public int getPlantCalories ( ){
		return 10;
	}

	@Override
	public int getMeatCalories ( ){
		return 0;
	}

	@Override
	public void moveTo (Location targetLocation){
		this.location = targetLocation; 
	}

	@Override
	public int getMovingRange ( ){
		return 1;
	}

	@Override
	public int getCoolDownPeriod ( ){
		return 1;
	}

	@Override
	public Command getNextAction (World world){
		Set<Item> surroundings = new HashSet<Item>();
		surroundings = world.searchSurroundings(location, 3);
		for(Item item : surroundings){
			if(item.getName().equals("Rabbit")){
				if(item.getLocation().getDistance(location) == 1){
					item.loseEnergy(Integer.MAX_VALUE);
				}
				return new WaitCommand();
			}
		}
		Direction dir = Util.getRandomDirection();
		Location targetLocation = new Location(this.getLocation(), dir);
		if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
			return new MoveCommand(this, targetLocation);
		}
		return new WaitCommand();
	}

	@Override
	public int getEnergy ( ){
		return 10;
	}

	@Override
	public LivingItem breed ( ){
		return null;
	}

	@Override
	public void eat (Food food){
	}
}
