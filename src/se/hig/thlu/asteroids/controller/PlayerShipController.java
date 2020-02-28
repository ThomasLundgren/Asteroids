package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.config.*;
import se.hig.thlu.asteroids.controller.command.*;
import se.hig.thlu.asteroids.controller.command.CommandController.*;
import se.hig.thlu.asteroids.model.*;

public class PlayerShipController {

	private static final PlayerShip playerShip = new PlayerShip();
	private static final CommandController commandController = new CommandController(playerShip);

	public PlayerShipController() {
		playerShip.setCenter(new Point(
				(double) (GameConfig.WINDOW_WIDTH / 2),
				(double) (GameConfig.WINDOW_HEIGHT / 2)));
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

	private void executeActiveCommands() {
		commandController.executeCommands();
	}
}
