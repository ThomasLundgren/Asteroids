package se.hig.thlu.asteroids.event.gamestate;

public class LevelClearedEvent extends GameStateEvent {

	public LevelClearedEvent(double levelClearTime) {
		super(levelClearTime);
	}
}
