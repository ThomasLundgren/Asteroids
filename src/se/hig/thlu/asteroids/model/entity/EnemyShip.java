package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;
import se.hig.thlu.asteroids.model.entity.Missile.MissileSource;

import java.util.Optional;

public final class EnemyShip extends AbstractEntity implements Shooter {

	private static final double ENEMY_SHIP_SPEED = 2.0;

	public EnemyShip(double direction) {
		super(new Point(),
				new Velocity(ENEMY_SHIP_SPEED, direction),
				0.0,
				23,
				16);
		setRotation(direction);
	}

	@Override
	public Missile shoot(double direction) {
		return new Missile(center, direction, MissileSource.ENEMY);
	}

	@Override
	public Optional<Explosion> destroy() {
		super.destroy();
		return Optional.of(new Explosion(center, Explosion.ExplosionSize.TWO));
	}
}
