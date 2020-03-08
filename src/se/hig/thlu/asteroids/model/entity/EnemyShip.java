package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.mathutil.Trigonometry;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;
import se.hig.thlu.asteroids.model.entity.Missile.MissileSource;

import java.util.Optional;

public final class EnemyShip extends AbstractEntity implements Shooter {

	private static final double ENEMY_SHIP_SPEED = 2.0;
	private int ticksSinceLastShot = 1337;

	public EnemyShip(double direction) {
		super(new Point(),
				new Velocity(ENEMY_SHIP_SPEED, direction),
				0.0,
				23,
				16);
		setRotation(direction);
	}

	@Override
	public Optional<Missile> shoot(double direction, double distance) {
		if (ticksSinceLastShot < MissileSource.ENEMY.getCoolDown()) {
			return Optional.empty();
		}
		ticksSinceLastShot = 0;
		double centerFrontDistance = ((double) width / 2.0);
		Point missileStart = Trigonometry.rotateAroundPoint(center,
				rotation,
				centerFrontDistance);
		Missile missile = new Missile(missileStart, direction, MissileSource.ENEMY, distance);
		return Optional.of(missile);
	}

	@Override
	public void update() {
		super.update();
		ticksSinceLastShot++;
	}

	@Override
	public Optional<Explosion> destroy() {
		super.destroy();
		return Optional.of(new Explosion(center, Explosion.ExplosionSize.TWO));
	}
}
