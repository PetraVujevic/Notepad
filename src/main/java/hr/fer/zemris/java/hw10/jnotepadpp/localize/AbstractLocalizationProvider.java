package hr.fer.zemris.java.hw10.jnotepadpp.localize;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements ILocalizationProvider interface and adds it the ability to
 * register, de-register and inform (fire() method) listeners.
 * 
 * @author petra
 *
 */
public abstract class AbstractLocalizationProvider implements
		ILocalizationProvider {
	// Listeners
	private List<ILocalizationListener> listeners;

	/**
	 * Initializes list of listeners.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<ILocalizationListener>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Informs listeners aboutchange.
	 */
	public void fire() {
		for (ILocalizationListener l : listeners) {
			l.localizationChanged();
		}
	}
}
