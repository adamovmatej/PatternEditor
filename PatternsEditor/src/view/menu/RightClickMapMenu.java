package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.EditorController;

public class RightClickMapMenu extends JPopupMenu {
	
	private static final long serialVersionUID = 1L;
	
	private EditorController controller;
	
	private JMenuItem newState;
	
    public RightClickMapMenu(EditorController controller, MouseEvent me){
    	this.controller = controller;
    	
    	newState  = new JMenuItem("New State");
    	
    	newState.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewStateDialog(me);
			}
			
		});
    	
        add(newState);
    }
}
