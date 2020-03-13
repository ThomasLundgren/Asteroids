package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.Entity;

public class ExplosionCreateEvent extends CreateEvent {

	public ExplosionCreateEvent(Entity entity) {
		super(entity);
	}
}
