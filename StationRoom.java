import java.awt.Color;

import becker.robots.City;
import becker.robots.Direction;
import becker.robots.Wall;

/**
 * A class to represent a room with a special exit#
 * 
 * @author James Taylor
 * @version 18.03.2015
 *
 */

public class StationRoom extends Room {
	
	private Direction trainExit;

	/**
	 * Creates a new room with a secret exit
	 * 
	 * @param street the street location of the top corner
	 * @param avenue the avenue location of the top corner
	 * @param description the description
	 * @param longDescription the long description
	 * @param trainExit the wall the special exit is located on
	 */
	public StationRoom(int street, int avenue, String description, String longDescription, Direction trainExit) {
		super(street, avenue, description, longDescription);
		this.trainExit = trainExit;
	}

	public StationRoom(int street, int avenue, String description, Direction trainExit) {
		this(street, avenue, description, "", trainExit);

	}
	

	/**
	 * Draws the room
	 * 
	 * @param theWorld the map to draw on
	 */
	public void draw(City theWorld) {
		drawCorners(theWorld);
		drawExits(theWorld);
		makeDoorPassable();
	}
	
	/**
	 * Makes exits of a room not crash the characters
	 */
	public void makeDoorPassable() {
		Wall wall = this.getExitWallDirection(this.trainExit);
		wall.setBlocksExit(false, false, false, false);
		wall.setColor(Color.RED);
		wall.setBlocksEntry(false, false, false, false);
	}

}
