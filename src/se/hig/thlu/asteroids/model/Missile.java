package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.config.*;

public final class Missile extends Entity {

	private final MissileSource missileSource;
	private final Point startingPosition;
	protected Missile(Point position, double direction, MissileSource source) {
		super(position, new Velocity(source.getMissileSpeed(), direction));
		startingPosition = position;
		missileSource = source;
	}

	@Override
	public void updatePosition() {
		super.updatePosition();
		if (center.distanceTo(startingPosition) > 0.6 * GameConfig.windowWidth) {
			isDestroyed = true;
		}
	}

	public enum MissileSource {
		PLAYER(5.0), ENEMY(5.0);

        final double missileSpeed;

        MissileSource(double missileSpeed) {
            this.missileSpeed = missileSpeed;
        }

        public double getMissileSpeed() {
            return missileSpeed;
        }
    }
}