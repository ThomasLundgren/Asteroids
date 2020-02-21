package se.hig.thlu.asteroids.model;

import java.util.Optional;

public class EntityBuilder {

    public static final long FIRING_INTERVAL_MILLIS = 500L;
    protected Point position = new Point(0.0, 0.0);
    protected double speed = 0.0;
    protected double direction = 0.0;
    protected boolean isDestroyed = false;
    protected Asteroid.AsteroidSize asteroidSize;
    protected Optional<Missile> latestMissile;
    protected long lastShotMillis;
    protected long firingIntervalMillis;
    protected Missile.MissileSource missileSource;
    private int lives = 0;

    public EntityBuilder() {
        resetAttributes();
    }

    private void resetAttributes() {
        position = new Point(0.0, 0.0);
        speed = 0.0;
        direction = 0.0;
        isDestroyed = false;
        asteroidSize = Asteroid.AsteroidSize.LARGE;
        latestMissile = Optional.empty();
        lastShotMillis = Long.MAX_VALUE;
        firingIntervalMillis = FIRING_INTERVAL_MILLIS;
        missileSource = Missile.MissileSource.ENEMY;
        lives = 3;
    }

    public EntityBuilder withPosition(Point position) {
        this.position = position;
        return this;
    }

    public EntityBuilder withSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public EntityBuilder withDirection(double direction) {
        this.direction = direction;
        return this;
    }

    public EntityBuilder withIsDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
        return this;
    }

    public EntityBuilder withAsteroidSize(Asteroid.AsteroidSize asteroidSize) {
        this.asteroidSize = asteroidSize;
        return this;
    }

    public EntityBuilder withLatestMissile(Optional<Missile> latestMissile) {
        this.latestMissile = latestMissile;
        return this;
    }

    public EntityBuilder withLastShotMillis(long lastShotMillis) {
        this.lastShotMillis = lastShotMillis;
        return this;
    }

    public EntityBuilder withFiringIntervalMillis(long firingIntervalMillis) {
        this.firingIntervalMillis = firingIntervalMillis;
        return this;
    }

    public EntityBuilder withSource(Missile.MissileSource missileSource) {
        this.missileSource = missileSource;
        return this;
    }

    public EntityBuilder withLives(int lives) {
        this.lives = lives;
        return this;
    }

}
