package se.hig.thlu.asteroids.entityfactory;

import se.hig.thlu.asteroids.model.Asteroid;
import se.hig.thlu.asteroids.model.EnemyShip;
import se.hig.thlu.asteroids.model.PlayerShip;
import se.hig.thlu.asteroids.model.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static se.hig.thlu.asteroids.model.Asteroid.AsteroidSize.*;

public class RandomEntityFactory implements EntityFactory {

	private static final PlayerShip playerShip = PlayerShip.createPlayerShip();

	@Override
	public Asteroid createLargeAsteroid(Point center) {
		double min = LARGE.getMinSpeed();
		double max = LARGE.getMaxSpeed();
		double speed = randomSpeed(min, max);
		double dir = randomDirection();
		return Asteroid.createAsteroid(center, speed, dir, LARGE);
	}

	@Override
	public List<Asteroid> shatterAsteroid(Asteroid destroyedAsteroid) {
		Point pos = destroyedAsteroid.getCenter();
		double minSpeed = destroyedAsteroid.getVelocity().getSpeed();
		double maxSpeed = 0.0;
		Asteroid.AsteroidSize newSize = LARGE;
		switch (destroyedAsteroid.getAsteroidSize()) {
			case LARGE:
				minSpeed = Math.max(minSpeed, MEDIUM.getMinSpeed());
				maxSpeed = MEDIUM.getMaxSpeed();
				newSize = MEDIUM;
				break;
			case MEDIUM:
				minSpeed = Math.max(minSpeed, SMALL.getMinSpeed());
				SMALL.getMaxSpeed();
				newSize = SMALL;
				break;
			default:
				return new ArrayList<>();
		}
		return createAsteroids(pos, minSpeed, maxSpeed, newSize);
	}

	@Override
	public PlayerShip createPlayerShip() {
		return playerShip;
	}

	@Override
	public EnemyShip createEnemyShip() {
		return null;
	}

	private static double randomSpeed(double min, double max) {
		return ThreadLocalRandom.current()
				.nextDouble(min, max);
	}

	private static double randomDirection() {
		return ThreadLocalRandom.current()
				.nextDouble(0.0, 360.0);
	}

	private static List<Asteroid> createAsteroids(Point center, double minSpeed, double maxSpeed,
												  Asteroid.AsteroidSize size) {
		List<Asteroid> asteroids = new ArrayList<>(5);
		for (int i = 0; i < 4; i++) {
			double randSpeed = randomSpeed(minSpeed, maxSpeed);
			double dir = randomDirection();
			Asteroid newAsteroid = Asteroid.createAsteroid(center, randSpeed, dir, size);
		}
		return asteroids;
	}
}
