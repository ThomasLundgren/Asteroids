package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.graphics.image.AwtImageAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageLoaderAwt extends ImageLoader<AwtImageAdapter> {

	public ImageLoaderAwt() throws IOException {
	}

	@Override
	protected AwtImageAdapter loadImage(ImageResource imageResource) throws IOException {
		ClassLoader classLoader = ImageLoaderAwt.class.getClassLoader();

		try (InputStream stream = Objects.requireNonNull(classLoader
				.getResourceAsStream(imageResource.getImagePath()))) {
			Image img = ImageIO.read(stream).getScaledInstance(
					imageResource.getWidth(),
					imageResource.getHeight(),
					Image.SCALE_SMOOTH);
			int width = img.getWidth(null);
			int height = img.getHeight(null);

			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
			return new AwtImageAdapter(bufferedImage);
		}
	}

	@Override
	protected List<AwtImageAdapter> loadAnimation(AnimationResource animationResource) throws IOException {
		ClassLoader classLoader = ImageLoaderAwt.class.getClassLoader();
		try (InputStream stream = Objects.requireNonNull(classLoader
				.getResourceAsStream(animationResource.getImagePath()))) {
			Image img = ImageIO.read(stream).getScaledInstance(
					animationResource.getWidth(),
					animationResource.getHeight(),
					Image.SCALE_SMOOTH);
			int width = img.getWidth(null);
			int height = img.getHeight(null);

			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();

			int columns = animationResource.getColumns();
			int rows = animationResource.getRows();
			List<AwtImageAdapter> images = new ArrayList<>(rows * columns);

			int stepX = width / columns;
			int stepY = height / rows;
			int x = 0;
			int y = 0;
			for (int i = 0; i < rows; i++) {
				x = 0;
				for (int j = 0; j < columns; j++) {
					BufferedImage image = bufferedImage.getSubimage(x, y, stepX, stepY);
					x += stepX;
					images.add(new AwtImageAdapter(image));
					g.dispose();
				}
				y += stepY;
			}
			return images;
		}
	}
}