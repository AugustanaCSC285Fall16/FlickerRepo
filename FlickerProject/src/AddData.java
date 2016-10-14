import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AddData implements ActionListener{

	JFrame frame;
	JPanel panel;
	JPanel buttonsPanel;
	JLabel optionsLabel;
	JComboBox<String> options;
	JLabel newDataLabel;
	JTextField newData;
	JButton submit;
	JButton cancel;
	
	public AddData(String[] options){
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 100);
		frame.setTitle("Home");
		frame.setLayout(new GridLayout(2,1));
		
		panel = new JPanel(new FlowLayout());
		buttonsPanel = new JPanel(new FlowLayout());
		
		optionsLabel = new JLabel("Select a data option: ");
		this.options = new JComboBox<>(options);
		newDataLabel = new JLabel("Data to add: ");
		newData = new JTextField(10);
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		
		panel.add(optionsLabel);
		panel.add(this.options);
		panel.add(newDataLabel);
		panel.add(newData);
		
		buttonsPanel.add(submit);
		buttonsPanel.add(cancel);
		
		frame.add(panel);
		frame.add(buttonsPanel);
		
		submit.addActionListener(this);
		cancel.addActionListener(this);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == submit){
			//save the data
		} else {
			frame.dispose();
		}
		
	}
	
}
