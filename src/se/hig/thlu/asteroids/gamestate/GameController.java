package se.hig.thlu.asteroids.gamestate;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.event.Event;
import se.hig.thlu.asteroids.event.EventHandlerFactory;
import se.hig.thlu.asteroids.event.IObserver;
import se.hig.thlu.asteroids.event.create.CreateEventHandler;
import se.hig.thlu.asteroids.event.create.MissileCreateEvent;
import se.hig.thlu.asteroids.event.entity.DestroyedEvent;
import se.hig.thlu.asteroids.event.entity.EntityEventHandler;
import se.hig.thlu.asteroids.event.gamestate.GameStateEventHandler;
import se.hig.thlu.asteroids.event.gamestate.LevelClearedEvent;
import se.hig.thlu.asteroids.event.gamestate.NextLevelEvent;
import se.hig.thlu.asteroids.gamestate.command.CommandController;
import se.hig.thlu.asteroids.gamestate.command.CommandController.CommandType;
import se.hig.thlu.asteroids.model.entity.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GameController implements IObserver {

	private static final double INVINCIBILITY_TIME = 250.0;
	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final Collection<Missile> missiles = new CopyOnWriteArrayList<>();
	private final Collection<Entity> enemies = new CopyOnWriteArrayList<>();
	private final EntityFactory factory;
	private BigDecimal totalGameTime = new BigDecimal("0.0");
	private double currentLevelTime = 0.0;
	private double enemyShipSpawnTimer = 0.0;
	private int level = 0;
	private double invincibilityTimer = 0.0;

	public GameController(EntityFactory factory,
						  CommandController commandController,
						  PlayerShip playerShip) {
		this.factory = factory;
		this.commandController = commandController;
		this.playerShip = playerShip;
		EventHandlerFactory.getEventHandler(CreateEventHandler.class)
				.addObserver(this);
		EventHandlerFactory.getEventHandler(EntityEventHandler.class)
				.addObserver(this);
	}

	public void update(double delta) {
		updateTimes(delta);
		spawnEnemies(delta);
		executeActiveCommands();
		updateEntities();
		checkCollisions();
	}

	private void updateEntities() {
		missiles.forEach(this::updateEntity);
		enemies.forEach(this::updateEntity);
		updateEntity(playerShip);
	}

	private void spawnEnemies(double delta) {
		if (enemies.isEmpty()) {
			level++;
			EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
					.notify(new LevelClearedEvent(currentLevelTime));
			EventHandlerFactory.getEventHandler(GameStateEventHandler.class)
					.notify(new NextLevelEvent(level));
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
		if (invincibilityTimer > 0.0) {
			invincibilityTimer -= delta;
		}
		totalGameTime = totalGameTime.add(new BigDecimal(delta));
		currentLevelTime += delta;
		enemyShipSpawnTimer += delta;
	}

	private void checkCollisions() {
		for (Entity enemy : enemies) {
			if (playerShip.intersectsWith(enemy)) {
				if (invincibilityTimer <= 0.0) {
					collideEntities(playerShip, enemy);
					invincibilityTimer = INVINCIBILITY_TIME;
				}
			}
			for (Missile missile : missiles) {
				if (missile.intersectsWith(enemy)) {
					collideEntities(missile, enemy);
				}
			}
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
		if (enemy instanceof Shatterable) {
			List<Entity> newAsteroids = ((Shatterable) enemy).shatter();
			enemies.addAll(newAsteroids);
		}
		friendly.destroy();
		enemy.destroy();
		if (friendly instanceof Missile) {
			missiles.remove(friendly);
		}
		enemies.remove(enemy);
	}

	@Override
	public void notify(Event event) {
		if (event instanceof MissileCreateEvent) {
			Missile missile = (Missile) event.getValue();
			if (missile.getMissileSource() == Missile.MissileSource.PLAYER) {
				missiles.add(missile);
			} else {
				enemies.add(missile);
			}
		}
		if (event instanceof DestroyedEvent) {
			if (event.getValue() instanceof Missile) {
				Missile missile = (Missile) event.getValue();
				if (missile.getMissileSource() == Missile.MissileSource.PLAYER) {
					missiles.remove(missile);
				} else {
					enemies.remove(missile);
				}
			}
		}
	}
}
