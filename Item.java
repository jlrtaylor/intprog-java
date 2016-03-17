/**
 * A class to represent items
 * 
 * @author James Taylor
 * @version 18.03.2015
 */
public class Item {
	
	private int weight;
	
	/**
	 * Creates a new item
	 * 
	 * @param weight the weight of the item
	 */
	public Item(int weight){
		this.setWeight(weight);
	}

	/**
	 * @return the weight of an item
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Sets the weight of an item
	 * 
	 * @param weight amount to set weight to
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
