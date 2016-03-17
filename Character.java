import becker.robots.*;

/**
 * A class to represent characters in the game
 * 
 * @author James Taylor
 * @version 18.03.2015
 */

public class Character extends RobotSE{
	
	protected Room currentRoom;
	protected Direction lastRoomDir;
	public final static int MaxHealth = 10;
	private int currentHealth;
	public final static int MaxWeight = 30;
	private int currentWeight;
	private Inventory bag;
	
	/**
	 * Creates a new character
	 * 
	 * @param theWorld The map the character is created on
	 * @param startRoom The room the character starts in
	 * @param direction The facing the character starts with
	 */
	public Character(City theWorld, Room startRoom, Direction direction) {
		super(theWorld, startRoom.getPlayerStreet(), startRoom.getPlayerAvenue(), direction);
		currentHealth = MaxHealth;
		currentRoom = startRoom;
		bag = new Inventory();
	}
	
	protected Character(City theWorld, int street, int avenue, Room startRoom, Direction direction) {
		super(theWorld, street, avenue, direction);
		currentHealth = MaxHealth;
		currentRoom = startRoom;
		bag = new Inventory();
	}
	
 
	/**
	 * @return the room the character is in
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	
	/**
	 * Puts the player in a new room
	 * 
	 * @param newRoom the room to set as a new room
	 */
	public void setCurrentRoom(Room newRoom) {
		currentRoom = newRoom;
		lastRoomDir = null;
	}
	
	/**
	 * @return the direction the previous room is in
	 */
	public Direction getLastRoomDir() {
		return lastRoomDir;
	}
	
	/**
	 * @return true if the character is alive 
	 */
	public boolean isAlive() {
		if (currentHealth > 0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Lowers the characters health by an amount
	 * 
	 * Characters have 10 health
	 * 
	 * @param amount of damage taken after reduction
	 */
    public int damage(int amount) {
    	if (this.hasItem("tacvest")) {
    		amount -= 3;
    	}
    	currentHealth -= amount;
    	if (!this.isAlive()) {
    		setCurrentRoom(null);
    	}
    	return amount;
    }
       

	/**
	 * Moves the character into another room
	 * 
	 * @param the Room to move into
	 */
    public void moveRoom(Exit exit) {
    	Direction exitDirection = this.getDirection();
    	moveToIntersection(exit.getStreetPos(), exit.getAvenuePos());
    	turn(exitDirection);
    	move();
    	lastRoomDir = oppositeDir(getDirection());
		currentRoom = exit.getNeighbor();
		moveToIntersection(currentRoom.getPlayerStreet(), currentRoom.getPlayerAvenue());
		turn(exitDirection);
    }
    
    /**
     * Gets the opposite compass direction
     * 
     * @param direction you want to check
     * @return the opposite direction
     */
    protected Direction oppositeDir(Direction direction){
    	if (direction == Direction.NORTH){
    		return Direction.SOUTH;
    	} else if (direction == Direction.EAST){
    		return Direction.WEST;
    	} else if (direction == Direction.SOUTH){
    		return Direction.NORTH;
    	} else {
    		return Direction.EAST;
    	}
    }
    
    /**
     * Moves the character to an intersection 
     * Will probably crash the character if the intersection is in a different room 
     * 
     * @param targetStreet The street coordinate
     * @param targetAve The avenue coordinate
     */
    protected void moveToIntersection(int targetStreet, int targetAve){
    	while (this.getAvenue() != targetAve) {
    	
    		if (this.getAvenue() < targetAve) {
    			turn(Direction.EAST);
    		} else if (this.getAvenue() > targetAve) {
    			turn(Direction.WEST);
    		}
     		move();
    	}
    	while (this.getStreet() != targetStreet) {
    		if (this.getStreet() < targetStreet) {
    			turn(Direction.SOUTH);
    		} else if (this.getStreet() > targetStreet) {
    			turn(Direction.NORTH);
    		}
     		move();
    	}
    }
    
    /**
     * Turns the character to face a certain direction
     * 
     * @param targetDirection the direction desired
     */
    @SuppressWarnings("incomplete-switch")
	public void turn(Direction targetDirection){
    	switch (getDirection()) {
    	
    		case NORTH:
    			switch (targetDirection) {
    				case NORTH:
    					break;
    				case EAST:
    					turnRight();
    					break;
    				case SOUTH:
    					turnAround();
    					break;
    				case WEST:
    					turnLeft();
    					break;
    			}
    		break;
    			
    		case EAST:
    			switch (targetDirection) {
					case NORTH:
						turnLeft();
					case EAST:
						break;
					case SOUTH:
						turnRight();
						break;
					case WEST:
						turnAround();
						break;
    			}
    		break;
    			
    		case SOUTH:
    			switch (targetDirection) {
					case NORTH:
						turnAround();
						break;
					case EAST:
						turnLeft();
						break;
					case SOUTH:
						break;
					case WEST:
						turnRight();
						break;
    			}	
    		break;
    			
    		case WEST:
    			switch (targetDirection) {
					case NORTH:
						turnRight();
						break;
					case EAST:
						turnAround();
						break;
					case SOUTH:
						turnLeft();
						break;
					case WEST:
						break;
    			}
    		break;
    	}
    }
   
    /**
     * Puts an item in the characters inventory 
     * 
     * @param itemName the items name
     * @param the item object
     * @return true if can pick up
     */
    public boolean addItem(String itemName, Item item) {
    	int newWeight = currentWeight + item.getWeight();
    	 if (newWeight > MaxWeight) {
    		 return false;
    	 } else {
    		 currentWeight = newWeight;
    		 bag.putItem(itemName, item);
    		 return true;
    	 }
    }
    
    /**
     * Attempts to remove an item from the characters inventory if the character has the item.
     * 
     * @param itemName the item to attempt to remove
     * @return true if the item exists and is removed
     */
    public boolean removeItem(String itemName) {
    	if (this.hasItem(itemName)) {
    		currentWeight = currentWeight - bag.getItem(itemName).getWeight();
    		bag.removeItem(itemName);
    		return true;
    	} else {
    		return false;
    	}
    	
    }

    /**
     * Prints a the items in the players inventory along with their weights
     */
    public void printInventory() {
    	bag.listItems();
    }
    
    /**
     * Checks to see if a character has an item in their inventory
     * 
     * @param itemName The item you want to check for
     * @return true if character has item
     */
    public boolean hasItem(String itemName) {
    	return bag.containsItem(itemName);
    }

    /**
     * @return the weight of items in the characters inventory
     */
	public int getWeight() {
		return currentWeight;
	}

	/**
	 * @return the current health of the character
	 */
	public int getHealth() {
		return currentHealth;
	}
	
	
	/**
	 * @return the characters inventory
	 */
	public Inventory getInventory() {
		return bag;
	}
    
    
}