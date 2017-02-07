package hr.fer.zemris.java.hw10.jnotepadpp.localize;

/**
 * Decorator for ILocalizationProvider.
 * 
 * @author petra
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	// Localization provider.
	private ILocalizationProvider lp;

	/**
	 * Adds localization listener to localization provider.
	 * 
	 * @param lp
	 *            Localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider lp) {
		this.lp = lp;
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				fire();
			}
		});
	}

	/**
	 * Registers an instance of anonymous ILocalizationListener on the decorated
	 * object.
	 */
	public void connect() {
		addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
			}
		});
	}

	/**
	 * This object is deregistered from decorated object.
	 */
	public void disconnect() {

		removeLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
			}
		});
	}

	@Override
	public String getString(String str) {
		return lp.getString(str);
	}
}
