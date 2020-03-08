package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.controller.command.CommandController.CommandType;
import se.hig.thlu.asteroids.factory.EntityFactory;
import se.hig.thlu.asteroids.mathutil.Trigonometry;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.entity.*;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.observer.IObservable;
import se.hig.thlu.asteroids.observer.IObserver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static se.hig.thlu.asteroids.controller.GameController.Action.ADD;

public final class GameController implements IObservable {


	// TODO: Create start() method that start gameloop
	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final Collection<Missile> missiles = new CopyOnWriteArrayList<>();
	private final Collection<Entity> enemies = new CopyOnWriteArrayList<>();
	private final EntityFactory factory;
	private final List<IObserver> observers = new ArrayList<>(1);
	private BigDecimal totalGameTime = new BigDecimal("0.0");
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

	private void updateEntity(Entity entity) {
		entity.update();
		if (entity instanceof Shooter && entity != playerShip) {
			double direction = Trigonometry.getAngle(entity.getCenter(), playerShip.getCenter());
			double distance = entity.getCenter().distanceTo(playerShip.getCenter()) + 50.0;
			distance /= (double) GameConfig.WINDOW_WIDTH;
			Optional<Missile> missile = ((Shooter) entity).shoot(direction, distance);
			missile.ifPresent(this::addEnemy);
		}
	}

	private void executeActiveCommands() {
		commandController.executeCommands();
	}

	private void updateTimes(double delta) {
		totalGameTime = totalGameTime.add(new BigDecimal(delta));
		enemyShipSpawnTimer += delta;
	}

	private void checkCollisions() {
		for (Entity enemy : enemies) {
			if (playerShip.intersectsWith(enemy)) {
				collideEntities(playerShip, enemy);
			}
			if (enemy.isDestroyed()) {
				enemies.remove(enemy);
			}
			for (Missile missile : missiles) {
				if (missile.intersectsWith(enemy)) {
					collideEntities(missile, enemy);
				} else if (missile.isDestroyed()) {
					// Missile has selfdestructed
					missiles.remove(missile);
				}
			}
		}
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
				if (canShoot) {
					canShoot = false;
					Optional<Missile> missile = playerShip.shoot(playerShip.getRotation(), 0.6);
					missile.ifPresent(this::addMissile);
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

}
