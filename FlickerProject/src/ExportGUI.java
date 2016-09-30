import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ExportGUI implements ActionListener{

	private JFrame frame;

	private JPanel northPanel;
	private JPanel southPanel;
	
	private JRadioButton palladio;
	private JRadioButton geffy;
	private JButton export;
	private JButton cancel;
	
	HomeScreenGUI home;
	
	public ExportGUI(HomeScreenGUI home) {
		this.home = home;
		
		northPanel = new JPanel(new FlowLayout());
		southPanel = new JPanel(new FlowLayout());
		palladio = new JRadioButton("Palladio");
		geffy = new JRadioButton("Geffy");
		export = new JButton("Export");
		cancel = new JButton("Cancel");
	
		northPanel.add(palladio);
		northPanel.add(geffy);
		southPanel.add(export);
		southPanel.add(cancel);
		
		frame = new JFrame("Export As:");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 100);
		frame.setTitle("Export");
		frame.setLayout(new BorderLayout());
		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(southPanel, BorderLayout.SOUTH);
		
		palladio.addActionListener(this);
		geffy.addActionListener(this);
		export.addActionListener(this);
		cancel.addActionListener(this);		
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	void makeVisible(){
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == palladio) {
			palladio.setSelected(true);
			geffy.setSelected(false);
		}
		else if (event.getSource() == geffy) {
			palladio.setSelected(false);
			geffy.setSelected(true);
		}
	}


}
