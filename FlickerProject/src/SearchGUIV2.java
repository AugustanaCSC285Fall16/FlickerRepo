import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class SearchGUIV2 implements ActionListener {

	JFrame frame;
	JPanel panel;
	JPanel buttonsPanel;
	JLabel optionsLabel;
	JComboBox<String> options;
	JLabel newDataLabel;
	JTextField newData;
	JButton submit;
	JButton cancel;
	
	public SearchGUIV2(String[] fields) {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 100);
		frame.setTitle("Search");
		frame.setLayout(new GridLayout(2, 1));
		
		panel = new JPanel(new FlowLayout());
		buttonsPanel = new JPanel(new FlowLayout());

		optionsLabel = new JLabel("Select a search option: ");
		this.options = new JComboBox<>(fields);
		newDataLabel = new JLabel("Vocab to add: ");
		newData = new JTextField(15);
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
	
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == submit) {
			
		} else {
			// CLOSES THE WHOLE PROGRAM???
			frame.dispose();
		}
		
	}
}
