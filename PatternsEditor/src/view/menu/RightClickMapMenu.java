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
	private JMenuItem newEnd;
	private JMenuItem newStart;
	
	
	
    public RightClickMapMenu(EditorController controller, MouseEvent me) {
    	this.controller = controller;
    	
    	newState  = new JMenuItem("New State");
    	newEnd = new JMenuItem("New End");
    	newStart = new JMenuItem("New Start");
    	
    	newState.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createStateDialog(me);					
			}	
		});
    	
    	newStart.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createStart(me);					
			}	
		});
    	
    	newEnd.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createEnd(me);					
			}	
		});
    	
    	
    	
        add(newState);
        add(newStart);
        add(newEnd);
    }
}
