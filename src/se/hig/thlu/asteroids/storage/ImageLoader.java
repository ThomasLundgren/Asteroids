package se.hig.thlu.asteroids.storage;

import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class ImageLoader {

	public enum ImagePath {
		PLAYER_SHIP_ACCEL_PNG("resources/images/final/player-ship-accel.png", 70, 70),
		BACKGROUND_PNG("resources/images/final/background.png", -1, -1);

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

		private int getWidth() {
			return width;
		}

		private int getHeight() {
			return height;
		}
	}

	private final Map<String, Image> imageCache;

	public ImageLoader() {
		imageCache = new HashMap<>(15);
		Stream.of(ImagePath.values()).forEach(this::loadImage);
	}

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
					.map(i -> i.getScaledInstance(
							imagePath.getWidth(),
							imagePath.getHeight(),
							Image.SCALE_SMOOTH));
		} catch (IOException e) {
			img = Optional.empty();
		}
		img.ifPresent(i -> imageCache.put(imagePath.getImagePath(), i));
		return img;
	}
}
