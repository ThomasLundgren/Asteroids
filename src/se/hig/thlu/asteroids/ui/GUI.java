package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.graphics.polygon.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class GUI implements UI {

	private final JFrame frame;
	private TranslucentPane bg;
	private BackgroundPanel backgroundPanel;

	public GUI() {
		frame = new JFrame();
		Image img = null;
		try {
			URL url = GUI.class.getClassLoader().getResource("resources/images/dark-starry-sky-sololos.jpg");
			if (url != null) {
				img = ImageIO.read(url);
				backgroundPanel = new BackgroundPanel(img);
//				bg = new TranslucentPane(backgroundPanel.getWidth(), backgroundPanel.getHeight(), 0.6f);
//				backgroundPanel.add(bg);
			}
		} catch (IOException e) {
			System.out.println("File not found");
			bg = new TranslucentPane();
		}
		frame.setSize(new Dimension(900, 900));
		frame.add(backgroundPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void render(List<IPolygon> entities) {
//		bg.setEntities(entities);
		backgroundPanel.setEntities(entities);
	}

	public void addKeyListener(KeyListener listener) {
		frame.addKeyListener(listener);
	}
}
