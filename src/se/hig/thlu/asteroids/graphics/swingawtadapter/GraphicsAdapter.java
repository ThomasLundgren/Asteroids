package se.hig.thlu.asteroids.graphics.swingawtadapter;

import se.hig.thlu.asteroids.graphics.renderer.Color;
import se.hig.thlu.asteroids.graphics.renderer.Font;
import se.hig.thlu.asteroids.graphics.renderer.Image;
import se.hig.thlu.asteroids.graphics.renderer.Paint;
import se.hig.thlu.asteroids.graphics.renderer.*;
import se.hig.thlu.asteroids.graphics.shape.Shape;
import se.hig.thlu.asteroids.graphics.shape.*;
import se.hig.thlu.asteroids.model.Point;

import java.awt.Graphics2D;

public class GraphicsAdapter implements Renderer {

	private Graphics2D g2d;
	private Font font;
	private Color color;

	public GraphicsAdapter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void drawLine(Line line) {
		g2d.drawLine(
				(int) line.getStart().getX(),
				(int) line.getStart().getY(),
				(int) line.getEnd().getX(),
				(int) line.getEnd().getY());
	}

	@Override
	public void drawImage(Image image, int x, int y, int width, int height) {
		if (!(image instanceof AwtImageAdapter)) {
			throw new IllegalArgumentException("Image must be of type AwtImageAdapter");
		}
		AwtImageAdapter img = (AwtImageAdapter) image;
		g2d.drawImage(img.getInnerImage(), x, y, width, height, null);
	}

	@Override
	public void drawOval(Point point, int width, int height) {
		g2d.drawOval(
				(int) point.getX(),
				(int) point.getY(),
				width,
				height);
	}

	@Override
	public void fill(Shape shape) {
		// TODO
	}

	@Override
	public Color getColor() {
		return (AwtColorAdapter) g2d.getColor();
	}

	@Override
	public void setColor(Color color) {
		if (!(color instanceof java.awt.Color)) {
			throw new IllegalArgumentException("Color must be a an instance of java.awt.Color");
		}
		AwtColorAdapter c = (AwtColorAdapter) color;
		g2d.setColor(c);
	}

	@Override
	public Font getFont() {
		return new AwtFontAdapter(g2d.getFont());
	}

	@Override
	public void setFont(Font font) {
		if (!(font instanceof java.awt.Font)) {
			throw new IllegalArgumentException("Font must be a an instance of java.awt.Font");
		}
		java.awt.Font awtFont = (java.awt.Font) font;
		g2d.setFont(awtFont);
	}

	@Override
	public void setPaint(Paint paint) {
		// TODO
	}
}
