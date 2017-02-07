package hr.fer.zemris.java.hw10.jnotepadpp.localize;

/**
 * Each ILocalizationProvider will be automatically the Subject that will notify
 * all registered listeners when a selected language has changed.
 * 
 * @author petra
 *
 */
public interface ILocalizationProvider {
	/**
	 * Registration of listeners.
	 * 
	 * @param listener
	 *            Listener to be registered.
	 */
	public void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Deregistration of listeners.
	 * 
	 * @param listener
	 *            Listener to be deregistered.
	 */
	public void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Uses loaded resource bundle to translate the requested key.
	 * 
	 * @param str
	 *            String to translate
	 * @return Translated string.
	 */
	public String getString(String str);
}
