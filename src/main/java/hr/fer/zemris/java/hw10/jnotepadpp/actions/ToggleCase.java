package hr.fer.zemris.java.hw10.jnotepadpp.actions;

/**
 * Toggle case changer.
 * 
 * @author petra
 *
 */
public class ToggleCase implements IChangeCase {

	@Override
	public char changeCase(char c) {
		if (Character.isLowerCase(c)) {
			return Character.toUpperCase(c);
		} else if (Character.isUpperCase(c)) {
			return Character.toLowerCase(c);
		} else
			return c;
	}

}
