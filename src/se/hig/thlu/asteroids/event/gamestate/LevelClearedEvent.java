package se.hig.thlu.asteroids.event.gamestate;

public class LevelClearedEvent extends GameStateEvent<Double> {

	public LevelClearedEvent(double levelClearTime) {
		super(levelClearTime);
	}
}
