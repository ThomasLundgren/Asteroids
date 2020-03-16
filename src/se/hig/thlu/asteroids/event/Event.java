package se.hig.thlu.asteroids.event;

import java.util.UUID;

public abstract class Event<T> {

	private final T value;
	private final UUID id;

	public Event(T value) {
		this.value = value;
		id = UUID.randomUUID();
	}

	public Event(T value, UUID id) {
		this.value = value;
		this.id = id;
	}

	public T getValue() {
		return value;
	}

	public UUID getId() {
		return id;
	}

	public boolean is(Class<? extends Event<?>> eventClass) {
		return this.getClass() == eventClass;
	}

	@Override
	public String toString() {
		return getClass().toString();
	}
}
