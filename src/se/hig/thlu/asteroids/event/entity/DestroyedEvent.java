package se.hig.thlu.asteroids.event.entity;

import java.util.UUID;

public class DestroyedEvent extends EntityEvent {

	public DestroyedEvent(UUID entityId) {
		super(null, entityId);
	}
}
