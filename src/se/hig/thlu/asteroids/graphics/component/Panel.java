package se.hig.thlu.asteroids.graphics.component;

public interface Panel extends  Component {

	void setLayout(LayoutManager layoutManager);

	void repaint();

	void add(Component component);

	Dimension getPreferredSize();

}
