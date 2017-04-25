package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import controller.MainScreenController;

public class MainMenuBar extends JMenuBar{
	

	private Locale currentLocale;
	
	private static final long serialVersionUID = 1L;

	private MainScreenController controller;
	
	private JMenu file = new JMenu();
	private JMenu edit = new JMenu();
	private JMenu help = new JMenu();
	private JMenu preferences = new JMenu();
	
	private JMenuItem newPattern = new JMenuItem();
	private JMenuItem newDiagram = new JMenuItem();
	private JMenuItem newVersion = new JMenuItem();
	private JMenuItem newAdapter = new JMenuItem();
	private JMenuItem patternOverView = new JMenuItem();
	private JMenuItem save = new JMenuItem();
	private JMenuItem saveAs = new JMenuItem();
	private JMenuItem open = new JMenuItem();	
		
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
		
		newAdapter.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewAdapterDialog();
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
		
		file.add(newPattern);
		file.add(newDiagram);
		file.addSeparator();
		file.add(newVersion);
		file.add(newAdapter);
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
		this.currentLocale = controller.getCurrentLocale();
		initText();
	}
	
	private void initText(){
		ResourceBundle bundle = ResourceBundle.getBundle("TextBundle", currentLocale);	
		
		file.setText(bundle.getString("menu.file"));
		edit.setText(bundle.getString("menu.edit"));
		help.setText(bundle.getString("menu.help"));
		preferences.setText(bundle.getString("menu.preferences"));
		
		newPattern.setText(bundle.getString("menu.newPattern"));
		newDiagram.setText(bundle.getString("menu.newDiagram"));
		newVersion.setText(bundle.getString("menu.newVersion"));
		newAdapter.setText(bundle.getString("menu.newAdapter"));
		patternOverView.setText(bundle.getString("menu.patternOverView"));
		save.setText(bundle.getString("menu.save"));
		saveAs.setText(bundle.getString("menu.saveAs"));
		open.setText(bundle.getString("menu.open"));
	}
}
