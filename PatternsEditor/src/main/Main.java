package main;

import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;

import controller.EditorController;
import controller.MainScreenController;
import controller.PatternOverviewController;
import controller.VersionPanelController;
import model.DiagramModel;
import model.Edge;
import model.PatternModel;
import model.State;
import model.db.SQLConnection;
import view.EditorView;
import view.MainScreen;
import view.VersionPanelView;
import view.menu.MainMenuBar;

public class Main {

	public static void main(String[] args) {
		
		SQLConnection.getInstance().initDatabase();
		
		mxCodecRegistry.register(new mxObjectCodec(new State()));
		mxCodecRegistry.register(new mxObjectCodec(new Edge()));
		
		MainMenuBar mainMenuBar = new MainMenuBar();
		VersionPanelView versionPanelView = new VersionPanelView();
		EditorView editorView = new EditorView(versionPanelView);
		MainScreen mainScreen = new MainScreen(mainMenuBar, editorView);
		
		PatternModel patternModel = new PatternModel();
		DiagramModel diagramModel = new DiagramModel();
		
		EditorController editorController = new EditorController(editorView, diagramModel);
		VersionPanelController versionPanelController = new VersionPanelController(versionPanelView, diagramModel);
		PatternOverviewController patternOverviewController = new PatternOverviewController(patternModel, diagramModel);
		MainScreenController mainScreenController = new MainScreenController(mainScreen, mainMenuBar, diagramModel, patternModel, patternOverviewController);
		
		versionPanelView.setController(versionPanelController);
		editorView.setController(editorController);
		mainMenuBar.setController(mainScreenController);
		mainScreen.setController(mainScreenController);
	}

}