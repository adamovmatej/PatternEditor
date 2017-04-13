package view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import controller.MainScreenController;

public class MainScreen extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private MainScreenController controller;
	
	public MainScreen(JMenuBar menu, EditorView editorView) {
		setJMenuBar(menu);
		setContentPane(editorView);
		init();
	}
	
	public void init(){
		setTitle("Organizational Patterns Editor");
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}

	public MainScreenController getController() {
		return controller;
	}

	public void setController(MainScreenController controller) {
		this.controller = controller;
	}
	
}
