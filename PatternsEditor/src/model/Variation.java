package model;

import java.io.Serializable;

public class Variation implements Serializable{

	private static final long serialVersionUID = 1L;
	private String mainPattern;
	private String secondaryPattern;
	
	
	public Variation() {		
	}
	
	public Variation(String mainPattern, String secondaryPattern) {
		this.setMainPattern(mainPattern);
		this.setSecondaryPattern(secondaryPattern);
	}

	public String getMainPattern() {
		return mainPattern;
	}

	public void setMainPattern(String mainPattern) {
		this.mainPattern = mainPattern;
	}

	public String getSecondaryPattern() {
		return secondaryPattern;
	}

	public void setSecondaryPattern(String secondaryPattern) {
		this.secondaryPattern = secondaryPattern;
	}	
}
