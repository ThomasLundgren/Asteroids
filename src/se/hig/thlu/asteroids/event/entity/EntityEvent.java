package se.hig.thlu.asteroids.event.entity;

import se.hig.thlu.asteroids.event.Event;

import java.util.UUID;

public abstract class EntityEvent<T> extends Event<T> {

	public EntityEvent(T value, UUID id) {
		super(value, id);
	}

	public EntityEvent(T value) {
		super(value);
	}

}
