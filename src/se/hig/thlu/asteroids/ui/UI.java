package se.hig.thlu.asteroids.ui;

import se.hig.thlu.asteroids.graphics.model.*;

import java.awt.event.*;
import java.util.*;

public interface UI {

	void render(List<GraphicModel> entities);

	void addKeyListener(KeyListener listener);

}
