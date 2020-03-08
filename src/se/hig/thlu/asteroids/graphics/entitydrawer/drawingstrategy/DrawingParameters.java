package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.entity.Entity;
import se.hig.thlu.asteroids.model.Point;

public class DrawingParameters {

	private ImageAdapter image;
	private Point center;
	private double rotation;
	private int width;
	private int height;

	public DrawingParameters(ImageAdapter image, Point center, double rotation, int width, int height) {
		this.image = image;
		this.center = center;
		this.rotation = rotation;
		this.width = width;
		this.height = height;
	}

	public ImageAdapter getImage() {
		return image;
	}

	public DrawingParameters setImage(ImageAdapter image) {
		this.image = image;
		return this;
	}

	public Point getCenter() {
		return center;
	}

	public DrawingParameters setCenter(Point center) {
		this.center = center;
		return this;
	}

	public double getRotation() {
		return rotation;
	}

	public DrawingParameters setRotation(double rotation) {
		this.rotation = rotation;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public DrawingParameters setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public DrawingParameters setHeight(int height) {
		this.height = height;
		return this;
	}

	public static DrawingParameters fromEntity(Entity entity, ImageAdapter image) {
		return new DrawingParameters(image,
				entity.getCenter(),
				entity.getRotation(),
				entity.getDimensions().getWidth(),
				entity.getDimensions().getHeight());
	}
}
