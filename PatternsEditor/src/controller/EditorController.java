package controller;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import controller.listener.ClickMapListener;
import controller.listener.KeyMapListener;
import controller.listener.MouseMotionMapListener;
import controller.listener.MouseWheelListener;
import model.Adapter;
import model.Diagram;
import model.DiagramModel;
import model.Edge;
import model.EditorModel;
import model.State;
import view.CustomTabPane;
import view.EditorView;
import view.dialog.EdgePropertiesDialog;
import view.dialog.StatePropertiesDialog;
import view.menu.RightClickMapMenu;
import view.menu.RightClickCellMenu;

public class EditorController implements PropertyChangeListener{
	
	private EditorView view;
	private EditorModel model;

	public EditorController(EditorView view, EditorModel editorModel) {
		this.view = view;
		this.model = editorModel;
		((CustomTabPane)view.getRightComponent()).addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				if (((CustomTabPane)view.getRightComponent()).getTabCount()>0){
					editorModel.changeDiagramModel(((Diagram) view.getMap().getComponentAt(view.getMap().getSelectedIndex())).getPattern());					
				}
			}
		});
		editorModel.addListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("newDiagramModel")){
			DiagramModel diagramModel = (DiagramModel)evt.getNewValue();
			diagramModel.addListener(this);
			initListenersDiagramModel(diagramModel);
			Diagram diagram = diagramModel.getCurrentAdapter().getDiagram();
			insertDiagram(diagram);
			return;
		}
		if (evt.getPropertyName().equals("newAdapter")){
			Diagram diagram = ((Adapter) evt.getNewValue()).getDiagram();
			initListeners(diagram);
			view.getMap().setComponentAt(view.getMap().getSelectedIndex(), diagram);
			return;
		}
		if (evt.getPropertyName().equals("adapterChange")){
			Diagram diagram = (Diagram) evt.getNewValue();
			view.getMap().setComponentAt(view.getMap().getSelectedIndex(), diagram);
			view.getMap().repaint();
			return;
		}
	}
	
	public void removeTab(String name){
		model.closeDiagramModel(name);
		if (view.getMap().getTabCount()==0){
			model.changeDiagramModel(null);
		} else {
			model.changeDiagramModel(((Diagram)view.getMap().getSelectedComponent()).getPattern());			
		}
	}
	
	public void createPropertiesDialog(MouseEvent me){
		Diagram diagram = model.getCurrentDiagramModel().getCurrentAdapter().getDiagram();
		mxCell cell = (mxCell)(diagram.getCellAt(me.getX(), me.getY()));
		if (cell.isEdge()){
			Object obj = cell.getValue();
			if (obj.getClass() == String.class){
				cell.setValue(new Edge());
			}
			Edge edge = (Edge) cell.getValue();
			EdgePropertiesDialog dialog = new EdgePropertiesDialog(this, edge.getName(), edge.getScene(), me);
			dialog.setVisible(true);
		} else{
			State state = (State) cell.getValue();
			StatePropertiesDialog dialog = new StatePropertiesDialog(this, state.getName(), state.getScene(),state.getDisabled(), false, me);
			dialog.setVisible(true);
		}
		
	}
	
	public void createStateDialog(MouseEvent me) {
		StatePropertiesDialog dialog = new StatePropertiesDialog(this, null, null, false, true, me);
		dialog.setVisible(true);
	}
	
	public void createState(String name, String scene, Boolean disable, MouseEvent me) {
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		State state = new State(name, scene, disable);
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, state, me.getX(), me.getY(), 100, 50);
		} finally{
			graph.getModel().endUpdate();
		}			

		graph.refresh();
		graph.repaint();
	}
	
	public void updateState(String name, String scene, Boolean disable, MouseEvent me){
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) (((Diagram) (view.getMap().getSelectedComponent())).getCellAt(me.getX(), me.getY()));
			State state = (State) cell.getValue();
			if (state == null){
				state = new State();
			}
			state.update(name, scene, disable);
		} finally{
			graph.getModel().endUpdate();
		}
		
		graph.refresh();
		graph.repaint();
	}
	
	private void paintCell(mxCell cell, String version){
		Object value = cell.getValue();
		if (value.getClass().equals(State.class)){
			if (((State)cell.getValue()).getDisabled()){
				cell.setStyle("defaultVertex;fillColor=gray");
			} else {
				cell.setStyle("defaultVertex");
			}			
		}
	}
	
	public void rightClick(MouseEvent e) {
		Diagram diagram = (Diagram) view.getMap().getSelectedComponent();

		if ((diagram.getGraph().getModel().isVertex(diagram.getCellAt(e.getX(), e.getY()))) || (diagram.getGraph().getModel().isEdge(diagram.getCellAt(e.getX(), e.getY())))){
			RightClickCellMenu menu = new RightClickCellMenu(this, e);
			menu.show(e.getComponent(), e.getX(), e.getY());
		} else {
			RightClickMapMenu menu = new RightClickMapMenu(this, e);
			menu.show(e.getComponent(), e.getX(), e.getY());			
		}
	}
	
	private void insertDiagram(Diagram diagram){
		view.getMap().add(diagram.getPattern(), diagram);
		view.getMap().setSelectedComponent(diagram);
	}
		
	private void initListenersDiagramModel(DiagramModel diagramModel){
		for (String key : diagramModel.getAdapters().keySet()){
			initListeners(diagramModel.getAdapters().get(key).getDiagram());
		}
	}
	
	private void initListeners(Diagram diagram) {
		diagram.getGraphControl().addMouseListener(new ClickMapListener(this));
		diagram.addKeyListener(new KeyMapListener(this));
		diagram.addMouseWheelListener(new MouseWheelListener(diagram));
		diagram.getGraphControl().addMouseMotionListener(new MouseMotionMapListener(model, diagram));
	}

	public void createDeleteDialog() {
		Diagram diagram = (Diagram) view.getMap().getSelectedComponent();
		Object[] selection = (diagram).getGraph().getSelectionModel().getCells();
	
		if (selection.length > 0){
			String msg = "Are you sure you want to delete this element?";
			if (selection.length > 1){
				msg = "Are you sure you want to delete these elements?";
			}
			int result = JOptionPane.showConfirmDialog(view, msg, "alert", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.YES_OPTION){
				diagram.getGraph().removeCells(selection);
			}			
		}
	}

	public void createEdgePropertiesDialog(MouseEvent me) {
		Diagram diagram = model.getCurrentDiagramModel().getCurrentAdapter().getDiagram();
		
		State state = (State) ((mxCell)(diagram.getCellAt(me.getX(), me.getY()))).getValue();
		StatePropertiesDialog dialog = new StatePropertiesDialog(this, state.getName(), state.getScene(),state.getDisabled(), false, me);
		dialog.setVisible(true);
	}

	public void updateEdge(String name, String scene, MouseEvent me) {
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) (((Diagram) (view.getMap().getSelectedComponent())).getCellAt(me.getX(), me.getY()));
			Edge edge = (Edge) cell.getValue();
			edge.update(name, scene);
		} finally{
			graph.getModel().endUpdate();
		}
		
		graph.refresh();
		graph.repaint();
	}

	public void createStart(MouseEvent me) {
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, "Start", me.getX(), me.getY(), 35, 35);
			cell.setStyle("CIRCLE;fillColor=black");
		} finally{
			graph.getModel().endUpdate();
		}			

		graph.refresh();
		graph.repaint();
	}

	public void createEnd(MouseEvent me) {
		Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
		mxGraph graph = diagram.getGraph();
		
		graph.getModel().beginUpdate();
		try{
			mxCell cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, "End", me.getX(), me.getY(), 35, 35);
			cell.setStyle("CIRCLE;fillColor=black");
		} finally{
			graph.getModel().endUpdate();
		}			

		graph.refresh();
		graph.repaint();
	}	
	
	public void toggleHighlight(Boolean highlight){
		model.setHighlight(highlight);
	}

	public void leftClick(MouseEvent me) {
		if (model.getHighlight()){
			Diagram diagram = (Diagram)view.getMap().getSelectedComponent();
			mxGraph graph = diagram.getGraph();
			
			graph.getModel().beginUpdate();
			try{
				mxCell cell = (mxCell) (((Diagram) (view.getMap().getSelectedComponent())).getCellAt(me.getX(), me.getY()));
				if (cell != null){
					if (cell.getStyle() == "fillColor=green"){
						cell.setStyle(null);				
					} else if (cell.getStyle() == null){
						cell.setStyle("fillColor=green");
					}
				}
			} finally{
				graph.getModel().endUpdate();
			}
			
			graph.refresh();
			graph.repaint();
		}
	}
}
