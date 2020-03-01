package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.storage.ImageLoader;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;

import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.FACING_DIRECTION;
import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.IS_ACCELERATING;

public class PlayerShipDrawerAwt extends AwtEntityDrawer {

	private static final double SPRITE_X_CENTER_PIXEL = 19.0;
	private static final double SPRITE_Y_CENTER_PIXEL = 10.0;

	public PlayerShipDrawerAwt(ImageLoader imageLoader) {
		super(imageLoader);
		setImage(false);
	}

	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform backup = g2d.getTransform();
		double angleRad = StrictMath.toRadians(angle);
		AffineTransform op = AffineTransform.getRotateInstance(angleRad, x, y);
		g2d.setTransform(op);
		double xCorner = x - sprite.getWidth(null) + SPRITE_X_CENTER_PIXEL;
		double yCorner = y - sprite.getHeight(null) + SPRITE_Y_CENTER_PIXEL;
		g2d.drawImage(sprite, (int) xCorner, (int) yCorner, null);
		g2d.setTransform(backup);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FACING_DIRECTION.getPropertyName())) {
			angle = (double) evt.getNewValue();
		} else if (evt.getPropertyName().equals(IS_ACCELERATING.getPropertyName())) {
			boolean isAccelerating = (boolean) evt.getNewValue();
			setImage(isAccelerating);
		}
	}

	// TODO: Move to ABC and override?
	private void setImage(boolean isAccelerating) {
		sprite = isAccelerating
				? imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_ACCEL_PNG)
				: imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_PNG);
	}
}
