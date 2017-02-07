package hr.fer.zemris.java.hw10.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Representation of single document, with it's text area and path.
 * 
 * @author petra
 *
 */
public class Editor {
	// path to document
	private Path path;
	// text area for this document
	private JTextArea editor;
	// document before opening with text editor
	private JTextArea original;

	/**
	 * Constructor takes text area, saves original document and sets path to
	 * null.
	 * 
	 * @param editor
	 *            text area current document is placed in.
	 */
	public Editor(JTextArea editor) {
		super();
		this.editor = editor;
		this.path = null;
		this.original = new JTextArea(editor.getText());
	}

	/**
	 * Constructor takes text area, saves original document and sets path to the
	 * path of the opened document
	 * 
	 * @param path
	 *            Path to document.
	 * @param editor
	 *            Text area for document.
	 */
	public Editor(Path path, JTextArea editor) {
		super();
		this.path = path;
		this.editor = editor;
		this.original = new JTextArea(editor.getText());
	}

	/**
	 * Path getter
	 * 
	 * @return Path of the document.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Text area getter.
	 * 
	 * @return Text area for the document.
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * Original text area getter.
	 * 
	 * @return Original State of the document at the moment it was opened.
	 */
	public JTextArea getOriginal() {
		return original;
	}

	/**
	 * Sets original text area.
	 * 
	 * @param current
	 *            Text area of the document.
	 */
	public void setOriginal(JTextArea current) {
		this.original = current;
	}
	
	/**
	 * Sets path of the text area.
	 * 
	 * @param path
	 *            Path of the document.
	 */
	public void setPath(Path path) {
		this.path = path;
	}

}
