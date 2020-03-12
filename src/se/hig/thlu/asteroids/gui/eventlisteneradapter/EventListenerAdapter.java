package se.hig.thlu.asteroids.gui.eventlisteneradapter;

public interface EventListenerAdapter<T extends EventAdapter> {

	void pressed(T e);

	void released(T e);

}
