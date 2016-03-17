import becker.robots.City;
import becker.robots.Direction;

/**
 * A class to represent an non player character
 * Extends Character
 * 
 * @author James Taylor
 * @version 18.03.2015
 */

public class NPCharacter extends Character {
	
	/**
	 * Creates a new NPC
	 * 
	 * @param theWorld the map to place the character in
	 * @param startRoom the room the character starts in
	 * @param direction the direction the character should face
	 */
	public NPCharacter(City theWorld, Room startRoom, Direction direction) {
		super(theWorld, startRoom.getNPCStreet(), startRoom.getNPCAvenue(), startRoom, direction);
	}
	
	
	/**
	 * Moves the npc into another room
	 * 
	 * @param newRoom the Room to move into
	 */
    public void moveRoom(Exit exit) {
    	Direction exitDirection = this.getDirection();
    	moveToIntersection(exit.getStreetPos(), exit.getAvenuePos());
    	turn(exitDirection);
    	move();
    	lastRoomDir = oppositeDir(getDirection());
		currentRoom = exit.getNeighbor();
		moveToIntersection(currentRoom.getNPCStreet(), currentRoom.getNPCAvenue());
		turn(exitDirection);
    }
    
}