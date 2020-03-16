package se.hig.thlu.asteroids.event;

import se.hig.thlu.asteroids.event.entity.DestroyedEvent;

import java.util.*;

public abstract class AbsEventHandler<T extends Event<?>> implements EventHandler<T> {

	protected Map<UUID, List<IObserver>> observerMapping = new LinkedHashMap<>(20);
	protected final List<IObserver> observers = new ArrayList<>(10);

	@Override
	public void addObserverMapping(UUID id, IObserver observer) {
		List<IObserver> obs = observerMapping.get(id);
		if (obs == null) {
			List<IObserver> newObserverList = new ArrayList<>(5);
			newObserverList.add(observer);
			observerMapping.put(id, newObserverList);
		} else {
			obs.add(observer);
		}

	}

	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

	@Override
	public void notify(T event) {
		List<IObserver> observerList = observerMapping.get(event.getId());
		if (observerList != null) {
			observerList.forEach(observer -> observer.notify(event));
		}
		observers.forEach(observer -> observer.notify(event));
		if (event instanceof DestroyedEvent) {
			observerMapping.remove(event.getId());
		}
	}


}
