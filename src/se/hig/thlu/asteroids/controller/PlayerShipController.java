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
	private PlayerShip playerShip = new PlayerShip();
	private List<Asteroid> asteroids = new ArrayList<>();
	private List<EnemyShip> enemyShips = new ArrayList<>();
	private List<Missile> missiles = new ArrayList<>();

	private PlayerShipController() {
		changeSupport = new PropertyChangeSupport(this);
	}

	public static PlayerShipController getInstance() {
		if (instance == null) {
			return new PlayerShipController();
		}
		return instance;
	}

	public void update(double delta) {
		updateTimes(delta);
		checkCollisions();
		// TODO update all positions
//		if (timer > 100) {
//			playerShip = (PlayerShip) playerShip.withUpdatedPosition();
//			timer = 0;
//		}
		// TODO if x amount of time has passed since pressing acceleration, start decelerating
		notifyObservers();
	}

	private void notifyObservers() {
		// TODO: Make PLAYER_SHIP a constant
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
				playerShip = playerShip.turnLeft();
				break;
			case UP_ARROW:
				System.out.println("UP");
				playerShip = playerShip.accelerate();
				System.out.println(playerShip.getPosition());
				break;
			case RIGHT_ARROW:
				playerShip = playerShip.turnRight();
				break;
			case SPACE_BAR:
				shoot();
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
