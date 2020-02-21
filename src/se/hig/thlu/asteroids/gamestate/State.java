package se.hig.thlu.asteroids.gamestate;

public abstract class State {

	private static State currentState;
	protected static long totalGameTime;

	public static void reset(StateClient client) {
//		setState(new LevelOneState(client));
	}

	protected static void setState(State newState) { currentState = newState; }

	public static State getState() { return currentState; }

	public abstract void update(double delta);
}
