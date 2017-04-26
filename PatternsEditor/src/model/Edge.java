package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Edge implements Serializable, PropertyChangeListener{

	private static final long serialVersionUID = 1L;

	private String currentVariation;
	private Map<String, String> scenes;
	private Map<String, String> names;
	private Map<String, String> disabled;
	
	public Edge(){	
		currentVariation = "Default";
		scenes = new HashMap<String, String>();
		names = new HashMap<String, String>();
		disabled = new HashMap<String, String>();
	}
	
	public Edge(String version) {
		currentVariation = version;
		scenes = new HashMap<String, String>();
		names = new HashMap<String, String>();
		disabled = new HashMap<String, String>();
		
		scenes.put(version, null);
		names.put(version, null);
		disabled.put(version, null);
	}
	
	@Override
	public String toString() {
		return getName(currentVariation);
	}

	public void update(String name, String scene, String version, Boolean disable){
		if (scenes.containsKey(version)){
			scenes.replace(version, scene);			
		} else {
			scenes.put(version, scene);
		}
		if (names.containsKey(version)){
			names.replace(version, name);			
		} else {
			names.put(version, name);
		}
		if (disabled.containsKey(version)){
			disabled.replace(version, disable.toString());
		} else {
			disabled.put(version, disable.toString());
		}
	}
	
	public void update(String name, String scene, String version){
		if (scenes.containsKey(version)){
			scenes.replace(version, scene);			
		} else {
			scenes.put(version, scene);
		}
		if (names.containsKey(version)){
			names.replace(version, name);			
		} else {
			names.put(version, name);
		}
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("variationChange") || evt.getPropertyName().equals("newVariation")){
			currentVariation = ((Variation) evt.getNewValue()).getSecondaryPattern();
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
	
	public Boolean getDisable(String version){
		String result = disabled.get(version);
		if (!(result==null)){
			return Boolean.valueOf(result);
		} else {
			return false;
		}
	}

	public Map<String, String> getScenes() {
		return scenes;
	}

	public void setScenes(Map<String, String> scenes) {
		this.scenes = scenes;
	}

	public Map<String, String> getNames() {
		return names;
	}

	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public Map<String, String> getDisabled() {
		return disabled;
	}

	public void setDisabled(Map<String, String> disabled) {
		this.disabled = disabled;
	}
	
}
