package hr.fer.zemris.java.hw10.jnotepadpp.localize;

/**
 * Listener model.
 * 
 * @author petra
 *
 */
public interface ILocalizationListener {
	/**
	 * Called by the Subject when the selected language changes.
	 */
	void localizationChanged();
}
