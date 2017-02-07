package hr.fer.zemris.java.hw10.jnotepadpp.actions;

/**
 * Lower case changer.
 * 
 * @author petra
 *
 */
public class LowerCase implements IChangeCase {

	@Override
	public char changeCase(char c) {
		return Character.toLowerCase(c);
	}

}
