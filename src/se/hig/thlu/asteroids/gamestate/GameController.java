package se.hig.thlu.asteroids.gamestate;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.event.EventHandlerFactory;
import se.hig.thlu.asteroids.event.gamestate.GameStateEventHandler;
import se.hig.thlu.asteroids.event.gamestate.LevelClearedEvent;
import se.hig.thlu.asteroids.gamestate.command.CommandController;
import se.hig.thlu.asteroids.gamestate.command.CommandController.CommandType;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.entity.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GameController {

	// TODO: Create start() method that start gameloop
	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final Collection<Missile> missiles = new CopyOnWriteArrayList<>();
	private final Collection<Entity> enemies = new CopyOnWriteArrayList<>();
	private final EntityFactory factory;
	private BigDecimal totalGameTime = new BigDecimal("0.0");
	private double currentLevelTime = 0.0;
	private double enemyShipSpawnTimer = 0.0;
	private boolean canShoot = true;

	public GameController(EntityFactory factory,
						  CommandController commandController,
						  PlayerShip playerShip) {
		this.factory = factory;
		this.commandController = commandController;
		this.playerShip = playerShip;
		centerPlayerShip();
	}

	public void update(double delta) {
		updateTimes(delta);
		spawnEnemies();
		executeActiveCommands();
		updateEntities();
		checkCollisions();
	}

	private void updateEntities() {
		missiles.forEach(this::updateEntity);
		enemies.forEach(this::updateEntity);
		updateEntity(playerShip);
	}

	private void spawnEnemies() {
		if (enemies.isEmpty()) {
			EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
					.notify(new LevelClearedEvent(currentLevelTime));
			currentLevelTime = 0.0;
			List<Entity> newEnemies = factory.nextLevel(playerShip.getCenter());
			enemies.addAll(newEnemies);
		}
		if (enemyShipSpawnTimer > GameConfig.ENEMY_SHIP_SPAWN_TIME) {
			enemyShipSpawnTimer = 0.0;
			EnemyShip enemyShip = factory.createEnemyShip(playerShip.getCenter());
			enemies.add(enemyShip);
		}
	}

	private void updateEntity(Entity entity) {
		entity.update();
	}

	private void executeActiveCommands() {
		commandController.executeCommands();
	}

	private void updateTimes(double delta) {
		totalGameTime = totalGameTime.add(new BigDecimal(delta));
		currentLevelTime += delta;
		enemyShipSpawnTimer += delta;
	}

	private void checkCollisions() {
//		for (Entity enemy : enemies) {
//			if (playerShip.intersectsWith(enemy)) {
//				collideEntities(playerShip, enemy);
//			}
//			for (Missile missile : missiles) {
//				if (missile.intersectsWith(enemy)) {
//					collideEntities(missile, enemy);
//				} else if (missile.isDestroyed()) {
//					// Missile has selfdestructed
//					missiles.remove(missile);
//				}
//			}
//			if (enemy.isDestroyed()) {
//				enemies.remove(enemy);
//			}
//		}
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
				playerShip.shoot();
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
				break;
			default:
				break;
		}
	}

	private void collideEntities(Entity friendly, Entity enemy) {
		if (friendly == playerShip) {
			centerPlayerShip();
		}
		if (enemy instanceof Shatterable) {
			List<Entity> newAsteroids = ((Shatterable) enemy).shatter();
			enemies.addAll(newAsteroids);
		}
		enemies.remove(enemy);
	}

	private void centerPlayerShip() {
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
	}

}
