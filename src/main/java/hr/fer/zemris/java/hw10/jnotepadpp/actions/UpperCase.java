package hr.fer.zemris.java.hw10.jnotepadpp.actions;

/**
 * Upper case changer.
 * 
 * @author petra
 *
 */
public class UpperCase implements IChangeCase {

	@Override
	public char changeCase(char c) {
		return Character.toUpperCase(c);
	}

}
