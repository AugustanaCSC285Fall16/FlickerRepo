
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddArtist implements ActionListener {

	private JFrame frame;
	private JComboBox<String> name;
	private JComboBox<String> culture;
	private JComboBox<String> gender;
	private JComboBox<String> occupation;
	private JTextField notes;
	private JPanel namePanel;
	private JPanel culturePanel;
	private JPanel genderPanel;
	private JPanel occupationPanel;
	private JPanel notesPanel;

	private JButton add;
	private JButton cancel;

	public AddArtist() {
		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		
		name = new JComboBox<>(new String[] {"","White", "Black"});
		culture = new JComboBox<>(new String[] {"","White", "Black"});
		gender = new JComboBox<>(new String[] {"","White", "Black"});
		occupation = new JComboBox<>(new String[] {"","White", "Black"});
		notes = new JTextField(10);
		add = new JButton("Add");
		cancel = new JButton("Cancel");
		
		JLabel label1 = new JLabel("Artist Name:");
		JLabel label2 = new JLabel("Cultural ID:");
		JLabel label3 = new JLabel("Gender:");
		JLabel label4 = new JLabel("Occupation:");
		JLabel label5 = new JLabel("Biography:");
		
		namePanel = new JPanel(new FlowLayout());
		culturePanel = new JPanel(new FlowLayout());
		genderPanel = new JPanel(new FlowLayout());
		occupationPanel = new JPanel(new FlowLayout());
		notesPanel = new JPanel(new FlowLayout());
		
		namePanel.add(name);
		culturePanel.add(culture);
		genderPanel.add(gender);
		occupationPanel.add(occupation);
		notesPanel.add(notes);
		
		JPanel centerPanel = new JPanel(new GridLayout(5,1));
		centerPanel.add(namePanel);
		centerPanel.add(culturePanel);
		centerPanel.add(genderPanel);
		centerPanel.add(occupationPanel);
		centerPanel.add(notesPanel);
		
		JPanel westPanel = new JPanel(new GridLayout(5,1));
		westPanel.add(label1);
		westPanel.add(label2);
		westPanel.add(label3);
		westPanel.add(label4);
		westPanel.add(label5);
		
		JPanel southPanel = new JPanel(new FlowLayout());
		southPanel.add(add);
		southPanel.add(cancel);
		
		frame.add(centerPanel,BorderLayout.CENTER);
		frame.add(southPanel, BorderLayout.SOUTH);
		frame.add(westPanel, BorderLayout.WEST);
		
		
		add.addActionListener(this);
		cancel.addActionListener(this);

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == add){
			//search things
			frame.dispose();
		} else if (event.getSource() == cancel){
			//reset fields
			frame.dispose();
		}
	}

}

