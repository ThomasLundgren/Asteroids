package se.hig.thlu.asteroids.gamestate.command;

import se.hig.thlu.asteroids.model.entity.PlayerShip;

abstract class AbstractCommand implements Command {

	protected final PlayerShip playerShip;

	protected AbstractCommand(PlayerShip playerShip) {
		this.playerShip = playerShip;
	}

}
