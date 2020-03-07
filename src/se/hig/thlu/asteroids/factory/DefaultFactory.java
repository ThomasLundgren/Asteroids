package se.hig.thlu.asteroids.factory;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.mathutil.Randomizer;
import se.hig.thlu.asteroids.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static se.hig.thlu.asteroids.model.Asteroid.AsteroidSize.LARGE;

public final class DefaultFactory implements EntityFactory {

	private int level = 0;

	@Override
	public List<Entity> nextLevel(Point playerCenter) {
		level++;
		List<Entity> asteroids = new ArrayList<>(level);
		for (int i = 0; i < level; i++) {
			Point randomPoint = randomPoint(playerCenter);
			Asteroid newAsteroid = createLargeAsteroid(randomPoint);
			asteroids.add(newAsteroid);
		}
		return asteroids;
	}

	private Point randomPoint(Point playerCenter) {
		Point randomPoint;
		do {
			int randomX = ThreadLocalRandom.current().nextInt(0, GameConfig.WINDOW_WIDTH);
			int randomY = ThreadLocalRandom.current().nextInt(0, GameConfig.WINDOW_HEIGHT);
			randomPoint = new Point((double) randomX, (double) randomY);
		} while (randomPoint.distanceTo(playerCenter) < (double) GameConfig.ENEMY_SPAWN_MIN_DISTANCE);

		return randomPoint;
	}

	@Override
	public EnemyShip createEnemyShip(Point playerCenter) {
		Point randomPoint;
		int shipX = 0;
		double direction = 0.0;

		int randomX = ThreadLocalRandom.current().nextInt(0, 2);
		if (randomX == 1) {
			shipX = GameConfig.WINDOW_WIDTH;
			direction = -180.0;
		}

		do {
			int randomY = ThreadLocalRandom.current().nextInt(0, GameConfig.WINDOW_HEIGHT);
			randomPoint = new Point((double) shipX, (double) randomY);
		} while (randomPoint.distanceTo(playerCenter) < (double) GameConfig.ENEMY_SPAWN_MIN_DISTANCE);

		EnemyShip enemyShip = new EnemyShip(direction);
		enemyShip.setCenter(randomPoint);
		return enemyShip;
	}

	@Override
	public PlayerShip createPlayerShip() {
		return new PlayerShip();
	}

	private Asteroid createLargeAsteroid(Point point) {
		double min = LARGE.getMinSpeed();
		double max = LARGE.getMaxSpeed();
		double speed = Randomizer.randomSpeed(min, max);
		double dir = Randomizer.randomDirection(0.0, 360.0);
		return new Asteroid(point, new Velocity(speed, dir), LARGE);
	}

}
