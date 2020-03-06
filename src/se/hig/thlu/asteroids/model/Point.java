package se.hig.thlu.asteroids.model;

public class Point {

	private double x, y;

	public Point() {
		x = 0.0;
		y = 0.0;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double distanceTo(Point point) {
		double dx = this.x - point.x;
		double dy = this.y - point.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
