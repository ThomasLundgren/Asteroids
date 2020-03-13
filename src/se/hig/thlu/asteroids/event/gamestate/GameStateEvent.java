package se.hig.thlu.asteroids.event.gamestate;

import se.hig.thlu.asteroids.event.Event;

import java.util.UUID;

public abstract class GameStateEvent extends Event {

	public GameStateEvent(Object value) {
		super(value);
	}

	public GameStateEvent(Object value, UUID id) {
		super(value, id);
	}
}
