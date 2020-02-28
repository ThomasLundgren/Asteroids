package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.config.*;

import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class ImageLoader {

	private static final Map<String, Image> imageCache = new HashMap<>(15);

	public ImageLoader() {
		Stream.of(ImagePath.values()).forEach(this::loadImage);
	}
	;

	public Optional<Image> loadImage(ImagePath imagePath) {
		Image image = imageCache.get(imagePath.getImagePath());
		if (image != null) {
			return Optional.of(image);
		}
		Optional<Image> img;
		ClassLoader classLoader = ImageLoader.class.getClassLoader();

		try (InputStream stream = Objects.requireNonNull(classLoader
				.getResourceAsStream(imagePath.getImagePath()))) {
			img = Optional.ofNullable(ImageIO.read(stream))
					.map(im -> im.getScaledInstance(
							imagePath.getWidth(),
							imagePath.getHeight(),
							Image.SCALE_SMOOTH));
		} catch (IOException e) {
			img = Optional.empty();
		}
		img.ifPresent(i -> imageCache.put(imagePath.getImagePath(), i));
		return img;
	}

	public enum ImagePath {
		PLAYER_SHIP_ACCEL_PNG("resources/images/final/player-ship-accel.png", 45, 22),
		BACKGROUND_PNG("resources/images/final/background.png", GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT),
		PLAYER_SHIP_PNG("resources/images/final/player-ship.png", 29, 22);

		private final String imagePath;
		private final int width, height;

		ImagePath(String imagePath, int width, int height) {
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
