package org.converger.userinterface.utility;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the IESource interface. 
 * @author Gabriele Graffieti
 * @param <T> the type of the event notification
 * */
public class ESource<T> implements IESource<T> {

	private final Set<EObserver<? super T>> set = new HashSet<>();
	
	@Override
	public void addEObserver(final EObserver<? super T> obs) {
		this.set.add(obs);
	}

	@Override
	public void notifyEObservers(final T msg) {
		for (final EObserver<? super T> obs : this.set) {
			obs.update(this, msg);
		}
	}

}
