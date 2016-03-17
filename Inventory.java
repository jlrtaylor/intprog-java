import java.util.HashMap;

/**
 * A class to represent an inventory
 * 
 * @author James Taylor
 * @version 18.03.2015
 */

public class Inventory {
	
	HashMap<String, Item> inventory;
	
	/**
	 * Creates a new inventory
	 */
	public Inventory(){
		inventory = new HashMap<String, Item>();
	}
	
	/**
	 * Puts an item in the inventory
	 *  
	 * @param name Name of item
	 * @param item Item object
	 */
	public void putItem(String name, Item item){
		inventory.put(name, item);
	}
	
	/**
	 * @return true if empty
	 */
	public boolean isEmpty(){
		return inventory.isEmpty();
	}
	
	/**
	 * Checks if a specified item is in the inventory
	 * 
	 * @param name Name of item
	 * @return true if item is in inventory
	 */
	public boolean containsItem(String name){
		return inventory.containsKey(name);
	}
	
	/**
	 * Removes an item from the inventory, if it exists
	 * 
	 * @param name Item to remove
	 */
	public void removeItem(String name){
		if (inventory.containsKey(name)){
			inventory.remove(name);
		}
	}
	
	/**
	 * Returns an item
	 * 
	 * @param itemName Item to return
	 * @return the item
	 */
	public Item getItem(String itemName) {
		return inventory.get(itemName);
	}
	
	/**
	 * Lists all items in inventory to screen along with weights
	 */
	public void listItems(){
		for (String itemName : inventory.keySet()) {
		    Item item = inventory.get(itemName);
		    System.out.println(itemName + " - " + item.getWeight() + "kg");
		}
	}
	
	
}
