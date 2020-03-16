package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.Entity;

public class EnemyShipCreateEvent extends CreateEvent {

	public EnemyShipCreateEvent(Entity entity) {
		super(entity);
	}
}
