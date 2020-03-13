package se.hig.thlu.asteroids.model.entity;

import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.Point;

import java.util.UUID;

public interface Entity {

	UUID getId();

	Dim getDimensions();

	Point getCenter();

	void setCenter(Point center);

	double getRotation();

	void destroy();

	void update();

	void intersectsWith(Entity other);

	int getScore();

}
