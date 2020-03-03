package se.hig.thlu.asteroids.gui.eventlistener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AwtKeyboardAdapter implements EventListenerAdapter<AwtEventAdapter>, KeyListener {

	private final KeyAdapter keyAdapter;

	public AwtKeyboardAdapter(KeyAdapter keyAdapter) {
		this.keyAdapter = keyAdapter;
	}

	@Override
	public void pressed(AwtEventAdapter e) {
		keyAdapter.keyPressed(e);
	}

	@Override
	public void released(AwtEventAdapter e) {
		keyAdapter.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressed(new AwtEventAdapter(e));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		released(new AwtEventAdapter(e));
	}
}
