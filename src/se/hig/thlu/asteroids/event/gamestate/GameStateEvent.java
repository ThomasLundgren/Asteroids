package se.hig.thlu.asteroids.event.gamestate;

import se.hig.thlu.asteroids.event.Event;

import java.util.UUID;

public abstract class GameStateEvent<T> extends Event<T> {

	public GameStateEvent(T value) {
		super(value);
	}

	public GameStateEvent(T value, UUID id) {
		super(value, id);
	}
}
