package model;

import java.io.Serializable;

public class Edge implements Serializable{

private static final long serialVersionUID = 1L;
	
	private int id;
	private String scene;
	private String name;
	private Boolean disabled;

	public Edge(){
	}
	
	public Edge(String name, String scene, Boolean disable) {
		this.setName(name);
		this.setScene(scene);
		this.setDisabled(disable);
	}
	
	public void update(String name, String scene, Boolean disable){
		this.setName(name);
		this.setScene(scene);
		this.setDisabled(disable);
	}	

	public void update(String name, String scene){
		this.setName(name);
		this.setScene(scene);
	}	
	
	@Override
	public String toString() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
