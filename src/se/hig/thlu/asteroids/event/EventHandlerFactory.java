package se.hig.thlu.asteroids.event;

import se.hig.thlu.asteroids.event.create.CreateEventHandler;
import se.hig.thlu.asteroids.event.entity.EntityEventHandler;
import se.hig.thlu.asteroids.event.gamestate.GameStateEventHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public enum EventHandlerFactory {
	;

	private static Map<Class<? extends EventHandler<? extends Event>>, EventHandler<?>> eventHandlers;

//	static {
//		eventHandlers.put(EntityEventHandler.class, new EntityEventHandler());
//		eventHandlers.put(CreateEventHandler.class, new CreateEventHandler());
//		eventHandlers.put(GameStateEventHandler.class, new GameStateEventHandler());
//	}

	@SuppressWarnings("unchecked")
	public static <T extends EventHandler<S>, S extends Event> T getEventHandler(Class<?
			extends EventHandler<? extends Event>> handlerClass) {
		if (eventHandlers == null) {
			eventHandlers = new LinkedHashMap<>(10);
			eventHandlers.put(EntityEventHandler.class, new EntityEventHandler());
			eventHandlers.put(CreateEventHandler.class, new CreateEventHandler());
			eventHandlers.put(GameStateEventHandler.class, new GameStateEventHandler());
		}
		return  (T) eventHandlers.get(handlerClass);
	}

}
