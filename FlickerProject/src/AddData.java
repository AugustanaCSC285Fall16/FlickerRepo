import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

	public AddData() {
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

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		try {
			DataStorage storage = DataStorage.getMainDataStorage();
			if (source == submit) {
				// make sure selectedIndexs are right after we change where the
				// button is on the screen
				if (options.getSelectedIndex() == 0) {
					storage.addCulteralIdChoice(newData.getText());
					storage.saveCulturalIDControlledVocab();
				} else if (options.getSelectedIndex() == 1) {
					storage.addOccupationChoice(newData.getText());
					storage.saveOccupationControlledVocab();
				} else if (options.getSelectedIndex() == 2) {
					storage.addInteractionChoice(newData.getText());
					storage.saveInteractionControlledVocab();
				} else if (options.getSelectedIndex() == 3) {
					storage.addLocationChoice(newData.getText());
					storage.saveLocationControlledVocab();}
				JOptionPane.showMessageDialog(null, "Controlled Vocab Successfully Saved");
				frame.dispose();
			}else {
					// CLOSES THE WHOLE PROGRAM???
					frame.dispose();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Data Could Not Be Loaded");
		}
	}

}
