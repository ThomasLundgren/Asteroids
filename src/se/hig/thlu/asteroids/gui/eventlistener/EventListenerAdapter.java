package se.hig.thlu.asteroids.gui.eventlistener;

public interface EventListenerAdapter<T extends EventAdapter> {

	void pressed(T e);

	void released(T e);

}
