package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import controller.MainScreenController;

public class MainMenuBar extends JMenuBar{

	private static final long serialVersionUID = 1L;

	private MainScreenController controller;
	
	private JMenu file = new JMenu("File");
	private JMenuItem newPattern = new JMenuItem("New Pattern");
	private JMenuItem newDiagram = new JMenuItem("New Diagram");
	private JMenuItem newVersion = new JMenuItem("New Version");
	private JMenuItem patternOverView = new JMenuItem("Pattern Overview");
	private JMenuItem save = new JMenuItem("Save");
	private JMenuItem saveAs = new JMenuItem("Save as");
	private JMenuItem open = new JMenuItem("Open");	
	
	private JMenu edit = new JMenu("Edit");
	private JMenu help = new JMenu("Help");
	
	private JFileChooser fc = new JFileChooser();
	
	public MainMenuBar() {
				
		newPattern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewPatternDialog();
			}
		});
		
		newDiagram.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewDiagramDialog();
			}
		});
		
		newVersion.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewVersionDialog();
			}
		});
		
		patternOverView.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showPatternOverview();
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				
			}
		});
		
		file.add(newDiagram);
		file.add(newVersion);
		file.add(newPattern);
		file.addSeparator();
		file.add(patternOverView);
		file.addSeparator();
		file.add(save);
		file.add(saveAs);
		file.add(open);
		
		add(file);
		add(edit);
		add(help);
}

	public MainScreenController getController() {
		return controller;
	}

	public void setController(MainScreenController controller) {
		this.controller = controller;
	}
}
