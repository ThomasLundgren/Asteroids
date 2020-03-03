package se.hig.thlu.asteroids.entityfactory;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.Asteroid;
import se.hig.thlu.asteroids.model.EnemyShip;
import se.hig.thlu.asteroids.model.Entity;
import se.hig.thlu.asteroids.model.PlayerShip;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;
import se.hig.thlu.asteroids.storage.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static se.hig.thlu.asteroids.model.Asteroid.AsteroidSize.*;

public final class DefaultFactory implements EntityFactory {

	private final ImageLoader<? extends ImageAdapter> imageLoader;
	private PlayerShip playerShip;
	private int level = 0;

	public DefaultFactory(ImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		playerShip = PlayerShip.createPlayerShip(imageLoader);
	}

	@Override
	public List<Asteroid> nextLevel(Point playerCenter) {
		level++;
		List<Asteroid> asteroids = new ArrayList<>(level);
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
			randomPoint = new Point(randomX, randomY);
		} while (randomPoint.distanceTo(playerCenter) < GameConfig.ENEMY_SPAWN_MIN_DISTANCE);

		return randomPoint;
	}

	@Override
	public List<Entity> shatterAsteroid(Asteroid destroyedAsteroid) {
		Point pos = destroyedAsteroid.getCenter();
		Asteroid.AsteroidSize size = destroyedAsteroid.getAsteroidSize();

		Asteroid.AsteroidSize newSize;
		switch (destroyedAsteroid.getAsteroidSize()) {
			case LARGE:
				newSize = MEDIUM;
				break;
			case MEDIUM:
				newSize = SMALL;
				break;
			default:
				return new ArrayList<>(0);
		}
		double destroyedSpeed = destroyedAsteroid.getVelocity().getSpeed();
		double minSpeed = Math.max(destroyedSpeed, newSize.getMinSpeed());
		double maxSpeed = newSize.getMaxSpeed();
		return createAsteroids(pos, minSpeed, maxSpeed, newSize);
	}

	@Override
	public EnemyShip createEnemyShip(Point playerCenter) {
		Point randomPoint;
		int shipX = 0;
		double direction = 0.0;

		int randomX = ThreadLocalRandom.current().nextInt(0, 1);
		if (randomX == 1) {
			shipX = GameConfig.WINDOW_WIDTH;
			direction = -180.0;
		}

		do {
			int randomY = ThreadLocalRandom.current().nextInt(0, GameConfig.WINDOW_HEIGHT);
			randomPoint = new Point(shipX, randomY);
		} while (randomPoint.distanceTo(playerCenter) < GameConfig.ENEMY_SPAWN_MIN_DISTANCE);

		EnemyShip enemyShip = EnemyShip.createEnemyShip(imageLoader, direction);
		enemyShip.setCenter(randomPoint);
		return enemyShip;
	}

	@Override
	public PlayerShip createPlayerShip() {
		return playerShip;
	}

	private Asteroid createLargeAsteroid(Point point) {
		double min = LARGE.getMinSpeed();
		double max = LARGE.getMaxSpeed();
		double speed = randomSpeed(min, max);
		double dir = randomDirection();
		return new Asteroid(point, new Velocity(speed, dir), LARGE, imageLoader);
	}

	private List<Entity> createAsteroids(Point center, double minSpeed, double maxSpeed,
										 Asteroid.AsteroidSize size) {
		List<Entity> asteroids = new ArrayList<>(2);
		for (int i = 0; i < 2; i++) {
			double randSpeed = randomSpeed(minSpeed, maxSpeed);
			double dir = randomDirection();
			Asteroid newAsteroid = new Asteroid(center, new Velocity(randSpeed,
					dir), size, imageLoader);
			asteroids.add(newAsteroid);
		}
		return asteroids;
	}

	private static double randomSpeed(double min, double max) {
		return ThreadLocalRandom.current()
				.nextDouble(min, max);
	}

	private static double randomDirection() {
		return ThreadLocalRandom.current()
				.nextDouble(0.0, 360.0);
	}
}
