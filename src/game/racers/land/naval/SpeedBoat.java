/**
 * A class that describes an SpeedBoat inherits from a race class and describes an SpeedBoat's race
 * 
 *
 * @version 03.04 03 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Racer class
 */

package game.racers.land.naval;

import game.racers.Racer;
import game.racers.land.Horse;
import game.racers.naval.NavalRacer;
import utilities.EnumContainer.BoatType;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.Team;

public class SpeedBoat extends Racer implements NavalRacer {

	private static final String CLASS_NAME = "SpeedBoat";
	private static final double DEFAULT_MAX_SPEED = 170;
	private static final double DEFAULT_ACCELERATION = 5;
	private static final Color DEFAULT_COLOR = Color.RED;

	private BoatType type;
	private Team team;

	// Constructors
	// Default
	public SpeedBoat() {
		this(CLASS_NAME + " #" + SerialNum, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR);
	}

	public SpeedBoat(String name, double maxSpeed, double acceleration, Color color) {
		super(name, maxSpeed, acceleration, color);
		this.type = BoatType.SKULLING;
		this.team = Team.DOUBLE;
	}

	// getters and setters
	public BoatType getType() {
		return type;
	}

	public boolean setType(BoatType type) {
		if (this.type != type) {
			this.type = type;
			return true;
		} else
			return false;
	}

	public Team getTeam() {
		return team;
	}

	public boolean setTeam(Team team) {
		if (this.team != team) {
			this.team = team;
			return true;
		} else
			return false;

	}

	// An abstract method that uses the @Override annotation to override the method
	// in the superclass.
	// The purpose of the method is to return specific details about a rowing boat.
	@Override
	public String describeSpecific() {
		return "Type: " + this.type.toString() + ", Team: " + this.team.toString();
	}

	// An abstract method that return name class
	@Override
	public String className() {
		return CLASS_NAME;
	}
	@Override
	public Racer clone() {
		
		return new SpeedBoat(this.getName(),this.getMaxSpeeed(),this.getAcceleration(),this.getColor());
	}
}
