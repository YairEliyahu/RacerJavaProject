/**
 * This class designs a general arena template 
 * 
 *
 * @version 04.04 04 Apr 2023
 * @update 11 May 2023
 * @author 1:  Yair Eliyahu
 * @see     
 */

package game.arenas;

import game.arenas.exceptions.*;
import utilities.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import game.arenas.exceptions.*;
import game.racers.Racer;
import utilities.EnumContainer.RacerEvent;

public abstract class Arena implements Observer {
	private ArrayList<Racer> activeRacers;
	private ArrayList<Racer> completedRacers;
	private ArrayList<Racer> brokenRacers;
	private ArrayList<Racer> failedRacers;
	private final double FRICTION;
	private final int MAX_RACERS;
	private final static int MIN_Y_GAP = 10;
	private double length;

	// Constructors
	public Arena(double length, int maxRacers, double friction) {
		this.length = length;
		this.MAX_RACERS = maxRacers;
		this.FRICTION = friction;
		this.setActiveRacers(new ArrayList<Racer>());
		this.setCompleatedRacers(new ArrayList<Racer>());
		this.setBrokenRacers(new ArrayList<Racer>());
		this.setFailedRacers(new ArrayList<Racer>());
	}

	// Getters and setters

	public double getLength() {
		return length;
	}

	public boolean setLength(double length) {
		if (this.length != length) {
			this.length = length;
			return true;
		}
		return false;
	}

	public ArrayList<Racer> getActiveRacers() {
		synchronized (activeRacers) {
			return activeRacers;
		}
	}

	public void setActiveRacers(ArrayList<Racer> activeRacers) {
		this.activeRacers = activeRacers;
	}

	public ArrayList<Racer> getCompletedRacers() {
		synchronized (activeRacers) {
			return completedRacers;
		}
	}

	public void setCompleatedRacers(ArrayList<Racer> compleatedRacers) {
		this.completedRacers = compleatedRacers;
	}

	public ArrayList<Racer> getFailedRacers() {
		synchronized (activeRacers) {
			return failedRacers;
		}
	}

	public void setFailedRacers(ArrayList<Racer> nfailedRacers) {
		this.failedRacers = nfailedRacers;
	}

	public void setBrokenRacers(ArrayList<Racer> nBrokenRacers) {
		this.brokenRacers = nBrokenRacers;
	}

	public ArrayList<Racer> getBrokenRacers() {
		synchronized (activeRacers) {
			return brokenRacers;
		}
	}

	public double getFRICTION() {
		return FRICTION;
	}

	public int getMAX_RACERS() {
		return MAX_RACERS;
	}

	public static int getMinYGap() {
		return MIN_Y_GAP;
	}

	/**
	 * Adds a new racer to the race.
	 *
	 * @param newRacer the racer to be added
	 * @throws RacerTypeException       if the racer type is invalid
	 * @throws RacerLimitException      if the maximum number of racers has been
	 *                                  reached
	 * @throws IllegalArgumentException if the newRacer parameter is null
	 */
	public void addRacer(Racer newRacer) throws RacerTypeException, RacerLimitException {

		if (newRacer == null) {
			throw new IllegalArgumentException("Cannot add a null racer.");
		}

		if (this.activeRacers.size() + 1 > this.MAX_RACERS) {
			throw new RacerLimitException(getMAX_RACERS(), newRacer.getSerialNumber());
		}
		newRacer.addObserver(this);
		this.activeRacers.add(newRacer);
	}

	/**
	 * Initializes the race by assigning starting and finishing points to active
	 * racers. Each racer is assigned a starting point at the leftmost side of the
	 * arena (x=0) and a finishing point at the rightmost side of the arena
	 * (x=length). The y-coordinate of the starting and finishing points increases
	 * for each racer, creating gaps between them.
	 */
	public void initRace() {
		int y = 0;
		synchronized (activeRacers) {
			for (Racer racer : this.activeRacers) {
				Point s = new Point(0, y);
				Point f = new Point(this.length, y);
				racer.initRace(this, s, f, this.FRICTION);
				y += Arena.MIN_Y_GAP;
			}
		}
	}

	// method to check if the array is empty (essentially, this method is used to
	// check if the race is still ongoing or not.)
	public boolean hasActiveRacers() {

		return this.activeRacers.size() > 0;

	}

	/**
	 * Plays a turn in the race by moving each active racer and checking if they
	 * have crossed the finish line. If a racer has crossed the finish line, it is
	 * handled by the crossFinishLine method.
	 */
	public void playTurn() {
		for (int i = 0; i < activeRacers.size(); i++) {
			this.activeRacers.get(i).move(getFRICTION());
			if (this.activeRacers.get(i).getCurrentLocation().getX() >= getLength()) {
				crossFinishLine(activeRacers.get(i));
				i--;
			}
			else if(this.activeRacers.get(i).hasMishap() && !this.activeRacers.get(i).isDisabled()) {
				crossFinishLine(activeRacers.get(i));
				i--;
			}
		}
		
		

	}

	/**
	 * Handles a racer crossing the finish line by moving it from the active racers
	 * list to the completed racers list.
	 *
	 * @param racer the racer that has crossed the finish line
	 */
	private void crossFinishLine(Racer racer) {

		this.completedRacers.add(racer);
		this.activeRacers.remove(racer);

	}

	/**
	 * Displays the results of the race by printing the completed racers and active
	 * racers (if any). The completed racers are displayed first, followed by the
	 * active racers.
	 */

	public void showResults() {

		synchronized (activeRacers) {
			int i = 1;
			System.out.println("Compleated");
			for (Racer r : this.completedRacers) {
				String s = "#" + i++ + " -> ";
				s += r.describeRacer();
				System.out.println(s);
			}

			// TEST verify list is empty
			System.out.println("Active");
			for (Racer r : this.activeRacers) {
				String s = "#" + i++ + " -> ";
				s += r.describeRacer();
				System.out.println(s);
			}
			// TEST verify list is empty
			System.out.println("Broken");
			for (Racer r : this.brokenRacers) {
				String s = "#" + i++ + " -> ";
				s += r.describeRacer();
				System.out.println(s);
			}
			// TEST verify list is empty
			System.out.println("Failed");
			for (Racer r : this.failedRacers) {
				String s = "#" + i++ + " -> ";
				s += r.describeRacer();
				System.out.println(s);
			}
		}

	}

	/**
	 * Starts the race by initializing it, creating a thread pool, and executing the
	 * racers in parallel. The method waits for the race to complete by shutting
	 * down the thread pool and waiting for a maximum of 10 minutes for all threads
	 * to finish.
	 *
	 * @throws InterruptedException if the thread is interrupted while waiting for
	 *                              the race to complete
	 */
	public void startRace() throws InterruptedException {
		synchronized (activeRacers) {

			initRace();
			ExecutorService e;
			synchronized (activeRacers) {
				e = Executors.newFixedThreadPool(this.activeRacers.size());
				synchronized (this) {
					for (Racer racer : activeRacers) {
						e.execute(racer);
					}
				}
			}
			e.shutdown();
			e.awaitTermination(10, TimeUnit.MINUTES);
		}
	}

	/**
	 * Updates the observer with the state changes of the observed racer. It handles
	 * the specific racer events, such as when a racer has finished the race.
	 *
	 * @param o   the observed racer object
	 * @param arg the argument passed from the observed racer
	 */
	@Override
	public void update(Observable o, Object arg) {

		Racer racer = (Racer) o;

		switch ((RacerEvent) arg) {

		case FINISHED:
			synchronized (this.activeRacers) {
				this.activeRacers.remove(racer);
				this.completedRacers.add(racer);
			}
			break;
		case BROKENDOWN:
			synchronized (this.activeRacers) {
				this.activeRacers.remove(racer);
				this.brokenRacers.add(racer);
			}
			break;
		case REPAIRED:
			synchronized (this.activeRacers) {
				  if (!this.brokenRacers.isEmpty()) {
			            Racer repairedRacer = this.brokenRacers.remove(0);  // Remove the first racer from the brokenRacers list
			            this.activeRacers.add(repairedRacer);  // Add the repaired racer to the activeRacers list
			        }
			}
			break;
		case DISABLED:
			synchronized (this.activeRacers) {
				this.activeRacers.remove(racer);
				this.failedRacers.add(racer);
			}
			break;

		}

	}
}
