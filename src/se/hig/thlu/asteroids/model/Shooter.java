package se.hig.thlu.asteroids.model;

@FunctionalInterface
public interface Shooter {

    Missile shoot(double direction);

}
