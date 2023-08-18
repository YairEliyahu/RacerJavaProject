/**
 * A class that describes an Horse inherits from a race class and describes an Horse's race
 * 
 *
 * @version 03.04 03 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Racer class
 */


package game.racers.land;
import game.racers.Racer;
import game.racers.air.Airplane;
import utilities.EnumContainer.*;  

public class Horse extends Racer implements LandRacer{

	private static final String CLASS_NAME = "Horse";
	private static final double DEFAULT_MAX_SPEED = 50;
	private static final double DEFAULT_ACCELERATION = 3;
	private static final Color DEFAULT_COLOR = Color.BLACK;

	private Breed breed;  

	// Constructors 
	//Default
	public Horse() {
	this(CLASS_NAME + " #" + SerialNum, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR);
    	}
	public Horse(String name, double maxSpeed, double acceleration, Color color) {
		super(name,maxSpeed,acceleration,color);
		this.breed = Breed.THOROUGHBRED; 
	}
	
	//getters and setters
	public Breed getType() {
		return breed;
	}
	public boolean setType(Breed type) {
		if(this.breed != type) {
			this.breed = type;
			return true;
		}
		else
			return false;
	}
	
	// Method- return number of wheels and engine of car
    @Override
    public String describeSpecific() {
        return String.format("Type breed=%s", breed);
    }
    
	//An abstract method that return name class
  	@Override
  	public String className() {
  		return CLASS_NAME;
  	}
  	@Override
	public Racer clone() {
		
		return new Horse(this.getName(),this.getMaxSpeeed(),this.getAcceleration(),this.getColor());
	}
	
}
