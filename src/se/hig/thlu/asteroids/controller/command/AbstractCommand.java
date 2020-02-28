package se.hig.thlu.asteroids.controller.command;

import se.hig.thlu.asteroids.model.*;

abstract class AbstractCommand implements Command {

	protected final PlayerShip playerShip;

	protected AbstractCommand(PlayerShip playerShip) {
		this.playerShip = playerShip;
	}

}
