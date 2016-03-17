/**
 * A class to hold the items in a room
 * Extends Inventory
 * 
 * @author James Taylor
 * @version 18.03.2015
 */
public class RoomInventory extends Inventory {

	/**
	 * Lists all items in inventory to screen without weights
	 */
	public void listItems() {
		for (String name : inventory.keySet()) {
			System.out.println("A " + name);
		}
	}

}
