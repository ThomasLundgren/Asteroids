package se.hig.thlu.asteroids.gamestate.command;

import se.hig.thlu.asteroids.model.entity.PlayerShip;

class NullCommand extends AbstractCommand {

	protected NullCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		// do nothing
	}
}
