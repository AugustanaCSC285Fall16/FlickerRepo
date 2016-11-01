package gui;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class AboutScreenGUI {
	private JFrame frame;
	private JLabel title;
	private JLabel version;
	private JLabel body;
	private JLabel supervisor;
	private JLabel specialThanks;

	/**
	 * Creates an about screen!
	 * 
	 * @throws IOException
	 */
	public AboutScreenGUI() throws IOException {
		title = new JLabel("World Connections");
		version = new JLabel("Version 1.0. November 2, 2016. Augustana College.");
		body = new JLabel("Created by: Lauren Johnson, Andrew Bainter, Megan Janssen, and Tony Learmann");
		supervisor = new JLabel("Supervised by Forrest Stonedahl.");
		specialThanks = new JLabel("Acknowledgements: openCSV");

		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(500, 100);
		frame.setTitle("About");
		frame.setLayout(new FlowLayout());
		frame.add(title);
		frame.add(version);
		frame.add(body);
		frame.add(supervisor);
		frame.add(specialThanks);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

}
