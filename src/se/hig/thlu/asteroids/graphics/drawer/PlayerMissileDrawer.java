package se.hig.thlu.asteroids.graphics.drawer;


import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;

public final class PlayerMissileDrawer extends EntityDrawer {

	private PlayerMissileDrawer(ImageAdapter sprite) {
		super(sprite);
	}

	public static PlayerMissileDrawer createPlayerMissileDrawer(ImageAdapter sprite) {
		return new PlayerMissileDrawer(sprite);
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		int cornerX = (int) x - activeSprite.getWidth() / 2;
		int cornerY = (int) y - activeSprite.getHeight() / 2;
		graphics.drawImageWithRotation(activeSprite,
				angle,
				x,
				y,
				(int) cornerX,
				(int) cornerY);
	}
}
