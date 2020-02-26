package test.se.hig.thlu.asteroids.model.velocity;

import org.junit.jupiter.api.*;
import se.hig.thlu.asteroids.model.*;

class VelocityTest {

	@Test
	public void constructor_withSpeedLessThanZero_throwsException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Velocity(-1.0, 10.0);
		});
	}
}