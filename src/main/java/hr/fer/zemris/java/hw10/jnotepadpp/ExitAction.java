package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

/**
 * Holds action that is in charge for terminating the application.
 * 
 * @author petra
 *
 */
public class ExitAction extends LocalizableAction {
	// default serial version id
	private static final long serialVersionUID = 1L;
	// notpad
	JNotepadPP notepad;
	// component that holds all currently opened editors
	JTabbedPane tabs;

	public ExitAction(String key, ILocalizationProvider lp, JNotepadPP notepad,
			JTabbedPane tabs) {
		super(key, lp);
		this.notepad = notepad;
		this.tabs = tabs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int n = tabs.getComponentCount();
		for (int i = 0; i < n; i++) {
			notepad.stopTime = true;
			notepad.dispose();
		}
	}
}
