package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;

public interface JShape {

	public void draw(Graphics g);
	public int getId();
	public String getName();
}
