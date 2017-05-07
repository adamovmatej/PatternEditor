package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.MainScreenController;

public class MainMenuBar extends JMenuBar{
	

	private Locale currentLocale;
	
	private static final long serialVersionUID = 1L;

	private MainScreenController controller;
	
	private JMenu file = new JMenu();
	private JMenu show = new JMenu();
	private JMenu edit = new JMenu();
	private JMenu help = new JMenu();
	private JMenu preferences = new JMenu();
	
	private JMenuItem newPattern = new JMenuItem();
	private JMenuItem newModel = new JMenuItem();
	private JMenuItem newAdapter = new JMenuItem();
	private JMenuItem patternOverView = new JMenuItem();
	private JMenuItem toolbar = new JMenuItem();
	private JMenuItem save = new JMenuItem();
	private JMenuItem open = new JMenuItem();
	private JMenuItem closeModel = new JMenuItem();
	private JMenuItem closeAll = new JMenuItem();
	private JMenuItem exit = new JMenuItem();
	private JMenuItem saveAll = new JMenuItem();
		
	public MainMenuBar() {
		
		newPattern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewPatternDialog();
			}
		});
		
		newModel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewDiagramDialog();
			}
		});
		
		newAdapter.setEnabled(false);
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
		
		save.setEnabled(false);
		save.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveModel();
			}
		});
		
		open.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				controller.createOpenDiagramDialog();
			}
		});
		
		toolbar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				controller.showToolbar();
			}
		});
		closeModel.setEnabled(false);
		closeModel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				controller.removeTab();
			}
		});
		closeAll.setEnabled(false);
		closeAll.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.removeAllTabs();
			}
		});
		saveAll.setEnabled(false);
		saveAll.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.saveAll();
			}
		});
		
		exit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.exit();
			}
		});
		
		file.add(newPattern);
		file.add(newModel);
		file.addSeparator();
		file.add(save);
		file.add(saveAll);
		file.add(open);
		file.addSeparator();
		file.add(closeAll);
		file.add(exit);
		
		show.add(patternOverView);
		show.add(toolbar);
		
		edit.add(newAdapter);
		edit.add(closeModel);
		
		add(file);
		add(edit);
		add(show);
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
		
		show.setText(bundle.getString("menu.show"));
		file.setText(bundle.getString("menu.file"));
		edit.setText(bundle.getString("menu.edit"));
		help.setText(bundle.getString("menu.help"));
		preferences.setText(bundle.getString("menu.preferences"));
		
		newPattern.setText(bundle.getString("menu.newPattern"));
		newModel.setText(bundle.getString("menu.newModel"));
		newAdapter.setText(bundle.getString("menu.newAdapter"));
		patternOverView.setText(bundle.getString("menu.patternOverView"));
		toolbar.setText(bundle.getString("menu.toolbar"));
		save.setText(bundle.getString("menu.save"));
		open.setText(bundle.getString("menu.open"));
		closeModel.setText(bundle.getString("menu.closeModel"));
		closeAll.setText(bundle.getString("menu.closeAll"));
		exit.setText(bundle.getString("menu.exit"));
		saveAll.setText(bundle.getString("menu.saveAll"));
	}

	public void chceckDiagramItems(Boolean enabled) {
		closeModel.setEnabled(enabled);
		closeAll.setEnabled(enabled);
		saveAll.setEnabled(enabled);
		save.setEnabled(enabled);
		newAdapter.setEnabled(enabled);
	}
}
