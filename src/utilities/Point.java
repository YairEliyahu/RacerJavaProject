/**
 *	The Point class describes a real-time location according to the x,y coordinates of that race participant
 *
 * @version 02.04 02 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	
 */

package utilities;

public class Point {
	    // Constants
	    private static final int MAX_X = 1000000;
	    private static final int MIN_X = 0;
	    private static final int MAX_Y = 800;
	    private static final int MIN_Y = 0;

	    // Fields
	    private double x;
	    private double y;

	    // Constructors
	    public Point(double x, double y) {
	        setX(x);
	        setY(y);
	    }

	    public Point() {
	        this(0, 0);
	    }

	    // Methods
	    public String toString() {
	        return "(" + x + ", " + y + ")";
	    }

	    public double getX() {
	        return x;
	    }

	    public double getY() {
	        return y;
	    }

	    public void setX(double x) {
	        if (x >= MIN_X && x <= MAX_X) {
	            this.x = x;
	        } else {
	            throw new IllegalArgumentException("Invalid x value");
	        }
	    }

	    public void setY(double y) {
	        if (y >= MIN_Y && y <= MAX_Y) {
	            this.y = y;
	        } else {
	            throw new IllegalArgumentException("Invalid y value");
	        }
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


