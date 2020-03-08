package se.hig.thlu.asteroids.model;

import java.util.Objects;

public class Dim {

	private final int width, height;

	public Dim(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public Dim withWidth(int width) {
		return new Dim(width, height);
	}

	public int getWidth() {
		return width;
	}

	public Dim withHeight(int height) {
		return new Dim(width, height);
	}

	public int getHeight() {
		return height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Dim dimension = (Dim) o;
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
