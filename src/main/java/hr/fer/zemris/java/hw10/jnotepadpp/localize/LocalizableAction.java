package hr.fer.zemris.java.hw10.jnotepadpp.localize;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Localized action.
 * 
 * @author petra
 *
 */
public class LocalizableAction extends AbstractAction {
	// default serial version id
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes localized action.
	 * 
	 * @param key
	 *            Key for action.
	 * @param lp
	 *            Reference to localization provider.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		String name = lp.getString(key);
		putValue(NAME, name);
		String desc = lp.getString(key + "Desc");
		putValue(SHORT_DESCRIPTION, desc);
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				String name = lp.getString(key);
				putValue(NAME, name);
				String desc = lp.getString(key + "Desc");
				putValue(SHORT_DESCRIPTION, desc);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// to Override
	}

}
