package se.hig.thlu.asteroids.gamestate;

import se.hig.thlu.asteroids.gamestate.GameController.Action;
import se.hig.thlu.asteroids.model.entity.Entity;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.observer.IObserver;

public class ScoreKeeper implements IObserver {

	private long score = 0L;
	private static double CLEAR_TIME_PENALTY_FACTOR = 0.001;

	public enum Score {
		SCORE_UPDATED;
	}

	public ScoreKeeper() {
		EventBus.getInstance().addObserver(this);
	}

	private void enemyKilled(Entity entity) {
		if (entity.isDestroyed()) {
			score += (long) entity.getScore();
		}
		EventBus.getInstance().notify(Score.SCORE_UPDATED.toString(), new Event(score));
		System.out.println(score);
	}

	private void levelCleared(double timeToClear) {
		if (timeToClear > 0.0) {
			long penalty = (long) (CLEAR_TIME_PENALTY_FACTOR * timeToClear);
			score -= penalty;
		}
		EventBus.getInstance().notify(Score.SCORE_UPDATED.toString(), new Event(score));
	}

	@Override
	public void notify(String propertyName, Event event) {
		Object value = event.getValue();
		if (propertyName.equals(Action.DESTROY.toString())) {
			if (value instanceof Entity) {
				Entity entity = (Entity) value;
				enemyKilled(entity);
			}
		} else if (propertyName.equals(Action.LEVEL_CLEARED.toString())) {
			double timeToClear = (double) value;
			levelCleared(timeToClear);
		}
	}
}
