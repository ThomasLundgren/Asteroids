package se.hig.thlu.asteroids.gui.eventlistener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class AwtEventAdapter extends KeyEvent implements EventAdapter {

	public AwtEventAdapter(KeyEvent keyEvent) {
		super(keyEvent.getComponent(),
				keyEvent.getID(),
				keyEvent.getWhen(),
				keyEvent.getModifiers(),
				keyEvent.getKeyCode(),
				keyEvent.getKeyChar(),
				keyEvent.getKeyLocation());
	}

	public AwtEventAdapter(Component source, int id, long when, int modifiers, int keyCode, char keyChar, int keyLocation) {
		super(source, id, when, modifiers, keyCode, keyChar, keyLocation);
	}

	public AwtEventAdapter(Component source, int id, long when, int modifiers, int keyCode, char keyChar) {
		super(source, id, when, modifiers, keyCode, keyChar);
	}
}
