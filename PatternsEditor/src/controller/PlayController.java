package controller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import com.mxgraph.model.mxCell;

import model.Diagram;
import model.Edge;
import model.State;
import view.PlayView;

public class PlayController {
	private PlayView view;
	private Diagram diagram;
	private Map<String, mxCell> options;
	
	public PlayController() {
		options = new HashMap<>();
		view = new PlayView();
		view.setController(this);
	}
	
	public void showView(){
		view.setVisible(true);		
	}
	
	public void play(Diagram diagram){
		this.diagram = diagram;
		mxCell cell;
		for (Object obj : diagram.getGraph().getChildVertices(diagram.getGraph().getDefaultParent())) {
			cell = (mxCell)obj;
			if (cell.getValue().equals("Start")){
				diagram.getGraph().getOutgoingEdges(cell);
				showScene((mxCell) ((mxCell)diagram.getGraph().getOutgoingEdges(cell)[0]).getTarget());				
				return;
			}
		}
	}
	
	public void continueWith(String key){
		showScene(options.get(key));
		
	}
	
	private void showScene(mxCell cell){
		if (cell.getValue().equals("End")){
			view.dispose();
			return;
		} else{
			DefaultTableModel tableModel = new DefaultTableModel(new Object[]{""}, 0);
			State state = (State) cell.getValue();
			view.getTextArea().setText(state.getScene());
			mxCell edge;
			Edge value;
			for (Object c : diagram.getGraph().getOutgoingEdges(cell)) {
				edge = (mxCell) c;
				if (!edge.getValue().getClass().equals(String.class)){
					value = (Edge)edge.getValue();
					if (!value.getDisabled()){
						options.put(value.getScene(), (mxCell) edge.getTarget());
						tableModel.addRow(new Object[]{value.getScene()});
					}					
				}
			}
			view.getTable().setModel(tableModel);
			view.getTable().clearSelection();
			view.repaint();
		}
	}
	
	public Diagram getDiagram() {
		return diagram;
	}
	
	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}
}
