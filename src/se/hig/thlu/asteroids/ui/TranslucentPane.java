package se.hig.thlu.asteroids.ui;

import javax.swing.*;
import java.awt.*;

public class TranslucentPane extends JPanel {

	private int width, height;

	public TranslucentPane(int width, int height) {
		this.width = width;
		this.height = height;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle fill = new Rectangle(getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.8f));
		g2d.setColor(Color.BLACK);
		g2d.fill(fill);
		g2d.dispose();
	}
}
