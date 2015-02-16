package org.converger.userinterface.utility;

/**
 * A representation of an event observer in the observer design pattern.
 * @author Gabriele Graffieti
 * @param <T> the type of the event notification 
 */
public interface EObserver<T> {
	/**
	 * Update the observer with a message from the source.
	 * @param s the source which send the event message
	 * @param message the event message from the source object
 	 */
	void update(ESource<? extends T> s, T message);
}
