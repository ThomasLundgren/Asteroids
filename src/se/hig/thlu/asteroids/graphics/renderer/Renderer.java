package se.hig.thlu.asteroids.graphics.renderer;

import se.hig.thlu.asteroids.graphics.shape.Line;
import se.hig.thlu.asteroids.graphics.shape.Shape;
import se.hig.thlu.asteroids.model.Point;

public interface Renderer {

	void drawLine(Line line);

	void drawImage(Image image, int x, int y, int width, int height);

	void drawOval(Point point, int width, int height);

	void fill(Shape shape);

	Color getColor();

	Font getFont();

	void setColor(Color color);

	void setFont(Font font);

	void setPaint(Paint paint);

}
