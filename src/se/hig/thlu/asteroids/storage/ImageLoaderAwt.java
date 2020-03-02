package se.hig.thlu.asteroids.storage;

import se.hig.thlu.asteroids.graphics.image.AwtImageAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
}
