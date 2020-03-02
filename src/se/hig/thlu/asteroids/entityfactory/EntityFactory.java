package se.hig.thlu.asteroids.entityfactory;

import se.hig.thlu.asteroids.model.Asteroid;
import se.hig.thlu.asteroids.model.EnemyShip;
import se.hig.thlu.asteroids.model.PlayerShip;

import java.util.List;

public interface EntityFactory {

	List<Asteroid> nextLevel();

	List<Asteroid> shatterAsteroid(Asteroid destroyedAsteroid);

	EnemyShip createEnemyShip();

	PlayerShip createPlayerShip();

}
