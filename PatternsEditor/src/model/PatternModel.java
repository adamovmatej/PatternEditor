package model;

import java.util.ArrayList;
import java.util.List;

public class PatternModel {
	
	private List<String> patterns;
	
	public PatternModel(){
		patterns = new ArrayList<String>();
		patterns.add("Architect also implements");
		patterns.add("Variation behind interface");
		patterns.add("Stand up meetings");
		patterns.add("Code ownership");
	}
	
	public void createPattern(String name, String desc){
		patterns.add(name);
	}

	public List<String> getPatterns() {
		return patterns;
	}

	public void setPatterns(List<String> patterns) {
		this.patterns = patterns;
	}
}
