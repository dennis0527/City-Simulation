import java.util.*;

/**
 * This class represents a city object that is on the island. It has a name and variables to help it be searched.
 * @author Dennis Heerlein
 * 		ID: 111689989
 * 		email: dennis.heerlein@stonybrook.edu
 *
 */
public class City implements Comparable {

	private HashMap<String, Integer> neighbors = new HashMap<>();
	private String name;
	private boolean visited;
	private boolean discovered;
	private HashMap<String, Integer> tempNeighbors = new HashMap<>();
	private int dist;
	private City prev;
	
	public City(String name) {
		this.name = name;
	}
	
	@Override
	public int compareTo(Object o) {
		City b = (City) o;
		return (getName().compareTo(b.getName()));
	}
	
	/**
	 * Returns the name of the city
	 * @return the name of the city
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Changes the visit variable of the city to true
	 */
	public void visit() {
		visited = true;
	}
	
	/**
	 * Changes the visit variable of the city to false
	 */
	public void unvisit() {
		visited = false;
	}
	
	/**
	 * Changes the discover variable of the city to false
	 */
	public void undiscover() {
		discovered = false;
	}
	
	/**
	 * Changes the discover variable of the city to true
	 */
	public void discover() {
		discovered = true;
	}
	
	/**
	 * Returns true if the city has been visited, otherwise returns false
	 * @return true if the city has been visited, otherwise returns false
	 */
	public boolean visited() {
		return visited;
	}
	
	/**
	 * Returns true if the city has been discovered, otherwise returns false
	 * @return true if the city has been discovered, otherwise returns false
	 */
	public boolean discovered() {
		return discovered;
	}
	
	/**
	 * Adds a neighbor to a city's neighbor map
	 * @param n The new neighbor
	 * @param val The int value of the flow from the city to its neighbor
	 */
	public void addNeighbor(City n, int val) {
		neighbors.put(n.getName(), val);
	}

	/**
	 * Returns the Hash Map representing a city's neighbors
	 * @return The city's neighbors in a Hash Map
	 */
	public HashMap<String, Integer> getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Resets the temporary neighbors Hash Map of the city
	 */
	public void resetTempNeighbors() {
		tempNeighbors = new HashMap<>();
	}
	
	/**
	 * Adds a neighbor to the temporary neighbors Hash Map
	 * @param n The new neighbor to be added
	 * @param val The int value of the flow from the city to its neighbor
	 */
	public void addTempNeighbor(City n, int val) {
		tempNeighbors.put(n.getName(), val);
	}
	
	/**
	 * Returns the temporary neighbors Hash Map of a city
	 * @return the temporary neighbors Hash Map of a city
	 */
	public HashMap<String, Integer> getTempNeighbors() {
		return tempNeighbors;
	}
	

	/**
	 * Returns the distance of a city
	 * @return the distance of a city
	 */
	public int getDist() {
		return dist;
	}

	/**
	 * Sets the distance of a city
	 * @param dist The distance of a city
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}

	/**
	 * Gets the previous city of a city
	 * @return the previous city of a city
	 */
	public City getPrev() {
		return prev;
	}

	/**
	 * Sets the previous city of a city
	 */
	public void setPrev(City prev) {
		this.prev = prev;
	}
	
}
