package model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import model.db.SQLConnection;

public class Diagram extends mxGraphComponent{

	private static final long serialVersionUID = 1L;
	
	private String pattern;
	private Variation currentVariation;
	private Map<String, Version> versions;
	private Map<String, Adapter> adapters;
  	private mxRubberband rubberband;
	  	
  	public Diagram(mxGraph graph, String pattern, VariationModel model) {
		super(graph);
		this.getGraph().setAllowDanglingEdges(false);
		this.getGraph().setCellsEditable(false);
		this.rubberband = new mxRubberband(this);
		this.graph.setCellsDeletable(true);

		this.pattern = pattern;
		this.versions = new HashMap<>();
		this.adapters = new HashMap<>();
		
		initStyleSheet();
		
		if (model == null){
			addVersion(new Version(pattern, "Default"));			
		} else {
			initVariations(model);
		}
  	}

	private void initStyleSheet() {
		mxStylesheet stylesheet = graph.getStylesheet();
		Hashtable<String, Object> style = new Hashtable<String, Object>();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		style.put(mxConstants.STYLE_FONTCOLOR, "white");
		stylesheet.putCellStyle("CIRCLE", style);
	}

	public void changeVersion(String name) {
		setCurrentVariation(getVersion(name));
		System.out.println(currentVariation.getSecondaryPattern());
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
		versions.put(version.getSecondaryPattern(), version);
		dbSaveVersion(version);
	}
	
	public void addAdapter(Adapter adapter) {
		setCurrentVariation(adapter);
		adapters.put(adapter.getSecondaryPattern(), adapter);
		dbSaveAdapter(adapter);
	}
	
	public String getXml(){
		mxCodec codec = new mxCodec();
		String xml = null;
		try {
			xml = URLEncoder.encode(mxXmlUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return xml;
	}
	
	public void xmlIn(String xml){
		Document document;
		try {
			document = mxXmlUtils.parseXml(URLDecoder.decode(xml, "UTF-8"));
			mxCodec codec = new mxCodec(document);
			codec.decode(document.getDocumentElement(), graph.getModel());	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void initVariations(VariationModel model){
		String variation;
		for (int i = 0; i < model.getMainTableModel().getRowCount(); i++){
			variation = (String) model.getMainTableModel().getValueAt(i, 0);
			versions.put(variation, new Version(pattern, variation));
		}
		for (int i = 0; i < model.getSecondaryTableModel().getRowCount(); i++){
			variation = (String) model.getSecondaryTableModel().getValueAt(i, 0);
			adapters.put(variation, new Adapter(pattern, variation));
		}
		currentVariation = versions.get("Default");
		System.out.println(currentVariation.getSecondaryPattern());
	}
	
	private void dbSaveVersion(Version version){
		String sql = "INSERT INTO version(mainPattern,secondaryPattern) VALUES(?,?)";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, version.getMainPattern());
            pstmt.setString(2, version.getSecondaryPattern());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	System.out.println(sql);
            System.out.println(e.getMessage());
        } finally {
        	try {
        		if (connection != null){
        			connection.close();
        		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	private void dbSaveAdapter(Adapter adapter){
		String sql = "INSERT INTO adapter(mainPattern,secondaryPattern) VALUES(?,?)";
		Connection connection = SQLConnection.getInstance().getConnection();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, adapter.getMainPattern());
            pstmt.setString(2, adapter.getSecondaryPattern());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	System.out.println(sql);
            System.out.println(e.getMessage());
        } finally {
        	try {
        		if (connection != null){
        			connection.close();
        		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
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
