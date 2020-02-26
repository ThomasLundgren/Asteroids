package se.hig.thlu.asteroids.model;

public class Asteroid extends Entity {

    private final AsteroidSize asteroidSize;

    public enum AsteroidSize { LARGE, MEDIUM, SMALL }

    public Asteroid() {
        asteroidSize = AsteroidSize.LARGE;
    }

    public Asteroid(Point position, Velocity velocity, AsteroidSize asteroidSize) {
        super(position, velocity);
        this.asteroidSize = asteroidSize;
    }

    private Asteroid(Point position, Velocity velocity, AsteroidSize asteroidSize, boolean isDestroyed) {
        super(position, velocity, isDestroyed);
        this.asteroidSize = asteroidSize;
    }

    @Override
    public Entity withPosition(Point position) {
        return new Asteroid(position, velocity, asteroidSize);
    }

    @Override
    protected Entity withVelocityImpl(Velocity velocity) {
        return new Asteroid(position, velocity, asteroidSize);
    }

    @Override
    public Entity destroy() {
        return new Asteroid(position, velocity, asteroidSize, true);
    }

}
