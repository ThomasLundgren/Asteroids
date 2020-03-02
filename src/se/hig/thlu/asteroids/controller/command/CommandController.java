package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.controller.InputController;
import se.hig.thlu.asteroids.model.PlayerShip;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static se.hig.thlu.asteroids.controller.command.CommandController.CommandType.NULL;
import static se.hig.thlu.asteroids.controller.command.CommandController.CommandType.values;

public final class CommandController {

	private static final Map<CommandType, Command> commands = new EnumMap<CommandType, Command>(CommandType.class);
	private static final CommandFactory factory = new CommandFactory();
	private final PlayerShip playerShip;

	private CommandController(PlayerShip playerShip) {
		Stream.of(values())
				.forEach(type -> commands.put(type, getCommand(NULL)));
		this.playerShip = playerShip;
	}

	public static CommandController createCommandController(PlayerShip playerShip) {
		return new CommandController(playerShip);
	}

	private void activate(CommandType commandType) {
		commands.put(commandType, getCommand(commandType));
	}

	private void deactivate(CommandType commandType) {
		commands.put(commandType, getCommand(NULL));
	}

	public void executeCommands() {
		commands.forEach((type, command) -> command.execute());
	}

	private Command getCommand(CommandType commandType) {
		return factory.createCommand(commandType, playerShip);
	}

	public enum CommandType {
		ACCELERATE, DECELERATE, TURN_LEFT, TURN_RIGHT, NULL;
	}

	public void handleKeyPressed(InputController.PressedKey key) {
		switch (key) {
			case LEFT_ARROW:
				activate(CommandType.TURN_LEFT);
				deactivate(CommandType.TURN_RIGHT);
				break;
			case UP_ARROW:
				activate(CommandType.ACCELERATE);
				deactivate(CommandType.DECELERATE);
				break;
			case RIGHT_ARROW:
				activate(CommandType.TURN_RIGHT);
				deactivate(CommandType.TURN_LEFT);
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
				deactivate(CommandType.TURN_LEFT);
				break;
			case UP_ARROW:
				deactivate(CommandType.ACCELERATE);
				activate(CommandType.DECELERATE);
				break;
			case RIGHT_ARROW:
				deactivate(CommandType.TURN_RIGHT);
				break;
			default:
				break;
		}
	}
}
