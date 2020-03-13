package se.hig.thlu.asteroids.event;

import java.util.UUID;

public interface EventHandler<T extends Event> {

	void addObserverMapping(UUID id, IObserver observer);

	void addObserver(IObserver observer);

	void notify(T event);

}
