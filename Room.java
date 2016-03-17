/**
 * A class to define rooms in the game
 * All rooms are 3 avenues tall and 3 streets wide
 * 
 * @author James Taylor
 */

import java.util.HashMap;
import java.util.HashSet;

import becker.robots.City;
import becker.robots.Direction;
import becker.robots.Wall;

public class Room  {

	private String description;
	private String longDescription;
	private int avenueCorner;
	private int streetCorner;
	private HashMap <Direction, Exit> exits;
	private HashMap <Direction, Wall> exitWalls;
	private RoomInventory floor;
	
	/**
	 * Creates a new room
	 * 
	 * @param avenue The top left position of the room
	 * @param street The top left position of the room
	 * @param description The text displayed when the player enters the room 
	 */
	public Room(int street, int avenue, String description, String longDescription) {
		streetCorner = street;
		avenueCorner = avenue;
        exits = new HashMap<Direction, Exit>();
        exitWalls = new HashMap<Direction, Wall>();
        this.description = description;
        this.longDescription = longDescription;
        floor = new RoomInventory();
	}
	
	public Room(int street, int avenue, String description) {
		this(street, avenue, description, "");
	}
	
	/**
	 * @return the top avenue position of a room
	 */
	public int getavenueCorner() {
		return avenueCorner;
	}
	
	/**
	 * @return the top street position of a room
	 */
	public int getstreetCorner() {
		return streetCorner;
	}
	
	/**
	 * @return the default player avenue position
	 */
	public int getPlayerAvenue() {
		return avenueCorner+1;
	}
	
	/**
	 * @return the default player street position
	 */
	public int getPlayerStreet() {
		return streetCorner+1;
	}
	
	/**
	 * @return the default NPC avenue position
	 */
	public int getNPCAvenue() {
		return avenueCorner+2;
	}
	
	/**
	 * @return the default NPC avenue position
	 */
	public int getNPCStreet() {
		return streetCorner;
	}
	
    /**
     * Puts an item in room 
     * 
     * @param itemName the items name
     * @param the item object
     */
    public void addItem(String itemName, Item item) {
    	floor.putItem(itemName, item);
    }
    
    /**
     * Lists the items in the room
     */
    public void printInventory() {
    	floor.listItems();
    }
    
    /**
     * @return the inventory of the room
     */
    public Inventory getInventory() {
    	return floor;
    }
    
    /**
     * Checks to see if an item is in the room
     * 
     * @param itemName The item you want to check for
     * @return true if character is has item
     */
    public boolean hasItem(String itemName) {
    	return floor.containsItem(itemName);
    }

	/**
	 * Draws the room on the map
	 * 
	 * @param theWorld The map the room is being drawn onto
	 */
	public void draw(City theWorld) {
		drawCorners(theWorld);
		drawExits(theWorld);
	}

	/**
	 * Draws the corners of the room
	 * 
	 * @param theWorld The map the walls are being drawn onto
	 */
	protected void drawCorners(City theWorld) {
		HashSet<Wall> walls = new HashSet<Wall>();
		walls.add(new Wall(theWorld, streetCorner, avenueCorner, Direction.NORTH));
		walls.add(new Wall(theWorld, streetCorner, avenueCorner, Direction.WEST));
		
		walls.add(new Wall(theWorld, streetCorner, avenueCorner+2, Direction.NORTH));
		walls.add(new Wall(theWorld, streetCorner, avenueCorner+2, Direction.EAST));
		
		walls.add(new Wall(theWorld, streetCorner+2, avenueCorner, Direction.SOUTH));
		walls.add(new Wall(theWorld, streetCorner+2, avenueCorner, Direction.WEST));
		
		walls.add(new Wall(theWorld, streetCorner+2, avenueCorner+2, Direction.SOUTH));
		walls.add(new Wall(theWorld, streetCorner+2, avenueCorner+2, Direction.EAST));
	}
	
	/**
	 * Works out which exits are unused then blocks them with a wall
	 * 
	 * @param theWorld The map the walls are being drawn onto
	 */
	protected void drawExits(City theWorld) {
		Boolean exitNorth = false;
		Boolean exitEast = false;
		Boolean exitSouth = false;
		Boolean exitWest = false;
		for (Direction exit : exits.keySet()) {
			if (exit == Direction.NORTH) {
				exitNorth = true;
			} else if (exit == Direction.EAST) {
				exitEast = true;
			} else if (exit == Direction.SOUTH) {
				exitSouth = true;
			} else if (exit == Direction.WEST) {
				exitWest = true;
			}
		}
		if (!exitNorth) {
			exitWalls.put(Direction.NORTH, new Wall(theWorld, streetCorner, avenueCorner+1, Direction.NORTH));
		}
		if (!exitEast) {
			exitWalls.put(Direction.EAST, new Wall(theWorld, streetCorner+1, avenueCorner+2, Direction.EAST));
		}
		if (!exitSouth) {
			exitWalls.put(Direction.SOUTH, new Wall(theWorld, streetCorner+2, avenueCorner+1, Direction.SOUTH));
		}
		if (!exitWest) {
			exitWalls.put(Direction.WEST, new Wall(theWorld, streetCorner+1, avenueCorner, Direction.WEST));
		}
	}
	
	/**
	 * Creates an exit
	 * 
	 * @param dir direction the exit is in
	 * @param room room to connect
	 */
    @SuppressWarnings("incomplete-switch")
	public void addExit(Direction dir, Room room) {
    	Exit newExit = null;
    	switch (dir) {
    	case NORTH:
    		newExit = new Exit(room, streetCorner, avenueCorner+1);
    		break;
    	case EAST:
    		newExit = new Exit(room, streetCorner+1, avenueCorner+2);
    		break;
    	case SOUTH:
    		newExit = new Exit(room, streetCorner+2, avenueCorner+1);
    		break;
    	case WEST:
    		newExit = new Exit(room, streetCorner+1, avenueCorner);
    		break;
    	}
        exits.put(dir, newExit);
    }
    
    /**
     * Returns exit in a given direction
     * 
     * @param direction direction to check
     * @return the exit in that direction
     */
    public Exit getExit(Direction direction) {
        return exits.get(direction);
    }
 
    /**
     * Returns room through exit in a given direction
     * 
     * @param direction direction to check
     * @return the room in that direction, null if no exit exists
     */
    public Room getExitRoom(Direction direction) {
    	if (exits.get(direction) == null){
    		return null;
    	} else {
        	return exits.get(direction).getNeighbor();
    	}
    }
	
    /**
     * @return the description of the room
     */
	public String getDescription() {
		return description;
	}
       
	/**
	 * Sets the description of the room
	 * @param newDescription the new description
	 */
    public void setDescription(String newDescription) {
    	description = newDescription;
    }
	
    /**
     * @return this object
     */
	public Room getRoom() {
		return this;
	}

	/**
	 * @return the long text description of a room
	 */
	public String getLongDescription() {
		return longDescription;
	}

	/**
	 * Sets the long text description of a room
	 * @param longDescription the new text
	 */
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	/**
	 * @return the exit walls of this room in a HashMap
	 */
	protected HashMap<Direction, Wall> getExitWalls() {
		return exitWalls;
	}
	
	/**
	 * 
	 * @param direction the exit wall in a particular direction
	 * @return the wall
	 */
	protected Wall getExitWallDirection(Direction direction) {
		Wall wall = exitWalls.get(direction);
		return wall;
	}

}
