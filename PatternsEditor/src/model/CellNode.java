package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Map;

public class CellNode implements Serializable, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	protected String currentVariation;
	protected Map<String, String> scenes;
	protected Map<String, String> names;
	protected Map<String, Boolean> disabled;
	
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
			disabled.replace(version, disable);
		} else {
			disabled.put(version, disable);
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
		Boolean result = disabled.get(version);
		if (!(result==null)){
			return result;
		} else {
			return false;
		}
	}
}
