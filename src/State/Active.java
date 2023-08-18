package State;
import game.racers.Racer;

public class Active implements RacerState{

	@Override
	public synchronized void state(Racer racer) {racer.setState(this);}

}
