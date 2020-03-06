package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.GameConfig;

public final class Missile extends AbstractEntity {

	private final MissileSource missileSource;
	private final Point startingPosition;

	Missile(Point position, double direction, MissileSource source) {
		super(position,
				new Velocity(source.getMissileSpeed(), direction),
				0.0,
				source.getWidth(),
				source.getHeight());
		rotation = direction;
		startingPosition = position;
		missileSource = source;
	}

	public MissileSource getMissileSource() {
		return missileSource;
	}

	@Override
	public void updatePosition() {
		super.updatePosition();
		distanceTravelled += missileSource.getMissileSpeed();
		if (distanceTravelled > missileSource.getMissileDistance() * (double) GameConfig.WINDOW_WIDTH) {
			destroy();
		}
	}

	private double distanceTravelled = 0.0;

	public enum MissileSource {
		PLAYER(9.0, 0.6, 17, 21),
		ENEMY(5.0, 0.3, 9, 6);

		private final double missileSpeed;
		private final double missileDistance;
		private final int width, height;

		MissileSource(double missileSpeed, double missileDistance, int width, int height) {
			this.missileSpeed = missileSpeed;
			this.missileDistance = missileDistance;
			this.width = width;
			this.height = height;
		}

		private double getMissileSpeed() {
			return missileSpeed;
		}

		public double getMissileDistance() {
			return missileDistance;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}
}