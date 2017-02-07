package hr.fer.zemris.java.hw10.jnotepadpp.localize;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Singleton, main role of this class is to get instance of localization
 * provider.
 * 
 * @author petra
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	// language
	private static String language;
	// bundle
	private static ResourceBundle bundle;
	// instance of localization provider
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Sets language to English.
	 */
	public LocalizationProvider() {
		language = "en";
	}

	/**
	 * Loads the resource bundle for this language and stores reference to it.
	 * 
	 * @return Instance of localization provider.
	 */
	public static LocalizationProvider getInstance() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw10.jnotepadpp.prijevodi", locale);
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Sets language to language given in parameters.
	 * 
	 * @param lang
	 *            New language
	 */
	public void setLanguage(String lang) {
		language = lang;
	}
}
