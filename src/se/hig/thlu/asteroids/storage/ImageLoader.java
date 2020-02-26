package se.hig.thlu.asteroids.storage;

import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ImageLoader {

	public static Image playerShipImg;
	public static Image backgroundImg;

	public ImageLoader() {
		ClassLoader loader = ImageLoader.class.getClassLoader();
		URL playerImgUrl = loader.getResource("resources/images/final/player-ship-accel.png");
		URL bgImgUrl = loader.getResource("resources/images/final/background.png");

		if (playerImgUrl != null && bgImgUrl != null) {
			try {
				playerShipImg = ImageIO.read(playerImgUrl).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
				backgroundImg = ImageIO.read(bgImgUrl);
			} catch (IOException e) {
				System.err.println("Could not load sprites, make sure that the sprites are in their right location");
			}
		}
	}

	public static Image getPlayerShipImg() {
		return playerShipImg;
	}

	public static Image getBackgroundImg() {
		return backgroundImg;
	}
}
