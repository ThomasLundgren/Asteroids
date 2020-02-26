package se.hig.thlu.asteroids.controller;

import se.hig.thlu.asteroids.gamestate.*;

import java.awt.event.*;

public class InputController implements StateClient, KeyListener {

	public enum PressedKey {
		LEFT_ARROW(KeyEvent.VK_LEFT),
		UP_ARROW(KeyEvent.VK_UP),
		RIGHT_ARROW(KeyEvent.VK_DOWN),
		SPACE_BAR(KeyEvent.VK_SPACE),
		UNDEFINED(1337);

		private int vkKey;

		PressedKey(int vkKey) {
			this.vkKey = vkKey;
		}

		public int getVkKey() {
			return vkKey;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO: Make key bindings configurable
		PressedKey key;
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				key = PressedKey.LEFT_ARROW;
				break;
			case KeyEvent.VK_UP:
				key = PressedKey.UP_ARROW;
				break;
			case KeyEvent.VK_RIGHT:
				key = PressedKey.RIGHT_ARROW;
				break;
			default:
				key = PressedKey.UNDEFINED;
				break;
		}
		PlayerShipController.getInstance().handleKeyPressed(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
