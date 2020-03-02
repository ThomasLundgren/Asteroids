package test.se.hig.thlu.asteroids.model;

import org.junit.jupiter.api.*;
import se.hig.thlu.asteroids.model.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerShipTest {

	private PlayerShip playerShip;

	@BeforeEach
	public void setUp() {
		playerShip = PlayerShip.createPlayerShip();
	}

	@AfterEach
	public void tearDown() {
		playerShip = null;
	}

	@Test
	public void getSpeed_onNewShip_returnsZero() {
		assertEquals(0.0, playerShip.getVelocity());
	}

	@Test
	public void accelerate_incrementsSpeed() {
		Velocity velocity = playerShip.getVelocity();
		assertEquals(0.0, velocity.getSpeed());
		double faultMargin = 0.0001;
		double expectedSpeed = 0.0;
		double actualSpeed = 0.0;
		double speedDiff = 0.0;

		for (int i = 1; i < 11; i++) {
			playerShip.accelerate();
			expectedSpeed = 0.1 * i;
			actualSpeed = velocity.getSpeed();
			speedDiff = expectedSpeed - actualSpeed;
			assertTrue(speedDiff < faultMargin);
		}
	}

	@Test
	public void turnLeft_changesFacingDirection_byPlusOne() {
		double expectedDir = 359.0;
		double actualDir = 0.0;

		for (int i = 359; i > 0; i--) {
			playerShip.turnLeft();
			expectedDir = (double) i;
			actualDir = playerShip.getFacingDirection();
			assertEquals(expectedDir, actualDir);
		}
		// overflow to 0.0
		playerShip.turnLeft();
		assertEquals(0.0, playerShip.getFacingDirection());
	}

	@Test
	public void accelerating_inAllDirections_increasesSpeed() {
		for (int i = 0; i < 360; i++) {
			playerShip.turnLeft();
		}
		assertEquals(180.0, playerShip.getFacingDirection());
		Point pos = playerShip.getCenter();
		assertEquals(0.0, pos.getX());
		assertEquals(0.0, pos.getY());
		playerShip.accelerate();
		assertEquals(0.1, playerShip.getVelocity().getSpeed());
		playerShip.updatePosition();
		pos = playerShip.getCenter();
		assertEquals(0.1, pos.getX());
		assertEquals(0.0, pos.getY());

	}

	@Test
	public void accelerating_afterSpeedEqualToTen_doesNotIncreaseSpeed() {
		for (int i = 0; i < 19; i++) {
			playerShip.accelerate();
		}
		assertEquals(9.5, playerShip.getVelocity().getSpeed());

		playerShip.accelerate();
		assertEquals(10.0, playerShip.getVelocity().getSpeed());
		// doesn't go above 10.0
		playerShip.accelerate();
		assertEquals(10.0, playerShip.getVelocity().getSpeed());
	}
}