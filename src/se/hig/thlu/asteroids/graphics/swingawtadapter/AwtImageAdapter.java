package se.hig.thlu.asteroids.graphics.swingawtadapter;

import se.hig.thlu.asteroids.graphics.renderer.*;

public class AwtImageAdapter implements Image {

	private java.awt.Image image;

	public AwtImageAdapter(java.awt.Image image) {
		this.image = image;
	}

	public java.awt.Image getInnerImage() {
		return image;
	}
}
