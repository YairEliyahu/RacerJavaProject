/**
 * A class that describes an airplane inherits from a race class and describes an airplane race
 * 
 *
 * @version 03.04 03 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Racer class
 */

package game.racers.air;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer.Color;

public class Airplane extends Racer implements AerialRacer {

	private static final String CLASS_NAME = "Airplane";
	private static final int DEFAULT_WHEELS = 3;
	private static final double DEFAULT_MAX_SPEED = 885;
	private static final double DEFAULT_ACCELERATION = 100;
	private static final Color DEFAULT_COLOR = Color.BLACK;

	private Wheeled wheeled;

	// Constructors

	public Airplane() {
		this(CLASS_NAME + " #" + SerialNum, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR,
				DEFAULT_WHEELS);
	}

	public Airplane(String name, double maxSpeed, double acceleration, Color color, int numOfWheels) {
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(numOfWheels);
	}

	// Method- return number of wheels and engine of car
	@Override
	public String describeSpecific() {
		return String.format("Wheels=%d ", wheeled.getNumOfWheels());
	}

	// Method- return name of class
	@Override
	public String className() {
		return CLASS_NAME;
	}

	@Override
	public Racer clone() {
		
		return new Airplane(this.getName(),this.getMaxSpeeed(),this.getAcceleration(),this.getColor(),wheeled.getNumOfWheels());
	}
}
