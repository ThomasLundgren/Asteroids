package se.hig.thlu.asteroids.gamestate;

public abstract class State {

	private static State currentState;

	public void reset() {
//		setState(new PlayingState());
	}

	protected void setState(State newState) {
		currentState = newState;
	}



}
