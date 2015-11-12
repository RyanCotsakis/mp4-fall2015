package ca.ubc.ece.cpen221.mp4.items.misc;

import javax.swing.ImageIcon;
import ca.ubc.ece.cpen221.mp4.*;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.*;

public class Nuisance implements LivingItem{
	
	protected Location location;

	@Override
	public int getStrength ( ){
		return 0;
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
		return 0;
	}

	@Override
	public int getMeatCalories ( ){
		return 0;
	}

	@Override
	public int getCoolDownPeriod ( ){
		return 1;
	}

	@Override
	public int getEnergy ( ){
		return 0;
	}

	@Override
	public LivingItem breed ( ){
		return null;
	}

	@Override
	public void eat (Food food){
		return;
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
	public ImageIcon getImage ( ){
		return null;
	}

	@Override
	public String getName ( ){
		return null;
	}

	@Override
	public Location getLocation ( ){
		return this.location;
	}

	@Override
	public Command getNextAction (World world){
		return null;
	}

}
