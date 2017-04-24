package controller.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

import com.mxgraph.swing.mxGraphOutline;

import model.Diagram;

public class MouseWheelListener extends MouseAdapter {
	
	private Diagram diagram;
	
	public MouseWheelListener(Diagram diagram) {
		this.diagram = diagram;
	}
	
	public void mouseWheelMoved(MouseWheelEvent e){
		if (e.getSource() instanceof mxGraphOutline || e.isControlDown()){
			zoom(e);
		}
	}
	
	public void zoom(MouseWheelEvent e){
		if (e.getWheelRotation() < 0){
			diagram.zoomIn();
		} else {
			diagram.zoomOut();
		}
	}
}
