package se.hig.thlu.asteroids.model.drawingstrategy;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Point;

public class CenteredDrawingStrategy implements DrawingStrategy {

	private final ImageAdapter image;
	private final double rotation;
	private final Point rotationPoint;
	private final int x, y;

	public CenteredDrawingStrategy(ImageAdapter image, double rotation, Point rotationPoint, int x, int y) {
		this.image = image;
		this.rotation = rotation;
		this.rotationPoint = rotationPoint;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics) {
		graphics.drawImageWithRotation(image,
				rotation,
				rotationPoint.getX(),
				rotationPoint.getY(),
				x,
				y);
	}
}
