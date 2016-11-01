import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class AddData implements ActionListener {

	JFrame frame;
	JPanel panel;
	JPanel buttonsPanel;
	JLabel optionsLabel;
	JComboBox<String> options;
	JLabel newDataLabel;
	JTextField newData;
	JButton submit;
	JButton cancel;
	DataStorage storage;

	public AddData() {
		storage = DataStorage.getMainDataStorage();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	}

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
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == submit) {
			try {
				submitClicked();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// CLOSES THE WHOLE PROGRAM???
			frame.dispose();
		}
	}

}
