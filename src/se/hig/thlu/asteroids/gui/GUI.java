package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.gui.eventlistener.EventAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.EventListenerAdapter;
import se.hig.thlu.asteroids.observer.IObserver;

public interface GUI<T extends EventListenerAdapter<? extends EventAdapter>> extends IObserver {

	void addEventListener(T listener);

}
