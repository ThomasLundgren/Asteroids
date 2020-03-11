package se.hig.thlu.asteroids.gamestate;

import se.hig.thlu.asteroids.observer.Event;
import se.hig.thlu.asteroids.observer.IObservable;
import se.hig.thlu.asteroids.observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class EventBus implements IObserver, IObservable {

	private static final List<IObserver> observers = new ArrayList<>(10);

	private EventBus() {
	}

	private enum InstanceHolder {
		;
		static final EventBus instance = new EventBus();
	}

	public static EventBus getInstance() {
		return InstanceHolder.instance;
	}

	@Override
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}

	@Override
	public void notify(String propertyName, Event event) {
		observers.forEach(observer -> observer.notify(propertyName, event));
	}
}
