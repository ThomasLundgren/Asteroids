package se.hig.thlu.asteroids.event.entity;

import se.hig.thlu.asteroids.model.entity.Entity;

import java.util.UUID;

public class IntersectEvent extends EntityEvent {

	public IntersectEvent(Entity other, UUID entityId) {
		super(other, entityId);
	}
}
