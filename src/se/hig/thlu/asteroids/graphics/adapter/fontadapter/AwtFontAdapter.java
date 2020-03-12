package se.hig.thlu.asteroids.graphics.adapter.fontadapter;

import java.awt.*;

public class AwtFontAdapter extends Font implements FontAdapter {

	private final Font font;

	public AwtFontAdapter(Font font) {
		super(font.getName(), font.getStyle(), font.getSize());
		this.font = font;
	}

	// TODO: Test constructors. Try this if the other doesn't work.
//	public AwtFontAdapter(Font font) {
//		super(font.getAttributes());
//	}

}
