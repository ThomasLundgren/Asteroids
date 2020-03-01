package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.storage.AbstractImageLoader.ImageResource;
import se.hig.thlu.asteroids.storage.ImageLoaderAwt;

import java.beans.PropertyChangeEvent;

import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.FACING_DIRECTION;
import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.IS_ACCELERATING;

public class PlayerShipDrawerAwt extends AwtEntityDrawer {

	private static final int SPRITE_X_CENTER_PIXEL = 19;
	private static final int SPRITE_Y_CENTER_PIXEL = 10;

	public PlayerShipDrawerAwt(ImageLoaderAwt imageLoader) {
		super(imageLoader);
		setImage(false);
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
				int xCorner = (int) x - sprite.getWidth() + SPRITE_X_CENTER_PIXEL;
		int yCorner = (int) y - sprite.getHeight() + SPRITE_Y_CENTER_PIXEL;
		graphics.drawImageWithRotation(sprite, angle, x, y, xCorner, yCorner);
		
//		AffineTransform backup = g2d.getTransform();
//		double angleRad = StrictMath.toRadians(angle);
//		AffineTransform op = AffineTransform.getRotateInstance(angleRad, x, y);
//		g2d.setTransform(op);
//		int xCorner = (int) x - sprite.getWidth() + SPRITE_X_CENTER_PIXEL;
//		int yCorner = (int) y - sprite.getHeight() + SPRITE_Y_CENTER_PIXEL;
//		g2d.drawImage(sprite, (int) xCorner, (int) yCorner, null);
//		g2d.setTransform(backup);
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
				? imageLoader.getImageResource(ImageResource.PLAYER_SHIP_ACCEL_PNG)
				: imageLoader.getImageResource(ImageResource.PLAYER_SHIP_PNG);
	}
}
