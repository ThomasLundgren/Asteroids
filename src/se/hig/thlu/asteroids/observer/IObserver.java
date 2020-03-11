package se.hig.thlu.asteroids.observer;

public interface IObserver {

	void notify(String propertyName, Event event);

}
