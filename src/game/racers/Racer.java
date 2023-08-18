/**
 * A race class for creating and planning races differs from an abstract layer that is implemented differently between successor classes
 * 
 *
 * @version 03.04 03 Apr 2023
 * @update 11 May 2023
 * @author 1:  Yair Eliyahu
 * @see   	
 */

package game.racers;

import java.util.Observable;
import java.util.Random;

import State.Active;
import State.Broken;
import State.Completed;
import State.Failed;
import State.RacerState;
import utilities.EnumContainer.RacerEvent;
import utilities.*;
import utilities.EnumContainer.Color;
import game.arenas.Arena;
import game.arenas.exceptions.RacerTypeException;

public abstract class Racer extends Observable implements Runnable {

	  // Constants
    private static final int MAX_X = 1000000;
    private static final int MIN_X = 0;
    private static final int MAX_Y = 800;
    private static final int MIN_Y = 0;
	private int serialNumber;
	protected static int SerialNum = 0;
	private String name;
	private Point currentLocation, finish;
	private Arena arena;
	private double maxSpeed, acceleration, currentSpeed, failureProbability;
	private Color color;
	private Mishap mishap;
	private double arenaFriction;
	

	private Point breakdownLocation;
    private long brokenTime;
    private long brokedownTime;
	private RacerState state;

	// Constructor
	public Racer() {
		this("Unnamed Racer", 0, 0, Color.BLACK);
	}

	protected Racer(String name, double maxSpeed, double acceleration, Color color) {
		this.serialNumber = Racer.SerialNum++;
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.color = color;
		this.serialNumber++;
		// DEV init here instead of set method
		this.setFailureProbability(0.2);
	}

	// getters and boolean setters

	public static int getSerial() {
		return SerialNum;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public boolean setSerialNumber(int serialNumber) {
		if (this.serialNumber != serialNumber) {
			this.serialNumber = serialNumber;
			return true;
		} else
			return false;
	}

	public String getName() {
		return name;
	}

	public boolean setName(String name) {
		if (this.name != name) {
			this.name = name;
			return true;
		} else
			return false;

	}

	public Point getCurrentLocation() {
		return currentLocation;
	}

	public boolean setCurrentLocation(Point currentLocation) {
		if (this.currentLocation != currentLocation) {
			this.currentLocation = currentLocation;
			return true;
		} else
			return false;
	}

	public Point getFinish() {
		return finish;
	}

	public boolean setFinish(Point finish) {
		if (this.finish != finish) {
			this.finish = finish;
			return true;
		} else
			return false;
	}

	public Arena getArena() {
		return arena;
	}

	public boolean setArena(Arena arena) {
		if (this.arena != arena) {
			this.arena = arena;
			return true;
		} else
			return false;
	}

	public double getMaxSpeeed() {
		return maxSpeed;
	}

	public boolean setMaxSpeeed(double maxSpeeed) {
		if (this.maxSpeed != maxSpeeed) {
			this.maxSpeed = maxSpeeed;
			return true;
		} else
			return false;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public boolean setAcceleration(double acceleration) {
		if (this.acceleration != acceleration) {
			this.acceleration = acceleration;
			return true;
		} else
			return false;
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public boolean setCurrentSpeed(double currentSpeed) {
		if (this.currentSpeed != currentSpeed) {
			this.currentSpeed = currentSpeed;
			return true;
		} else
			return false;
	}

	public double getFailureProbability() {
		Random random = new Random();
	    this.failureProbability= random.nextDouble();
		return failureProbability;
	}

	public boolean setFailureProbability(double failureProbability) {
		if (this.failureProbability != failureProbability) {
			this.failureProbability = failureProbability;
			return true;
		} else
			return false;
	}

	public Color getColor() {
		return color;
	}

	public double getArenaFriction() {
		return arenaFriction;
	}

	public boolean setColor(Color color) {
		if (this.color != color) {
			this.color = color;
			return true;
		} else
			return false;
	}

	public Mishap getMishap() {
		return mishap;
	}

	public boolean setMishap(Mishap mishap) {
		if (this.mishap != mishap) {
			this.mishap = mishap;
			return true;
		} else
			return false;
	}

	// A method that initializes a race according to the parameters it receives
	public void initRace(Arena arena, Point start, Point finish, double friction) {
		this.arena = arena;
		this.currentLocation = start;
		this.finish = finish;
		this.currentSpeed = 0;
		this.mishap = null;
		this.setArenaFriction(friction);
	}

	private void setArenaFriction(double friction) {
		this.arenaFriction = friction;

	}

	// Abstract method which returns specific details about a competitor
	public abstract String describeSpecific();

	public String describeRacer() {

		String s = "";
		s += "name: " + this.name + ", ";

		// DEV print location
		s += " @" + this.currentLocation + ", ";

		s += "SerialNumber: " + this.serialNumber + ", ";
		s += "maxSpeed: " + this.maxSpeed + ", ";
		s += "acceleration: " + this.acceleration + ", ";
		s += "color: " + this.color + ", ";
		s = s.substring(0, s.length() - 2);
		s += this.describeSpecific();
		return s;
	}

	public void introduce() {
		System.out.println("I am a " + className() + ", " + describeRacer());

	}

	public abstract String className();

	/**
	 * Checks if the racer has a mishap. If the racer has a mishap with zero turns
	 * to fix, it is considered as repaired and the mishap is cleared. It returns
	 * true if the racer has a mishap, false otherwise.
	 *
	 * @return true if the racer has a mishap, false otherwise
	 */
	public boolean hasMishap() {
		// return (this.mishap != null);
		if (this.mishap != null && this.mishap.getTurnsToFix() == 0) {
			this.setMishap(null);
			this.setChanged();
			this.notifyObservers(RacerEvent.REPAIRED);
		}
		return this.mishap != null;
	}

	/**
	 * Checks if the racer is disabled due to a mishap. It returns true if the racer
	 * has a mishap that is not fixable, false otherwise.
	 *
	 * @return true if the racer is disabled, false otherwise
	 */

	public boolean isDisabled() {
		if (this.mishap != null) {
			return !this.mishap.isFixable();
		}
		return false;
	}

	/**
	 * Moves the racer based on the provided friction. If the racer has a mishap, it
	 * affects the racer's current speed and updates the mishap state. The racer's
	 * current location is updated based on the current speed and friction. If the
	 * racer reaches or exceeds the finish line, the current location is adjusted to
	 * the finish line.
	 *
	 * @param friction the friction coefficient affecting the racer's movement
	 * @return the new location of the racer after the movement
	 */
	public Point move(double friction) {
		if (!(this.hasMishap())) {
			this.setMishap(null);
			if (Fate.breakDown()) {
				this.setMishap(Fate.generateMishap());
				System.out.println(this.getName() + " Has a new mishap!: " + this.getMishap());
				this.getMishap().nextTurn();
				if (this.getCurrentSpeed() < this.getMaxSpeeed()) {
					this.setCurrentSpeed(this.getCurrentSpeed()
							+ this.getAcceleration() * friction * this.getMishap().getReductionFactor());
				}
				if (this.getCurrentSpeed() > this.getMaxSpeeed()) {
					this.setCurrentSpeed(this.getMaxSpeeed());
				}

			} else {
				if (this.getCurrentSpeed() < this.getMaxSpeeed()) {
					this.setCurrentSpeed(this.getCurrentSpeed() + this.getAcceleration() * friction);
				}
				if (this.getCurrentSpeed() > this.getMaxSpeeed()) {
					this.setCurrentSpeed(this.getMaxSpeeed());
				}
			}
		} else {
			this.getMishap().nextTurn();
			if (this.getCurrentSpeed() < this.getMaxSpeeed()) {
				this.setCurrentSpeed(this.getCurrentSpeed()
						+ this.getAcceleration() * friction * this.getMishap().getReductionFactor());
			}
			if (this.getCurrentSpeed() > this.getMaxSpeeed()) {
				this.setCurrentSpeed(this.getMaxSpeeed());
			}
		}
		 if (this.getState() instanceof Failed) {
		        // Reset the current speed to zero to keep the racer stuck in the same place
		        this.setCurrentSpeed(0);
		    }
		if (this.getCurrentLocation().getX() + this.getCurrentSpeed() >= this.getFinish().getX()) {
			Point newLocation = new Point(this.getFinish().getX(), this.getCurrentLocation().getY());
			this.setCurrentLocation(newLocation);
			return newLocation;

		} else {
			Point newLocation = new Point(this.getCurrentLocation().getX() + this.getCurrentSpeed(),
					this.getCurrentLocation().getY());
			this.setCurrentLocation(newLocation);
			return newLocation;
		}
	}

	private boolean raceInPrograss() {
		return this.currentLocation.getX() < this.finish.getX(); //this.finish.getX()
	}

	/**
	 * Runs the racer's movement in a separate thread until it reaches or exceeds
	 * the length of the arena. The racer continuously moves based on the arena's
	 * friction and sleeps for 100 milliseconds between each movement. Once the
	 * racer finishes the race, it introduces itself, notifies the observers about
	 * the finish event, and marks itself as changed.
	 */
	@Override
	public void run() {
		
		long startTime = System.currentTimeMillis();
		
		boolean shouldContinue = true;

	    while (shouldContinue && this.raceInPrograss()) {
	        this.move(arena.getFRICTION());
	        try {
	        	 Thread.sleep(100);
	        // Check if the racer has finished the race
	        if (getState() instanceof Active && getCurrentLocation().getX() == arena.getLength()) {
	            setCompletedState( new Completed());
	            getCompletedState().state(this);
	            System.out.println(getName() + " finished the race!");
	            break;
	        }

	        // Check if the racer has reached the breakdown location 
	        else if (getState() instanceof Active && hasMishap() ) {
	            setBrokenState(new Broken());
	            getBrokenState().state(this); 
	            setBreakdownTime( System.currentTimeMillis() - startTime);
	            System.out.println(getName() + " broke down at " + getBreakdownTime() + "ms");
	            Thread.sleep(getBrokenTime());
	            System.out.println(getName() + " fixed after " + getBrokenTime() + "ms");
	            
	            if(isDisabled()) {
	            	setActiveState(new Active());
		            getActiveState().state(this);
	            }
	            else {
	            	setFailedState(new Failed());
		            getFailedState().state(this);
		            System.out.println(getName() + " Failed to finish race");
		            shouldContinue = false;
		            break;
		            
	            }
	        }else if(getState() instanceof Failed) {
	        	System.out.println(getName() + " Failed to finish race");
	            shouldContinue = false;
	            break;
	           
	        }

//	        // Check if the racer has failed to finish the race
//	        if (getCurrentLocation().getX() != arena.getLength() && getState() instanceof Failed) {
//	            setState(new Failed());
//	            getState().state(this); 
//	            System.out.println(getName() + " failed to finish the race!");
//	            shouldContinue = false;
//	            
//	        }

	        // Pause between moves
	       
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    this.introduce();

	    // Notify the arena about the racer's finish
	    setChanged();
	    notifyObservers(RacerEvent.FINISHED);
	    notifyObservers(RacerEvent.BROKENDOWN);
	    notifyObservers(RacerEvent.REPAIRED);
	    notifyObservers(RacerEvent.DISABLED);
	
//		while (this.currentLocation.getX() < this.arena.getLength()) {
//
//			// DEV disable sleep
//			try {
//				this.move(this.arena.getFRICTION());
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		this.introduce();
//		setChanged();
//		this.notifyObservers(RacerEvent.FINISHED);
	}
	
	
	

	public boolean activeOrfailed() {
		Random rand = new Random();
		int num = rand.nextInt(11);
		return num != 10;
	}
	public abstract Racer clone();
	
	public void upgrade(Color NewColor) {
		this.color=NewColor;
	}

	public void setState(RacerState nstate) {
		this.state=nstate;
		
	}
	public RacerState getState() {
		return state;
		
	}

	
	
	
	

    

    
   
    

    public void setBreakdownLocation(Point breakdownLocation) {
        this.breakdownLocation = breakdownLocation;
    }

    public long getBrokenTime() {
    	long startT=System.currentTimeMillis();
		return startT;
    }
    public void setBrokenTime(long brokenTime) {
        this.brokenTime = brokenTime;
    }

    public long getBreakdownTime() {
        return brokedownTime;
    }

    public void setBreakdownTime(long breakdownTime) {
        this.brokedownTime = breakdownTime;
    }
    
    // Setter for setting the state
    public void setCompletedState(RacerState newState) {
        this.state = newState;
    }
    public void setBrokenState(RacerState newState) {
        this.state = newState;
    }
    public void setFailedState(RacerState newState) {
        this.state =  newState;
    }
    public void setActiveState(RacerState newState) {
        this.state =  newState;
    }
    
    public RacerState getCompletedState() {
    	return this.state;    }
    public RacerState getBrokenState() {
    	return this.state;    }
    public RacerState getFailedState() {
    	return this.state;    }
    public RacerState getActiveState() {
       return this.state;
    }
    
    
    
    
    
    public Point getBreakdownLocation() {
        // Generate random x and y coordinates within the valid range
        double x = Math.random() * (MAX_X - MIN_X) + MIN_X;
        double y = Math.random() * (MAX_Y - MIN_Y) + MIN_Y;

        // Create a new Point object with the generated coordinates
        Point breakdownLocation = new Point(x, y);

        return breakdownLocation;
    }
}
