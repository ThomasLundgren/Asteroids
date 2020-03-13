package se.hig.thlu.asteroids.event;

import java.util.UUID;

public abstract class Event {

	private final Object value;
	private final UUID id;

	public Event(Object value) {
		this.value = value;
		id = UUID.randomUUID();
	}

	public Event(Object value, UUID id) {
		this.value = value;
		this.id = id;
	}

	public Object getValue() {
		return value;
	}

	public UUID getId() {
		return id;
	}

	public String getTypeString() {
		return getClass().toString();
	}

}
