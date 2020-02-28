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
	private static PropertyChangeSupport changeSupport;
	private static final PlayerShip playerShip = new PlayerShip();
	private static final List<Asteroid> asteroids = new ArrayList<>(30);
	private static final List<EnemyShip> enemyShips = new ArrayList<>(5);
	private static final List<Missile> missiles = new ArrayList<>(30);

	public GameController() {
		changeSupport = new PropertyChangeSupport(this);
		commandController = new CommandController(playerShip);
		playerShip.setCenter(new Point((double) (GameConfig.windowWidth / 2), (double) (GameConfig.windowHeight / 2)));
	}

	public void update(double delta) {
		updateTimes(delta);
		executeActiveCommands();
		updatePositions();
		checkCollisions();
		notifyObservers();
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

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	private void updatePositions() {
		playerShip.updatePosition();
		double x = playerShip.getCenter().getX();
		double y = playerShip.getCenter().getY();
		if (x > GameConfig.windowWidth) {
			playerShip.setCenter(new Point(0.0, y));
		}
		if (x < 0.0) {
			playerShip.setCenter(new Point(GameConfig.windowWidth, y));
		}
		if (y > GameConfig.windowHeight) {
			playerShip.setCenter(new Point(x, 0.0));
		}
		if (y < 0.0) {
			playerShip.setCenter(new Point(x, GameConfig.windowHeight));
		}

	}

	private void executeActiveCommands() {
		commandController.executeCommands();
	}

	private void notifyObservers() {
		changeSupport.firePropertyChange(Property.PLAYER_SHIP.getPropertyName(), null, playerShip);
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

}
