package se.hig.thlu.asteroids.graphics.model;

import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.storage.*;

import java.awt.*;
import java.io.*;

public class PlayerShipGModel implements GraphicModel {

	private final Image playerShipImage;
	private final PlayerShip playerShip;

	public PlayerShipGModel(PlayerShip playerShip) throws IOException {
		this.playerShipImage = ImageLoader.getPlayerShipImg();
		this.playerShip = playerShip;
	}

	@Override
	public void draw(Graphics g) {
		Point position = playerShip.getPosition();
		g.drawImage(
				playerShipImage,
				(int) position.getX(),
				(int) position.getY(),
				null);
	}
}
