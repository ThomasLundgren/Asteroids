package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.config.*;
import se.hig.thlu.asteroids.controller.GameController.*;
import se.hig.thlu.asteroids.graphics.model.*;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.storage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.List;
import java.util.*;

public class GUI implements UI {

	private final JFrame frame;
	private BackgroundPanel backgroundPanel;

	public GUI() {
		frame = new JFrame();
		initBackgroundPanel();
		configureFrame();
	}

	@Override
	public void render(List<GraphicModel> entities) {
		backgroundPanel.setEntities(entities);
	}

	// TODO: Abstract keylistener away so that it can be replaced with any event emitter?
	public void addKeyListener(KeyListener listener) {
		frame.addKeyListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO: Make backgroundpanel subscribe instead?
		if (evt.getPropertyName().equals(Property.PLAYER_SHIP.getPropertyName())) {
			PlayerShip playerShip = (PlayerShip) evt.getNewValue();
			PlayerShipGModel model = new PlayerShipGModel(playerShip);
			backgroundPanel.setEntities(Collections.singletonList(model));
		}
	}

	private void initBackgroundPanel() {
		Optional<Image> bgImg = new ImageLoader().loadImage(ImageLoader.ImagePath.BACKGROUND_PNG);
		if (bgImg.isPresent()) {
			backgroundPanel = new BackgroundPanel(bgImg.get());
		} else {
			throw new RuntimeException("Could not find the background image!");
		}
	}

	private void configureFrame() {
		// TODO: Make JFrame always square and resize all graphics accordingly
		frame.setResizable(false);
		frame.setSize(new Dimension(GameConfig.windowWidth, GameConfig.windowHeight));
		frame.add(backgroundPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
