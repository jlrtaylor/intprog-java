/**
 * A class to represent exits from a room
 * 
 * @author James Taylor
 * @version 18.03.2015
 */
public class Exit {
	
	private Room neighbor;
	private int avenuePos;
	private int streetPos;
	
	/**
	 * Creates an exit
	 * 
	 * @param room Room exit points at
	 * @param street street position of exit
	 * @param avenue avenue position of exit
	 */
	public Exit(Room room, int street, int avenue){
		neighbor = room;
		avenuePos = avenue;
		streetPos = street;
	}

	/**
	 * @return the room this exit leads to
	 */
	public Room getNeighbor() {
		return neighbor;
	}

	/**
	 * Sets the room this exit leads to
	 * @param neighbor the room this exit should lead to
	 */
	public void setNeighbor(Room neighbor) {
		this.neighbor = neighbor;
	}

	/**
	 * @return the avenue position as integer
	 */
	public int getAvenuePos() {
		return avenuePos;
	}

	/**
	 * Sets the avenue position
	 * 
	 * @param avenuePos the avenue position as integer
	 */
	public void setAvenuePos(int avenuePos) {
		this.avenuePos = avenuePos;
	}

	/**
	 * @return the street position as integer
	 */
	public int getStreetPos() {
		return streetPos;
	}

	/**
	 * Sets the street position
	 * 
	 * @param streetPos the street position as integer
	 */
	public void setStreetPos(int streetPos) {
		this.streetPos = streetPos;
	}

}
