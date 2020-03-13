package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.model.Point;

import java.util.List;

public interface EntityFactory {

	List<Entity> nextLevel(Point playerCenter);

	EnemyShip createEnemyShip(Point playerCenter);

	PlayerShip createPlayerShip();

}
