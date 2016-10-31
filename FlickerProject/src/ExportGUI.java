import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ExportGUI implements ActionListener{

	private JFrame frame;

	private JPanel northPanel;
	private JPanel southPanel;
	private JRadioButton palladio;
	private JRadioButton gephi;
	private JButton export;
	private JButton cancel;
	
	private JFileChooser chooser;
	
	HomeScreenGUI home;
	
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
		
		frame = new JFrame("Export As:");
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
	 * Makes the frame visible.
	 */
	
	void makeVisible(){
		frame.setVisible(true);
	}
	
	/**
	 * Makes the browse gui
	 */
	
	public String makeChooser(){
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("choosertitle");
		chooser.showSaveDialog(frame);
		try{
			return chooser.getSelectedFile().getAbsolutePath(); //the string of the file path
		} catch (NullPointerException e){
			//they clicked cancel
			return null;
		}
	}
	
	public boolean export(){
		Exporter export;
		if(!palladio.isSelected() && !gephi.isSelected()){
			JOptionPane.showMessageDialog(null, "Pick a file type!");
			return false;
		}
		String pathName = makeChooser();
		if(palladio.isSelected() && pathName != null){
			export = new PalladioExport();
			export.export(home.getFilteredStorage(), pathName);
			return true;
		} else if (gephi.isSelected()){
			export = new GephiExport();
			export.export(home.getFilteredStorage(), pathName);
			return true;
		} else {
			return false;
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == palladio) {
			palladio.setSelected(true);
			gephi.setSelected(false);
		}else if (event.getSource() == gephi) {
			palladio.setSelected(false);
			gephi.setSelected(true);
		} else if (event.getSource() == export){
			if(export()){
				JOptionPane.showMessageDialog(null, "Successfully Saved! (I think?)");
				frame.dispose();
			}
		} else {
			frame.dispose();
		}
	}
}