package se.hig.thlu.asteroids.event;

import java.util.*;

public abstract class AbsEventHandler<T extends Event> implements EventHandler<T> {

	protected Map<UUID, IObserver> observerMapping = new LinkedHashMap<>(20);
	protected final List<IObserver> observers = new ArrayList<>(10);

	@Override
	public void addObserverMapping(UUID id, IObserver observer) {
		observerMapping.put(id, observer);
	}

	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

	@Override
	public void notify(T event) {
		observers.forEach(observer -> observer.notify(event));
	}


}
