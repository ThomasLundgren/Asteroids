package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.model.Dim;

public abstract class AbstractDrawingStrategy implements DrawingStrategy {

	protected double x;
	protected double y;
	protected Dim dimensions;
	protected ImageAdapter image;
	protected double rotation;

	protected void setParameters(DrawingParameters drawingParameters) {
		x = drawingParameters.getCenter().getX();
		y = drawingParameters.getCenter().getY();
		dimensions = drawingParameters.getDimensions();
		image = drawingParameters.getImage();
		rotation = drawingParameters.getRotation();
	}

}
