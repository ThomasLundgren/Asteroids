package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.gamestate.*;

import java.awt.event.*;

public class EntityController implements StateClient, KeyListener {

	public void update() {
		// TODO
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		State.PressedKey key;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				key = State.PressedKey.LEFT_ARROW;
				break;
			case KeyEvent.VK_UP:
				key = State.PressedKey.UP_ARROW;
				break;
			case KeyEvent.VK_RIGHT:
				key = State.PressedKey.RIGHT_ARROW;
				break;
			default:
				key = State.PressedKey.SPACE_BAR;
				break;
		}
		State.getState().handleKeyPressed(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
