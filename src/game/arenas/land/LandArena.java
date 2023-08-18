/**
 * The arena land class which describes the design of the arena
 * 
 *
 * @version 02.04 02 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Arena class
 */


package game.arenas.land;
import utilities.EnumContainer.*; 
import game.racers.Racer;
import game.racers.land.LandRacer;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
public class LandArena extends Arena{

	private final static double DEFAULT_FRICTION = 0.5;
	private final static int DEFAULT_MAX_RACERS =8;
	private final static int DEFAULT_LENGTH =800;
	
	private Coverage coverage;
	private LandSurface surface;
	
	//Constructors
	//Default
			
	public LandArena(){
		this(DEFAULT_MAX_RACERS,DEFAULT_LENGTH); 
			this.surface= LandSurface.FLAT; 
			this.coverage= Coverage.GRASS;
		}
			
	public LandArena(double length, int maxRacers) {
		super(length, maxRacers, DEFAULT_FRICTION);
			this.surface= LandSurface.FLAT; 
			this.coverage= Coverage.GRASS;
		}

	//Getters and Setters

	
	public Coverage getCoverage() {
		return coverage;
	}

	public boolean setCoverage(Coverage coverage) {
		if(this.coverage != coverage) {
			this.coverage = coverage;
			return true;
		}
		else
			return false;
	}

	public LandSurface getSurface() {
		return surface;
	}

	public boolean setSurface(LandSurface surface) {
		if(this.surface != surface) {
			this.surface = surface;
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
		if (newRacer instanceof LandRacer)
		{
			super.addRacer(newRacer);
		}
		else 
			throw new RacerTypeException(newRacer.className(),"LandArena");
		
	}
	
}
