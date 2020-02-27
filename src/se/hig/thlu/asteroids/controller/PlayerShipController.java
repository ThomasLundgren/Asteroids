package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.*;
import se.hig.thlu.asteroids.model.*;

import java.beans.*;
import java.util.*;

public class PlayerShipController {

	public static PlayerShipController instance;
	private final PropertyChangeSupport changeSupport;
	private long totalGameTime = 0;
	private long nextSpawn = GameConfig.INITIAL_SPAWN_INTERVAL;
	private double timeSinceLastShot = Long.MAX_VALUE;
	private int timer = 0;
	private PlayerShip playerShip;
	private List<Asteroid> asteroids = new ArrayList<>();
	private List<EnemyShip> enemyShips = new ArrayList<>();
	private List<Missile> missiles = new ArrayList<>();

	private PlayerShipController() {
		changeSupport = new PropertyChangeSupport(this);
		playerShip = (PlayerShip) new PlayerShip().withPosition(new Point(100.0, 100.0));
	}

	public static PlayerShipController getInstance() {
		if (instance == null) {
			return new PlayerShipController();
		} else {
			return instance;
		}
	}

	public void update(double delta) {
		updateTimes(delta);
		updatePositions();
		checkCollisions();
		executeActiveCommands();
		notifyObservers();
	}

	private void updatePositions() {
//		if (timer > 100) {
//			playerShip = (PlayerShip) playerShip.withUpdatedPosition();
//			timer = 0;
//		}
	}

	private void executeActiveCommands() {
		// TODO Active commands are executed so long as a key is being held down. Command are added when a key is
		//  pressed down and removed when the key is released
	}

	private void notifyObservers() {
		// TODO: Make "PLAYER_SHIP" a constant
		changeSupport.firePropertyChange("PLAYER_SHIP", null, playerShip);
	}

	private void updateTimes(double delta) {
		totalGameTime = (long) ((double) totalGameTime + delta);
		nextSpawn = (long) ((double) nextSpawn - delta);
		timeSinceLastShot += delta;
		timer++;
	}

	private void checkCollisions() {
		// TODO
	}

	public void handleKeyPressed(InputController.PressedKey key) {
		switch (key) {
			case LEFT_ARROW:
				// TODO add turnLeftCommand to list of active commands
				break;
			case UP_ARROW:
				// TODO add accelerateCommand to list of active commands
				System.out.println("UP");
				playerShip = playerShip.accelerated();
				System.out.println(playerShip.getPosition());
				System.out.println(playerShip.getVelocity());
				break;
			case RIGHT_ARROW:
				// TODO add turnRightCommand to list of active commands
				break;
			case SPACE_BAR:
				shoot();
				break;
			default:
				break;
		}
	}

	public void handleKeyReleased(InputController.PressedKey key) {
		switch (key) {
			case LEFT_ARROW:
				// TODO remove turnLeftCommand
				break;
			case UP_ARROW:
				// TODO remove accelerateCommand and add decelerateCommand
				break;
			case RIGHT_ARROW:
				// TODO remove turnRightCommand
				break;
			default:
				break;
		}
	}

	protected void shoot() {
		if (timeSinceLastShot > GameConfig.PLAYER_SHOOTING_DELAY_MS) {
			// TODO: Shouldn't have to get velocity and direction here
			Missile firedMissile = playerShip.shoot(playerShip.getVelocity().getDirection());
			missiles.add(firedMissile);
			System.out.println("shoot");
			timeSinceLastShot = 0.0;
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

}
