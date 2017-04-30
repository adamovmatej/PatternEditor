package controller.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.EditorController;


public class ClickMapListener extends MouseAdapter {
	EditorController controller;
	
	public ClickMapListener(EditorController controller) {
		this.controller = controller;
	}

	public void mousePressed(MouseEvent e){
		if (SwingUtilities.isRightMouseButton(e)){
			rightClick(e);
			return;
		}
	}
	
	public void mouseReleased(MouseEvent e){
		if (SwingUtilities.isLeftMouseButton(e)){
			leftClick(e);
			return;
		}
		if (SwingUtilities.isRightMouseButton(e)){
			rightClick(e);
			return;
		}
	}
	
	private void rightClick(MouseEvent e){
		System.out.println("RIGHTCLICK");
		if (e.isPopupTrigger()){
			System.out.println("RIGHTCLICK");
			controller.rightClick(e);
		}
	}
	
	private void leftClick(MouseEvent e){
		System.out.println("LEFTCLICK");
		controller.leftClick(e);
	}
}
