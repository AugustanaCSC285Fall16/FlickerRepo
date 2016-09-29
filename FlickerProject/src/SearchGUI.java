
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class SearchGUI implements ActionListener {
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.mm.dd");

	private JFrame frame;
	private JComboBox<String> name;
	private JFormattedTextField date;
	private JFormattedTextField otherDate;
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
	

	private JButton moreNames;
	private JButton moreDates;
	
	private JPanel innerNamePanel;
	private JPanel innerPlusPanel;
	private JPanel innerDatePanel;
	private JPanel innerDatePlusPanel;
	
	private int additionalNames;
	private int numDates;
	
	private JPanel centerPanel;
	private JPanel westPanel;
	
	HomeScreenGUI home;

	public SearchGUI(HomeScreenGUI home) {
		
		this.home=home;
		
		additionalNames = 0;
		numDates = 0;
		
		name = new JComboBox<>(new String[] { "", "White", "Black" });
		date = new JFormattedTextField(DATE_FORMAT);
		date.setColumns(7);
		date.setFocusLostBehavior(JFormattedTextField.PERSIST);
		otherDate = new JFormattedTextField(DATE_FORMAT);
		otherDate.setColumns(7);
		otherDate.setFocusLostBehavior(JFormattedTextField.PERSIST);
		type = new JComboBox<>(new String[] { "", "White", "Black" });
		culture = new JComboBox<>(new String[] { "", "White", "Black" });
		gender = new JComboBox<>(new String[] { "", "White", "Black" });
		occupation = new JComboBox<>(new String[] { "", "White", "Black" });
		location = new JComboBox<>(new String[] { "", "White", "Black" });
		reset = new JButton("Reset");
		search = new JButton("Search");
		nameLabel = new JLabel("Name:");
		dateLabel = new JLabel("Date (yyyy/mm/dd):");
		typeLabel = new JLabel("Type:");
		cultureLabel = new JLabel("Culture:");
		genderLabel = new JLabel("Gender:");
		occupationLabel = new JLabel("Occupation:");
		locationLabel = new JLabel("Location:");
		moreNames = new JButton("+");
		moreDates = new JButton("+");
		
		namePanel = new JPanel(new BorderLayout());
		innerNamePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		innerNamePanel.add(name);
		
		innerPlusPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		innerPlusPanel.add(moreNames);
		
		datePanel = new JPanel(new BorderLayout());
		innerDatePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		innerDatePanel.add(date);
		
		innerDatePlusPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		innerDatePlusPanel.add(moreDates);
		
		typePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		genderPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		culturePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		occupationPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		locationPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		namePanel.add(innerNamePanel, BorderLayout.CENTER);
		namePanel.add(innerPlusPanel, BorderLayout.EAST);
		datePanel.add(innerDatePanel, BorderLayout.CENTER);
		datePanel.add(innerDatePlusPanel, BorderLayout.EAST);
		typePanel.add(type);
		genderPanel.add(gender);
		culturePanel.add(culture);
		occupationPanel.add(occupation);
		locationPanel.add(location);
		
		try {
            MaskFormatter dateMask = new MaskFormatter("####/##/##");
            MaskFormatter dateMask2 = new MaskFormatter("####/##/##");
            dateMask.install(date);
            dateMask2.install(otherDate);
        } catch (ParseException ex) {
            Logger.getLogger(SearchGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
		frame.add(createSouthPanel(), BorderLayout.SOUTH);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);

		reset.addActionListener(this);
		search.addActionListener(this);
		moreNames.addActionListener(this);
		moreDates.addActionListener(this);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private JPanel createCenterPanel(int numNames) {
		centerPanel = new JPanel(new GridLayout(7+numNames+numDates, 1));
		centerPanel.add(namePanel);
		for(int i = 0 ; i < numNames; i++){
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			JComboBox<String> name = new JComboBox<>(new String[] { "", "White", "Black" });
			JPanel namePanel = new JPanel (new FlowLayout(FlowLayout.LEADING));
			namePanel.add(name);
			panel.add(namePanel);
			centerPanel.add(panel);
		}
		centerPanel.add(datePanel);
		if(numDates > 0){
			JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
			panel2.add(otherDate);
			centerPanel.add(panel2);
		}
		centerPanel.add(typePanel);
		centerPanel.add(culturePanel);
		centerPanel.add(genderPanel);
		centerPanel.add(occupationPanel);
		centerPanel.add(locationPanel);
		return centerPanel;
	}

	private JPanel createWestPanel(int numNames) {
		westPanel = new JPanel(new GridLayout(7+numNames+numDates, 1));
		westPanel.add(nameLabel);
		if(numNames>0){
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			JComboBox<String> toFrom = new JComboBox<>(new String[] { "", "To", "From" });
			JPanel toFromPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
			toFromPanel.add(toFrom);
			panel.add(toFromPanel);
			westPanel.add(panel);
		}
		for(int i = 1; i < numNames ; i++){
			JPanel tempPanel = new JPanel();
			westPanel.add(tempPanel);
		}
		westPanel.add(dateLabel);
		if(numDates > 0){
			JPanel tepPanel = new JPanel();
			westPanel.add(tepPanel);
		}
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

	void setDefault(){
		numDates=0;
		additionalNames=0;
		
		name.setSelectedIndex(0);
		type.setSelectedIndex(0);
		culture.setSelectedIndex(0);
		gender.setSelectedIndex(0);
		occupation.setSelectedIndex(0);
		location.setSelectedIndex(0);
		
		refreshPanel();
	}
	private void refreshPanel(){
		frame.remove(centerPanel);
		frame.remove(westPanel);
		
		frame.add(createCenterPanel(additionalNames), BorderLayout.CENTER);
		frame.add(createWestPanel(additionalNames), BorderLayout.WEST);
		
		frame.setSize(250,300+40*(additionalNames+numDates));
		makeVisible();
	}
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == search) {
			// search things
			frame.dispose();
			home.actionPerformed(event);

		} else if (event.getSource() == reset) {
			// reset fields
			setDefault();
		} else if (event.getSource()== moreNames){
			additionalNames++;
			refreshPanel();
		} else if(event.getSource() == moreDates){
			numDates = 1;
			refreshPanel();
		}
	}

}
