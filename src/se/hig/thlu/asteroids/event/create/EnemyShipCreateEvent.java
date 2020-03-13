package se.hig.thlu.asteroids.event.create;

import se.hig.thlu.asteroids.model.entity.EnemyShip;

public class EnemyShipCreateEvent extends CreateEvent {

	public EnemyShipCreateEvent(EnemyShip entity) {
		super(entity);
	}
}
