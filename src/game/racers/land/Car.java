/**
 * A class that describes an Car inherits from a race class and describes an Car's race
 * 
 *
 * @version 03.04 03 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Racer class
 */

package game.racers.land;

import game.racers.Racer;
import game.racers.Wheeled;
import game.racers.air.Airplane;
import utilities.EnumContainer.*;

public class Car extends Racer implements LandRacer {
	private static final String CLASS_NAME = "Car";
	private static final int DEFAULT_WHEELS = 4;
	private static final double DEFAULT_MAX_SPEED = 400;
	private static final double DEFAULT_ACCELERATION = 20;
	private static final Color DEFAULT_COLOR = Color.RED;

	private Wheeled wheeled;
	private Engine engine;

	// Constructors

	public Car() {
		this(CLASS_NAME + " #" + SerialNum, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR,
				DEFAULT_WHEELS);
	}

	public Car(String name, double maxSpeed, double acceleration, Color color, int numOfWheels) {
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(numOfWheels);
		this.engine = Engine.FOURSTROKE;
	}

	// Getters and setters
	public Engine getEngine() {
		return engine;
	}

	public boolean setEngine(Engine type) {
		if (this.engine != type) {
			this.engine = type;
			return true;
		} else
			return false;
	}

	// Method- return number of wheels and engine of car
	@Override
	public String describeSpecific() {
		return String.format("Wheels=%d, Type=%s", wheeled.getNumOfWheels(), engine);
	}

	
	// An abstract method that return name class
	@Override
	public String className() {
		return CLASS_NAME;
	}
	@Override
	public Racer clone() {
		
		return new Car(this.getName(),this.getMaxSpeeed(),this.getAcceleration(),this.getColor(),wheeled.getNumOfWheels());
	}
}
