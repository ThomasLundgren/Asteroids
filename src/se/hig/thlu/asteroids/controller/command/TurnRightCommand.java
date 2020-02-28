package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.*;

public class TurnRightCommand extends AbstractCommand {

	protected TurnRightCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		playerShip.turnRight();
	}
}
