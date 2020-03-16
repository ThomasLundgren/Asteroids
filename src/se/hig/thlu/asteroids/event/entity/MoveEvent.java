package se.hig.thlu.asteroids.event.entity;

import se.hig.thlu.asteroids.model.Point;

import java.util.UUID;

public class MoveEvent extends EntityEvent<Point> {

	public MoveEvent(Point position, UUID entityId) {
		super(position, entityId);
	}
}
