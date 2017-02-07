package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

/**
 * 
 * Holds action that is in charge for pasting selected text area.
 *
 * @author petra
 *
 */
public class PasteAction extends LocalizableAction {

	// default serial version id
	private static final long serialVersionUID = 1L;
	// editors
	private List<Editor> editors = new ArrayList<>();
	// current tab
	private int currentTab;
	// notepad
	private JNotepadPP notepad;

	/**
	 * Sets action's name and mnemonic key, initializes variables.
	 * 
	 * @param key
	 *            Reference to String to be translated
	 * @param lp
	 *            Reference to localization provider
	 * @param editors
	 *            Reference to editors opened in current JNotepadPP
	 * @param currentTab
	 *            Index of currently opened tab
	 * @param notepad
	 *            JNotepadPP
	 */
	public PasteAction(String key, ILocalizationProvider lp,
			List<Editor> editors, int currentTab, JNotepadPP notepad) {
		super(key, lp);
		this.editors = editors;
		this.currentTab = currentTab;
		this.notepad = notepad;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = editors.get(currentTab).getEditor();
		Document doc = editor.getDocument();
		int pocetak = Math.min(editor.getCaret().getDot(), editor.getCaret()
				.getMark());
		try {
			doc.insertString(pocetak, notepad.paste, null);
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(editor,
					"It is not possible to get your content of the document.");
		}
	}
}
