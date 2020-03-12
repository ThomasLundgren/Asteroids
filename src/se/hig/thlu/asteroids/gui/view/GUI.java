package se.hig.thlu.asteroids.gui.view;

import se.hig.thlu.asteroids.gui.eventlisteneradapter.EventAdapter;
import se.hig.thlu.asteroids.gui.eventlisteneradapter.EventListenerAdapter;
import se.hig.thlu.asteroids.observer.IObserver;

public interface GUI<T extends EventListenerAdapter<? extends EventAdapter>> extends IObserver {

	void addEventListener(T listener);

}
