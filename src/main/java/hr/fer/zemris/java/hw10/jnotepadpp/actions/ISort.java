package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import java.text.Collator;
import java.util.List;

/**
 * Interface for sorting lines.
 * 
 * @author petra
 *
 */
public interface ISort {
	/**
	 * 
	 * @param lines Lines to sort
	 * @param collator Performs locale-sensitive String comparison
	 * @return List of sorted lines
	 */
	public List<String> sortLines(List<String> lines, Collator collator);
}
