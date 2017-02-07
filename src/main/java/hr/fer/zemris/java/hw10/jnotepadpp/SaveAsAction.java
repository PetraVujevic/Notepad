package hr.fer.zemris.java.hw10.jnotepadpp;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw10.jnotepadpp.localize.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.LocalizableAction;

/**
 * Holds action that is in charge for saving the document under the wanted name.
 * 
 * @author petra
 *
 */
public class SaveAsAction extends LocalizableAction {

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
	 * Sets action's name, initializes variables.
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
	public SaveAsAction(String key, ILocalizationProvider lp,
			JNotepadPP notepad, List<Editor> editors, int currentTab,
			JTabbedPane tabs) {
		super(key, lp);
		this.notepad = notepad;
		this.editors = editors;
		this.currentTab = currentTab;
		this.tabs = tabs;
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control shift S"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Path openedFilePath = editors.get(currentTab).getPath();

		JFileChooser fc = new JFileChooser();
		if (openedFilePath == null) {
			fc.setSelectedFile(new File("untitled"));
		} else {
			fc.setSelectedFile(openedFilePath.toFile());
		}
		fc.setDialogTitle("Save document");
		if (fc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(notepad, "Nista nije pohranjeno",
					"Poruka", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Path file = fc.getSelectedFile().toPath();
		if (Files.exists(file) && (!file.equals(openedFilePath))) {
			int rez = JOptionPane
					.showConfirmDialog(
							notepad,
							"Odabrana datoteka ("
									+ file
									+ ") vec postoji. Jeste li SIGURNI da ju zelite pregaziti ?",
							"Upozorenje", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
			if (rez != JOptionPane.YES_OPTION) {
				return;
			}
		}
		openedFilePath = file;

		try {
			JTextArea editor = editors.get(currentTab).getEditor();
			Files.write(openedFilePath,
					editor.getText().getBytes(StandardCharsets.UTF_8));
			String path = openedFilePath.toString();
			int n = path.lastIndexOf(File.separatorChar);
			String title = path.substring(n + 1);
			String filePath = path.substring(0, n);
			tabs.setTitleAt(currentTab, filePath + " - " + title);
			tabs.setToolTipTextAt(currentTab, path);
			tabs.setIconAt(currentTab, new ImageIcon("img/green.png"));
			// poruka da je uspjesno spremljena datoteka
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(notepad,
					"Pogreska pri zapisivanju datoteke " + openedFilePath
							+ ": " + e1.getMessage() + ".", "Pogreska",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}
