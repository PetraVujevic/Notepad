package hr.fer.zemris.java.hw10.jnotepadpp;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.*;
import hr.fer.zemris.java.hw10.jnotepadpp.localize.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Implementation of text editor, similar to Notepad++.
 * 
 * @author petra
 *
 */
@SuppressWarnings("serial")
public class JNotepadPP extends JFrame {
	// component that holds all currently opened editors
	private JTabbedPane tabs;
	// list of all currently opened editors
	private static List<Editor> editors = new ArrayList<>();
	// cardinal order of currently opened editor
	private static int currentTab;
	// string that is saved by "cat" or "copy" action, waiting to be pasted
	protected String paste;
	// number of characters in currently opened editor
	protected int length;
	// line of the caret
	protected int ln;
	// column of the caret
	protected int col;
	// number of characters in selected text area
	protected int selected;
	// label that shows current line, column, size and selected area size
	private JLabel lenLabel;
	// label that shows current time
	private JLabel timeLabel;
	// panel that holds both previous labels
	protected JPanel numbers;
	// indicates whether the application is terminated
	protected boolean stopTime = false;
	// current language
	static String jezik = null;
	// localization provider for this window
	private FormLocalizationProvider flp = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	/**
	 * Constructor calls components initialization.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int n = tabs.getComponentCount();
				for (int i = 0; i < n; i++) {
					close();
				}
				stopTime = true;
				dispose();
			}
		});
		setSize(800, 600);
		initGUI();
	}

	/**
	 * Initialization of the main frame.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		setLocation(50, 50);
		tabs = new JTabbedPane();
		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				currentTab = tabs.getSelectedIndex();
			}
		});
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
				length = editor.getDocument().getLength();
				int caretpos = editor.getCaretPosition();
				try {
					ln = editor.getLineOfOffset(caretpos);
					col = caretpos - editor.getLineStartOffset(ln);
					int pocetak = Math.min(editor.getCaret().getDot(), editor
							.getCaret().getMark());
					selected = Math.max(editor.getCaret().getDot(), editor
							.getCaret().getMark())
							- pocetak;
				} catch (BadLocationException e1) {
					JOptionPane.showMessageDialog(editor,
							"It is not possible to get current line and row.");
				}
				ln += 1;
				numbers.repaint();
			}
		});
		editors.add(new Editor(editor));
		tabs.addTab("New file", new ImageIcon("img/green.png"),
				new JScrollPane(editor), "New file");
		getContentPane().add(tabs, BorderLayout.CENTER);
		numbers = new JPanel(new GridLayout(1, 2));
		numbers.setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1,
				Color.gray));

		timeLabel = new Time();
		timeLabel.setText("Currently :");
		timeLabel.setForeground(Color.black);
		timeLabel.setOpaque(true);
		lenLabel = new Labela();
		lenLabel.setText("Currently :");
		lenLabel.setForeground(Color.black);
		lenLabel.setOpaque(true);
		numbers.add(lenLabel);
		numbers.add(timeLabel);
		getContentPane().add(numbers, BorderLayout.PAGE_END);
		createActions();
		createMenus();
		createToolbars();
	}

	/**
	 * Actions creator.
	 */
	private void createActions() {
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}

	/**
	 * Menus creator.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new LocalizableJMenu("file", flp);
		menuBar.add(fileMenu);
		fileMenu.add(new JMenuItem(new NewDocumentAction("new", flp,
				JNotepadPP.this, editors, currentTab, tabs)));
		fileMenu.add(new JMenuItem(new OpenDocumentAction("open", flp,
				JNotepadPP.this, editors, currentTab, tabs)));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(new SaveAsAction("saveAs", flp,
				JNotepadPP.this, editors, currentTab, tabs)));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(new ExitAction("exit", flp, JNotepadPP.this,
				tabs)));

		JMenu editMenu = new LocalizableJMenu("edit", flp);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(new DeleteSelectedPartAction("delete", flp,
				editors, currentTab)));
		editMenu.add(new JMenuItem(new ToggleCaseAction("toggle", flp)));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(new CatAction("cat", flp, editors,
				currentTab, JNotepadPP.this)));
		editMenu.add(new JMenuItem(new CopyAction("copy", flp, editors,
				currentTab, JNotepadPP.this)));
		editMenu.add(new JMenuItem(new PasteAction("paste", flp, editors,
				currentTab, JNotepadPP.this)));

		JMenu toolsMenu = new LocalizableJMenu("tools", flp);
		menuBar.add(toolsMenu);
		JMenu caseMenu = new LocalizableJMenu("changeCase", flp);
		caseMenu.add(new JMenuItem(new ToggleCaseAction("invert", flp)));
		caseMenu.add(new JMenuItem(new UpperCaseAction("toUpper", flp)));
		caseMenu.add(new JMenuItem(new LowerCaseAction("toLower", flp)));
		toolsMenu.add(caseMenu);

		JMenu sortMenu = new LocalizableJMenu("sort", flp);

		sortMenu.add(new JMenuItem(new AscendingAction("asc", flp)));
		sortMenu.add(new JMenuItem(new DescendingAction("desc", flp)));
		sortMenu.add(new JMenuItem(new UniqueAction("unique", flp)));
		menuBar.add(sortMenu);

		JMenu langMenu = new LocalizableJMenu("lang", flp);
		menuBar.add(langMenu);
		langMenu.add(new JMenuItem(new ChangeLangAction("hr", "Hrvatski")));
		langMenu.add(new JMenuItem(new ChangeLangAction("en", "English")));
		langMenu.add(new JMenuItem(new ChangeLangAction("de", "Deutsch")));

		setJMenuBar(menuBar);
	}

	/**
	 * Toolbars creator.
	 */
	private void createToolbars() {
		JToolBar toolbar = new JToolBar("Datoteka");
		toolbar.setFloatable(true);
		toolbar.add(new JButton(new NewDocumentAction("new", flp,
				JNotepadPP.this, editors, currentTab, tabs)));
		toolbar.addSeparator();
		toolbar.add(new JButton(new OpenDocumentAction("open", flp,
				JNotepadPP.this, editors, currentTab, tabs)));
		toolbar.addSeparator();
		toolbar.add(new JButton(saveDocumentAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(new SaveAsAction("saveAs", flp,
				JNotepadPP.this, editors, currentTab, tabs)));
		toolbar.addSeparator();
		toolbar.add(new JButton(new StatisticsAction("statistics", flp,
				JNotepadPP.this, editors, currentTab)));
		toolbar.addSeparator();
		toolbar.add(new JButton(new CloseFileAction("close", flp,
				JNotepadPP.this, editors, currentTab, tabs)));

		JToolBar toolbar2 = new JToolBar("Alati");
		toolbar2.add(new JButton(new ToggleCaseAction("toggle", flp)));
		toolbar2.addSeparator();
		toolbar2.add(new JButton(new DeleteSelectedPartAction("delete", flp,
				editors, currentTab)));
		toolbar2.addSeparator();
		toolbar2.add(new JButton(new CatAction("cat", flp, editors, currentTab,
				JNotepadPP.this)));
		toolbar2.addSeparator();
		toolbar2.add(new JButton(new CopyAction("copy", flp, editors,
				currentTab, JNotepadPP.this)));
		toolbar2.addSeparator();
		toolbar2.add(new JButton(new PasteAction("paste", flp, editors,
				currentTab, JNotepadPP.this)));

		JPanel toolbars = new JPanel(new GridLayout(2, 1));
		toolbars.add(toolbar);
		toolbars.add(toolbar2);
		getContentPane().add(toolbars, BorderLayout.PAGE_START);
	}

	/**
	 * Sorts selected lines. Type of sorting is conditioned by sorter given as
	 * parameter.
	 * 
	 * @param sorter
	 *            Determines the way of sorting that can be
	 *            ascending/descending/unique. Unique is actually not sorting,
	 *            it removes duplicated lines.
	 */
	public static void sort(ISort sorter) {
		Locale hrLocale = new Locale(jezik);
		Collator collator = Collator.getInstance(hrLocale);
		JTextArea editor = editors.get(currentTab).getEditor();
		try {
			int selectedBegin = Math.min(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int lineBegin = editor.getLineOfOffset(selectedBegin);
			int offsetBegin = editor.getLineStartOffset(lineBegin);

			int selectedEnd = Math.max(editor.getCaret().getDot(), editor
					.getCaret().getMark());
			int lineEnd = editor.getLineOfOffset(selectedEnd);
			int offsetEnd = editor.getLineEndOffset(lineEnd);
			String toSort = editor.getDocument().getText(offsetBegin,
					offsetEnd - offsetBegin);
			List<String> lines = Arrays.asList(toSort.split("\\n"));

			lines = sorter.sortLines(lines, collator);

			String toInsert = String.join("\n", lines) + "\n";
			editor.getDocument().remove(offsetBegin, offsetEnd - offsetBegin);
			editor.getDocument().insertString(offsetBegin, toInsert, null);
		} catch (BadLocationException e1) {
			JOptionPane.showMessageDialog(editor,
					"It is not possible to get your content of the document.");
		}
	}

	/**
	 * Extracts selected area.
	 * 
	 * @param changer
	 *            Determines the way selected area is going to be changed to :
	 *            upper case/ lower case/ inverted.
	 */
	public static void textoToChangeCase(IChangeCase changer) {
		JTextArea editor = editors.get(currentTab).getEditor();
		Document doc = editor.getDocument();
		int pocetak = Math.min(editor.getCaret().getDot(), editor.getCaret()
				.getMark());
		int duljina = Math.max(editor.getCaret().getDot(), editor.getCaret()
				.getMark())
				- pocetak;
		try {
			String text = doc.getText(pocetak, duljina);
			text = changeCase(text, changer);
			doc.remove(pocetak, duljina);
			doc.insertString(pocetak, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Changes string in a way that is determined by parameter changer.
	 * 
	 * @param text
	 *            Text to be changed
	 * @param changer
	 *            Upper case/Lower case/Toggle case
	 * @return Changed string.
	 */
	private static String changeCase(String text, IChangeCase changer) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			znakovi[i] = changer.changeCase(c);
		}
		return new String(znakovi);
	}

	/**
	 * Asks for permission is current file is about to overwrite existing file
	 * and acts depending on user's answer (overwrites or discards current
	 * file).
	 */
	protected void save() {
		Path openedFilePath = editors.get(currentTab).getPath();

		if (openedFilePath == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save document");
			if (fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						"Nista nije pohranjeno", "Poruka",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Path file = fc.getSelectedFile().toPath();
			if (Files.exists(file)) {
				int rez = JOptionPane
						.showConfirmDialog(
								JNotepadPP.this,
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
		}
		try {
			JTextArea editor = editors.get(currentTab).getEditor();
			Files.write(openedFilePath,
					editor.getText().getBytes(StandardCharsets.UTF_8));
			editors.get(currentTab).setOriginal(editor);
			String path = openedFilePath.toString();
			editors.get(currentTab).setPath(new File(path).toPath());
			int n = path.lastIndexOf(File.separatorChar);
			String title = path.substring(n + 1);
			String file = path.substring(0, n);
			tabs.setTitleAt(currentTab, file + " - " + title);
			tabs.setToolTipTextAt(currentTab, path);
			tabs.setIconAt(currentTab, new ImageIcon("img/green.png"));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					"Pogreska pri zapisivanju datoteke " + openedFilePath
							+ ": " + e1.getMessage() + ".", "Pogreska",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * Asks if user wants to save document before closing, saves document if
	 * requested and closes document.
	 */
	protected void close() {
		JTextArea editor = editors.get(currentTab).getEditor();
		JTextArea original = editors.get(currentTab).getOriginal();
		if (!editor.getText().equals(original.getText())) {
			int rez = JOptionPane.showConfirmDialog(JNotepadPP.this,
					"Zelite li spremiti prije zatvaranja ?", "Upozorenje",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (rez == JOptionPane.YES_OPTION) {
				save();
			}
		}
		editors.remove(currentTab);
		tabs.remove(currentTab);
	}

	/**
	 * Action in charge for saving document.
	 */
	private Action saveDocumentAction = new LocalizableAction("save", flp) {

		@Override
		public void actionPerformed(ActionEvent e) {
			save();
		}
	};
	
	/**
	 * Shows current line, column, text size and selected area size in the left
	 * bottom part of the main window.
	 * 
	 * @author petra
	 *
	 */
	private class Labela extends JLabel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponents(g);
			FontMetrics fm = g.getFontMetrics();
			int len = fm.stringWidth(length + "");
			Dimension size = getSize();
			setForeground(Color.black);
			String toDraw = "Length: " + length;
			toDraw += "        Ln: " + ln;
			toDraw += " Col: " + col;
			toDraw += " Sel: " + selected;
			g.drawString(toDraw, (int) (size.getHeight() / 2) - len / 2, 12);
		}
	}

	/**
	 * Creates one independent thread for time display and displays time in the
	 * right bottom part of the main window.
	 * 
	 * @author petra
	 *
	 */
	private class Time extends JLabel {
		public Time() {
			Thread t = new Thread(() -> {
				while (true && !stopTime) {
					SwingUtilities.invokeLater(() -> {
						repaint();
					});
					try {
						Thread.sleep(500);
					} catch (Exception ignorable) {

					}
				}
			});

			t.setDaemon(true);
			t.start();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponents(g);
			FontMetrics fm = g.getFontMetrics();
			int yDescriptionLen = fm.stringWidth(length + "");
			Dimension size = getSize();
			setForeground(Color.black);
			g.drawString(new Date().toString(), (int) (size.getHeight() / 2)
					- yDescriptionLen / 2, 12);
		}
	}

	/**
	 * Invokes window creation and sets language to the language given in the
	 * parameters or sets language to English if no parameters were given.
	 * 
	 * @param args
	 *            One optional parameter, language, that can be hr/en/de
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			jezik = "en";
		} else {
			jezik = args[0];
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				LocalizationProvider.getInstance().setLanguage(jezik);
				new JNotepadPP().setVisible(true);
			}
		});
	}
}
