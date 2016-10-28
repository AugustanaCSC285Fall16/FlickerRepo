import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	SearchBackend search;
	DataStorage storage;
	HomeScreenGUI home;

	public SearchGUIV2(HomeScreenGUI home, String[] fields) throws IOException {

		this.home = home;
		storage = DataStorage.getMainDataStorage();

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 100);
		frame.setTitle("Search");
		frame.setLayout(new GridLayout(2, 1));

		panel = new JPanel(new FlowLayout());
		buttonsPanel = new JPanel(new FlowLayout());

		optionsLabel = new JLabel("Select a search option: ");
		this.options = new JComboBox<>(fields);
		newDataLabel = new JLabel("Criteria to search: ");
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
			try {
				search = new SearchBackend();
				String criteria = newData.getText();
				int index = options.getSelectedIndex();
				if (index == 0) {
					search.searchByName(criteria);
				} else if (index == 1) {
					search.searchByCulturalID(criteria);
				} else if (index == 2) {
					search.searchByGender(criteria);
				} else if (index == 3) {
					search.searchByOccupation(criteria);
				} else if (index == 4) {
					// do we NEED base name???
				} else if (index == 5) {
					search.searchByDate(criteria);
					// need to make it so they know to put it in date format
				} else if (index == 6) {
					search.searchByLocation(criteria);
				} else if (index == 7) {
					search.searchByInteraction(criteria);
					}
				SearchResultsGUI gui = new SearchResultsGUI(home, storage.getPersonHeaderRow(),
						search.getPersonCollection(), storage.getConnectionHeaderRow(),
						search.getConnectionCollection());
				if (search.getPersonCollection().isEmpty() && search.getConnectionCollection().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Could Not Find Search Criteria in Data");
					gui.closeFrame();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Could not load backend search");
			}
		} else if (source == cancel) {
			frame.dispose();
		}
	}
}
