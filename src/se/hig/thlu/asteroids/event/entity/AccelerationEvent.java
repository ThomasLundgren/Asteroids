package se.hig.thlu.asteroids.event.entity;

import java.util.UUID;

public class AccelerationEvent extends EntityEvent {

	public AccelerationEvent(boolean isAccelerating, UUID entityId) {
		super(isAccelerating, entityId);
	}
}
