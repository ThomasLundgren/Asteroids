package se.hig.thlu.asteroids.graphics.graphicsadapter;

import se.hig.thlu.asteroids.graphics.image.AwtImageAdapter;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class AwtGraphicsAdapter implements GraphicsAdapter<AwtImageAdapter> {

	private final Graphics2D g2d;

	public AwtGraphicsAdapter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void drawImageWithRotation(AwtImageAdapter image, double angle, double anchorX, double anchorY, int x, int y) {
		AffineTransform backup = g2d.getTransform();
		double angleRad = StrictMath.toRadians(angle);
		AffineTransform op = AffineTransform.getRotateInstance(angleRad, anchorX, anchorY);
		g2d.setTransform(op);
		g2d.drawImage(image, x, y, null);
		g2d.setTransform(backup);
	}

	@Override
	public void drawImage(AwtImageAdapter image, int x, int y, int width, int height) {
		g2d.drawImage(image, x, y, width, height, null);
	}
}
