
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SearchGUI implements ActionListener {

	private JFrame frame;
	private JTextField name;
	private JTextField date;
	private JTextField type;
	private JTextField culture;
	private JTextField gender;
	private JTextField occupation;
	private JTextField location;
	private JButton reset;
	private JButton search;
	private boolean inUse;
	private JLabel nameLabel;
	private JLabel dateLabel;
	private JLabel typeLabel;
	private JLabel cultureLabel;
	private JLabel genderLabel;
	private JLabel occupationLabel;
	private JLabel locationLabel;

	public SearchGUI() {
		name = new JTextField(15);
		date = new JTextField(15);
		type = new JTextField(15);
		culture = new JTextField(15);
		gender = new JTextField(15);
		occupation = new JTextField(15);
		location = new JTextField(15);
		reset = new JButton("Reset");
		search = new JButton("Search");
		inUse = true;
		nameLabel = new JLabel("Name:");
		dateLabel = new JLabel("Date:");
		typeLabel = new JLabel("Type:");
		cultureLabel = new JLabel("Culture:");
		genderLabel = new JLabel("Gender:");
		occupationLabel = new JLabel("Occupation:");
		locationLabel = new JLabel("Location:");

		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(), BorderLayout.WEST);

		reset.addActionListener(this);
		search.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private JPanel createCenterPanel() {
		JPanel centerPanel = new JPanel(new GridLayout(7, 1));
		centerPanel.add(name);
		centerPanel.add(date);
		centerPanel.add(type);
		centerPanel.add(culture);
		centerPanel.add(gender);
		centerPanel.add(occupation);
		centerPanel.add(location);
		return centerPanel;
	}

	private JPanel createWestPanel() {
		JPanel westPanel = new JPanel(new GridLayout(7, 1));
		westPanel.add(nameLabel);
		westPanel.add(dateLabel);
		westPanel.add(typeLabel);
		westPanel.add(cultureLabel);
		westPanel.add(genderLabel);
		westPanel.add(occupationLabel);
		westPanel.add(locationLabel);
		return westPanel;
	}

	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(reset);
		southPanel.add(search);
		return southPanel;
	}

	public boolean inUse() {
		return inUse;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == search) {
			// search things
			inUse = false;
			frame.dispose();

		} else if (event.getSource() == reset) {
			// reset fields
			name.setText("");
			date.setText("");
			type.setText("");
			culture.setText("");
			gender.setText("");
			occupation.setText("");
			location.setText("");
		}
	}

}
