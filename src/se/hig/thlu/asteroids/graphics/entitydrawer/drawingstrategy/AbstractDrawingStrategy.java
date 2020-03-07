package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public abstract class AbstractDrawingStrategy implements DrawingStrategy {

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected ImageAdapter image;
	protected double rotation;

	protected void setParameters(DrawingParameters drawingParameters) {
		x = drawingParameters.getCenter().getX();
		y = drawingParameters.getCenter().getY();
		width = drawingParameters.getWidth();
		height = drawingParameters.getHeight();
		image = drawingParameters.getImage();
		rotation = drawingParameters.getRotation();
	}

}
