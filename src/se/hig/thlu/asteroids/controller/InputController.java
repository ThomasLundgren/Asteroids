package se.hig.thlu.asteroids.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public final class InputController extends KeyAdapter {

	private final GameController gameController;

	private InputController(GameController gameController) {
		this.gameController = gameController;
	}

	public static InputController createInputController(GameController gameController) {
		return new InputController(gameController);
	}

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
			case KeyEvent.VK_SPACE:
				key = PressedKey.SPACE_BAR;
				break;
			default:
				key = PressedKey.UNDEFINED;
				break;
		}
		gameController.handleKeyPressed(key);
	}

	@Override
	public void keyReleased(KeyEvent e) {
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
		gameController.handleKeyReleased(key);
	}
}
