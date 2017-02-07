package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizationProvider;

/**
 * Holds action that is in charge for dynamically language change.
 * 
 * @author petra
 *
 */
public class ChangeLangAction extends AbstractAction {
	// default serial version id
	private static final long serialVersionUID = 1L;
	// language to change to
	String jezik;

	/**
	 * Gives name to action and sets language.
	 * 
	 * @param jezik
	 *            Language to be change to
	 * @param name
	 *            Languages name.
	 */
	public ChangeLangAction(String jezik, String name) {
		this.jezik = jezik;
		putValue(Action.NAME, name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(jezik);
		LocalizationProvider.getInstance().fire();
	}

}
