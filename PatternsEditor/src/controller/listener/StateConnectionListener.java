package controller.listener;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;

import model.Edge;

public class StateConnectionListener implements mxIEventListener {

	@Override
	public void invoke(Object sender, mxEventObject evt) {
		mxCell cell = (mxCell) evt.getProperty("cell");
		cell.setValue(new Edge("", "", false));
	}

}
