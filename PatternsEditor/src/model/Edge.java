package model;

import java.util.HashMap;

public class Edge extends CellNode{

	private static final long serialVersionUID = 1L;

	public Edge(){
		
	}
	
	public Edge(String version) {
		currentVariation = version;
		scenes = new HashMap<>();
		names = new HashMap<>();
		disabled = new HashMap<>();
		
		scenes.put(version, null);
		names.put(version, null);
		disabled.put(version, null);
	}
	
	@Override
	public String toString() {
		return getName(currentVariation);
	}
	
}
