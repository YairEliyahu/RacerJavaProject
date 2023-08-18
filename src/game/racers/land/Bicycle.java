/**
 * A class that describes an Bicycle inherits from a race class and describes an Bicycle's race
 * 
 *
 * @version 03.04 03 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Racer class
 */

package game.racers.land;

import game.racers.*;
import game.racers.air.Airplane;
import utilities.EnumContainer.*;

public class Bicycle extends Racer implements LandRacer {
	private static final String CLASS_NAME = "Bicycle";
	private static final int DEFAULT_WHEELS = 2;
	private static final double DEFAULT_MAX_SPEED = 270;
	private static final double DEFAULT_ACCELERATION = 10;
	private static final Color DEFAULT_COLOR = Color.GREEN;

	private Wheeled wheeled;
	private BicycleType type;

	public Bicycle() {
		this(CLASS_NAME + " #" + SerialNum, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR,
				DEFAULT_WHEELS);
	}

	public Bicycle(String name, double maxSpeed, double acceleration, Color color, int numOfWheels) {
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(numOfWheels);
		this.type = BicycleType.MOUNTAIN;
	}

	// Getters and setters
	public BicycleType getType() {
		return type;
	}

	public boolean setType(BicycleType type) {
		if (this.type != type) {
			this.type = type;
			return true;
		} else
			return false;
	}

	// Method- return number of wheels and type of bicycle
	@Override
	public String describeSpecific() {
		return String.format("Wheels=%d, Type=%s", wheeled.getNumOfWheels(), type);
	}

	// An abstract method that return name class
	@Override
	public String className() {
		return CLASS_NAME;
	}
	@Override
	public Racer clone() {
		
		return new Bicycle(this.getName(),this.getMaxSpeeed(),this.getAcceleration(),this.getColor(),wheeled.getNumOfWheels());
	}
}
