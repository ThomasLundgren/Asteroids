package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.*;

import java.util.*;
import java.util.stream.*;

import static se.hig.thlu.asteroids.controller.command.CommandController.CommandType.*;

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

	public void activate(CommandType commandType) {
		commands.put(commandType, getCommand(commandType));
	}

	public void deactivate(CommandType commandType) {
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

}
