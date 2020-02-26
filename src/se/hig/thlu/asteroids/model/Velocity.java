package se.hig.thlu.asteroids.model;

public class Velocity {

	private final double speed;
	private final double direction;

	public Velocity(double speed, double direction) {
		this.speed = validateSpeed(speed);
		this.direction = validateDirection(direction);
	}

	public Velocity computeNew(Velocity velocity) {
		double x1 = StrictMath.cos(direction) * speed;
		double y1 = StrictMath.sin(direction) * speed;
		double x2 = StrictMath.cos(velocity.getDirection()) * velocity.getSpeed();
		double y2 = StrictMath.sin(velocity.getDirection()) * velocity.getSpeed();

		double xRes = x1 + x2;
		double yRes = y1 + y2;

		double newDir = StrictMath.acos(xRes);
		double newSpeed = Math.sqrt(xRes*xRes + yRes*yRes);
		return new Velocity(newSpeed, newDir);
	}

	public double getSpeed() {
		return speed;
	}

	public double getDirection() {
		return direction;
	}

	private static double validateDirection(double direction) {
		final double lap = 360.0;
		double temp = direction % lap;
		if (temp < 0.0) {
			temp = lap - temp;
		}
		return temp % lap;
	}

	private static double validateSpeed(double speed) {
		if (speed < 0.0) {
			throw new IllegalArgumentException("Speed cannot be less than zero");
		}
		return speed;
	}
}
