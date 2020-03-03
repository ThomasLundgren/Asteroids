package se.hig.thlu.asteroids.entityfactory;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.storage.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static se.hig.thlu.asteroids.model.Asteroid.AsteroidSize.*;

public final class RandomEntityFactory implements EntityFactory {

	private ImageLoader<? extends ImageAdapter> imageLoader;
	private PlayerShip playerShip;
	private static int level = 0;

	public RandomEntityFactory(ImageLoader<? extends ImageAdapter> imageLoader) {
		this.imageLoader = imageLoader;
		playerShip = PlayerShip.createPlayerShip(imageLoader);
	}

	@Override
	public List<Asteroid> nextLevel() {
		level++;
		List<Asteroid> asteroids = new ArrayList<>(10);
		for (int i = 0; i < level; i++) {
			// TODO: Randomize starting point along edge of screen - off-screen
			Asteroid newAsteroid = createLargeAsteroid(new Point(0.0, 0.0));
			asteroids.add(newAsteroid);
		}
		return asteroids;
	}

	@Override
	public List<Asteroid> shatterAsteroid(Asteroid destroyedAsteroid) {
		Point pos = destroyedAsteroid.getCenter();
		double minSpeed = destroyedAsteroid.getVelocity().getSpeed();
		double maxSpeed = 0.0;
		Asteroid.AsteroidSize newSize;
		switch (destroyedAsteroid.getAsteroidSize()) {
			case LARGE:
				minSpeed = Math.max(minSpeed, MEDIUM.getMinSpeed());
				maxSpeed = MEDIUM.getMaxSpeed();
				newSize = MEDIUM;
				break;
			case MEDIUM:
				minSpeed = Math.max(minSpeed, SMALL.getMinSpeed());
				maxSpeed = SMALL.getMaxSpeed();
				newSize = SMALL;
				break;
			default:
				return new ArrayList<>(0);
		}
		return createAsteroids(pos, minSpeed, maxSpeed, newSize);
	}

	@Override
	public EnemyShip createEnemyShip() {
		return null;
	}

	@Override
	public PlayerShip createPlayerShip() {
		return playerShip;
	}

	private static double randomSpeed(double min, double max) {
		return ThreadLocalRandom.current()
				.nextDouble(min, max);
	}

	private static double randomDirection() {
		return ThreadLocalRandom.current()
				.nextDouble(0.0, 360.0);
	}

	private Asteroid createLargeAsteroid(Point point) {
		double min = LARGE.getMinSpeed();
		double max = LARGE.getMaxSpeed();
		double speed = randomSpeed(min, max);
		double dir = randomDirection();
		return new Asteroid(point, new Velocity(speed, dir), LARGE, imageLoader);
//		return Asteroid.createAsteroid(center, new Velocity(speed, dir), LARGE, imageLoader);
	}

	private List<Asteroid> createAsteroids(Point center, double minSpeed, double maxSpeed,
												  Asteroid.AsteroidSize size) {
		List<Asteroid> asteroids = new ArrayList<>(4);
		for (int i = 0; i < 4; i++) {
			double randSpeed = randomSpeed(minSpeed, maxSpeed);
			double dir = randomDirection();
			Asteroid newAsteroid = new Asteroid(center, new Velocity(randSpeed,
					dir), size, imageLoader);
//			Asteroid newAsteroid = Asteroid.createAsteroid(center, new Velocity(randSpeed,
//					dir), size, imageLoader);
			asteroids.add(newAsteroid);
		}
		return asteroids;
	}
}
