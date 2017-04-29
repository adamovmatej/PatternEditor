package main;

import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;

import controller.EditorController;
import controller.MainScreenController;
import controller.PatternOverviewController;
import controller.VersionPanelController;
import model.DiagramModel;
import model.Edge;
import model.EditorModel;
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
		EditorModel editorModel = new EditorModel();
		
		EditorController editorController = new EditorController(editorView, editorModel);
		VersionPanelController versionPanelController = new VersionPanelController(versionPanelView, editorModel);
		PatternOverviewController patternOverviewController = new PatternOverviewController(patternModel, editorModel);
		MainScreenController mainScreenController = new MainScreenController(mainScreen, mainMenuBar, editorModel, patternModel, patternOverviewController);
		
		versionPanelView.setController(versionPanelController);
		editorView.setController(editorController);
		mainMenuBar.setController(mainScreenController);
		mainScreen.setController(mainScreenController);
	}

}