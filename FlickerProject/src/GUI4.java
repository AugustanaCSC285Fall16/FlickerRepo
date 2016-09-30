/*
 * Name: Andrew Bainter
 * Date:  1-18-16
 * Class: CSC 212
 * 
 * Purpose: Using GUIs
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI4 implements ActionListener {
	// main
	public static void main(String[] args) {
		GUI4 gui = new GUI4();
	}

	// fields
	private JButton capsButton;
	private JButton lowerButton;
	private JTextField field;

	// constructor
	public GUI4() {
		capsButton = new JButton("Uppercase");
		lowerButton = new JButton("Lowercase");
		field = new JTextField(
				"This text can be made to all uppercase or lowercase");

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(350, 125));
		frame.setTitle("Silly String Game");
		frame.setLayout(new BorderLayout());
		frame.add(capsButton, BorderLayout.NORTH);
		frame.add(lowerButton, BorderLayout.SOUTH);
		frame.add(field, BorderLayout.CENTER);

		frame.setVisible(true);

		capsButton.addActionListener(this);
		lowerButton.addActionListener(this);

	}

	// event
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == capsButton) {
			String str = field.getText();
			str = str.toUpperCase();
			field.setText(str);
		} else if (source == lowerButton) {
			String str = field.getText();
			str = str.toLowerCase();
			field.setText(str);
		}
	}

}
