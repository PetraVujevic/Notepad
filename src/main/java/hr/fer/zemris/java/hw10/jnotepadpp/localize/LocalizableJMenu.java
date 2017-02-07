package hr.fer.zemris.java.hw10.jnotepadpp.localize;

import javax.swing.JMenu;

/**
 * Localized JMenu.
 * 
 * @author petra
 *
 */
public class LocalizableJMenu extends JMenu {

	// default serial version id
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Initializes localized JMenu.
	 * 
	 * @param key
	 *            Key for action.
	 * @param lp
	 *            Reference to localization provider.
	 */
	public LocalizableJMenu(String key, ILocalizationProvider lp) {
		String name = lp.getString(key);
		setText(name);
		setName(name);

		lp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				String name = lp.getString(key);
				setText(name);
				setName(name);
			}
		});
	}

}
