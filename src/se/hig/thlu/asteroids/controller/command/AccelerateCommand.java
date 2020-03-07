package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.entity.PlayerShip;

class AccelerateCommand extends AbstractCommand {

	AccelerateCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		playerShip.accelerate();
	}
}
