package se.hig.thlu.asteroids.event.entity;

import se.hig.thlu.asteroids.model.Point;

import java.util.UUID;

public class PlayerMoveEvent extends EntityEvent {

	public PlayerMoveEvent(Point position, UUID entityId) {
		super(position, entityId);
	}
}
