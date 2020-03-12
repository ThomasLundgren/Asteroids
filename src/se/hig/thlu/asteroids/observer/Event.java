package se.hig.thlu.asteroids.observer;

public class Event {

	private final Object value;

	public Event(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
