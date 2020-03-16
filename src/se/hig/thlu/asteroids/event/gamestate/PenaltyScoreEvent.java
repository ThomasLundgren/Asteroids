package se.hig.thlu.asteroids.event.gamestate;

public class PenaltyScoreEvent extends GameStateEvent<Long> {

	public PenaltyScoreEvent(long value) {
		super(value);
	}

}
