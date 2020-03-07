package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.mathutil.Trigonometry;
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
	private boolean isAccelerating = false;

	public PlayerShip() {
		super(new Point(),
				new Velocity(),
				5.0,
				35,
				26);
	}

	public void accelerate() {
		isAccelerating = true;
		notifyObservers(EntityProperty.IS_ACCELERATING,true);

		Velocity acceleration = new Velocity(ACCELERATION, rotation);
		velocity.composeWith(acceleration);
		if (velocity.getSpeed() >= MAX_SPEED) {
			velocity = new Velocity(MAX_SPEED, velocity.getDirection());
		}
	}

	public void decelerate() {
		isAccelerating = false;
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
		return Optional.of(new Explosion(center, Explosion.ExplosionSize.FOUR));
	}

	@Override
	public Missile shoot(double direction) {
		double centerFrontDistance = (double) (width / 2);
		Point missileStart = Trigonometry.rotateAroundPoint(center,
				rotation,
				centerFrontDistance);
		// TODO: Magic number...
//		missileStart.setY(missileStart.getY() + 5.0);
		return new Missile(missileStart, rotation, MissileSource.PLAYER);
	}

}