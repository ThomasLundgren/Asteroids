package se.hig.thlu.asteroids.gamestate.command;

import se.hig.thlu.asteroids.model.entity.PlayerShip;

class TurnRightCommand extends AbstractCommand {

	protected TurnRightCommand(PlayerShip playerShip) {
		super(playerShip);
	}

	@Override
	public void execute() {
		playerShip.turnRight();
	}
}
