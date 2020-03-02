package se.hig.thlu.asteroids.graphics.drawer;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;

import java.beans.PropertyChangeEvent;

import static se.hig.thlu.asteroids.model.PlayerShip.PlayerShipProperty.IS_ACCELERATING;

public class PlayerShipDrawer extends EntityDrawer {

	private static final int SPRITE_X_CENTER_PIXEL = 19;
	private static final int SPRITE_Y_CENTER_PIXEL = 10;
	private final ImageAdapter accelerating;
	private final ImageAdapter notAccelerating;

	// TODO: Change so that this class always draws the non-accelerating sprite. Create decorator that draws the
	//  "exhausts".
	private PlayerShipDrawer(ImageAdapter accelerating, ImageAdapter notAccelerating) {
		super(notAccelerating);
		this.accelerating = accelerating;
		this.notAccelerating = notAccelerating;
		activeSprite = notAccelerating;
	}

	public static PlayerShipDrawer createPlayerShipDrawer(ImageAdapter accelerating, ImageAdapter notAccelerating) {
		return new PlayerShipDrawer(accelerating, notAccelerating);
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		int xCorner = (int) x - activeSprite.getWidth() + SPRITE_X_CENTER_PIXEL;
		int yCorner = (int) y - activeSprite.getHeight() + SPRITE_Y_CENTER_PIXEL;
		graphics.drawImageWithRotation(activeSprite, angle, x, y, xCorner, yCorner);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(IS_ACCELERATING.getPropertyName())) {
			boolean isAccelerating = (boolean) evt.getNewValue();
			activeSprite = isAccelerating ? accelerating : notAccelerating;
		}
	}

}
