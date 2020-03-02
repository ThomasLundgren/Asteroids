package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.controller.command.CommandController.CommandType;
import se.hig.thlu.asteroids.entityfactory.EntityFactory;
import se.hig.thlu.asteroids.model.Asteroid;
import se.hig.thlu.asteroids.model.EnemyShip;
import se.hig.thlu.asteroids.model.Entity;
import se.hig.thlu.asteroids.model.Missile;
import se.hig.thlu.asteroids.model.PlayerShip;
import se.hig.thlu.asteroids.model.Point;

import java.util.ArrayList;
import java.util.List;

public final class GameController {

	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final List<Asteroid> asteroids = new ArrayList<>(30);
	private final List<EnemyShip> enemyShips = new ArrayList<>(5);
	private final List<Missile> missiles = new ArrayList<>(30);
	private final EntityFactory factory;
	private long totalGameTime = 0L;
	private long nextSpawn = (long) GameConfig.INITIAL_SPAWN_INTERVAL;
	private double timeSinceLastShot = Double.MAX_VALUE;

	private GameController(EntityFactory factory, CommandController commandController, PlayerShip playerShip) {
		this.factory = factory;
		this.commandController = commandController;
		this.playerShip = playerShip;
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
		asteroids.addAll(factory.nextLevel());
	}

	public static GameController createGameController(EntityFactory factory, CommandController commandController,
													  PlayerShip playerShip) {
		return new GameController(factory, commandController, playerShip);
	}

	public void update(double delta) {
		updateTimes(delta);
		executeActiveCommands();
		updatePositions();
		checkCollisions();
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
				Missile missile = playerShip.shoot(playerShip.getFacingDirection());
				missiles.add(missile);
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
			default:
				break;
		}
	}


	private void updatePositions() {
		asteroids.forEach(Asteroid::updatePosition);
		enemyShips.forEach(EnemyShip::updatePosition);
		missiles.forEach(Missile::updatePosition);
		playerShip.updatePosition();
		asteroids.forEach(this::handleOverFlow);
		enemyShips.forEach(this::handleOverFlow);
		missiles.forEach(this::handleOverFlow);
		handleOverFlow(playerShip);
	}

	private void handleOverFlow(Entity entity) {
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
		totalGameTime = (long) ((double) totalGameTime + delta);
		nextSpawn = (long) ((double) nextSpawn - delta);
		timeSinceLastShot += delta;
	}

	private void checkCollisions() {
		// TODO
	}

}
