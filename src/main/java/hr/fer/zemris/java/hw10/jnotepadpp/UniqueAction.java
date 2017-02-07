package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.Unique;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

/**
 * Holds action that is in charge for removing duplicated lines.
 * @author petra
 *
 */
public class UniqueAction extends LocalizableAction {
	// default serial version id
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor is delegated to parent class LocalizableAction.
	 * 
	 * @param key
	 *            String to translate
	 * @param lp
	 *            Localization provider
	 */
	public UniqueAction(String key, ILocalizationProvider lp) {
		super(key, lp);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JNotepadPP.sort(new Unique());
	}

}
