package se.hig.thlu.asteroids.graphics.entitydrawer;

import se.hig.thlu.asteroids.graphics.image.ImageAdapter;
import se.hig.thlu.asteroids.graphics.renderer.GraphicsAdapter;
import se.hig.thlu.asteroids.model.Asteroid;

import java.beans.PropertyChangeEvent;

public class AsteroidDrawer extends EntityDrawer {

	private final ImageAdapter small, medium, large;

	private AsteroidDrawer(ImageAdapter small, ImageAdapter medium, ImageAdapter large) {
		super(large);
		this.small = small;
		this.medium = medium;
		this.large = large;
	}

	public static AsteroidDrawer createAsteroidDrawer(ImageAdapter small, ImageAdapter medium, ImageAdapter large) {
		return new AsteroidDrawer(small, medium, large);
	}

	@Override
	public void draw(GraphicsAdapter<ImageAdapter> graphics) {
		graphics.drawImageWithRotation(activeSprite,
				angle,
				x,
				y,
				(int) x,
				(int) y);
	}

	// TODO: Get rid of this method?
	protected void setImage(ImageAdapter sprite) {
		activeSprite = sprite;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(Asteroid.AsteroidProperty.SIZE.getPropertyName())) {
			Asteroid.AsteroidSize size = (Asteroid.AsteroidSize) evt.getNewValue();
			switch (size) {
				case LARGE:
					activeSprite = large;
					break;
				case MEDIUM:
					activeSprite = medium;
					break;
				case SMALL:
					activeSprite = small;
					break;
			}
		}
	}
}
