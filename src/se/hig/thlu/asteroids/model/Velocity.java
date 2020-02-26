package se.hig.thlu.asteroids.model;

public class Velocity {

	private final double speed;
	private final double direction;

	public Velocity(double speed, double direction) {
		this.speed = speed;
		this.direction = direction;
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
}
