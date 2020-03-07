package se.hig.thlu.asteroids.model;

import se.hig.thlu.asteroids.observer.IObservable;

import java.util.Optional;
import java.util.UUID;

public interface Entity extends IObservable {

	UUID getId();

	int getWidth();

	int getHeight();

	Point getCenter();

	void setCenter(Point center);

	double getRotation();

	boolean isDestroyed();

	Optional<Explosion> destroy();

	void update();

	boolean intersectsWith(Entity other);

}
