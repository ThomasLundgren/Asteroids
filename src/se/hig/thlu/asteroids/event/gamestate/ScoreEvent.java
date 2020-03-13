package se.hig.thlu.asteroids.event.gamestate;

public class ScoreEvent extends GameStateEvent {

	public ScoreEvent(long score) {
		super(score);
	}
}
