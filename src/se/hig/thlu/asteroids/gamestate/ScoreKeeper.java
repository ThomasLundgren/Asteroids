package se.hig.thlu.asteroids.gamestate;

import se.hig.thlu.asteroids.event.Event;
import se.hig.thlu.asteroids.event.EventHandlerFactory;
import se.hig.thlu.asteroids.event.IObserver;
import se.hig.thlu.asteroids.event.entity.DestroyedEvent;
import se.hig.thlu.asteroids.event.entity.EntityEventHandler;
import se.hig.thlu.asteroids.event.gamestate.GameStateEventHandler;
import se.hig.thlu.asteroids.event.gamestate.LevelClearedEvent;
import se.hig.thlu.asteroids.event.gamestate.PenaltyScoreEvent;
import se.hig.thlu.asteroids.event.gamestate.ScoreEvent;
import se.hig.thlu.asteroids.model.entity.Entity;

public class ScoreKeeper implements IObserver {

	private static double CLEAR_TIME_PENALTY_FACTOR = 0.001;
	private long score = 0L;

	public ScoreKeeper() {
		EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
				.addObserver(this);
		EventHandlerFactory.getEventHandler(EntityEventHandler.class)
				.addObserver(this);
	}

	private void enemyKilled(Entity entity) {
		score += (long) entity.getScore();
		EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
				.notify(new ScoreEvent(score));
		System.out.println(score);
	}

	private void levelCleared(double timeToClear) {
		if (timeToClear > 0.0) {
			EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
					.notify(new ScoreEvent(score));
			long penalty = (long) (CLEAR_TIME_PENALTY_FACTOR * timeToClear);
			score -= penalty;
			EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
					.notify(new PenaltyScoreEvent(penalty));
		}
	}

	@Override
	public void notify(Event event) {
		if (event.toString().equals(DestroyedEvent.class.toString())) {
			Entity entity = (Entity) event.getValue();
			enemyKilled(entity);
		} else if (event.toString().equals(LevelClearedEvent.class.toString())) {
			double timeToClear = (double) event.getValue();
			levelCleared(timeToClear);
		}
	}
}
