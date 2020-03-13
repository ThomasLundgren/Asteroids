package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.PlayerShip;

public class PlayerShipCreateEvent extends CreateEvent {

	public PlayerShipCreateEvent(PlayerShip entity) {
		super(entity);
	}
}
