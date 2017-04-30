package controller.listener;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.Diagram;
import model.EditorModel;

public class MouseMotionMapListener implements MouseMotionListener {

	private Diagram diagram;
	private EditorModel model;

	public MouseMotionMapListener(EditorModel model, Diagram diagram) {
		this.diagram = diagram;
		this.model = model;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		final int x = e.getX();
        final int y = e.getY();
        final Rectangle cellBounds = new Rectangle(diagram.getBounds());
        if (cellBounds != null && cellBounds.contains(x, y) && model.getHighlight()){
        	diagram.getGraphControl().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
	}

}
