import becker.robots.City;
import becker.robots.Direction;

/**
 * A class to represent player characters
 * 
 * @author James Taylor
 * @version 18.03.2015
 *
 */

public class PlayerCharacter extends Character {
	
	public final static double DefaultSpeed = 4.0;
	
	/**
	 * Creates a new PC
	 * 
	 * @param theWorld the map to place them in
	 * @param startRoom the room to start in
	 * @param direction the direction to face
	 */
	public PlayerCharacter(City theWorld, Room startRoom, Direction direction) {
		super(theWorld, startRoom, direction);
		this.setSpeed(DefaultSpeed);
	}

}