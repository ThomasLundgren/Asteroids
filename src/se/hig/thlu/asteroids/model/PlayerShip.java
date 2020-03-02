package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.model.Missile.MissileSource;

import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.IS_ACCELERATING;
import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.LIVES;

public final class PlayerShip extends Entity implements Shooter {

	private static final double MAX_SPEED = 7.5;
	private static final double ACCELERATION = 0.05;
	private static final double DECELERATION = ACCELERATION / 3.0;
	private int lives = 3;

	private PlayerShip() {
		super(new Point(0.0, 0.0), new Velocity(0.0, 0.0), 5.0);
	}

	public static PlayerShip createPlayerShip() {
		return new PlayerShip();
	}

	public void accelerate() {
		if (velocity.getSpeed() >= MAX_SPEED) {
			velocity = new Velocity(MAX_SPEED, velocity.getDirection());
			return;
		}
		Velocity acceleration = new Velocity(ACCELERATION, facingDirection);
		velocity.composeWith(acceleration);
		notifyListeners(IS_ACCELERATING.getPropertyName(), true);
		System.out.println(center);
	}

	public void decelerate() {
		if (velocity.getSpeed() < DECELERATION) {
			velocity = new Velocity(0.0, velocity.getDirection());
			return;
		}
		Velocity deceleration = new Velocity(DECELERATION, velocity.getDirection() - 180.0);
		velocity.composeWith(deceleration);
		notifyListeners(IS_ACCELERATING.getPropertyName(), false);
	}

	@Override
	public void turnLeft() {
		super.turnLeft();
	}

	@Override
	public void turnRight() {
		super.turnRight();
	}

	@Override
	public void collide() {
		if (lives == 1) {
			super.collide();
		} else {
			lives--;
		}
		notifyListeners(LIVES.getPropertyName(), lives);
	}

	//TODO: remove direction argument from missile
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