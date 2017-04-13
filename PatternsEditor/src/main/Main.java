package main;

import com.mxgraph.io.mxCodecRegistry;
import com.mxgraph.io.mxObjectCodec;

import controller.EditorController;
import controller.MainScreenController;
import controller.VersionPanelController;
import model.DiagramModel;
import model.State;
import view.EditorView;
import view.MainScreen;
import view.VersionPanelView;
import view.menu.MainMenuBar;

public class Main {

	public static void main(String[] args) {
		
		mxCodecRegistry.register(new mxObjectCodec(new State()));		
		
		MainMenuBar mainMenuBar = new MainMenuBar();
		VersionPanelView versionPanelView = new VersionPanelView();
		EditorView editorView = new EditorView(versionPanelView);
		MainScreen mainScreen = new MainScreen(mainMenuBar, editorView);
		
		DiagramModel diagramModel = new DiagramModel();
		
		EditorController editorController = new EditorController(editorView, diagramModel);
		VersionPanelController versionPanelController = new VersionPanelController(versionPanelView, diagramModel);
		MainScreenController mainScreenController = new MainScreenController(mainScreen, diagramModel);
		
		versionPanelView.setController(versionPanelController);
		editorView.setController(editorController);
		mainMenuBar.setController(mainScreenController);
		mainScreen.setController(mainScreenController);		
	}

}