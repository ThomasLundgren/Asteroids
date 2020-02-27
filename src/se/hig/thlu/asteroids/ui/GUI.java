package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.graphics.model.*;
import se.hig.thlu.asteroids.model.*;
import se.hig.thlu.asteroids.storage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.util.List;
import java.util.*;

public class GUI implements UI {

	private final JFrame frame;
	private BackgroundPanel backgroundPanel;

	public GUI() throws IOException {
		frame = new JFrame();
		backgroundPanel = new BackgroundPanel(ImageLoader.getBackgroundImg());
		configureFrame();
	}

	private void configureFrame() {
		frame.setSize(new Dimension(900, 900));
		frame.add(backgroundPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void render(List<GraphicModel> entities) {
		backgroundPanel.setEntities(entities);
	}

	public void addKeyListener(KeyListener listener) {
		frame.addKeyListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO: Make PLAYER_SHIP a constant
		if (evt.getPropertyName().equals("PLAYER_SHIP")) {
			PlayerShip playerShip = (PlayerShip) evt.getNewValue();
			PlayerShipGModel model = new PlayerShipGModel(playerShip);
			backgroundPanel.setEntities(Collections.singletonList(model));
		}
	}
}
