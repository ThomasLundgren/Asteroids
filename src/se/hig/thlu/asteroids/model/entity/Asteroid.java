package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.mathutil.Randomizer;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static se.hig.thlu.asteroids.mathutil.Randomizer.randomSpeed;
import static se.hig.thlu.asteroids.model.entity.Asteroid.AsteroidSize.MEDIUM;
import static se.hig.thlu.asteroids.model.entity.Asteroid.AsteroidSize.SMALL;

public final class Asteroid extends AbstractEntity implements Shatterable {

	private final AsteroidSize asteroidSize;
	private final RotationDirection rotationDirection;

	public Asteroid(Point position, Velocity velocity, AsteroidSize size) {
		super(position,
				velocity,
				velocity.getSpeed(),
				new Dim(size.getWidth(), size.getHeight()));
		asteroidSize = size;
		int randomRotation = ThreadLocalRandom.current().nextInt(2);
		rotationDirection = randomRotation == 0 ? RotationDirection.LEFT : RotationDirection.RIGHT;
	}

	@Override
	public void update() {
		switch (rotationDirection) {
			case LEFT:
				turnLeft();
				break;
			case RIGHT:
				turnRight();
				break;
			default:
				break;
		}
		super.update();
	}

	@Override
	public Optional<Explosion> destroy() {
		super.destroy();
		Explosion.ExplosionSize size;
		switch (asteroidSize) {
			case LARGE:
				size = Explosion.ExplosionSize.THREE;
				break;
			case MEDIUM:
				size = Explosion.ExplosionSize.TWO;
				break;
			default:
				size = Explosion.ExplosionSize.ONE;
		}
		return Optional.of(new Explosion(center, size));
	}

	public AsteroidSize getAsteroidSize() {
		return asteroidSize;
	}

	@Override
	public List<Entity> shatter() {
		Asteroid.AsteroidSize newSize;
		switch (asteroidSize) {
			case LARGE:
				newSize = MEDIUM;
				break;
			case MEDIUM:
				newSize = SMALL;
				break;
			default:
				return new ArrayList<>(0);
		}
		double destroyedSpeed = velocity.getSpeed();
		double minSpeed = Math.max(destroyedSpeed, newSize.getMinSpeed());
		double maxSpeed = newSize.getMaxSpeed();

		return createAsteroids(center, minSpeed, maxSpeed, newSize);
	}

	private List<Entity> createAsteroids(Point center, double minSpeed, double maxSpeed,
										   Asteroid.AsteroidSize size) {
		List<Entity> asteroids = new ArrayList<>(2);
		double parentDir = velocity.getDirection();
		double deviationDeg = 75.0;
		for (int i = 0; i < 2; i++) {
			double randSpeed = randomSpeed(minSpeed, maxSpeed);
			double randomDir = Randomizer.randomDirection(parentDir - deviationDeg, parentDir + deviationDeg);
			Asteroid newAsteroid = new Asteroid(center, new Velocity(randSpeed,
					randomDir), size);
			asteroids.add(newAsteroid);
		}
		return asteroids;
	}

	private enum RotationDirection {LEFT, RIGHT}

	public enum AsteroidSize {
		LARGE(1.0, 1.5, 91, 87),
		MEDIUM(1.25, 1.875, 41, 38),
		SMALL(1.5625, 2.34375, 21, 21);

		private final double minSpeed;
		private final double maxSpeed;
		private final int width;
		private final int height;

		AsteroidSize(double minSpeed, double maxSpeed, int width, int height) {
			this.minSpeed = minSpeed;
			this.maxSpeed = maxSpeed;
			this.width = width;
			this.height = height;
		}

		public double getMaxSpeed() {
			return maxSpeed;
		}

		public double getMinSpeed() {
			return minSpeed;
		}

		public int getHeight() {
			return height;
		}

		public int getWidth() {
			return width;
		}
	}
}
