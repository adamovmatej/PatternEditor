package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.EditorController;

public class RightClickCellMenu extends JPopupMenu {
	
	private static final long serialVersionUID = 1L;

	private EditorController controller;
	
	private JMenuItem properties;
	private JMenuItem delete;
	
    public RightClickCellMenu(EditorController controller, MouseEvent me){
    	this.controller = controller;
    	
    	properties  = new JMenuItem("Properties");
    	delete = new JMenuItem("Delete");
    	
    	properties.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createPropertiesDialog(me);				
			}
			
		});
    	
    	delete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createDeleteDialog();			
			}
		});
    	
        this.add(properties);
        this.add(delete);
    }
}
