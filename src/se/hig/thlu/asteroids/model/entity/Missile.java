package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;

import java.util.Optional;

public final class Missile extends AbstractEntity {

	private final MissileSource missileSource;
	private final Point startingPosition;
	private double distanceTravelled = 0.0;
	private final double maxDistance;

	Missile(Point position, double direction, MissileSource source, double maxDistance) {
		super(position,
				new Velocity(source.getMissileSpeed(), direction),
				0.0,
				new Dim(source.getWidth(),
						source.getHeight()));
		this.maxDistance = maxDistance;
		rotation = direction;
		startingPosition = position;
		missileSource = source;
	}

	public MissileSource getMissileSource() {
		return missileSource;
	}

	@Override
	public void update() {
		super.update();
		distanceTravelled += missileSource.getMissileSpeed();
		if (distanceTravelled > maxDistance * (double) GameConfig.WINDOW_WIDTH) {
			destroy();
		}
	}

	@Override
	protected Optional<Explosion> createDeathExplosion() {
		return Optional.empty();
	}

	@Override
	public int getScore() {
		if (missileSource == MissileSource.PLAYER) {
			return 0;
		} else {
			return 1;
		}
	}

	public enum MissileSource {
		PLAYER(9.0,  17, 9, 9),
		ENEMY(3.5,  9, 6, 310);

		private final double missileSpeed;
		private final int width, height;
		private final int coolDown;

		MissileSource(double missileSpeed, int width, int height, int coolDown) {
			this.missileSpeed = missileSpeed;
			this.width = width;
			this.height = height;
			this.coolDown = coolDown;
		}

		private double getMissileSpeed() {
			return missileSpeed;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public int getCoolDown() {
			return coolDown;
		}
	}
}