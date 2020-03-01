package se.hig.thlu.asteroids.graphics.image;

import java.awt.image.BufferedImage;

public class AwtImageAdapter extends BufferedImage implements ImageAdapter {

	public AwtImageAdapter(BufferedImage image) {
		super(image.getColorModel(), image.getRaster(), image.getColorModel().isAlphaPremultiplied(), null);
	}

}
