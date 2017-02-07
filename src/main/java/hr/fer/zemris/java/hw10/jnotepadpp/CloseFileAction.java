package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

/**
 * Holds action that is in charge for closing document.
 * 
 * @author petra
 *
 */
public class CloseFileAction extends LocalizableAction {

	// default serial version id
	private static final long serialVersionUID = 1L;
	// notepad
	private JNotepadPP notepad;
	// editors that notepad contains
	private List<Editor> editors = new ArrayList<>();
	// cardinal number of current tab
	private int currentTab;
	// component that holds all currently opened editors
	private JTabbedPane tabs;

	/**
	 * Sets action's name and mnemonic key, initializes variables.
	 * 
	 * @param key
	 *            String to be translated
	 * @param lp
	 *            Localization provider
	 * @param editors
	 *            Editors opened in current JNotepadPP
	 * @param currentTab
	 *            Currently opened tab
	 * @param tabs
	 *            Tabs of JNotepadPP.
	 */
	public CloseFileAction(String key, ILocalizationProvider lp,
			JNotepadPP notepad, List<Editor> editors, int currentTab,
			JTabbedPane tabs) {
		super(key, lp);
		this.notepad = notepad;
		this.editors = editors;
		this.currentTab = currentTab;
		this.tabs = tabs;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (editors.size() < 1)
			return;
		JTextArea editor = editors.get(currentTab).getEditor();
		JTextArea original = editors.get(currentTab).getOriginal();
		if (!editor.getText().equals(original.getText())) {
			int rez = JOptionPane.showConfirmDialog(notepad,
					"Zelite li spremiti dokument prije zatvaranja ?",
					"Upozorenje", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (rez == JOptionPane.YES_OPTION) {
				notepad.save();
			}
		}

		editors.remove(tabs.getSelectedIndex());
		tabs.remove(tabs.getSelectedIndex());
	}
}
