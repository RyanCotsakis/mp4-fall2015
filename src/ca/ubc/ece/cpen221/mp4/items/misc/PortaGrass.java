package ca.ubc.ece.cpen221.mp4.items.misc;

import java.util.*;
import javax.swing.ImageIcon;
import ca.ubc.ece.cpen221.mp4.*;
import ca.ubc.ece.cpen221.mp4.commands.*;
import ca.ubc.ece.cpen221.mp4.items.*;

public class PortaGrass extends Nuisance{
	
	public PortaGrass (Location loc){
		super.location = loc;
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
		return MoveCommand.moveInRandomDirection(this, world);
	}
}
