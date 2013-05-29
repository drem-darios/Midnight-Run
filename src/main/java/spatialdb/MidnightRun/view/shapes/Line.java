package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class Line implements JShape
{
	public static final ShapeType type = ShapeType.LINE;
	private SDOShape shape;
	List<LineSegment> segments = new ArrayList<LineSegment>();
	
	public Line(SDOShape shape)
	{
		this.shape = shape;
		init();
	}
	private void init() 
	{
		int[] xPoints = shape.getX();
		int[] yPoints = shape.getY();
		for (int i = 0; i < xPoints.length-1; i++)
		{
			LineSegment segment = new LineSegment(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
			segments.add(segment);
		}
			
	}
	
	public ShapeType getShapeType() 
	{
		return type;
	}
	
	public void draw(Graphics g) 
	{
		
		for (LineSegment segment : segments)
		{
			g.drawLine(segment.startX * SDOShape.xScale, toPixel(segment.startY * SDOShape.yScale), segment.endX * SDOShape.xScale, toPixel(segment.endY * SDOShape.yScale));
		}
	}
	
	public int toPixel(int y)
	{
		return 500-y;
	}
	
	class LineSegment
	{
		int startX;
		int startY;
		int endX;
		int endY;
		
		public LineSegment(int startX, int startY, int endX, int endY)
		{
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
		}
	}

	public int getId() {
		return shape.getId();
	}
	public String getName() {
		return shape.getName();
	}
}
