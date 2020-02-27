package se.hig.thlu.asteroids.model;

public class Velocity {

	private final double speed;
	private final double direction;

	public Velocity(double speed, double direction) {
		this.speed = validateSpeed(speed);
		this.direction = validateDirection(direction);
	}

	public Velocity composeWith(Velocity velocity) {
		double x1 = StrictMath.cos(StrictMath.toRadians(direction)) * speed;
		double y1 = StrictMath.sin(StrictMath.toRadians(direction)) * speed;
		double x2 = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		double y2 = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();

		double xRes = x1 + x2;
		double yRes = y1 + y2;

		double newDir = 0.0;
		if (xRes != 0.0) {
			newDir = StrictMath.toDegrees(StrictMath.atan(yRes/xRes));
		} else if (yRes > 0.0) {
			newDir = 90.0;
		} else {
			newDir = 270.0;
		}

		double newSpeed = StrictMath.sqrt(xRes*xRes + yRes*yRes);
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
		direction = direction % lap;
		if (direction < 0.0) {
			direction = direction + lap;
		}
		return direction % lap;
	}

	private static double validateSpeed(double speed) {
		if (speed < 0.0) {
			throw new IllegalArgumentException("Speed cannot be less than zero");
		}
		return speed;
	}

	@Override
	public String toString() {
		return "Velocity{" +
				"speed=" + speed +
				", direction=" + direction +
				'}';
	}
}
