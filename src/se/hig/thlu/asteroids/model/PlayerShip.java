package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.model.Missile.*;
import se.hig.thlu.asteroids.util.*;

public final class PlayerShip extends Entity implements Shooter {

	private static final double MAX_SPEED = 7.5;
	private static final double TURNING_DEGREE = 5.0;
	private static final double ACCELERATION = 0.05;
	private static final double DECELERATION = 0.04;
	private int lives = 3;
	private double facingDirection = 0.0;
	private boolean isAccelerating = false;

	public PlayerShip() {
		super(new Point(0.0, 0.0), new Velocity(0.0, 0.0));
	}

	private static double lessThanTopSpeed(double speed) {
		if (speed > MAX_SPEED) {
			throw new IllegalArgumentException("PlayerShip cannot exceed MAX_SPEED");
		}
		return speed;
	}

	public int getLives() {
		return lives;
	}

	public void accelerate() {
		isAccelerating = true;
		Velocity acceleration = new Velocity(ACCELERATION, facingDirection);
		velocity.composeWith(acceleration);
		if (velocity.getSpeed() > MAX_SPEED) {
			velocity.setSpeed(MAX_SPEED);
		}
	}

	public void decelerate() {
		isAccelerating = false;
		Velocity deceleration = new Velocity(DECELERATION, velocity.getDirection() - 180.0);
		velocity.composeWith(deceleration);
	}

	public void turnLeft() {
		facingDirection -= TURNING_DEGREE;
		facingDirection = Trigonometry.normalizeDegree(facingDirection);
	}

	public void turnRight() {
		facingDirection += TURNING_DEGREE;
		facingDirection = Trigonometry.normalizeDegree(facingDirection);
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
			isDestroyed = true;
		} else {
			lives--;
		}
	}

	@Override
	public Missile shoot(double direction) {
		return new Missile(center, velocity.getDirection(), MissileSource.PLAYER);
	}

}