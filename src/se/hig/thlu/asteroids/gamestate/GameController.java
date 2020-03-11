package se.hig.thlu.asteroids.gamestate;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.factory.EntityFactory;
import se.hig.thlu.asteroids.gamestate.command.CommandController;
import se.hig.thlu.asteroids.gamestate.command.CommandController.CommandType;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.entity.*;
import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.util.Trigonometry;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static se.hig.thlu.asteroids.gamestate.GameController.Action.*;

public final class GameController /*implements IObservable*/ {


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
		EventBus.getInstance().notify(ADD.toString(), new Event(playerShip));
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
			emitEvent(LEVEL_CLEARED, currentLevelTime);
			currentLevelTime = 0.0;
			List<Entity> newEnemies = factory.nextLevel(playerShip.getCenter());
			addEnemies(newEnemies);
		}
		if (enemyShipSpawnTimer > GameConfig.ENEMY_SHIP_SPAWN_TIME) {
			enemyShipSpawnTimer = 0.0;
			EnemyShip enemyShip = factory.createEnemyShip(playerShip.getCenter());
			addEnemy(enemyShip);
		}
	}

	private void emitEvent(Action action, Object object) {
		EventBus.getInstance().notify(action.toString(), new Event(object));
	}

	private void updateEntity(Entity entity) {
		entity.update();
		if (entity instanceof Shooter && entity != playerShip) {
			double direction = Trigonometry.getAngle(entity.getCenter(), playerShip.getCenter());
			double distance = entity.getCenter().distanceTo(playerShip.getCenter()) + 150.0;
			distance /= (double) GameConfig.WINDOW_WIDTH;
			distance = StrictMath.max(distance, 0.6);
			Optional<Missile> missile = ((Shooter) entity).shoot(direction, distance);
			missile.ifPresent(this::addEnemy);
		}
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
		for (Entity enemy : enemies) {
			if (playerShip.intersectsWith(enemy)) {
				collideEntities(playerShip, enemy);
			}
			for (Missile missile : missiles) {
				if (missile.intersectsWith(enemy)) {
					collideEntities(missile, enemy);
				} else if (missile.isDestroyed()) {
					// Missile has selfdestructed
					missiles.remove(missile);
				}
			}
			if (enemy.isDestroyed()) {
				enemies.remove(enemy);
			}
		}
	}

//	@Override
//	public void addObserver(IObserver observer) {
//		observers.add(observer);
//		// TODO: MOve this to a new start() method
//		notifyObservers(ADD, playerShip);
//	}

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
				Optional<Missile> missile = playerShip.shoot(playerShip.getRotation(), 0.6);
				missile.ifPresent(this::addMissile);
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
		Optional<Explosion> friendlyExplosion = friendly.destroy();
		Optional<Explosion> enemyExplosion = enemy.destroy();

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
		friendlyExplosion.ifPresent(this::addExplosion);
		enemyExplosion.ifPresent(this::addExplosion);
		removeEnemy(enemy);
	}

	private void removeEnemy(Entity enemy) {
		emitEvent(DESTROY, enemy);
		enemies.remove(enemy);
	}

	private void addMissile(Missile missile) {
		missiles.add(missile);
		emitEvent(ADD, missile);
	}

	private void addEnemies(Collection<Entity> enemies) {
		this.enemies.addAll(enemies);
		enemies.forEach(a -> emitEvent(ADD, a));
	}

	private void addEnemy(Entity enemy) {
		enemies.add(enemy);
		emitEvent(ADD, enemy);
	}

	private void addExplosion(Explosion explosion) {
		emitEvent(ADD, explosion);
	}

	private void centerPlayerShip() {
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
	}

	public enum Action {
		ADD, DESTROY, LEVEL_CLEARED;
	}

}
