package gui;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class SplashScreenGUI {

	public static void main(String[] args) {
		try {
			SplashScreenGUI gui = new SplashScreenGUI();
		} catch (IOException | InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Could Not Run Splash Screen");
		}
	}

	/**
	 * Creates a new splash screen GUI
	 * 
	 * @throws InterruptedException
	 * @throws MalformedURLException
	 */
	public SplashScreenGUI() throws IOException, InterruptedException {
		JWindow window = new JWindow();
		window.getContentPane().add(new JLabel("", new ImageIcon("images/splashscreen.jpg"), SwingConstants.CENTER));
		window.setBounds(500, 200, 750, 700);
		window.setVisible(true);
		Thread.sleep(1000);
		LoginGUI gui = new LoginGUI(false);
		window.setVisible(false);
		window.dispose();
	}
}
