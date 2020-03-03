package se.hig.thlu.asteroids.gamestate;

import se.hig.thlu.asteroids.gui.GUI;

public interface State {

	void update();

	void render(GUI GUI);

}
