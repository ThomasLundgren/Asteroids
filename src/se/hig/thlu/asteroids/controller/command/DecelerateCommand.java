package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.*;

class DecelerateCommand extends AbstractCommand {

	DecelerateCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		playerShip.decelerate();
	}
}
