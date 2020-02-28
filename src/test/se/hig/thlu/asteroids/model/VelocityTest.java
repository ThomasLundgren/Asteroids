package test.se.hig.thlu.asteroids.model;

import org.junit.jupiter.api.*;
import se.hig.thlu.asteroids.model.*;

import static org.junit.jupiter.api.Assertions.*;

class VelocityTest {

	private Velocity v;

	@BeforeEach
	public void setUp() {
		v = new Velocity(0.0, 0.0);
	}

	@AfterEach
	public void tearDown() {
		v = null;
	}

	@Test
	public void constructor_withSpeedLessThanZero_setsSpeedToZero() {
		Velocity vel = new Velocity(-1.0, 10.0);
		assertEquals(0.0, vel.getSpeed());
	}

	@Test
	public void constructor_withDirectionLessThanZero_convertsToCorrectDegrees() {
		Velocity v = new Velocity(1.0, -359);
		assertEquals(1.0, v.getDirection());
		v = new Velocity(1.0, -719.0);
		assertEquals(1.0, v.getDirection());
	}

	@Test
	public void constructor_withDirectionMoreThan360_convertsToCorrectDegrees() {
		Velocity v = new Velocity(1.0, 361.0);
		assertEquals(1.0, v.getDirection());
		v = new Velocity(1.0, 721.0);
		assertEquals(1.0, v.getDirection());
	}

	@Test
	public void composeWithTest() {
		Velocity startV = new Velocity(1.0, 180.0);
		Velocity otherV = new Velocity(1.0, 180.0);
		startV.composeWith(otherV);
		double expectedSpeed = 2.0;
		double actualSpeed = startV.getSpeed();
		assertEquals(expectedSpeed, actualSpeed);
	}


//	private double getRandomSpeedBetween0And10() {
//		return Math.random() * 10;
//	}
}