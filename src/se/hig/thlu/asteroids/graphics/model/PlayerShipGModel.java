package se.hig.thlu.asteroids.graphics.model;

import se.hig.thlu.asteroids.model.Point;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.storage.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class PlayerShipGModel implements GraphicModel {

	private final PlayerShip playerShip;
	private Image playerShipImage;

	public PlayerShipGModel(PlayerShip playerShip) {
		this.playerShip = playerShip;
		playerShipImage = getImage();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		Point position = playerShip.getCenter();
		double x = position.getX();
		double y = position.getY();
		double angle = StrictMath.toRadians(playerShip.getFacingDirection());

		AffineTransform backup = g2d.getTransform();
		AffineTransform op = AffineTransform.getRotateInstance(angle, x, y);
		g2d.setTransform(op);
		double xCorner = x - (double) playerShipImage.getWidth(null) + 17.0;
		double yCorner = y - (double) playerShipImage.getHeight(null) + 10.0;
		g2d.drawImage(playerShipImage, (int) xCorner, (int) yCorner, null);
		g2d.setTransform(backup);
	}

	private Image getImage() {
		Optional<Image> shipImage;
		if (playerShip.isAccelerating()) {
			shipImage = new ImageLoader().loadImage(ImageLoader.ImagePath.PLAYER_SHIP_ACCEL_PNG);
		} else {
			shipImage = new ImageLoader().loadImage(ImageLoader.ImagePath.PLAYER_SHIP_PNG);
		}
		if (shipImage.isPresent()) {
			return shipImage.get();
		} else {
			throw new RuntimeException("Could not find the Ship image!");
		}
	}
}
