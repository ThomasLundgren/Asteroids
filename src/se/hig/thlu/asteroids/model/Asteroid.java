package se.hig.thlu.asteroids.model;

public class Asteroid extends Entity {

    private final AsteroidSize asteroidSize;

    public enum AsteroidSize { LARGE, MEDIUM, SMALL }

    public Asteroid() {
        asteroidSize = AsteroidSize.LARGE;
    }

    public Asteroid(Point position, double speed, double direction, AsteroidSize asteroidSize) {
        super(position, speed, direction);
        this.asteroidSize = asteroidSize;
    }

    private Asteroid(Point position, double speed, double direction, AsteroidSize asteroidSize, boolean isDestroyed) {
        super(position, speed, direction, isDestroyed);
        this.asteroidSize = asteroidSize;
    }

    @Override
    public Entity withPosition(Point position) {
        return new Asteroid(position, speed, direction, asteroidSize);
    }

    @Override
    protected Entity withSpeedImpl(double speed) {
        return new Asteroid(position, speed, direction, asteroidSize);
    }

    @Override
    protected Entity withDirectionImpl(double direction) {
        return new Asteroid(position, speed, direction, asteroidSize);
    }

    @Override
    public Entity destroy() {
        return new Asteroid(position, speed, direction, asteroidSize, true);
    }

}
