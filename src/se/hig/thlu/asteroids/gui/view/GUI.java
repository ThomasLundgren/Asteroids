package se.hig.thlu.asteroids.gui.view;

import se.hig.thlu.asteroids.gui.eventlisteneradapter.EventAdapter;
import se.hig.thlu.asteroids.gui.eventlisteneradapter.EventListenerAdapter;

public interface GUI<T extends EventListenerAdapter<? extends EventAdapter>> {

	void addEventListener(T listener);

}
