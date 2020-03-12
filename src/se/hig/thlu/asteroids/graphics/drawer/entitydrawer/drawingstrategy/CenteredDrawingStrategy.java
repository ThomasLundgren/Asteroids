package se.hig.thlu.asteroids.graphics.drawer.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.adapter.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.adapter.imageadapter.ImageAdapter;

public class CenteredDrawingStrategy extends AbstractDrawingStrategy {

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics,
					 DrawingParameters drawingParameters) {
		setParameters(drawingParameters);

		int xCorner = (int) x - dimensions.getWidth() / 2;
		int yCorner = (int) y - dimensions.getHeight() / 2;
		graphics.drawImageWithRotation(image,
				rotation,
				(double) x,
				(double) y,
				xCorner,
				yCorner);
	}

}
