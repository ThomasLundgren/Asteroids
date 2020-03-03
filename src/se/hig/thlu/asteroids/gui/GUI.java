package se.hig.thlu.asteroids.gui;

import se.hig.thlu.asteroids.gui.eventlistener.EventAdapter;
import se.hig.thlu.asteroids.gui.eventlistener.EventListenerAdapter;
import se.hig.thlu.asteroids.model.Entity;

import java.util.Collection;

public interface GUI<T extends EventListenerAdapter<? extends EventAdapter>> {

	void addEventListener(T listener);

//	void render(GraphicsAdapter<ImageAdapter> graphics);

	void setEntities(Collection<Entity> entities);

}
