package test.se.hig.thlu.asteroids.model;

import org.junit.jupiter.api.*;
import se.hig.thlu.asteroids.model.*;

import static org.junit.jupiter.api.Assertions.*;

class VelocityTest {

	@Test
	public void constructor_withSpeedLessThanZero_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Velocity(-1.0, 10.0);
		});
	}

	@Test
	public void constructor_withDirectionLessThanZero_convertsToCorrectDegrees() {
		Velocity v = new Velocity(1.0, -359);
		assertEquals(1, v.getDirection());
		v = new Velocity(1.0, -719);
		assertEquals(1, v.getDirection());
	}

	@Test
	public void constructor_withDirectionMoreThan360_convertsToCorrectDegrees() {
		Velocity v = new Velocity(1.0, 361);
		assertEquals(1, v.getDirection());
		v = new Velocity(1.0, 721);
		assertEquals(1, v.getDirection());
	}

	@Test
	public void composeWith_onlySpeedInSameDirection_returnsVelocityWithSpeedsAdded() {
		Velocity v1 = new Velocity(1.0, 0.0);
		Velocity v2 = new Velocity(1.0, 0.0);
		Velocity result = v1.composeWith(v2);
		assertEquals(2, result.getSpeed());

		v1 = new Velocity(1.5, 0.0);
		v2 = new Velocity(2.5, 0.0);
		result = v1.composeWith(v2);
		assertEquals(4, result.getSpeed());
	}

	@Test
	public void composeWith_speedsInOppositeDirections_returnsVelocityWithSpeedsSubtracted() {
		Velocity v1 = new Velocity(1.0, 0.0);
		Velocity v2 = new Velocity(1.0, 180.0);
		Velocity result = v1.composeWith(v2);
		double expectedSpeed = 0.0;
		double difference = result.getSpeed() - expectedSpeed;
		assertTrue(difference < 0.0001);

		v1 = new Velocity(5.0, 0.0);
		v2 = new Velocity(2.0, 180.0);
		result = v1.composeWith(v2);
		expectedSpeed = 3.0;
		difference = result.getSpeed() - expectedSpeed;
		assertTrue(difference < 0.0001);

		v1 = new Velocity(1000.0, 0.0);
		v2 = new Velocity(1.0, 180.0);
		result = v1.composeWith(v2);
		expectedSpeed = 999;
		difference = result.getSpeed() - expectedSpeed;
		assertTrue(difference < 0.0001);
	}

	@Test
	public void composeWith_speedsInOrthogonalDirections_returnsVelocityWithSpeedsEqualToHypotenuse() {
		double speed1 = getRandomSpeedBetween0And10();
		double speed2 = getRandomSpeedBetween0And10();
		Velocity v1 = new Velocity(speed1, 0.0);
		Velocity v2 = new Velocity(speed2, 90.0);
		Velocity result = v1.composeWith(v2);
		double expectedSpeed = StrictMath.sqrt(speed1*speed1 + speed2*speed2);
		double difference = result.getSpeed() - expectedSpeed;
		assertTrue(difference < 0.0001);

		//
		speed1 = getRandomSpeedBetween0And10();
		speed2 = getRandomSpeedBetween0And10();
		v1 = new Velocity(speed1, 0.0);
		v2 = new Velocity(speed2, -90.0);
		result = v1.composeWith(v2);
		expectedSpeed = expectedSpeed = StrictMath.sqrt(speed1*speed1 + speed2*speed2);
		difference = result.getSpeed() - expectedSpeed;
		assertTrue(difference < 0.0001);

		speed1 = getRandomSpeedBetween0And10();
		speed2 = getRandomSpeedBetween0And10();
		v1 = new Velocity(speed1, 45.0);
		v2 = new Velocity(speed2, 135.0);
		result = v1.composeWith(v2);
		expectedSpeed = expectedSpeed = StrictMath.sqrt(speed1*speed1 + speed2*speed2);
		difference = result.getSpeed() - expectedSpeed;
		assertTrue(difference < 0.0001);
	}

	private double getRandomSpeedBetween0And10() {
		return Math.random() * 10;
	}
}