package se.hig.thlu.asteroids.graphics.adapter.imageadapter;

import se.hig.thlu.asteroids.model.Dim;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AwtImageAdapter extends BufferedImage implements ImageAdapter {

	private BufferedImage image;

	public AwtImageAdapter(BufferedImage image) {
		super(image.getColorModel(), image.getRaster(), image.getColorModel().isAlphaPremultiplied(), null);
		this.image = image;
	}

	@Override
	public <T extends ImageAdapter> T resizeTo(Dim dimensions) {
		int width = dimensions.getWidth();
		int height = dimensions.getHeight();
		Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = bufferedImage.getGraphics();
		g.drawImage(newImage, 0, 0, null);
		g.dispose();
		return (T) new AwtImageAdapter(bufferedImage);
	}

	@Override
	public String toString() {
		return "AwtImageAdapter{" +
				"width=" + image.getWidth() +
				"height=" + image.getHeight() +
				'}';
	}
}
