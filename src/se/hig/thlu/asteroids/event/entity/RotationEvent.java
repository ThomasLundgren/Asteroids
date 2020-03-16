package se.hig.thlu.asteroids.event.entity;

import java.util.UUID;

public class RotationEvent extends EntityEvent<Double> {

	public RotationEvent(double rotation, UUID entityId) {
		super(rotation, entityId);
	}
}
