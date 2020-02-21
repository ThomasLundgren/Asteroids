package se.hig.thlu.asteroids.ui;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class GUI implements UI {

	private final JFrame frame;
	private JPanel bg;
	private JPanel backgroundPanel;

	public GUI() {
		frame = new JFrame();
		Image img = null;
		try {
			URL url = GUI.class.getClassLoader().getResource("resources/images/dark-starry-sky-sololos.jpg");
			if (url != null) {
				img = ImageIO.read(url);
				backgroundPanel = new BackgroundPanel(img);
				bg = new TranslucentPane(backgroundPanel.getWidth(), backgroundPanel.getHeight());
				backgroundPanel.add(bg);
			}
		} catch (IOException e) {
			System.out.println("File not found");
			backgroundPanel = new JPanel();
			bg = new JPanel();
		}
		frame.setSize(new Dimension(900, 900));
		frame.add(backgroundPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void render() {
		// TODO
	}
}
