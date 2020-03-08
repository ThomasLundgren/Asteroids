package se.hig.thlu.asteroids.model.entity;

import java.util.Optional;

@FunctionalInterface
public interface Shooter {

	Optional<Missile> shoot(double direction, double distance);

}
