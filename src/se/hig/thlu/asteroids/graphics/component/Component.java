package se.hig.thlu.asteroids.graphics.component;

import se.hig.thlu.asteroids.graphics.renderer.*;

public interface Component {

	void setOpaque(boolean isOpaque);

	void paintComponent(Renderer r);

	Dimension getSize();
}
