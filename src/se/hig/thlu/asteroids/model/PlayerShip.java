package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.model.Missile.MissileSource;
import se.hig.thlu.asteroids.util.Trigonometry;

import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.*;

public final class PlayerShip extends Entity implements Shooter {

	private static final double MAX_SPEED = 7.5;
	private static final double TURNING_DEGREE = 5.0;
	private static final double ACCELERATION = 0.05;
	private static final double DECELERATION = ACCELERATION / 3.0;
	private int lives = 3;
	private double facingDirection = 0.0;
	private boolean isAccelerating = false;

	public PlayerShip() {
		super(new Point(0.0, 0.0), new Velocity(0.0, 0.0));
	}

	public int getLives() {
		return lives;
	}

	public void accelerate() {
		Velocity acceleration = new Velocity(ACCELERATION, facingDirection);
		velocity.composeWith(acceleration);
		if (velocity.getSpeed() > MAX_SPEED) {
			velocity.setSpeed(MAX_SPEED);
		}
		isAccelerating = true;
		notifyListeners(IS_ACCELERATING.getPropertyName(), true);
	}

	public void decelerate() {
		if (velocity.getSpeed() < DECELERATION) {
			velocity.setSpeed(0.0);
			return;
		}
		Velocity deceleration = new Velocity(DECELERATION, velocity.getDirection() - 180.0);
		velocity.composeWith(deceleration);
		isAccelerating = false;
		notifyListeners(IS_ACCELERATING.getPropertyName(), false);
	}

	public void turnLeft() {
		facingDirection -= TURNING_DEGREE;
		facingDirection = Trigonometry.normalizeDegree(facingDirection);
		notifyListeners(FACING_DIRECTION.getPropertyName(), facingDirection);
	}

	public void turnRight() {
		facingDirection += TURNING_DEGREE;
		facingDirection = Trigonometry.normalizeDegree(facingDirection);
		notifyListeners(FACING_DIRECTION.getPropertyName(), facingDirection);
	}

	public double getFacingDirection() {
		return facingDirection;
	}

	public boolean isAccelerating() {
		return isAccelerating;
	}

	@Override
	public void collide() {
		if (lives == 1) {
			collide();
		} else {
			lives--;
		}
		notifyListeners(LIVES.getPropertyName(), lives);
	}

	@Override
	public Missile shoot(double direction) {
		return new Missile(center, velocity.getDirection(), MissileSource.PLAYER);
	}

	public enum PlayerShipProperty {

		LIVES("LIVES"),
		FACING_DIRECTION("FACING_DIRECTION"),
		IS_ACCELERATING("IS_ACCELERATING");

		private String propertyName;

		PlayerShipProperty(String propertyName) {
			this.propertyName = propertyName;
		}

		public String getPropertyName() {
			return propertyName;
		}
	}

}