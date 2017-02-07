package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

public class DeleteSelectedPartAction extends LocalizableAction {
	// default serial version id
	private static final long serialVersionUID = 1L;
	// editors that notepad contains
	private List<Editor> editors = new ArrayList<>();
	// cardinal number of current tab
	private int currentTab;

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
	 */
	public DeleteSelectedPartAction(String key, ILocalizationProvider lp,
			List<Editor> editors, int currentTab) {
		super(key, lp);
		this.editors = editors;
		this.currentTab = currentTab;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control d"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
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
			doc.remove(pocetak, duljina);
		} catch (BadLocationException ignorable) {
		}
	}
}
