package se.hig.thlu.asteroids.graphics.image;

import java.awt.image.BufferedImage;

public class AwtImage extends BufferedImage implements IImage {

	public AwtImage(BufferedImage image) {
		super(image.getColorModel(), image.getRaster(), image.getColorModel().isAlphaPremultiplied(), null);
	}

}
