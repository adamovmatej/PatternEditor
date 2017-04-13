package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class State implements Serializable {

	private static final long serialVersionUID = 4673115549643245981L;
	
	private String name;
	private Map<String, String> scenes;
	
	public State(){
	}
	
	public State(String name, String scene, String version) {
		scenes = new HashMap<>();
		scenes.put(version, scene);
		this.name = name;
	}
	
	public void update(String name, String scene, String version){
		this.name = name;
		if (scenes.containsKey(version)){
			scenes.replace(version, scene);			
		} else {
			scenes.put(version, scene);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public String toString() {
		return name;
	}
}

