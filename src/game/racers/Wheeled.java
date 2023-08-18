/**
 * Wheel class that describes the amount of vehicle tire data
 * 
 *
 * @version 03.04 03 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	
 */

package game.racers;

public class Wheeled {

	private int numOfWheels;

	// Constructors
	public Wheeled(int numOfWheels) {
		super();
		this.numOfWheels = numOfWheels;
	}

	public Wheeled() {
		this.numOfWheels = 0;
	}

	// getters and setters
	public int getNumOfWheels() {
		return numOfWheels;
	}

	public boolean setNumOfWheels(int numOfWheels) {
		if (this.numOfWheels != numOfWheels) {
			this.numOfWheels = numOfWheels;
			return true;
		} else
			return false;
	}

	// An abstract method that return number of wheels

	public String describeSpecific() {
		return "Number of wheels: " + this.numOfWheels;
	}

}
