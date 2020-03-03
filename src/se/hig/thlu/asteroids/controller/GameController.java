package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.controller.command.CommandController.CommandType;
import se.hig.thlu.asteroids.entityfactory.EntityFactory;
import se.hig.thlu.asteroids.gui.GUI;
import se.hig.thlu.asteroids.gui.eventlistener.EventAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.EventListenerAdapter;
import se.hig.thlu.asteroids.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GameController {

	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final GUI<? extends EventListenerAdapter<? extends EventAdapter>> gui;
	private final List<Entity> missiles = new CopyOnWriteArrayList<>();
	private final List<Entity> asteroids = new CopyOnWriteArrayList<>();
	private final List<Entity> enemyShips = new CopyOnWriteArrayList<>();
	private final List<Entity> explosions = new CopyOnWriteArrayList<>();
	private final EntityFactory factory;
	private BigDecimal totalGameTime = new BigDecimal("0.0");
	private double timeSinceLastShot = Double.MAX_VALUE;
	private double enemyShipSpawnTimer = 0.0;
	private boolean canShoot = true;

	public GameController(EntityFactory factory,
						   CommandController commandController,
						   PlayerShip playerShip,
						   GUI<?extends EventListenerAdapter<? extends EventAdapter>> gui) {
		this.factory = factory;
		this.commandController = commandController;
		this.playerShip = playerShip;
		this.gui = gui;
		centerPlayerShip();
		gui.addEntity(playerShip);
	}

	public void update(double delta) {
		updateTimes(delta);
		spawnEnemies();
		executeActiveCommands();
		updatePositions();
		checkCollisions();
	}

	private void updatePositions() {
		asteroids.forEach(this::updatePosition);
		missiles.forEach(this::updatePosition);
		enemyShips.forEach(this::updatePosition);
		updatePosition(playerShip);
	}

	private void spawnEnemies() {
		if (asteroids.isEmpty() && enemyShips.isEmpty()) {
			asteroids.addAll(factory.nextLevel(playerShip.getCenter()));
			gui.addEntities(asteroids);
		}
		if (enemyShipSpawnTimer > GameConfig.ENEMY_SHIP_SPAWN_TIME) {
			enemyShipSpawnTimer = 0.0;
			EnemyShip enemyShip = factory.createEnemyShip(playerShip.getCenter());
			enemyShips.add(enemyShip);
			gui.addEntity(enemyShip);
		}
	}

	public void handleKeyPressed(InputController.PressedKey key) {
		switch (key) {
			case LEFT_ARROW:
				commandController.activate(CommandType.TURN_LEFT);
				commandController.deactivate(CommandType.TURN_RIGHT);
				break;
			case UP_ARROW:
				commandController.activate(CommandType.ACCELERATE);
				commandController.deactivate(CommandType.DECELERATE);
				break;
			case RIGHT_ARROW:
				commandController.activate(CommandType.TURN_RIGHT);
				commandController.deactivate(CommandType.TURN_LEFT);
				break;
			case SPACE_BAR:
				if (timeSinceLastShot > GameConfig.PLAYER_MISSILE_RECHARGE_MS && canShoot) {
					canShoot = false;
					timeSinceLastShot = 0.0;
					Missile missile = playerShip.shoot(playerShip.getFacingDirection());
					missiles.add(missile);
					gui.addEntity(missile);
				}
				break;
			default:
				break;
		}
	}

	public void handleKeyReleased(InputController.PressedKey key) {
		switch (key) {
			case LEFT_ARROW:
				commandController.deactivate(CommandType.TURN_LEFT);
				break;
			case UP_ARROW:
				commandController.deactivate(CommandType.ACCELERATE);
				commandController.activate(CommandType.DECELERATE);
				break;
			case RIGHT_ARROW:
				commandController.deactivate(CommandType.TURN_RIGHT);
				break;
			case SPACE_BAR:
				canShoot = true;
				break;
			default:
				break;
		}
	}

	private void updatePosition(Entity entity) {
		entity.updatePosition();
		handleOverflow(entity);
	}

	private void handleOverflow(Entity entity) {
		double x = entity.getCenter().getX();
		double y = entity.getCenter().getY();
		if (x > (double) GameConfig.WINDOW_WIDTH) {
			entity.setCenter(new Point(0.0, y));
		}
		if (x < 0.0) {
			entity.setCenter(new Point((double) GameConfig.WINDOW_WIDTH, y));
		}
		if (y > (double) GameConfig.WINDOW_HEIGHT) {
			entity.setCenter(new Point(x, 0.0));
		}
		if (y < 0.0) {
			entity.setCenter(new Point(x, (double) GameConfig.WINDOW_HEIGHT));
		}
	}

	private void executeActiveCommands() {
		commandController.executeCommands();
	}

	private void updateTimes(double delta) {
		totalGameTime = totalGameTime.add(new BigDecimal(delta));
		timeSinceLastShot += delta;
		enemyShipSpawnTimer += delta;
	}

	private void checkCollisions() {
		for (Entity asteroid : asteroids) {
			if (playerShip.intersectsWith(asteroid)) {
				System.out.println("PlayerShip and Asteroid collision");
				Optional<Explosion> explosion = playerShip.collide();
				explosion.ifPresent(expl -> {
					centerPlayerShip();
					explosions.add(expl);
					gui.addEntity(expl);
				});
				explosion = asteroid.collide();
				explosion.ifPresent(expl -> {
					explosions.add(expl);
					gui.addEntity(expl);
				});
				List<Entity> newAsteroids = factory.shatterAsteroid((Asteroid) asteroid);
				asteroids.addAll(newAsteroids);
				gui.addEntities(newAsteroids);
				asteroids.remove(asteroid);
				gui.removeEntity(asteroid);
			}
			for (Entity missile : missiles) {
				if (missile.isDestroyed()) {
					missiles.remove(missile);
					gui.removeEntity(missile);
				}
				if (missile.intersectsWith(asteroid)) {
					missile.collide();
					Optional<Explosion> explosion = asteroid.collide();
					explosion.ifPresent(expl -> {
						explosions.add(expl);
						gui.addEntity(expl);
					});
					List<Entity> newAsteroids = factory.shatterAsteroid((Asteroid) asteroid);
					asteroids.addAll(newAsteroids);
					gui.addEntities(newAsteroids);
					missiles.remove(missile);
					gui.removeEntity(missile);
					asteroids.remove(asteroid);
					gui.removeEntity(asteroid);
				}
			}
		}
		for (Entity enemyShip : enemyShips) {
			if (playerShip.intersectsWith(enemyShip)) {
				playerShip.collide();
				enemyShip.collide();
				enemyShips.remove(enemyShip);
				gui.removeEntity(enemyShip);
			}
			for (Entity missile : missiles) {
				if (missile.intersectsWith(enemyShip)) {
					missile.collide();
					enemyShip.collide();
					missiles.remove(missile);
					gui.removeEntity(missile);
					enemyShips.remove(enemyShip);
					gui.removeEntity(enemyShip);
				}
			}
		}
		explosions.stream()
				.filter(Entity::isDestroyed)
				.forEach(explosion -> {
					System.out.println("Explosion removed");
					explosions.remove(explosion);
					gui.removeEntity(explosion);
				});
	}

	private void centerPlayerShip() {
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
	}

}
