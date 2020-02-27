package test.se.hig.thlu.asteroids.model;

import org.junit.jupiter.api.*;
import se.hig.thlu.asteroids.model.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerShipTest {

	@Test
	public void getSpeed_onNewShip_returnsZero() {
		PlayerShip playerShip = new PlayerShip();
		assertEquals(0.0, playerShip.getVelocity().getSpeed());
	}

	@Test
	public void accelerate_incrementsSpeed() {
		PlayerShip playerShip = new PlayerShip();
		Velocity velocity = playerShip.getVelocity();
		assertEquals(0.0, velocity.getSpeed());

		playerShip = playerShip.accelerate();
		velocity = playerShip.getVelocity();
		assertEquals(1.0, velocity.getSpeed());

		playerShip = playerShip.accelerate();
		velocity = playerShip.getVelocity();
		assertEquals(2.0, velocity.getSpeed());

		playerShip = playerShip.accelerate();
		velocity = playerShip.getVelocity();
		assertEquals(3.0, velocity.getSpeed());
	}

}