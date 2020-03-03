package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.GameConfig;
import se.hig.thlu.asteroids.controller.command.CommandController;
import se.hig.thlu.asteroids.controller.command.CommandController.CommandType;
import se.hig.thlu.asteroids.entityfactory.EntityFactory;
import se.hig.thlu.asteroids.gui.GUI;
import se.hig.thlu.asteroids.gui.eventlistener.EventAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.EventListenerAdapter;
import se.hig.thlu.asteroids.model.Entity;
import se.hig.thlu.asteroids.model.Missile;
import se.hig.thlu.asteroids.model.PlayerShip;
import se.hig.thlu.asteroids.model.Point;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GameController {

	private final CommandController commandController;
	private final PlayerShip playerShip;
	private final GUI<? extends EventListenerAdapter<? extends EventAdapter>> gui;
	private final List<Missile> playerMissiles = new CopyOnWriteArrayList();
	private final List<Entity> entities = new CopyOnWriteArrayList();
	private final EntityFactory factory;
	private long totalGameTime = 0L;
	private long nextSpawn = (long) GameConfig.INITIAL_SPAWN_INTERVAL;
	private long timeSinceLastShot = Long.MAX_VALUE;

	public GameController(EntityFactory factory,
						   CommandController commandController,
						   PlayerShip playerShip,
						   GUI<?extends EventListenerAdapter<? extends EventAdapter>> gui) {
		this.factory = factory;
		this.commandController = commandController;
		this.playerShip = playerShip;
		this.gui = gui;
		playerShip.setCenter(new Point((double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
		entities.addAll(factory.nextLevel());
		entities.add(playerShip);
	}

	public void update(double delta) {
		updateTimes(delta);
		executeActiveCommands();
		entities.forEach(entity -> {
			updatePosition(entity);
			checkCollision(entity);
		});
		gui.setEntities(entities);
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
				timeSinceLastShot = System.currentTimeMillis();
				if (timeSinceLastShot > GameConfig.PLAYER_MISSILE_UPDATE_MS) {
					Missile missile = playerShip.shoot(playerShip.getFacingDirection());
					playerMissiles.add(missile);
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
			default:
				break;
		}
	}

	private void updatePosition(Entity entity) {
		entity.updatePosition();
		handleOverflow(entity);
	}

	private void handleOverflow(Entity entity) {
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

	private void checkCollision(Entity entity) {

	}

}
