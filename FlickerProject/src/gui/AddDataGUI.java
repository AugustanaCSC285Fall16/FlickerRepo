package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import dataModel.VocabStorage;

public class AddDataGUI implements ActionListener {

	JFrame frame;
	JPanel panel;
	JPanel buttonsPanel;
	JLabel optionsLabel;
	JComboBox<String> options;
	JLabel newDataLabel;
	JTextField newData;
	JButton submit;
	JButton cancel;
	VocabStorage storage;

	/**
	 * Creates an Add Data gui for the user to make new vocab options for the
	 * program
	 * 
	 */
	public AddDataGUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(600, 100);
		frame.setTitle("Home");
		frame.setLayout(new GridLayout(2, 1));

		panel = new JPanel(new FlowLayout());
		buttonsPanel = new JPanel(new FlowLayout());

		optionsLabel = new JLabel("Select a vocab option: ");
		this.options = new JComboBox<>(
				(new String[] { "Cultural ID", "Occupation", "Types of Interaction", "Location" }));
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

		try {
			storage = VocabStorage.getMainVocabStorage();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Unable to load vocab!");
		}
	}

	/**
	 * This method saves new, inputed data from the add data GUI
	 * 
	 * @throws IOException
	 */
	public void submitClicked() throws IOException {
		if (options.getSelectedIndex() == 0) {
			if (!storage.getCultureChoices().contains(newData.getText().toLowerCase())) {
				storage.addCulteralIdChoice(newData.getText());
				storage.saveCulturalIDControlledVocab();
				JOptionPane.showMessageDialog(null, "Cultural Id successfully saved");
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "This cultural Id is already in the list!");
			}
		} else if (options.getSelectedIndex() == 1) {
			if (!storage.getOccupationChoices().contains(newData.getText().toLowerCase())) {
				storage.addOccupationChoice(newData.getText());
				storage.saveOccupationControlledVocab();
				JOptionPane.showMessageDialog(null, "Occupation successfully saved");
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "This occupation is already in the list!");
			}
		} else if (options.getSelectedIndex() == 2) {
			if (!storage.getInteractionTypes().contains(newData.getText().toLowerCase())) {
				storage.addInteractionChoice(newData.getText());
				storage.saveInteractionControlledVocab();
				JOptionPane.showMessageDialog(null, "Interaction type successfully saved");
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "This interaction type is already in the list!");
			}
		} else if (options.getSelectedIndex() == 3) {
			if (!storage.getLocationTypes().contains(newData.getText().toLowerCase())) {
				storage.addLocationChoice(newData.getText());
				storage.saveLocationControlledVocab();
				JOptionPane.showMessageDialog(null, "Location successfully saved");
				frame.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "This location is already in the list!");
			}
		}
	}

	@Override
	/**
	 * This method overrides the actionPerformed method from Action Listener
	 * 
	 * @param -
	 *            an action event from the GUI
	 */
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == submit) {
			try {
				submitClicked();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could Not save Data!");
			}
		} else {
			frame.dispose();
		}
	}

}
