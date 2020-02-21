package se.hig.thlu.asteroids.graphics.shape;

import se.hig.thlu.asteroids.model.Point;

public final class Line {

    private final Point start, end;

    Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
