/**
 * A class that describes an Helicopter inherits from a race class and describes an Helicopter's race
 * 
 *
 * @version 03.04 03 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Racer class
 */

package game.racers.air;

import game.racers.Racer;
import utilities.EnumContainer.Color;

public class Helicopter extends Racer implements AerialRacer {
	private static final String CLASS_NAME = "Helicopter";
	private static final double DEFAULT_MAX_SPEED = 400;
	private static final double DEFAULT_ACCELERATION = 50;
	private static final Color DEFAULT_COLOR = Color.BLUE;

	// Constructors

	public Helicopter() {
		this(CLASS_NAME + " #" + SerialNum, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR);
	}

	public Helicopter(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
	}

	// Method- return number of wheels and engine of car
	@Override
	public String describeSpecific() {
		return "";
	}

	// Method- return name of class
	@Override
	public String className() {
		return CLASS_NAME;
	}
	
	@Override
	public Racer clone() {
		
		return new Helicopter(this.getName(),this.getMaxSpeeed(),this.getAcceleration(),this.getColor());
	}
}
