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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GameController {

	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final GUI<? extends EventListenerAdapter<? extends EventAdapter>> gui;
	private final Collection<Entity> missiles = new CopyOnWriteArrayList<>();
	private final Collection<Entity> enemies = new CopyOnWriteArrayList<>();
	private final Collection<Entity> explosions = new CopyOnWriteArrayList<>();
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
		missiles.forEach(this::updatePosition);
		enemies.forEach(this::updatePosition);
		updatePosition(playerShip);
	}

	private void spawnEnemies() {
		if (enemies.isEmpty()) {
			enemies.addAll(factory.nextLevel(playerShip.getCenter()));
			gui.addEntities(enemies);
		}
		if (enemyShipSpawnTimer > GameConfig.ENEMY_SHIP_SPAWN_TIME) {
			enemyShipSpawnTimer = 0.0;
			EnemyShip enemyShip = factory.createEnemyShip(playerShip.getCenter());
			enemies.add(enemyShip);
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
					Missile missile = playerShip.shoot(playerShip.getRotation());
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
		int x = (int) entity.getCenter().getX();
		int y = (int) entity.getCenter().getY();
		int halfWidth = entity.getWidth()/2;
		int halfHeight = entity.getHeight()/2;

		if (x - halfWidth > GameConfig.WINDOW_WIDTH) {
			entity.setCenter(new Point(0.0, (double) y));
		}
		if (x + halfWidth < 0) {
			entity.setCenter(new Point((double) GameConfig.WINDOW_WIDTH, (double) y));
		}
		if (y - halfHeight > GameConfig.WINDOW_HEIGHT) {
			entity.setCenter(new Point((double) x, 0.0));
		}
		if (y + halfHeight < 0) {
			entity.setCenter(new Point((double) x, (double) GameConfig.WINDOW_HEIGHT));
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
		for (Entity enemy : enemies) {
			if (playerShip.intersectsWith(enemy)) {
				collideEntities(playerShip, enemy);
			}
			for (Entity missile : missiles) {
				if (missile.intersectsWith(enemy)) {
					collideEntities(missile, enemy);
				} else if (missile.isDestroyed()) {
					// Missile has selfdestructed
					missiles.remove(missile);
					gui.removeEntity(missile);
				}
			}
		}
		explosions.stream()
				.filter(Entity::isDestroyed)
				.forEach(explosion -> {
					explosions.remove(explosion);
					gui.removeEntity(explosion);
				});
	}

	private void collideEntities(Entity friendly, Entity enemy) {
		Optional<Explosion> friendlyExplosion = friendly.collide();
		Optional<Explosion> enemyExplosion = enemy.collide();

		friendlyExplosion.ifPresent(this::addExplosion);
		enemyExplosion.ifPresent(this::addExplosion);
		if (friendly == playerShip) {
			centerPlayerShip();
		}
		if (enemy instanceof Asteroid) {
			List<Entity> newAsteroids = factory.shatterAsteroid((Asteroid) enemy);
			addEnemies(newAsteroids);
		}
		enemies.remove(enemy);
		gui.removeEntity(enemy);
	}

	private void addEnemies(Collection<Entity> newEnemies) {
		enemies.addAll(newEnemies);
		gui.addEntities(newEnemies);
	}

	private void addExplosion(Entity explosion) {
		explosions.add(explosion);
		gui.addEntity(explosion);
	}

	private void removeMissile(Entity missile) {
		missiles.remove(missile);
		gui.removeEntity(missile);
	}

	private void centerPlayerShip() {
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
	}

}
