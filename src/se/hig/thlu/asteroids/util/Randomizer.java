package se.hig.thlu.asteroids.util;

import java.util.concurrent.ThreadLocalRandom;

public enum Randomizer {
	;

	public static double randomSpeed(double min, double max) {
		return ThreadLocalRandom.current()
				.nextDouble(min, max);
	}

	public static double randomDirection(double min, double max) {
		return ThreadLocalRandom.current()
				.nextDouble(min, max);
	}

	private static double chooseOne(double first, double second) {
		int randomInt = ThreadLocalRandom.current().nextInt(0, 2);
		return randomInt == 0 ? first : second;
	}

}
