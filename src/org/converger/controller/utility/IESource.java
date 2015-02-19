package org.converger.controller.utility;

/**
 * An interface representing a single source of events in the observer design pattern.
 * @author Gabriele Graffieti
 * @param <T> the type of the event notification
 * */
public interface IESource<T> {
	/**
	 * Add an observer to the source.
	 * @param obs the EObserver object to add to the source's observers.
	 * .*/
	void addEObserver(EObserver<? super T> obs);
	
	/**
	 *	Send a message to all the observers with the given message.
	 * @param msg the message sent to observers
	 */
	void notifyEObservers(T msg);
}
