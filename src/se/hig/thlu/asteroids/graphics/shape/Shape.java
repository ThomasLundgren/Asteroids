package se.hig.thlu.asteroids.graphics.shape;

import se.hig.thlu.asteroids.graphics.renderer.Renderer;
import se.hig.thlu.asteroids.model.Point;

public interface Shape {

	public void draw(Renderer g);

	public Point getPosition();

	public double getWidth();

	public double getHeight();

	public boolean intersects(Point point);

	public void moveTo(Point point);

	public void move(double dx, double dy);

	public void resizeTo(Point point);
}
