package model;

import java.io.Serializable;

public class Version implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pattern;
	private String version;
	private Boolean main;
	
	public Version(String pattern, String version, Boolean main) {
		this.setPattern(pattern);
		this.setVersion(version);
		this.setMain(main);
	}
	
	public String getComplexName(){
		String result = pattern;
		if (main){
			result += "-default";
		} else {
			result += "-ver-" + version;
		}
		return result;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean getMain() {
		return main;
	}

	public void setMain(Boolean main) {
		this.main = main;
	}
}
