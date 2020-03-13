package se.hig.thlu.asteroids.event.entity;

import se.hig.thlu.asteroids.event.AbsEventHandler;
import se.hig.thlu.asteroids.event.IObserver;

public class EntityEventHandler extends AbsEventHandler<EntityEvent> {

	@Override
	public void notify(EntityEvent event) {
		IObserver obs = observerMapping.get(event.getId());
		if (obs != null) {
			obs.notify(event);
		}
		observers.forEach(observer -> observer.notify(event));
	}
}
