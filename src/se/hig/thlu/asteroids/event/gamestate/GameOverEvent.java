package se.hig.thlu.asteroids.event.gamestate;

public class GameOverEvent extends GameStateEvent<Boolean> {

	public GameOverEvent() {
		super(true);
	}
}
