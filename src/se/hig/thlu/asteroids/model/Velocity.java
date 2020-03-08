package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.util.*;

public class Velocity {

	private double speed;
	private double direction;

	public Velocity() { }

	public Velocity(double speed, double direction) {
		setSpeed(speed);
		setDirection(direction);
	}

	public static Velocity compose(Velocity v1, Velocity v2) {
		double x1 = StrictMath.cos(StrictMath.toRadians(v1.getDirection())) * v1.getSpeed();
		double y1 = StrictMath.sin(StrictMath.toRadians(v1.getDirection())) * v1.getSpeed();
		double x2 = StrictMath.cos(StrictMath.toRadians(v2.getDirection())) * v2.getSpeed();
		double y2 = StrictMath.sin(StrictMath.toRadians(v2.getDirection())) * v2.getSpeed();

		double xRes = x1 + x2;
		double yRes = y1 + y2;

		double newDir = 0.0;

		if (xRes != 0.0) {
			newDir = StrictMath.toDegrees(StrictMath.atan((yRes / xRes)));
			if (xRes < 0.0) {
				// 90 < degree < 270-359
				newDir += 180.0;
			}
		} else if (yRes > 0.0) {
			newDir = 90.0;
		} else {
			newDir = 270.0;
		}

		double newSpeed = Trigonometry.hypotenuse(xRes, yRes);
		return new Velocity(newSpeed, newDir);
	}

	public void composeWith(Velocity velocity) {
		Velocity composed = compose(this, velocity);
		setDirection(composed.getDirection());
		setSpeed(composed.getSpeed());
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
