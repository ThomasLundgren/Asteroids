package se.hig.thlu.asteroids.graphics.renderer;

import java.awt.image.*;
import java.util.*;

public abstract class AwtImage extends BufferedImage implements IImage{

	public AwtImage(int width, int height, int imageType) {
		super(width, height, imageType);
	}

	public AwtImage(int width, int height, int imageType, IndexColorModel cm) {
		super(width, height, imageType, cm);
	}

	public AwtImage(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
		super(cm, raster, isRasterPremultiplied, properties);
	}
}
