package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.util.Trigonometry;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;
import se.hig.thlu.asteroids.model.entity.Missile.MissileSource;

import java.util.Optional;

public final class PlayerShip extends AbstractEntity implements Shooter {

	private static final double MAX_SPEED = 7.0;
	private static final double ACCELERATION = 0.04;
	private static final double DECELERATION = ACCELERATION / 2.5;
	private int lives = 3;
	private int ticksSinceLastShot = 1337;

	public PlayerShip() {
		super(new Point(),
				new Velocity(),
				5.0,
				new Dim(35,
						26));
	}

	public void accelerate() {
		notifyObservers(EntityProperty.IS_ACCELERATING,true);

		Velocity acceleration = new Velocity(ACCELERATION, rotation);
		velocity.composeWith(acceleration);
		if (velocity.getSpeed() >= MAX_SPEED) {
			velocity = new Velocity(MAX_SPEED, velocity.getDirection());
		}
	}

	public void decelerate() {
		notifyObservers(EntityProperty.IS_ACCELERATING,false);
		if (velocity.getSpeed() < DECELERATION) {
			velocity = new Velocity(0.0, velocity.getDirection());
			return;
		}
		Velocity deceleration = new Velocity(DECELERATION, velocity.getDirection() - 180.0);
		velocity.composeWith(deceleration);
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
	public Optional<Explosion> destroy() {
		if (lives == 1) {
			isDestroyed = true;
			lives--;
			super.destroy();
		} else {
			velocity = new Velocity();
			lives--;
		}
		return Optional.of(new Explosion(center, Explosion.ExplosionSize.THREE));
	}

	@Override
	public Optional<Missile> shoot(double direction, double distance) {
		if (ticksSinceLastShot < MissileSource.PLAYER.getCoolDown()) {
			return Optional.empty();
		}
		ticksSinceLastShot = 0;
		double centerFrontDistance = ((double) getDimensions().getWidth() / 2.0);
		Point missileStart = Trigonometry.rotateAroundPoint(center,
				rotation,
				centerFrontDistance);
		Missile missile = new Missile(missileStart, rotation, MissileSource.PLAYER, 0.6);
		return Optional.of(missile);
	}

	@Override
	public void update() {
		super.update();
		ticksSinceLastShot++;
	}
}