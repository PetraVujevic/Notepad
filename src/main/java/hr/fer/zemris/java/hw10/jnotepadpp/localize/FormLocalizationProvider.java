package hr.fer.zemris.java.hw10.jnotepadpp.localize;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class derived from LocalizationProviderBridge; in its constructor it
 * registers itself as a WindowListener to its JFrame; when frame is opened, it
 * calls connect and when frame is closed, it calls disconnect.
 * 
 * @author petra
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * When frame is opened, it calls connect and when frame is closed, it calls
	 * disconnect.
	 * 
	 * @param lp
	 *            Localization provider
	 * @param jf
	 *            Frame
	 */
	public FormLocalizationProvider(ILocalizationProvider lp, JFrame jf) {
		super(lp);
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
}
