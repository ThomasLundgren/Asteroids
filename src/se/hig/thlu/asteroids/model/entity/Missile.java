package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;

public final class Missile extends AbstractEntity {

	private final MissileSource missileSource;
	private final Point startingPosition;
	private double distanceTravelled = 0.0;
	private final double maxDistance;

	Missile(Point position, double direction, MissileSource source, double maxDistance) {
		super(position,
				new Velocity(source.getMissileSpeed(), direction),
				0.0,
				source.getWidth(),
				source.getHeight());
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

	public enum MissileSource {
		PLAYER(9.0,  17, 9, 12),
		ENEMY(4.0,  9, 6, 310);

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