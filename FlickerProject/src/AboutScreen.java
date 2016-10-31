import java.awt.FlowLayout;
import java.io.IOException;
import javax.swing.*;

public class AboutScreen {
	private JFrame frame;
	private JLabel title;
	private JLabel version;
	private JLabel body;
	private JLabel supervisor;
	private JLabel specialThanks;

	public AboutScreen() throws IOException {
		title = new JLabel("Title");
		version = new JLabel("Version 1.0. November 2, 2016. Augustana College.");
		body = new JLabel("Created by: Lauren Johnson, Andrew Bainter, Megan Janssen, and Tony Learmann");
		supervisor = new JLabel("Supervised by Forrest Stonedahl.");
		specialThanks = new JLabel("Acknowledgements: openCSV");

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
