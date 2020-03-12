package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.graphics.adapter.imageadapter.AwtImageAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageLoaderAwt extends AbstractImageLoader<AwtImageAdapter> {

	public ImageLoaderAwt() throws IOException {
	}

	@Override
	protected List<AwtImageAdapter> spriteSheetToImageList(AwtImageAdapter spriteSheet, AnimationResource animation) {
		int columns = animation.getColumns();
		int rows = animation.getRows();
		List<AwtImageAdapter> images = new ArrayList<>(rows * columns);

		int width = spriteSheet.getWidth();
		int height = spriteSheet.getHeight();
		int stepX = width / columns;
		int stepY = height / rows;
		int x = 0;
		int y = 0;
		for (int i = 0; i < rows; i++) {
			x = 0;
			for (int j = 0; j < columns; j++) {
				BufferedImage image = spriteSheet.getSubimage(x, y, stepX, stepY);
				x += stepX;
				images.add(new AwtImageAdapter(image));
			}
			y += stepY;
		}
		return images;
	}

	@Override
	protected AwtImageAdapter loadImageFromString(String path, int width, int height) throws IOException {
		ClassLoader classLoader = ImageLoaderAwt.class.getClassLoader();

		try (InputStream stream = Objects.requireNonNull(classLoader
				.getResourceAsStream(path))) {
			Image img = ImageIO.read(stream).getScaledInstance(
					width,
					height,
					Image.SCALE_SMOOTH);

			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
			return new AwtImageAdapter(bufferedImage);
		}
	}
}