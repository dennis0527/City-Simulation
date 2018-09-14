/**
 * An exception class for when a city name is entered that does not exist.
 * @author Dennis Heerlein
 * 		ID: 111689989
 * 		email: dennis.heerlein@stonybrook.edu
 *
 */
public class InvalidCityNameException extends Exception {

	public InvalidCityNameException(String message) {
		super(message);
	}
}
