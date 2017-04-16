package model;

import java.util.HashMap;

public class State extends CellNode{

	private static final long serialVersionUID = 1L;

	public State(){
		
	}
	
	public State(String name, String scene, Boolean disable, String version) {
		currentVersion = version;
		scenes = new HashMap<>();
		names = new HashMap<>();
		disabled = new HashMap<>();
		
		scenes.put(version, scene);
		names.put(version, name);
		disabled.put(version, disable);
	}	
	
	@Override
	public String toString() {
		return getName(currentVersion);
	}
}

