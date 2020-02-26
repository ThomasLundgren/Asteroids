package se.hig.thlu.asteroids.graphics.primitives;

import se.hig.thlu.asteroids.model.*;

public class Line {

	private final Point start, end;

	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public Line withStart(Point start) {
		return new Line(start, end);
	}

	public Point getEnd() {
		return end;
	}

	public Line withEnd(Point end) {
		return new Line(start, end);
	}
}
