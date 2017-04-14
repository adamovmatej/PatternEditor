package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class State implements Serializable {

	private static final long serialVersionUID = 4673115549643245981L;

	private Diagram diagram;
	private Map<String, String> scenes;
	private Map<String, String> names;
	
	public State(){
	}
	
	public State(String name, String scene, String version, Diagram diagram) {
		this.diagram = diagram;
		scenes = new HashMap<>();
		names = new HashMap<>();
		scenes.put(version, scene);
		names.put(version, name);
	}
	
	public void update(String name, String scene, String version){
		if (scenes.containsKey(version)){
			scenes.replace(version, scene);			
		} else {
			scenes.put(version, scene);
		}
		if (names.containsKey(version)){
			names.replace(version, scene);			
		} else {
			names.put(version, scene);
		}
	}
	
	public String getScene(String version){
		String result = scenes.get(version);
		if (!(result==null)){
			return result;
		} else {
			String def = scenes.get("Default");
			if (!(def==null)){
				return def;
			} else {
				return "Default - empty";
			}
		}
	}
	
	public String getName(String version){
		String result = names.get(version);
		if (!(result==null)){
			return result;
		} else {
			String def = names.get("Default");
			if (!(def==null)){
				return def;
			} else {
				return "Default - empty";
			}
		}
	}
	
	@Override
	public String toString() {
		return getName(diagram.getCurrentVersion().getVersion());
	}

	public Map<String, String> getNames() {
		return names;
	}

	public void setNames(Map<String, String> names) {
		this.names = names;
	}
}

