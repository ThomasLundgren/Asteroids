package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.mathutil.Trigonometry;

import java.util.concurrent.ThreadLocalRandom;

public class Asteroid extends Entity {

	private final AsteroidSize asteroidSize;
	private final RotationDirection rotationDirection;

	private Asteroid(Point position, Velocity velocity, AsteroidSize size) {
		super(position, velocity, velocity.getSpeed());
		asteroidSize = size;
		int r = ThreadLocalRandom.current().nextInt(2);
		rotationDirection = r == 0 ? RotationDirection.LEFT : RotationDirection.RIGHT;
	}

	public static Asteroid createAsteroid(Point position, double speed, double direction, AsteroidSize size) {
		double dir = Trigonometry.normalizeDegree(direction);
		double spd = validateSpeed(speed, size);
		Velocity v = new Velocity(spd, dir);
		return new Asteroid(position, v, size);
	}

	private static double validateSpeed(double speed, AsteroidSize asteroidSize) {
		if (speed > asteroidSize.getMaxSpeed()) {
			return asteroidSize.getMaxSpeed();
		}
		if (speed < asteroidSize.getMinSpeed()) {
			return asteroidSize.getMinSpeed();
		}
		return speed;
	}

	@Override
	public void updatePosition() {
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
		super.updatePosition();
	}

	public AsteroidSize getAsteroidSize() {
		return asteroidSize;
	}

	private enum RotationDirection {LEFT, RIGHT}

	public enum AsteroidSize {
		LARGE(1.0, 2.0), MEDIUM(1.5, 3.0), SMALL(2.0, 4.0);

		private final double minSpeed;
		private final double maxSpeed;

		AsteroidSize(double minSpeed, double maxSpeed) {
			this.minSpeed = minSpeed;
			this.maxSpeed = maxSpeed;
		}

		public double getMaxSpeed() {
			return maxSpeed;
		}

		public double getMinSpeed() {
			return minSpeed;
		}
	}
}
