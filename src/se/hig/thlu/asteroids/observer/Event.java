package se.hig.thlu.asteroids.observer;

import java.util.UUID;

public class Event {

	private final UUID id;
	private final Object value;

	public Event(Object value) {
		this.value = value;
		id = UUID.randomUUID();
	}

	public Event(Object value, UUID id) {
		this.value = value;
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public Object getValue() {
		return value;
	}
}
