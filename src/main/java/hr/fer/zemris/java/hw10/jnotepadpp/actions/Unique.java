package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import java.text.Collator;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Class for duplicate lines removal.
 * 
 * @author petra
 *
 */
public class Unique implements ISort {

	@Override
	public List<String> sortLines(List<String> lines, Collator collator) {
		lines = new ArrayList<>(new LinkedHashSet<>(lines));
		return lines;
	}
}
