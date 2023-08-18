/**
 * This department designs an aerial arena template
 * 
 *
 * @version 04.04 04 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Arena class
 */

package game.arenas.air;
import utilities.EnumContainer.*; 
import game.racers.Racer;
import game.racers.air.AerialRacer;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;

public class AerialArena extends Arena {
	
	private final static double DEFAULT_FRICTION = 0.4;
	private final static int DEFAULT_MAX_RACERS =6;
	private final static int DEFAULT_LENGTH =1500;
	
	private Vision vision; 
	private Weather weather;
	private Height height;
	private Wind wind;

	//Constructors
	//Default
	
	public AerialArena(){
		this(DEFAULT_MAX_RACERS,DEFAULT_LENGTH); 
		this.vision= Vision.SUNNY; 
		this.height= Height.HIGH;
		this.weather= Weather.DRY;
		this.wind = Wind.HIGH;
	}
	
	public AerialArena(double length, int maxRacers) {
		super(length, maxRacers, DEFAULT_FRICTION);
		this.vision = Vision.SUNNY;
        this.weather = Weather.DRY;
        this.height = Height.HIGH;
        this.wind = Wind.HIGH;
	}
	
	//Getters and Setters


	public Vision getVision() {
		return vision;
	}

	public boolean setVision(Vision vision) {
		if(this.vision != vision) {
			this.vision = vision;
			return true;
		}
		else
			return false;
	}

	public Weather getWeather() {
		return weather;
	}

	public boolean setWeather(Weather weather) {
		if(this.weather != weather) {
			this.weather = weather;
			return true;
		}
		else
			return false;
	}

	public Height getHeight() {
		return height;
	}

	public boolean setHeight(Height height) {
		if(this.height != height) {
			this.height = height;
			return true;
		}
		else
			return false;
	}

	public Wind getWind() {
		return wind;
	}

	public boolean setWind(Wind wind) {
		if(this.wind != wind) {
			this.wind = wind;
			return true;
		}
		else
			return false;
	}

	// A method that first checks if the object is an air contest style, then adds it. 
	//otherwise raises an exception.
	@Override
	public void addRacer(Racer newRacer) throws RacerLimitException,RacerTypeException
	{
		if (newRacer instanceof AerialRacer)  
		{
			super.addRacer(newRacer);
		}
		else
			throw new RacerTypeException(newRacer.className(),"AerialArena");
		
	}
}
