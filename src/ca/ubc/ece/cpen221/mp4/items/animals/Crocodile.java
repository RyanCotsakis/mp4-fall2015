package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

public class Crocodile implements LivingItem{
	private static final ImageIcon crocodileImage = Util.loadImage("crocodile.gif");
	private static final int MEAT_CALORIES = 120;
	private static final int STRENGTH = 20;

	private Location location;
	private boolean isDead;
	
	public Crocodile (Location initialLocation){
		this.location = initialLocation;
		isDead = false;
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
		return crocodileImage;
	}

	@Override
	public String getName ( ){
		return "Crocodile";
	}

	@Override
	public Location getLocation ( ){
		return location;
	}

	@Override
	public int getStrength ( ){
		return STRENGTH;
	}

	//change??
	@Override
	public void loseEnergy (int energy){
		isDead = true;
		
	}

	@Override
	public boolean isDead ( ){
		return isDead;
	}

	@Override
	public int getPlantCalories ( ){
		return 0;
	}

	@Override
	public int getMeatCalories ( ){
		return MEAT_CALORIES;
	}

	@Override
	public int getCoolDownPeriod ( ){
		return Util.RAND.nextInt(4) + 1;
	}

	@Override
	public Command getNextAction (World world){
		// The Frog selects a random direction and check if the next location at
		// the direction is valid and empty. If yes, then it moves to the
		// location, otherwise it waits.
		Direction dir = Util.getRandomDirection();
		Location targetLocation = new Location(this.getLocation(), dir);
		if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
			return new MoveCommand(this, targetLocation);
		}

		return new WaitCommand();
	}

	@Override
	public int getEnergy ( ){
		return MEAT_CALORIES;
	}

	@Override
	public LivingItem breed ( ){
		//Doesn't breed
		return null;
	}

	@Override
	public void eat (Food food){
		//Doesn't eat
	}

}
