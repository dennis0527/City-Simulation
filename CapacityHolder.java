import java.util.LinkedList;

/**
 * The data structure that is returned for use with maximum flow.
 * @author Dennis Heerlein
 * 		ID: 111689989
 * 		email: dennis.heerlein@stonybrook.edu
 *
 */
public class CapacityHolder {
	LinkedList<String> list = new LinkedList<>();
	private int cap;
	
	/**
	 * Sets the capacity of the flow
	 * @param cap Capacity of the flow
	 */
	public void setCap(int cap) {
		this.cap = cap;
	}
	
	/**
	 * Returns the capacity of the flow
	 * @return The capacity of the flow
	 */
	public int getCap() {
		return cap;
	}
	
}
