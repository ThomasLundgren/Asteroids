package se.hig.thlu.asteroids.model;

public class Asteroid extends Entity {

    public enum AsteroidSize { LARGE, MEDIUM, SMALL }

    private final AsteroidSize asteroidSize;

    protected Asteroid(Point position, Velocity velocity, AsteroidSize size) {
        super(position, velocity);
        asteroidSize = size;
    }

}
