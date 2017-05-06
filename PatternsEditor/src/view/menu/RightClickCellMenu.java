package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.EditorController;

public class RightClickCellMenu extends JPopupMenu {
	
	private static final long serialVersionUID = 1L;
	
	private JMenuItem properties;
	private JMenuItem delete;
	private JMenuItem newState;
	
    public RightClickCellMenu(EditorController controller, Boolean prop, Boolean state, MouseEvent me){
    	if (prop){
    		properties  = new JMenuItem("Properties");
    		properties.addActionListener(new ActionListener() {	
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				controller.createPropertiesDialog(me);				
    			}
    			
    		});    		
    		this.add(properties);
    	}
    	
    	
    	delete = new JMenuItem("Delete");
    	delete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createDeleteDialog();			
			}
		});
    	this.add(delete);
    	
    	if (state){
    		newState = new JMenuItem("New state");
    		newState.addActionListener(new ActionListener() {			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				controller.createStateDialog(me);
    			}
    		});
    		this.add(newState);
    	}    	
    }
}
