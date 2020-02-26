package se.hig.thlu.asteroids.graphics.polygon;

import se.hig.thlu.asteroids.ui.*;

import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class PlayerShipImage implements IPolygon {

	private Image img;

	public PlayerShipImage() {
		URL url = GUI.class.getClassLoader().getResource("resources/images/Spaceship_01_BLUE_v3png.png");
		if (url != null) {
			try {
				img = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (img != null) {
			Image tmp = img.getScaledInstance(100, 100,
					Image.SCALE_SMOOTH);
			g.drawImage(tmp, 200, 200, null);
		}
	}
}
