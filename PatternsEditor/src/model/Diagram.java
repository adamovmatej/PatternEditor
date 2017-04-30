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

import javax.swing.JOptionPane;

import org.w3c.dom.Document;

import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
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
  	private mxRubberband rubberband;
	  	
  	public Diagram(mxGraph graph, String pattern) {
		super(graph);
		this.getGraph().setAllowDanglingEdges(false);
		this.getGraph().setCellsEditable(false);
		this.rubberband = new mxRubberband(this);
		this.graph.setCellsDeletable(true);
		initStyleSheet();
		
		this.pattern = pattern;
  	}

	private void initStyleSheet() {
		mxStylesheet stylesheet = graph.getStylesheet();
		Hashtable<String, Object> style = new Hashtable<String, Object>();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		style.put(mxConstants.STYLE_FONTCOLOR, "white");
		stylesheet.putCellStyle("CIRCLE", style);
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
	
	public void xmlCloneIn(String xml, String name){
		Document document;
		try {
			document = mxXmlUtils.parseXml(URLDecoder.decode(xml, "UTF-8"));
			mxCodec codec = new mxCodec(document);
			mxGraph temp = new mxGraph();
			mxCell cell;
			graph.getModel().beginUpdate();
			try{
				cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, name, 30, 30, 100, 50);
				if (xml!=null){
					codec.decode(document.getDocumentElement(), temp.getModel());	
					graph.addCells(temp.cloneCells(temp.getChildCells(temp.getDefaultParent())), cell);				
				}
			} finally{
				graph.getModel().endUpdate();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void dbSaveAdapter(Adapter adapter){
		/*
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
        */
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}	
}
