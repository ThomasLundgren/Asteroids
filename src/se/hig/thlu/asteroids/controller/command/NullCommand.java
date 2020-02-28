package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.*;

class NullCommand extends AbstractCommand {

	protected NullCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		// do nothing
	}
}