package se.hig.thlu.asteroids.graphics.polygon;

import se.hig.thlu.asteroids.graphics.primitives.*;
import se.hig.thlu.asteroids.model.Point;

import java.awt.*;
import java.util.List;
import java.util.*;

public class PlayerShipPolygon implements IPolygon {

	private final List<Line> lines = new ArrayList<>(7);
	private static final double SHIP_LENGTH = 50.0;

	public PlayerShipPolygon(Point center, double direction) {
		setLines(center, direction);
	}

	private void setLines(Point center, double direction) {
		double x = center.getX() + SHIP_LENGTH /2.0 * Math.cos(direction);
		double y = center.getY() + SHIP_LENGTH /2.0 * Math.sin(direction);
		Point tip = new Point(x, y);
		lines.add(new Line(center, tip));

		x = center.getX() + getWingLength() * Math.cos(direction - 90.0);
		y = center.getY() + getWingLength() * Math.sin(direction - 90.0);
		Point leftWingEdge = new Point(x, y);
		lines.add(new Line(center, leftWingEdge));

		x = center.getX() + getWingLength() * Math.cos(direction + 90.0);
		y = center.getY() + getWingLength() * Math.sin(direction + 90.0);
		Point rightWingEdge = new Point(x, y);
		lines.add(new Line(center, rightWingEdge));

		lines.add(new Line(leftWingEdge, tip));
		lines.add(new Line(rightWingEdge, tip));

		x = center.getX() - SHIP_LENGTH /2.0 * Math.cos(direction);
		y = center.getY() - SHIP_LENGTH /2.0 * Math.sin(direction);
		Point end = new Point(x, y);
		lines.add(new Line(leftWingEdge, end));
		lines.add(new Line(rightWingEdge, end));
		lines.add(new Line(center, end));
	}

	public void draw(Graphics g) {
		lines.forEach(l -> g.drawLine(
				(int) l.getStart().getX(),
				(int) l.getStart().getY(),
				(int) l.getEnd().getX(),
				(int) l.getEnd().getY()));
	}

	private static double getWingLength() {
		double a = Math.pow(SHIP_LENGTH/ 4.0, 2.0);
		return Math.sqrt(a + a);
	}

}
