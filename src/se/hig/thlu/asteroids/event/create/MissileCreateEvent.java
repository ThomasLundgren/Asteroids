package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.Entity;

public class MissileCreateEvent extends CreateEvent {

	public MissileCreateEvent(Entity missile) {
		super(missile);
	}
}
