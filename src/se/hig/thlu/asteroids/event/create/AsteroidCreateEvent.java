package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.Entity;

public class AsteroidCreateEvent extends CreateEvent {

	public AsteroidCreateEvent(Entity entity) {
		super(entity);
	}
}
