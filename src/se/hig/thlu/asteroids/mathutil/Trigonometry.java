package se.hig.thlu.asteroids.mathutil;

import se.hig.thlu.asteroids.model.Point;

public enum Trigonometry {
	;

	/**
	 * Normalizes a degree to the range 0 through 360.
	 *
	 * @param degree The degree to normalize.
	 * @return The validated normalize.
	 */
	public static double normalizeDegree(double degree) {
		final double lap = 360.0;
		degree = degree % lap;
		if (degree < 0.0) {
			degree = degree + lap;
		}
		return degree % lap;
	}

	public static double hypotenuse(double a, double b) {
		return StrictMath.sqrt((a * a) + (b * b));
	}

	public static Point rotateAroundPoint(Point rotationPoint, double angle, double distance) {
		double xRad = StrictMath.cos(StrictMath.toRadians(angle));
		double yRad = StrictMath.sin(StrictMath.toRadians(angle));
		double x = rotationPoint.getX() + xRad * distance;
		double y = rotationPoint.getY() + yRad * distance;
		return new Point(x, y);
	}
}
