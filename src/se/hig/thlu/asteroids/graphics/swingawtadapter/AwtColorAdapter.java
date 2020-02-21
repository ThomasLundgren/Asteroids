package se.hig.thlu.asteroids.graphics.swingawtadapter;

import se.hig.thlu.asteroids.graphics.renderer.*;

public class AwtColorAdapter extends java.awt.Color implements Color {

	public AwtColorAdapter(int rgb) {
		super(rgb);
	}

	public AwtColorAdapter(int r, int g, int b, int a) {
		super(r, g, b, a);
	}




}
