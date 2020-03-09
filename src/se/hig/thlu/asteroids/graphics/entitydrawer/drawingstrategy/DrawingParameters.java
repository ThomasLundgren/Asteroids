package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.Dim;
import se.hig.thlu.asteroids.model.entity.Entity;
import se.hig.thlu.asteroids.model.Point;

public class DrawingParameters {

	private ImageAdapter image;
	private Point center;
	private double rotation;
	private Dim dimensions;

	public DrawingParameters(ImageAdapter image, Point center, double rotation, Dim dimensions) {
		this.image = image;
		this.center = center;
		this.rotation = rotation;
		this.dimensions = dimensions;
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

	public Dim getDimensions() {
		return dimensions;
	}

	public DrawingParameters setDimensions(Dim dimensions) {
		this.dimensions = dimensions;
		return this;
	}

	public double getRotation() {
		return rotation;
	}

	public DrawingParameters setRotation(double rotation) {
		this.rotation = rotation;
		return this;
	}

	public static DrawingParameters fromEntity(Entity entity, ImageAdapter image) {
		return new DrawingParameters(image,
				entity.getCenter(),
				entity.getRotation(),
				entity.getDimensions());
	}
}
