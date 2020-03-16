package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.Entity;

public class PlayerShipCreateEvent extends CreateEvent {

	public PlayerShipCreateEvent(Entity entity) {
		super(entity);
	}
}
