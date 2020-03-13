package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.Asteroid;

public class AsteroidCreateEvent extends CreateEvent {

	public AsteroidCreateEvent(Asteroid entity) {
		super(entity);
	}
}
