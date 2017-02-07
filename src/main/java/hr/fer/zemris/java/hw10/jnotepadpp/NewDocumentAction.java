package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

/**
 * Holds action that is in charge for opening new blank document.
 * 
 * @author petra
 *
 */
public class NewDocumentAction extends LocalizableAction {

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
	 *            Reference to String to be translated
	 * @param lp
	 *            Reference to localization provider
	 * @param editors
	 *            Reference to editors opened in current JNotepadPP
	 * @param currentTab
	 *            Index of currently opened tab
	 * @param notepad
	 *            Reference to JNotepadPP
	 * @param tabs
	 *            Reference to tabs in notepad.
	 */
	public NewDocumentAction(String key, ILocalizationProvider lp,
			JNotepadPP notepad, List<Editor> editors, int currentTab,
			JTabbedPane tabs) {
		super(key, lp);
		this.notepad = notepad;
		this.editors = editors;
		this.currentTab = currentTab;
		this.tabs = tabs;
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = new JTextArea();
		editor.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				tabs.setIconAt(currentTab, new ImageIcon("img/red.png"));
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				tabs.setIconAt(currentTab, new ImageIcon("img/red.png"));
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				tabs.setIconAt(currentTab, new ImageIcon("img/red.png"));
			}
		});
		editor.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				notepad.length = editor.getDocument().getLength();
				int caretpos = editor.getCaretPosition();
				try {
					notepad.ln = editor.getLineOfOffset(caretpos);
					notepad.col = caretpos
							- editor.getLineStartOffset(notepad.ln);
					int pocetak = Math.min(editor.getCaret().getDot(), editor
							.getCaret().getMark());
					notepad.selected = Math.max(editor.getCaret().getDot(),
							editor.getCaret().getMark()) - pocetak;
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(editor,
							"It is not possible to get current line and row.");
				}
				notepad.ln += 1;
				notepad.numbers.repaint();
			}
		});
		editors.add(new Editor(editor));
		tabs.addTab("New file", new ImageIcon("img/green.png"),
				new JScrollPane(editor), "New file");
		tabs.setSelectedIndex(editors.size() - 1);
	}
}
