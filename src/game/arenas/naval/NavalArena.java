/**
 * The arena Naval class which describes the design of the arena
 * 
 *
 * @version 02.04 02 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Arena class
 */


package game.arenas.naval;

import utilities.EnumContainer.*;
import game.racers.Racer;
import game.racers.naval.NavalRacer;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.arenas.Arena;

public class NavalArena extends Arena {

	private final static double DEFAULT_FRICTION = 0.7;
	private final static int DEFAULT_MAX_RACERS = 5;
	private final static int DEFAULT_LENGTH = 1000;

	private WaterSurface surface;
	private Water water;
	private Body body;

	// Constructors
	// Default

	public NavalArena() {
		this(DEFAULT_MAX_RACERS, DEFAULT_LENGTH);
		this.surface = WaterSurface.FLAT;
		this.water = Water.SWEET;
		this.body = Body.LAKE;
	}

	public NavalArena(double length, int maxRacers) {
		super(length, maxRacers, DEFAULT_FRICTION);
		this.surface = WaterSurface.FLAT;
		this.water = Water.SWEET;
		this.body = Body.LAKE;
	}

	// Getters and Setters

	public WaterSurface getSurface() {
		return surface;
	}

	public boolean setSurface(WaterSurface surface) {
		if (this.surface != surface) {
			this.surface = surface;
			return true;
		} else
			return false;
	}

	public Water getWater() {
		return water;
	}

	public boolean setWater(Water water) {
		if (this.water != water) {
			this.water = water;
			return true;
		} else
			return false;
	}

	public Body getBody() {
		return body;
	}

	public boolean setBody(Body body) {
		if (this.body != body) {
			this.body = body;
			return true;
		} else
			return false;
	}

	
	// A method that first checks if the object is an air contest style, then adds it. 
	//otherwise raises an exception.
	@Override
	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if (newRacer instanceof NavalRacer) {
			super.addRacer(newRacer);
		} else
			throw new RacerTypeException(newRacer.className(), "NavalArena");
	}

}
