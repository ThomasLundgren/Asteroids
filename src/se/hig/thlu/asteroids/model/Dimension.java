package se.hig.thlu.asteroids.model;

import java.util.Objects;

public class Dimension {

	private final int width, height;

	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Dimension withWidth(int width) {
		return new Dimension(width, height);
	}

	public int getWidth() {
		return width;
	}

	public Dimension withHeight(int height) {
		return new Dimension(width, height);
	}

	public int getHeight() {
		return height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Dimension dimension = (Dimension) o;
		return width == dimension.getWidth() &&
				height == dimension.getHeight();
	}

	@Override
	public int hashCode() {
		return Objects.hash(width, height);
	}

	@Override
	public String toString() {
		return "Dimension{" +
				"width=" + width +
				", height=" + height +
				'}';
	}
}
