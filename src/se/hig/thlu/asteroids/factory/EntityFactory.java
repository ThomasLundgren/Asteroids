package se.hig.thlu.asteroids.factory;

import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.model.entity.EnemyShip;
import se.hig.thlu.asteroids.model.entity.Entity;
import se.hig.thlu.asteroids.model.entity.PlayerShip;

import java.util.List;

public interface EntityFactory {

	List<Entity> nextLevel(Point playerCenter);

	EnemyShip createEnemyShip(Point playerCenter);

	PlayerShip createPlayerShip();

}
