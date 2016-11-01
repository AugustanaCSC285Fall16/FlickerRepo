import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

public class SplashScreenGUI {
	
	public static void main(String[] args) {
		try {
			SplashScreenGUI gui = new SplashScreenGUI();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new splash screen GUI
	 * 
	 * @throws MalformedURLException
	 */
	
	public SplashScreenGUI() throws MalformedURLException {
	JWindow window = new JWindow();
	window.getContentPane().add(
	    new JLabel("", new ImageIcon("images/splashscreen.jpg"), SwingConstants.CENTER));
	window.setBounds(500, 200, 750, 700);
	window.setVisible(true);
	try {
	    Thread.sleep(1000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	LoginGUI gui = new LoginGUI(false);
	window.setVisible(false);
	window.dispose();
	}
}
