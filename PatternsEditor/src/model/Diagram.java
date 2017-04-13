package model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.view.mxGraph;

public class Diagram extends mxGraphComponent{

	private static final long serialVersionUID = 1L;
	
	private Boolean hasFile = false;
	private File file = null;
	private String pattern;
	private Version currentVersion;
	private Boolean main;
	private Map<String, Version> versions;
  	private mxRubberband rubberband;
	
	public Diagram(mxGraph graph, String pattern, String version, Boolean main) {
		super(graph);
		
		this.pattern = pattern;	
		currentVersion = new Version(pattern, version, main);
		this.versions = new HashMap<String, Version>();
		versions.put(version, currentVersion);
		this.getGraph().setAllowDanglingEdges(false);
		this.graph.setCellsDeletable(true);
		this.rubberband = new mxRubberband(this);
	}
	
	private void addVersion(Version version){
		versions.put(version.getVersion(), version);
	}

	public void changeVersion(String name) {
		currentVersion = getVersion(name);
	}

	public void setCurrentFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Boolean getHasFile() {
		return hasFile;
	}

	public void setHasFile(Boolean hasFile) {
		this.hasFile = hasFile;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String name) {
		this.pattern = name;
	}

	public Version getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(Version version) {
		this.currentVersion = version;
	}

	public Boolean getMain() {
		return main;
	}

	public void setMain(Boolean main) {
		this.main = main;
	}

	public void createVersion(Version version) {
		addVersion(version);
		setCurrentVersion(version);
		
	}
	
	public Version getVersion(String name){
		return versions.get(name);
	}

	public Map<String, Version> getVersions() {
		return versions;
	}

	public void setVersions(Map<String, Version> versions) {
		this.versions = versions;
	}
}
