package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.EditorController;

public class RightClickStateMenu extends JPopupMenu {
	
	private static final long serialVersionUID = 1L;

	private EditorController controller;
	
	private JMenuItem properties;
	private JMenuItem delete;
	
    public RightClickStateMenu(EditorController controller, MouseEvent me){
    	this.controller = controller;
    	
    	properties  = new JMenuItem("Properties");
    	delete = new JMenuItem("Delete");
    	
    	properties.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createStatePropertiesDialog(me);
			}
			
		});
    	
    	delete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO				
			}
		});
    	
        this.add(properties);
        this.add(delete);
    }
}
