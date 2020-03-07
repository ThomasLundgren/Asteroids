package se.hig.thlu.asteroids.observer;

public interface IObserver {

	void onNotify(String propertyName, Event event);

}
