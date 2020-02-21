package se.hig.thlu.asteroids.model;

public class Point {

	private final double x, y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double distanceTo(Point point) {
		// TODO
		return 0d;
	}

	public Point withX(double x) {
		return new Point(this.x, y);
	}

	public Point withY(double y) {
		return new Point(x, this.y);
	}

	public double getX() { return x; }

	public double getY() { return y; }

	@Override
	public String toString() {
		return "Point{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
