package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import java.text.Collator;
import java.util.Collections;
import java.util.List;

/**
 * Sorter for ascending lines sorting.
 * 
 * @author petra
 *
 */
public class AscendingSort implements ISort {
	@Override
	public List<String> sortLines(List<String> lines, Collator collator) {
		Collections.sort(lines, collator);
		return lines;
	}
}
