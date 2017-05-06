package model;

import java.io.Serializable;

public class PatternObject implements Serializable{
	 
	private static final long serialVersionUID = 1L;

	private String name;
	private String description;
	
	public PatternObject(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
