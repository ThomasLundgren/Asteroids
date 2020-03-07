package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.entity.PlayerShip;

class TurnLeftCommand extends AbstractCommand {

	protected TurnLeftCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		playerShip.turnLeft();
	}
}
