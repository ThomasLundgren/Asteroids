package se.hig.thlu.asteroids.entityfactory;

import se.hig.thlu.asteroids.model.*;

import java.util.List;

public interface EntityFactory {

	List<Asteroid> nextLevel(Point playerCenter);

	List<Entity> shatterAsteroid(Asteroid destroyedAsteroid);

	EnemyShip createEnemyShip(Point playerCenter);

	PlayerShip createPlayerShip();

}
