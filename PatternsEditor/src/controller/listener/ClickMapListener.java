package controller.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.EditorController;


public class ClickMapListener extends MouseAdapter {
	EditorController controller;
	
	public ClickMapListener(EditorController controller) {
		this.controller = controller;
	}

	public void mousePressed(MouseEvent e){
		if (e.getButton()==MouseEvent.BUTTON3){
			rightClick(e);
		}
	}
	
	public void mouseReleased(MouseEvent e){
		if (e.getButton()==MouseEvent.BUTTON3){
			rightClick(e);
		}
	}
	
	private void rightClick(MouseEvent e){
		if (e.isPopupTrigger()){
			controller.rightClick(e);
		}
	}
}
