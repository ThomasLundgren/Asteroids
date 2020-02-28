package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.config.*;

import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ImageLoader {

	private static final Map<ImageResource, Image> imageCache = new EnumMap<ImageResource, Image>(ImageResource.class);

	/*
	Just throwing exception here since we NEED all images.
	If we cannot load all images, something has gone wrong.
	 */
	public ImageLoader() throws IOException {
		for (ImageResource imageResource : ImageResource.values()) {
			Image image = loadImage(imageResource);
			imageCache.put(imageResource, image);
		}
	}

	public Image getImageResource(ImageResource imageResource) {
		return imageCache.get(imageResource);
	}

	private Image loadImage(ImageResource imageResource) throws IOException {
		ClassLoader classLoader = ImageLoader.class.getClassLoader();

		try (InputStream stream = Objects.requireNonNull(classLoader
				.getResourceAsStream(imageResource.getImagePath()))) {
			return ImageIO.read(stream).getScaledInstance(
					imageResource.getWidth(),
					imageResource.getHeight(),
					Image.SCALE_SMOOTH);
		}
	}

	public enum ImageResource {
		PLAYER_SHIP_ACCEL_PNG("resources/images/final/player-ship-accel.png", 45, 22),
		BACKGROUND_PNG("resources/images/final/background.png", GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT),
		PLAYER_SHIP_PNG("resources/images/final/player-ship.png", 29, 22);

		private final String imagePath;
		private final int width, height;

		ImageResource(String imagePath, int width, int height) {
			this.imagePath = imagePath;
			this.width = width;
			this.height = height;
		}

		private String getImagePath() {
			return imagePath;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}
}
