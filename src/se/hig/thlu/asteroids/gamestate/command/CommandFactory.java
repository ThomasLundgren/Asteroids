package se.hig.thlu.asteroids.gamestate.command;

import se.hig.thlu.asteroids.gamestate.command.CommandController.*;
import se.hig.thlu.asteroids.model.entity.PlayerShip;

class CommandFactory {

	CommandFactory() {
	}

	Command createCommand(CommandType commandType, PlayerShip playerShip) {
		switch (commandType) {
			case ACCELERATE:
				return new AccelerateCommand(playerShip);
			case DECELERATE:
				return new DecelerateCommand(playerShip);
			case TURN_LEFT:
				return new TurnLeftCommand(playerShip);
			case TURN_RIGHT:
				return new TurnRightCommand(playerShip);
			default:
				return new NullCommand(playerShip);
		}
	}

}
