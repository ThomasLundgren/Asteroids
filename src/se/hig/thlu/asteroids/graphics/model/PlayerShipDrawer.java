package se.hig.thlu.asteroids.graphics.model;

import se.hig.thlu.asteroids.graphics.renderer.*;
import se.hig.thlu.asteroids.storage.*;

import java.beans.*;

import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.*;

public class PlayerShipDrawer extends EntityDrawer {

	private static final int SPRITE_X_CENTER_PIXEL = 19;
	private static final int SPRITE_Y_CENTER_PIXEL = 10;

	public PlayerShipDrawer(ImageLoader imageLoader, IRenderer renderer) {
		super(imageLoader, renderer);
	}

	@Override
	public void draw(IRenderer renderer) {
		x = x - playerShipImage.getWidth() + SPRITE_X_CENTER_PIXEL;
		y = y - playerShipImage.getHeight() + SPRITE_Y_CENTER_PIXEL;
		renderer.drawImageWithRotation(playerShipImage, angle, anchorX, anchorY, x, y);
	}

	@Override
	protected void onPropertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(FACING_DIRECTION.getPropertyName())) {
			angle = (double) evt.getNewValue();
		} else if (evt.getPropertyName().equals(IS_ACCELERATING.getPropertyName())) {
			boolean isAccelerating = (boolean) evt.getNewValue();
			if (isAccelerating) {
				playerShipImage = imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_ACCEL_PNG);
			} else {
				playerShipImage = imageLoader.getImageResource(ImageLoader.ImageResource.PLAYER_SHIP_PNG);
			}
		}
	}
}
