package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class Polygon implements JShape 
{
	public static final ShapeType type = ShapeType.POLYGON;
	private SDOShape shape;
	private int[] xPoints;
	private int[] yPoints;
	private int numPoints;
	
	public Polygon(SDOShape shape)
	{
		this.shape = shape;
		init();
	}
	private void init() 
	{
		int[] x = shape.getX();
		int[] y = shape.getY();
		xPoints = new int[x.length];
		yPoints = new int[y.length];
		for (int i = 0; i < x.length; i++)
		{
			xPoints[i] = x[i]*SDOShape.xScale;
			yPoints[i] = 500-(y[i]*SDOShape.yScale);
		}
	}
	public ShapeType getShapeType() 
	{
		return type;
	}
	public int[] getYPoints() 
	{
		return yPoints;
	}
	public int[] getXPoints() 
	{
		return xPoints;
	}
	public int numPoints() 
	{
		return numPoints;
	}
	public void draw(Graphics g) 
	{
		g.drawPolygon(xPoints, yPoints, xPoints.length);
		
	}
	public int toPixel(int yCoord) {
		return 0;
	}
	public int getId() {
		return shape.getId();
	}
	public String getName() {
		return shape.getName();
	}
}
