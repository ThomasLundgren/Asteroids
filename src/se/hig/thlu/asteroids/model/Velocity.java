package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.mathutil.*;

public class Velocity {

	private double speed;
	private double direction;

	public Velocity(double speed, double direction) {
		setSpeed(speed);
		setDirection(direction);
	}

	public void composeWith(Velocity velocity) {
		double x1 = StrictMath.cos(StrictMath.toRadians(direction)) * speed;
		double y1 = StrictMath.sin(StrictMath.toRadians(direction)) * speed;
		double x2 = StrictMath.cos(StrictMath.toRadians(velocity.direction)) * velocity.speed;
		double y2 = StrictMath.sin(StrictMath.toRadians(velocity.getDirection())) * velocity.getSpeed();

		double xRes = x1 + x2;
		double yRes = y1 + y2;


		double newDir = 0.0;
		if (xRes != 0.0) {
			// TODO: This is wrong. Only works for degrees between 0-90, 270-359
			newDir = StrictMath.toDegrees(StrictMath.atan((yRes / xRes)));
			if (xRes < 0.0) {
				newDir += 180.0;
			}
		} else if (yRes > 0.0) {
			newDir = 90.0;
		} else {
			newDir = 270.0;
		}

		double newSpeed = Trigonometry.hypotenuse(xRes, yRes);
		setDirection(newDir);
		setSpeed(newSpeed);
	}

	public double getSpeed() {
		return speed;
	}

	private void setSpeed(double speed) {
		this.speed = validateSpeed(speed);
	}

	public double getDirection() {
		return direction;
	}

	private void setDirection(double direction) {
		this.direction = Trigonometry.normalizeDegree(direction);
	}

	@Override
	public String toString() {
		return "Velocity{" +
				"speed=" + speed +
				", direction=" + direction +
				'}';
	}

	private static double validateSpeed(double speed) {
		if (speed < 0.0) {
			return 0.0;
		}
		return speed;
	}
}
