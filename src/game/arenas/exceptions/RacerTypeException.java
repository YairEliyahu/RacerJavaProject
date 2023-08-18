/**
 * An exception class for handling type and name errors
 * 
 *
 * @version 02.04 02 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Exception
 */

package game.arenas.exceptions;
@SuppressWarnings("serial")
public class RacerTypeException extends Exception {
	
	public RacerTypeException(String className,String arenaName) 
	{
		super("Invalid Racer of type "+className +" for "+arenaName );
		
	}

}
