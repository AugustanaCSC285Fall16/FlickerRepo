
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddArtist implements ActionListener {

	private JFrame frame;
	private JTextField name;
	private JTextField culture;
	private JTextField gender;
	private JTextField occupation;
	private JTextField notes;
	private JButton add;
	private JButton cancel;

	public AddArtist() {
		frame = new JFrame("Search");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setTitle("Frame");
		frame.setLayout(new BorderLayout());
		
		name = new JTextField(15);
		culture = new JTextField(15);
		gender = new JTextField(15);
		occupation = new JTextField(15);
		notes = new JTextField(15);
		add = new JButton("Add");
		cancel = new JButton("Cancel");
		
		JLabel label1 = new JLabel("Artist Name:");
		JLabel label2 = new JLabel("Cultural ID:");
		JLabel label3 = new JLabel("Gender:");
		JLabel label4 = new JLabel("Occupation:");
		JLabel label5 = new JLabel("Biographical Notes:");
		
		JPanel centerPanel = new JPanel(new GridLayout(5,1));
		centerPanel.add(name);
		centerPanel.add(culture);
		centerPanel.add(gender);
		centerPanel.add(occupation);
		centerPanel.add(notes);
		
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

