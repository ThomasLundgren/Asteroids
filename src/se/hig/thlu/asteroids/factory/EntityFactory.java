package se.hig.thlu.asteroids.factory;

import se.hig.thlu.asteroids.model.*;

import java.util.List;

public interface EntityFactory {

	List<Entity> nextLevel(Point playerCenter);

	EnemyShip createEnemyShip(Point playerCenter);

	PlayerShip createPlayerShip();

}
