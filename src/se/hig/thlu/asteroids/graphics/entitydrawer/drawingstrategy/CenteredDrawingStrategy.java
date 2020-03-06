package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public class CenteredDrawingStrategy implements DrawingStrategy {

	public CenteredDrawingStrategy() {
	}

	@Override
	public void draw(GraphicsAdapter<? super ImageAdapter> graphics, DrawingParameters drawingParameters) {
		double x = drawingParameters.getCenter().getX();
		double y = drawingParameters.getCenter().getY();
		int width = drawingParameters.getWidth();
		int height = drawingParameters.getHeight();
		ImageAdapter image = drawingParameters.getImage();
		double rotation = drawingParameters.getRotation();

		int xCorner = (int) x - width / 2;
		int yCorner = (int) y - height / 2;
		graphics.drawImageWithRotation(image,
				rotation,
				(double) x,
				(double) y,
				xCorner,
				yCorner);
	}

}
