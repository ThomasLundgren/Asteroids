package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.*;

import java.util.*;
import java.util.stream.*;

import static se.hig.thlu.asteroids.controller.command.CommandController.CommandType.*;

public class CommandController {

	private final Map<CommandType, Command> commands = new EnumMap<CommandType, Command>(CommandType.class);
	private final CommandFactory factory = new CommandFactory();
	private final PlayerShip playerShip;

	public CommandController(PlayerShip playerShip) {
		Stream.of(values())
				.forEach(type -> commands.put(type, getCommand(NULL)));
		this.playerShip = playerShip;
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
