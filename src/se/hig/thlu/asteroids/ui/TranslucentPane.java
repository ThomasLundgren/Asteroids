package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.graphics.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class TranslucentPane extends JPanel {

	private int width, height;
	private float alpha;
	private List<GraphicModel> entities = new ArrayList();

	public TranslucentPane() {}

	public TranslucentPane(int width, int height, float alpha) {
		this.width = width;
		this.height = height;
		this.alpha = alpha;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle fill = new Rectangle(getWidth(), getHeight());
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
		g2d.setColor(Color.BLACK);
		g2d.fill(fill);

//		g2d.setComposite(AlphaComposite.SrcOver.derive(0f));
//		g2d.setColor(Color.WHITE);
//		g2d.drawOval(450, 450, 900, 900);
//		entities.forEach(e -> {
//			e.draw(g);
//		});

		g2d.dispose();
	}

	public void setEntities(List<GraphicModel> entities) {
		this.entities = entities;
		repaint();
	}
}
