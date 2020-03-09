package se.hig.thlu.asteroids.graphics.entitydrawer.drawingstrategy;

import se.hig.thlu.asteroids.graphics.font.FontAdapter;
import se.hig.thlu.asteroids.graphics.graphicsadapter.GraphicsAdapter;
import se.hig.thlu.asteroids.graphics.image.ImageAdapter;

public class CenteredDrawingStrategy extends AbstractDrawingStrategy {

	@Override
	public void draw(GraphicsAdapter<FontAdapter, ImageAdapter> graphics, DrawingParameters drawingParameters) {
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
