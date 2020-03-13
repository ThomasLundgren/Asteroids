package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.Missile;

public class MissileCreateEvent extends CreateEvent {

	public MissileCreateEvent(Missile missile) {
		super(missile);
	}
}
