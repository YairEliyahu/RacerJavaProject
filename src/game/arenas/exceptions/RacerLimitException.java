/**
 * Exceptions department for handling place errors
 * 
 *
 * @version 02.04 02 Apr 2023
 * @author 1:  Yair Eliyahu
 * @see   	Exception
 */

package game.arenas.exceptions;
@SuppressWarnings("serial")

public class RacerLimitException extends Exception {

	 public RacerLimitException(int maxRacers,int serialNumber)
		{
			super("Arena is full! (" + maxRacers +"active racers exist). racer #" + serialNumber +" was not added\r\n");		
		}

}
