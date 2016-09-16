import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class HomeScreen implements ActionListener {
	
	private JFrame frame;
	private JButton search;
	private JButton add;
	private JButton edit;
	private JButton artists;
	private JButton connections;
	private JPanel bigPanel;
	

	public HomeScreen() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setTitle("Home");
		frame.setLayout(new BorderLayout());

		search = new JButton("Search");
		add = new JButton("Add");
		edit = new JButton("Edit");
		artists = new JButton("Artists");
		connections = new JButton("Connections");

		JPanel westPanel = new JPanel(new GridLayout(3,1));
		westPanel.add(search);
		westPanel.add(add);
		westPanel.add(edit);
		
		JPanel northPanel = new JPanel(new GridLayout(1,5));
		northPanel.add(artists);
		northPanel.add(connections);
		
		JPanel centerPanel = new JPanel(new BorderLayout()); //curently does nothing
		
		bigPanel = new JPanel(new BorderLayout()); 
		bigPanel.add(northPanel, BorderLayout.NORTH);

		frame.add(westPanel, BorderLayout.WEST);
		frame.add(bigPanel,BorderLayout.CENTER);

		search.addActionListener(this);
		add.addActionListener(this);
		edit.addActionListener(this);
		artists.addActionListener(this);
		connections.addActionListener(this);

		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == search) {
			Search search = new Search();
			
			System.out.println("hi");
			
			JPanel southPanel = new JPanel(new GridLayout(1,3));
			JButton clear = new JButton("Clear");
			JButton edit = new JButton("Edit");
			JButton export = new JButton("Export");
			
			southPanel.add(clear);
			southPanel.add(edit);
			southPanel.add(export);
			
			bigPanel.add(southPanel, BorderLayout.SOUTH);
			bigPanel.revalidate();
			
			System.out.println("bye");
		} else if(event.getSource() == add){
			Object[] options = {"Add Artist", "Add Connection"};
			int val=JOptionPane.showOptionDialog(frame, "What would you like to add?", "Answer me", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if(val == 0){ //if artist
				artists.setPressedIcon(new ImageIcon());
				AddArtist newArtist = new AddArtist();
			} else { //if connection
				connections.setPressedIcon(new ImageIcon());
				AddConnection newConnection = new AddConnection();
			}
			if(true){
				//make add connection
			}else{
				//make add artist
				AddArtist artist = new AddArtist();
			}
		}
	}
}
