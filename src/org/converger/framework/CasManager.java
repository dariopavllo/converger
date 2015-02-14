package org.converger.framework;

import org.converger.framework.core.CasFrameworkImpl;

/**
 * This class manages the framework instances. The Converger framework
 * can be instantiated multiple times to permit its use in a multithreaded environment.
 * @author Dario Pavllo
 */
public final class CasManager {

	private static final CasManager SINGLETON = new CasManager();
	
	private CasManager() {
	}
	
	/**
	 * Returns the unique instance of the framework manager.
	 * @return a CasManager instance
	 */
	public static CasManager getSingleton() {
		return CasManager.SINGLETON;
	}
	
	/**
	 * Creates and returns a framework instance.
	 * @return a Converger framework object
	 */
	public CasFramework createFramework() {
		return new CasFrameworkImpl();
	}

}
