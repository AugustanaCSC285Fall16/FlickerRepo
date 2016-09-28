
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SearchGUI implements ActionListener {

	private JFrame frame;
	private JComboBox<String> name;
	private JComboBox<String> date;
	private JComboBox<String> type;
	private JComboBox<String> culture;
	private JComboBox<String> gender;
	private JComboBox<String> occupation;
	private JComboBox<String> location;
	private JButton reset;
	JButton search;
	
	private JLabel nameLabel;
	private JLabel dateLabel;
	private JLabel typeLabel;
	private JLabel cultureLabel;
	private JLabel genderLabel;
	private JLabel occupationLabel;
	private JLabel locationLabel;
	
	private JPanel namePanel;
	private JPanel datePanel;
	private JPanel typePanel;
	private JPanel culturePanel;
	private JPanel genderPanel;
	private JPanel occupationPanel;
	private JPanel locationPanel;
	

	private JButton namePlus;
	private JButton datePlus;
	
	private JPanel innerNamePanel;
	private JPanel innerPlusPanel;
	private JPanel innerDatePanel;
	private JPanel innerDatePlusPanel;
	
	private int additionalNames;
	private JPanel centerPanel;
	private JPanel westPanel;
	
	HomeScreenGUI home;

	public SearchGUI(HomeScreenGUI home) {
		
		this.home=home;
		
		additionalNames = 0;
		
		name = new JComboBox<>(new String[] { "", "White", "Black" });
		date = new JComboBox<>(new String[] { "", "White", "Black" });
		type = new JComboBox<>(new String[] { "", "White", "Black" });
		culture = new JComboBox<>(new String[] { "", "White", "Black" });
		gender = new JComboBox<>(new String[] { "", "White", "Black" });
		occupation = new JComboBox<>(new String[] { "", "White", "Black" });
		location = new JComboBox<>(new String[] { "", "White", "Black" });
		reset = new JButton("Reset");
		search = new JButton("Search");
		nameLabel = new JLabel("Name:");
		dateLabel = new JLabel("Date:");
		typeLabel = new JLabel("Type:");
		cultureLabel = new JLabel("Culture:");
		genderLabel = new JLabel("Gender:");
		occupationLabel = new JLabel("Occupation:");
		locationLabel = new JLabel("Location:");
		namePlus = new JButton("+");
		datePlus = new JButton("+");
		
		namePanel = new JPanel(new BorderLayout());
		innerNamePanel = new JPanel(new FlowLayout());
		innerNamePanel.add(name);
		
		innerPlusPanel = new JPanel(new FlowLayout());
		innerPlusPanel.add(namePlus);
		
		datePanel = new JPanel(new BorderLayout());
		innerDatePanel = new JPanel(new FlowLayout());
		innerDatePanel.add(date);
		
		innerDatePlusPanel = new JPanel(new FlowLayout());
		innerDatePlusPanel.add(datePlus);
		
		typePanel = new JPanel(new FlowLayout());
		genderPanel = new JPanel(new FlowLayout());
		culturePanel = new JPanel(new FlowLayout());
		occupationPanel = new JPanel(new FlowLayout());
		locationPanel = new JPanel(new FlowLayout());
		
		namePanel.add(innerNamePanel, BorderLayout.CENTER);
		namePanel.add(innerPlusPanel, BorderLayout.EAST);
		datePanel.add(innerDatePanel, BorderLayout.CENTER);
		datePanel.add(innerDatePlusPanel, BorderLayout.EAST);
		typePanel.add(type);
		genderPanel.add(gender);
		culturePanel.add(culture);
		occupationPanel.add(occupation);
		locationPanel.add(location);

		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);

		reset.addActionListener(this);
		search.addActionListener(this);
		namePlus.addActionListener(this);
		datePlus.addActionListener(this);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private JPanel createCenterPanel(int numNames) {
		centerPanel = new JPanel(new GridLayout(7+numNames, 1));
		centerPanel.add(namePanel);
		for(int i = 0 ; i < numNames; i++){
			JPanel panel = new JPanel(new FlowLayout());
			JComboBox<String> name = new JComboBox<>(new String[] { "", "White", "Black" });
			JPanel namePanel = new JPanel (new FlowLayout());
			namePanel.add(name);
			panel.add(namePanel);
			centerPanel.add(panel);
		}
		centerPanel.add(datePanel);
		centerPanel.add(typePanel);
		centerPanel.add(culturePanel);
		centerPanel.add(genderPanel);
		centerPanel.add(occupationPanel);
		centerPanel.add(locationPanel);
		return centerPanel;
	}

	private JPanel createWestPanel(int numNames) {
		westPanel = new JPanel(new GridLayout(7+numNames, 1));
		westPanel.add(nameLabel);
		for(int i = 0 ; i < numNames; i++){
			JPanel panel = new JPanel(new FlowLayout());
			JComboBox<String> toFrom = new JComboBox<>(new String[] { "", "To", "From" });
			JPanel toFromPanel = new JPanel(new FlowLayout());
			toFromPanel.add(toFrom);
			panel.add(toFromPanel);
			westPanel.add(panel);
		}
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
	

	void makeVisible(){
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == search) {
			// search things
			frame.dispose();
			home.actionPerformed(event);

		} else if (event.getSource() == reset) {
			// reset fields
			name.setSelectedIndex(0);
			date.setSelectedIndex(0);
			type.setSelectedIndex(0);
			culture.setSelectedIndex(0);
			gender.setSelectedIndex(0);
			occupation.setSelectedIndex(0);
			location.setSelectedIndex(0);
		
			additionalNames=0;
			
			frame.remove(centerPanel);
			frame.remove(westPanel);
			
			frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
			frame.add(createWestPanel(additionalNames), BorderLayout.WEST);
			
			frame.setSize(200,300+40*additionalNames);
			
			makeVisible();
		} else if (event.getSource()== namePlus){
			additionalNames++;
			frame.remove(centerPanel);
			frame.remove(westPanel);
			frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
			frame.add(createWestPanel(additionalNames), BorderLayout.WEST);
			
			frame.setSize(200,300+40*additionalNames);
			
			makeVisible();
		} else if(event.getSource() == datePlus){
			
		}
	}

}
