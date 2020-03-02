package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.*;
import se.hig.thlu.asteroids.controller.command.*;
import se.hig.thlu.asteroids.controller.command.CommandController.*;
import se.hig.thlu.asteroids.model.*;

import java.beans.*;
import java.util.*;

public class GameController {

	private static CommandController commandController;
	private static long totalGameTime = 0L;
	private static long nextSpawn = (long) GameConfig.INITIAL_SPAWN_INTERVAL;
	private static double timeSinceLastShot = Double.MAX_VALUE;
	private static final PlayerShip playerShip = PlayerShip.createPlayerShip();
	private static final List<Asteroid> asteroids = new ArrayList<>(30);
	private static final List<EnemyShip> enemyShips = new ArrayList<>(5);
	private static final List<Missile> missiles = new ArrayList<>(30);

	public GameController() {
		commandController = new CommandController(playerShip);
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2), (double) (GameConfig.WINDOW_HEIGHT / 2)));
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
				playerShip.shoot(playerShip.getFacingDirection());
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
		playerShip.updatePosition();
		double x = playerShip.getCenter().getX();
		double y = playerShip.getCenter().getY();
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
		if (y > GameConfig.WINDOW_HEIGHT) {
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

	public enum Property {

		PLAYER_SHIP("PLAYER_SHIP");

		private String propertyName;

		Property(String propertyName) {
			this.propertyName = propertyName;
		}

		public String getPropertyName() {
			return propertyName;
		}
	}

	public void addListenerForShip(PropertyChangeListener listener) {
		playerShip.addPropertyChangeListener(listener);
	}

}
