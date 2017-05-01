package model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
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
		
		Hashtable<String, Object> style2 = new Hashtable<String, Object>();	
		style2.put(mxConstants.STYLE_DASHED, true);
		stylesheet.putCellStyle("DASHED", style2);
		
		Hashtable<String, Object> style3 = new Hashtable<String, Object>();	
		style3.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
		stylesheet.putCellStyle("PARENT", style3);		
		
		Hashtable<String, Object> style4 = new Hashtable<String, Object>();	
		style4.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
		stylesheet.putCellStyle("PARENT", style4);		
		
		Hashtable<String, Object> style5 = new Hashtable<String, Object>();	
		style5.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP);
		stylesheet.putCellStyle("PARENT", style5);		
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
			if (xml != null){
				document = mxXmlUtils.parseXml(URLDecoder.decode(xml, "UTF-8"));
				mxCodec codec = new mxCodec(document);
				codec.decode(document.getDocumentElement(), graph.getModel());	
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void xmlCloneIn(String xml, String name){
		Document document;
		try {
			if (xml!=null){
				document = mxXmlUtils.parseXml(URLDecoder.decode(xml, "UTF-8"));
				mxCodec codec = new mxCodec(document);
				mxGraph temp = new mxGraph();
				mxCell cell;
				graph.getModel().beginUpdate();
				try{
					cell = (mxCell) graph.insertVertex(graph.getDefaultParent(), null, name, 30, 30, 100, 50);
					cell.setStyle("PARENT");
					cell.setConnectable(false);
					codec.decode(document.getDocumentElement(), temp.getModel());	
					List<Object> cells = new ArrayList<Object>(Arrays.asList(temp.getChildCells(temp.getDefaultParent())));
					for (int i = 0; i < cells.size(); i++) {
						mxCell c = (mxCell) cells.get(i);
						if (c.isEdge()){
							c.setStyle("DASHED");
							if (c.getValue().getClass().equals(String.class)){
								c.setValue(new Edge("", "", true));
							}else {
								((Edge)c.getValue()).setDisabled(true);
							}
						} else if (c.getValue().getClass().equals(String.class)){
							cells.remove(i);
						} else {
							c.setStyle("fillColor=lightgray");
							((State)c.getValue()).setDisabled(true);
						}
					}					
					graph.addCells(temp.cloneCells(cells.toArray()), cell);	
					List<Object> toRemove = new ArrayList<>();
					for (Object object : graph.getChildEdges(cell)) {
						if (((mxCell)object).getTarget()==null || ((mxCell)object).getSource()==null){
							toRemove.add(object);
						}
					}
					graph.removeCells(toRemove.toArray());
				} finally{
					graph.getModel().endUpdate();
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}	
}
