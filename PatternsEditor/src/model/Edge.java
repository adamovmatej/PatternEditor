package model;

public class Edge extends CellNode{

	private static final long serialVersionUID = 1L;

	public Edge(){
		this(null,null,false);
	}
	
	public Edge(String name, String scene, Boolean disable) {
		super(name, scene, disable);
	}	
}
