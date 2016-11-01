package dataModel;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JOptionPane;

import gui.SplashScreenGUI;

public class LaunchProgram {

	private static DataStorage storage;

	/**
	 * Starts the Program and spash screen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			storage = DataStorage.getMainDataStorage();
			SplashScreenGUI gui = new SplashScreenGUI();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not load data!");
		}
	}
}
