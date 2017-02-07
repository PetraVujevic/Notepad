package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

/**
 * Holds action that is in charge for statistics display : number of characters,
 * non blank characters and number of lines.
 * 
 * @author petra
 *
 */
public class StatisticsAction extends LocalizableAction {

	// default serial version id
	private static final long serialVersionUID = 1L;
	// notepad
	private JNotepadPP notepad;
	// editors that notepad contains
	private List<Editor> editors = new ArrayList<>();
	// carinal number of current tab
	private int currentTab;

	/**
	 * Initializes variables.
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
	 */
	public StatisticsAction(String key, ILocalizationProvider lp,
			JNotepadPP notepad, List<Editor> editors, int currentTab) {
		super(key, lp);
		this.notepad = notepad;
		this.editors = editors;
		this.currentTab = currentTab;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int chars;
		int nonBlancChars;
		String text = editors.get(currentTab).getEditor().getText();
		chars = text.length();
		text = text.replaceAll("\\s+", "");
		text = text.replaceAll("\\r?\\n", "");
		nonBlancChars = text.length();

		JOptionPane.showMessageDialog(notepad, "Your document has " + chars
				+ " characters, " + nonBlancChars
				+ " non-blank characters and "
				+ editors.get(currentTab).getEditor().getLineCount()
				+ " lines.", "Poruka", JOptionPane.INFORMATION_MESSAGE);
	}

}
