import becker.robots.City;
import becker.robots.Direction;

/**
 * A class to represent an enemy character
 * Extends NPCharacter
 * 
 * @author James Taylor
 * @version 18.03.2015
 */

public class EnemyCharacter extends NPCharacter {
	
	public final static double DefaultSpeed = 12.0;
	
	/**
	 * Creates a new enemy character
	 * 
	 * @param theWorld The map the character is created on
	 * @param startRoom The room the character starts in
	 * @param direction The facing the character starts with
	 * @param patrol the direction to patrol
	 */
	public EnemyCharacter(City theWorld, Room startRoom, Direction direction, Direction patrol) {
		super(theWorld, startRoom, direction);
		lastRoomDir = patrol;
		this.setSpeed(DefaultSpeed);
	}

}