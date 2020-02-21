package se.hig.thlu.asteroids.graphics.swingawtadapter;

import se.hig.thlu.asteroids.graphics.renderer.*;

public class AwtFontAdapter implements Font {

	private java.awt.Font font;

	public AwtFontAdapter(java.awt.Font font) {
		this.font = font;
	}

	public java.awt.Font getInnerFont() {
		return font;
	}
}
