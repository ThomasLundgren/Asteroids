package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.controller.command.CommandController.CommandType;
import se.hig.thlu.asteroids.factory.EntityFactory;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.observer.IObservable;
import se.hig.thlu.asteroids.observer.IObserver;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static se.hig.thlu.asteroids.controller.GameController.Action.ADD;

public final class GameController implements IObservable {


	// TODO: Create start() method that start gameloop
	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final Collection<Missile> missiles = new CopyOnWriteArrayList<>();
	private final Collection<Entity> enemies = new CopyOnWriteArrayList<>();
//	private final Collection<EnemyShip> enemyShips = new CopyOnWriteArrayList<>();
	private final EntityFactory factory;
	private final List<IObserver> observers = new ArrayList<>(1);
	private BigDecimal totalGameTime = new BigDecimal("0.0");
	private double timeSinceLastShot = Double.MAX_VALUE;
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
			List<Entity> newEnemies = factory.nextLevel(playerShip.getCenter());
			addEnemies(newEnemies);
		}
		if (enemyShipSpawnTimer > GameConfig.ENEMY_SHIP_SPAWN_TIME) {
			enemyShipSpawnTimer = 0.0;
			EnemyShip enemyShip = factory.createEnemyShip(playerShip.getCenter());
			addEnemy(enemyShip);
		}
	}

	private void notifyObservers(Action action, Object object) {
		for (IObserver observer : observers) {
			Event event = new Event(object);
			observer.onNotify(action.toString(), event);
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
					addMissile(missile);
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
		entity.update();
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
		for (Entity asteroid : enemies) {
			if (playerShip.intersectsWith(asteroid)) {
				collideEntities(playerShip, asteroid);
			}
			for (Missile missile : missiles) {
				if (missile.intersectsWith(asteroid)) {
					collideEntities(missile, asteroid);
				} else if (missile.isDestroyed()) {
					// Missile has selfdestructed
					missiles.remove(missile);
				}
			}
		}
	}

	private void collideEntities(Entity friendly, Entity enemy) {
		Optional<Explosion> friendlyExplosion = friendly.destroy();
		Optional<Explosion> enemyExplosion = enemy.destroy();

		friendlyExplosion.ifPresent(this::addExplosion);
		enemyExplosion.ifPresent(this::addExplosion);

		if (friendly == playerShip) {
			centerPlayerShip();
			if (playerShip.isDestroyed()) {
				System.out.println("Game over");
			}
		}
		if (enemy instanceof Shatterable) {
			List<Entity> newAsteroids = ((Shatterable) enemy).shatter();
			addEnemies(newAsteroids);
		}
		enemies.remove(enemy);
	}

	private void addMissile(Missile missile) {
		missiles.add(missile);
		notifyObservers(ADD, missile);
	}

	private void addEnemies(Collection<Entity> enemies) {
		this.enemies.addAll(enemies);
		enemies.forEach(a -> notifyObservers(ADD, a));
	}

	private void addEnemy(Entity enemy) {
		enemies.add(enemy);
		notifyObservers(ADD, enemy);
	}

	private void addExplosion(Explosion explosion) {
		notifyObservers(ADD, explosion);
	}

	private void centerPlayerShip() {
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
	}

	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
		// TODO: MOve this to a new start() method
		notifyObservers(ADD, playerShip);
	}

	public enum Action {
		ADD;
	}
}
