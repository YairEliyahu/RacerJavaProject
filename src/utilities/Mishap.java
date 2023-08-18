/**
 *	The Mishap class is a utility class that represents an event in which a boat breaks down while rowing. It has three private fields: fixable, reductionFactor, and turnsToFix. * 
 *
 * @version 02.04 02 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	
 */

package utilities;

import java.text.DecimalFormat;

public class Mishap {
	
	private boolean fixable;
	private double reductionFactor;
	private int turnsToFix;
	
	//Constructor
	Mishap(boolean fixable, int turnsToFix, double reductionFactor){
		this.fixable = fixable;
		this.turnsToFix = turnsToFix;
		this.reductionFactor = reductionFactor;
	}
	
	//getters and boolean setters
	
	public boolean isFixable() {
		return fixable;
	}



	public boolean setFixable(boolean fixable) {
		if(this.fixable != fixable) {
			this.fixable = fixable;
			return true;
		}
		else
			return false;
		
	}



	public double getReductionFactor() {
		return reductionFactor;
	}



	public boolean setReductionFactor(double reductionFactor) {
		if(this.reductionFactor != reductionFactor) {
			this.reductionFactor = reductionFactor;
			return true;
		}
		else
			return false;
	}



	public int getTurnsToFix() {
		return turnsToFix;
	}



	public boolean setTurnsToFix(int turnsToFix) {
		if(this.turnsToFix != turnsToFix) {
			this.turnsToFix = turnsToFix;
			return true;
		}
		else
			return false;
	}


	// reduces the amount of time for repair (if the fault can be repaired) 
	public void nextTurn() {
		if (isFixable() && turnsToFix > 0)
            turnsToFix--;
	}
	
	//A method for overriding a built-in method in JAVA in order to return a string containing the values of its fields in a certain format.
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return "(" + isFixable() + ", " + df.format(reductionFactor) + ", " + turnsToFix + ")" ;
	}
	
	
}
