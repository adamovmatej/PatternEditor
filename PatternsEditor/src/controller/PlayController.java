package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
		List<Object> cells = new ArrayList<>(Arrays.asList(diagram.getGraph().getChildCells(diagram.getGraph().getDefaultParent())));
		for (int i = 0; i < cells.size(); i++){
			mxCell current = (mxCell) cells.get(i);
			cells.addAll(Arrays.asList(diagram.getGraph().getChildCells(current, true, true)));
			if (current.getValue().equals("Start")){
				diagram.getGraph().getOutgoingEdges(current);
				showScene((mxCell) ((mxCell)diagram.getGraph().getOutgoingEdges(current)[0]).getTarget());				
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
			view.getTextPane().setText(state.getScene());
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
