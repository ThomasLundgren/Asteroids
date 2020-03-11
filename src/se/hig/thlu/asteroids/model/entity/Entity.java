package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Explosion;
import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.observer.IObservable;

import java.util.Optional;

public interface Entity extends IObservable {

	Dim getDimensions();

	Point getCenter();

	void setCenter(Point center);

	double getRotation();

	boolean isDestroyed();

	Optional<Explosion> destroy();

	void update();

	boolean intersectsWith(Entity other);

	int getScore();

}
