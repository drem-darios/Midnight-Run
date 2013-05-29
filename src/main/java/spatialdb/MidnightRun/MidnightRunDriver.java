package spatialdb.MidnightRun;

import java.util.List;

import javax.swing.JFrame;

import spatialdb.MidnightRun.controller.ConnectionManager;
import spatialdb.MidnightRun.controller.SDOManager;
import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.view.MidnightRunWindow;
import spatialdb.MidnightRun.view.shapes.JShape;

public class MidnightRunDriver {

	public static void main(String[] args) {
		SDOManager shpManager = new SDOManager(new ConnectionManager());
		List<SDOShape> shapes = shpManager.getSDOShapes();
		List<JShape> jshapes = shpManager.toJShape(shapes);
		
		JFrame window = new MidnightRunWindow(shpManager, jshapes);
		window.setSize(775,550);
		window.setVisible(true);
	}

}
