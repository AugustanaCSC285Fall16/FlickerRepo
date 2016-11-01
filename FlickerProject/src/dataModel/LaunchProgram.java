package dataModel;

import java.io.IOException;

import javax.swing.JOptionPane;

import gui.SplashScreenGUI;

public class LaunchProgram {

	/**
	 * Starts the Program and splash screen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SplashScreenGUI gui = new SplashScreenGUI();
		} catch (IOException | InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Could not load data!");
		}
	}
}
