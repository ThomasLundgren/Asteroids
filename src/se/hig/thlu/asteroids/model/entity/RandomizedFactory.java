package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.event.EventHandlerFactory;
import se.hig.thlu.asteroids.event.create.AsteroidCreateEvent;
import se.hig.thlu.asteroids.event.create.EnemyShipCreateEvent;
import se.hig.thlu.asteroids.event.create.CreateEventHandler;
import se.hig.thlu.asteroids.event.create.PlayerShipCreateEvent;
import se.hig.thlu.asteroids.event.entity.EntityEventHandler;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.Velocity;
import se.hig.thlu.asteroids.util.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static se.hig.thlu.asteroids.model.entity.Asteroid.AsteroidSize.LARGE;

public final class RandomizedFactory implements EntityFactory {

	private PlayerShip playerShip;
	private int level = 0;
	private final EntityEventHandler entityHandler;
	private final CreateEventHandler createHandler;

	public RandomizedFactory() {
		entityHandler = EventHandlerFactory.getEventHandler(EntityEventHandler.class);
		createHandler = EventHandlerFactory.getEventHandler(CreateEventHandler.class);
		playerShip = createPlayerShip();
	}

	@Override
	public List<Entity> nextLevel(Point playerCenter) {
		level++;
		List<Entity> asteroids = new ArrayList<>(level);
		for (int i = 0; i < level; i++) {
			Point randomPoint = randomPoint(playerCenter);
			Asteroid newAsteroid = createLargeAsteroid(randomPoint);
			createHandler.notify(new AsteroidCreateEvent(newAsteroid));
			asteroids.add(newAsteroid);
		}
		return asteroids;
	}

	@Override
	public EnemyShip createEnemyShip(Point playerCenter) {
		Point randomPoint;
		int shipX = 0;
		double direction = 0.0;

		int randomX = ThreadLocalRandom.current().nextInt(0, 2);
		if (randomX == 1) {
			shipX = GameConfig.WINDOW_WIDTH;
			direction = -180.0;
		}

		double yDistanceToPlayer = 0.0;
		do {
			int randomY = ThreadLocalRandom.current().nextInt(0, GameConfig.WINDOW_HEIGHT);
			randomPoint = new Point((double) shipX, (double) randomY);
			yDistanceToPlayer = StrictMath.abs((double) randomY - playerCenter.getY());
		} while (yDistanceToPlayer < ((double) GameConfig.WINDOW_WIDTH / 4.0));

		EnemyShip enemyShip = new EnemyShip(direction);
		enemyShip.setCenter(randomPoint);

		entityHandler.addObserverMapping(playerShip.getId(), enemyShip);
		createHandler.notify(new EnemyShipCreateEvent(enemyShip));
		return enemyShip;
	}

	@Override
	public PlayerShip createPlayerShip() {
		if (playerShip == null) {
			playerShip = new PlayerShip();
		}
		createHandler.notify(new PlayerShipCreateEvent(playerShip));
		return playerShip;
	}

	private Point randomPoint(Point playerCenter) {
		Point randomPoint;
		do {
			int randomX = ThreadLocalRandom.current().nextInt(0, GameConfig.WINDOW_WIDTH);
			int randomY = ThreadLocalRandom.current().nextInt(0, GameConfig.WINDOW_HEIGHT);
			randomPoint = new Point((double) randomX, (double) randomY);
		} while (randomPoint.distanceTo(playerCenter) < (double) GameConfig.ENEMY_SPAWN_MIN_DISTANCE);

		return randomPoint;
	}

	private Asteroid createLargeAsteroid(Point point) {
		double min = LARGE.getMinSpeed();
		double max = LARGE.getMaxSpeed();
		double speed = Randomizer.randomSpeed(min, max);
		double dir = Randomizer.randomDirection(0.0, 360.0);
		return new Asteroid(point, new Velocity(speed, dir), LARGE);
	}

}
