package se.hig.thlu.asteroids.util;

public class Trigonometry {

	private Trigonometry() {
	}

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
}
