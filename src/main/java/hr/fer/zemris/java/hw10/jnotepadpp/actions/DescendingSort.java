package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import java.text.Collator;
import java.util.Collections;
import java.util.List;

/**
 * Sorter for descending lines sorting.
 * 
 * @author petra
 *
 */
public class DescendingSort implements ISort {
	@Override
	public List<String> sortLines(List<String> lines, Collator collator) {
		Collections.sort(lines, collator.reversed());
		return lines;
	}
}
