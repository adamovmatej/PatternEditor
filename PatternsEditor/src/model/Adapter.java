package model;

import java.io.Serializable;
import java.util.List;

import com.mxgraph.view.mxGraph;

public class Adapter implements Serializable{

	private static final long serialVersionUID = 1L;
	private String pattern;
	private List<String> patterns;	
	private Diagram diagram;
		
	public Adapter(String pattern, List<String> patterns) {
		this.setPattern(pattern);
		if (patterns != null){
			this.setPatterns(patterns);
		}
		diagram = new Diagram(new mxGraph(), pattern);
	}
	
	public String getLineName(){
		String name = "";
		for (String string : patterns) {
			name += string + "\n";
		}
		return name;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}

}
