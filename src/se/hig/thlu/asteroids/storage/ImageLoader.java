package se.hig.thlu.asteroids.storage;

import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ImageLoader {

	// TODO turn into singleton with cached images

	public static Image getPlayerShipImg() throws IOException {
		ClassLoader loader = ImageLoader.class.getClassLoader();
		URL playerImgUrl = loader.getResource("resources/images/final/player-ship-accel.png");

		// TODO set constants for width and height of image
		return ImageIO.read(playerImgUrl).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
	}

	public static Image getBackgroundImg() throws IOException {
		ClassLoader loader = ImageLoader.class.getClassLoader();
		URL bgImgUrl = loader.getResource("resources/images/final/background.png");

		// TODO set constants for width and height of image
		return ImageIO.read(bgImgUrl);
	}
}
