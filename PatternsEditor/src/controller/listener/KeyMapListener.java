package controller.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import controller.EditorController;

public class KeyMapListener extends KeyAdapter{
	EditorController controller;
	
	public KeyMapListener(EditorController controller) {
		this.controller = controller;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE){
			controller.createDeleteDialog();
		}
	}
	
}
