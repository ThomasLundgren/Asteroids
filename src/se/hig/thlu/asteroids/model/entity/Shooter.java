package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.model.entity.Missile;

@FunctionalInterface
public interface Shooter {

	Missile shoot(double direction);

}
