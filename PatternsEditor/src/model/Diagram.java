package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.view.mxGraph;

public class Diagram extends mxGraphComponent{

	private static final long serialVersionUID = 1L;
	
	private String pattern;
	private Variation currentVariation;
	private Boolean main;
	private Map<String, Version> versions;
	private Map<String, Adapter> adapters;
  	private mxRubberband rubberband;
	
  	public Diagram(mxGraph graph, String pattern) {
		super(graph);
		this.getGraph().setAllowDanglingEdges(false);
		this.rubberband = new mxRubberband(this);
		this.graph.setCellsDeletable(true);

		this.pattern = pattern;	
		this.versions = new HashMap<>();
		this.adapters = new HashMap<>();
		
		Version version = new Version(pattern, "Default");
		setCurrentVariation(version);
		versions.put("Default", version);
  	}

	public void changeVersion(String name) {
		setCurrentVariation(getVersion(name));
		graph.refresh();
		graph.repaint();
	}
	
	public void changeAdapter(String name) {
		setCurrentVariation(getAdapter(name));
		graph.refresh();
		graph.repaint();
	}

	public void addVersion(Version version) {
		setCurrentVariation(version);
		versions.put(version.getMainPattern(), version);
	}
	
	public void addAdapter(Adapter adapter) {
		setCurrentVariation(adapter);
		adapters.put(adapter.getMainPattern(), adapter);
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	private Version getVersion(String key){
		return versions.get(key);
	}

	private Variation getAdapter(String key) {
		return adapters.get(key);
	}

	public Variation getCurrentVariation() {
		return currentVariation;
	}

	public void setCurrentVariation(Variation currentVariation) {
		this.currentVariation = currentVariation;
	}

	public Map<String, Version> getVersions() {
		return versions;
	}

	public void setVersions(Map<String, Version> versions) {
		this.versions = versions;
	}

	public Map<String, Adapter> getAdapters() {
		return adapters;
	}

	public void setAdapters(Map<String, Adapter> adapters) {
		this.adapters = adapters;
	}

}
