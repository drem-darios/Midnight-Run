package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class Point implements JShape
{
	public static final ShapeType type = ShapeType.POINT;
	private SDOShape shape;
	private int x;
	private int y;
	
	public Point(SDOShape shape) 
	{
		
	}
	
	public ShapeType getShapeType() 
	{
		return type;
	}
	public int getX() 
	{
		return x;
	}
	public int getY() 
	{
		return y;
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public int getId()
	{
		return shape.getId();
	}

	public int toPixel(int yCoord) 
	{
		return 0;
	}

	public String getName() {
		return shape.getName();
	}
}
