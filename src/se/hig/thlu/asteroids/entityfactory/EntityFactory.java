package se.hig.thlu.asteroids.entityfactory;

import se.hig.thlu.asteroids.model.Asteroid;
import se.hig.thlu.asteroids.model.EnemyShip;
import se.hig.thlu.asteroids.model.PlayerShip;
import se.hig.thlu.asteroids.model.Point;

import java.util.List;

public interface EntityFactory {

	Asteroid createLargeAsteroid(Point center);

	List<Asteroid> shatterAsteroid(Asteroid destroyedAsteroid);

	PlayerShip createPlayerShip();

	EnemyShip createEnemyShip();

}
