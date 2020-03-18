package se.hig.thlu.asteroids.event.gamestate;

public class HighScoreEvent extends GameStateEvent<Integer> {

	public HighScoreEvent(Integer value) {
		super(value);
	}
}
