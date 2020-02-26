package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.graphics.polygon.*;

import java.awt.event.*;
import java.util.*;

public interface UI {

	void render(List<IPolygon> entities);

	void addKeyListener(KeyListener listener);

}
