package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.event.Event;
import se.hig.thlu.asteroids.model.entity.Entity;

public abstract class CreateEvent extends Event {

	public CreateEvent(Entity entity) {
		super(entity);
	}
}
