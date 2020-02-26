package se.hig.thlu.asteroids.model;

public class Velocity {

	private final double speed;
	private final double direction;

	Velocity(double speed, double direction) {
		this.speed = validateSpeed(speed);
		this.direction = validateDirection(direction);
	}

	Velocity composeWith(Velocity velocity) {
		double x1 = StrictMath.cos(StrictMath.toRadians(direction)) * speed;
		double y1 = StrictMath.sin(StrictMath.toRadians(direction)) * speed;
		double x2 = StrictMath.cos(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();
		double y2 = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();

		double xRes = x1 + x2;
		double yRes = y1 + y2;

		double newDir = StrictMath.acos(StrictMath.toRadians(xRes));
		double newSpeed = Math.sqrt(xRes*xRes + yRes*yRes);
		return new Velocity(newSpeed, newDir);
	}

	double getSpeed() {
		return speed;
	}

	double getDirection() {
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

	@Override
	public String toString() {
		return "Velocity{" +
				"speed=" + speed +
				", direction=" + direction +
				'}';
	}
}
