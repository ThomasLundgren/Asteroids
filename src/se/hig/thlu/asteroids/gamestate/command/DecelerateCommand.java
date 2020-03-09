package se.hig.thlu.asteroids.gamestate.command;

import se.hig.thlu.asteroids.model.entity.PlayerShip;

class DecelerateCommand extends AbstractCommand {

	DecelerateCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		playerShip.decelerate();
	}
}
