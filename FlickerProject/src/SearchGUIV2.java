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
	SearchBackend search;
	DataStorage storage;
	HomeScreenGUI home;

	public SearchGUIV2(HomeScreenGUI home,String[] fields) throws IOException {
		
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
			if (options.getSelectedIndex() == 0) {
				try {
					search = new SearchBackend();
					search.searchByName(newData.getText());
					System.out.println(search.getPersonCollection().toString());
					for (Connection connection : search.getConnectionCollection()) {
						System.out.println(connection.getPeopleList());
					}
					SearchResultsGUI gui = new SearchResultsGUI(home, storage.getPersonHeaderRow(),
							search.getPersonCollection(), storage.getConnectionHeaderRow(),
							search.getConnectionCollection());
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
}
