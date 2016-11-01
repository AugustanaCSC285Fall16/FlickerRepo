package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import dataModel.Exporter;
import dataModel.GephiExport;
import dataModel.PalladioExport;

public class ExportGUI implements ActionListener {

	private JFrame frame;
	private JPanel northPanel;
	private JPanel southPanel;
	private JRadioButton palladio;
	private JRadioButton gephi;
	private JButton export;
	private JButton cancel;

	private JFileChooser chooser;

	private HomeScreenGUI home;

	/**
	 * Creates an exportGUI
	 * 
	 * @param HomeScreenGUI
	 *            home
	 */
	public ExportGUI(HomeScreenGUI home) {
		this.home = home;

		northPanel = new JPanel(new FlowLayout());
		southPanel = new JPanel(new FlowLayout());
		palladio = new JRadioButton("Palladio");
		gephi = new JRadioButton("Gephi");
		export = new JButton("Export");
		cancel = new JButton("Cancel");

		northPanel.add(palladio);
		northPanel.add(gephi);
		southPanel.add(export);
		southPanel.add(cancel);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 100);
		frame.setTitle("Export");
		frame.setLayout(new BorderLayout());
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(southPanel, BorderLayout.SOUTH);

		palladio.addActionListener(this);
		gephi.addActionListener(this);
		export.addActionListener(this);
		cancel.addActionListener(this);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Makes the frame visible. Used in our HomeScreenGUI
	 */
	void makeVisible() {
		frame.setVisible(true);
	}

	/**
	 * Makes the browse GUI
	 * 
	 * @return String the path name or cancel if cancelled
	 */
	public String makeChooser() {
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Save");
		int option = chooser.showSaveDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().getAbsolutePath(); // the string of
																// the file path
		} else { // they clicked cancel
			return "cancel";
		}
	}

	/**
	 * Sees what option was selected and exports based on that
	 * 
	 * @return boolean true if export worked
	 */
	public boolean export() {
		if (!palladio.isSelected() && !gephi.isSelected()) {
			JOptionPane.showMessageDialog(null, "Pick a file type!");
			return false;
		}
		Exporter export;
		String pathName = makeChooser();
		if (!pathName.substring(2).contains(":")) { // skip the drive semicolon
													// i.e. "H: ..."
			if (palladio.isSelected() && pathName != "cancel") {
				export = new PalladioExport();
				export.export(home.getStorage(), pathName);
				return true;
			} else if (gephi.isSelected() && pathName != "cancel") {
				export = new GephiExport();
				export.export(home.getStorage(), pathName);
				return true;
			} else {
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error: Could not save to this file location." + "\n\n" + pathName
					+ "\n\nNot a valid Windows directory (cannot have semicolons)");
			return false;
		}

	}

	/**
	 * Overrides the actionPerformed method in ActionListener
	 * 
	 * @param ActionEvent
	 *            from GUI
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == palladio) {
			palladio.setSelected(true);
			gephi.setSelected(false);
		} else if (event.getSource() == gephi) {
			palladio.setSelected(false);
			gephi.setSelected(true);
		} else if (event.getSource() == export) {
			if (export()) {
				frame.dispose();
				JOptionPane.showMessageDialog(null, "Successfully Saved!");
			}
		} else {
			frame.dispose();
		}
	}
}