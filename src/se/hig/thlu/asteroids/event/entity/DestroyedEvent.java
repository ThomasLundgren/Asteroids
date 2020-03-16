package se.hig.thlu.asteroids.event.entity;

import se.hig.thlu.asteroids.model.entity.Entity;

import java.util.UUID;

public class DestroyedEvent extends EntityEvent<Entity> {

	public DestroyedEvent(Entity entity, UUID entityId) {
		super(entity, entityId);
	}
}
