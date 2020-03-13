package se.hig.thlu.asteroids.event.entity;

import se.hig.thlu.asteroids.event.Event;

import java.util.UUID;

public abstract class EntityEvent extends Event {

	public EntityEvent(Object value, UUID id) {
		super(value, id);
	}

	public EntityEvent(Object value) {
		super(value);
	}

}
