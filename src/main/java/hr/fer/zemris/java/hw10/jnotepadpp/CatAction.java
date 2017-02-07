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
 * Holds action that is in charge for copying and deleting selected text area.
 * 
 * @author petra
 *
 */
public class CatAction extends LocalizableAction {
	// default serial version id
	private static final long serialVersionUID = 1L;
	// editors that notepad contains
	private List<Editor> editors = new ArrayList<>();
	// cardinal number of current tab
	private int currentTab;
	// notepad
	JNotepadPP notepad;

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
	 * @param notepad
	 *            JNotepadPP
	 */
	public CatAction(String key, ILocalizationProvider lp,
			List<Editor> editors, int currentTab, JNotepadPP notepad) {
		super(key, lp);
		this.editors = editors;
		this.currentTab = currentTab;
		this.notepad = notepad;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = editors.get(currentTab).getEditor();
		Document doc = editor.getDocument();
		int pocetak = Math.min(editor.getCaret().getDot(), editor.getCaret()
				.getMark());
		int duljina = Math.max(editor.getCaret().getDot(), editor.getCaret()
				.getMark())
				- pocetak;
		try {
			notepad.paste = doc.getText(pocetak, duljina);
			doc.remove(pocetak, duljina);
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(editor,
					"It is not possible to get your content of the document.");
		}
	}
}
