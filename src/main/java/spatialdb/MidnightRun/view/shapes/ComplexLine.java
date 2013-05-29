package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class ComplexLine extends Line 
{
	public static final ShapeType type = ShapeType.COMPLEX_LINE;
	public ComplexLine(SDOShape shape) {
		super(shape);
	}
	@Override
	public ShapeType getShapeType() 
	{
		return type;
	}
	@Override
	public void draw(Graphics g)
	{
		for (LineSegment segment : segments)
		{
			System.out.println(segment.startX * SDOShape.xScale +" "+ segment.startY * SDOShape.yScale +" "+ segment.endX * SDOShape.xScale +" "+ segment.endY * SDOShape.yScale);
			g.drawLine(segment.startX * SDOShape.xScale, 500-(segment.startY * SDOShape.yScale), segment.endX * SDOShape.xScale, 500-(segment.endY * SDOShape.yScale));
		}
	}
}
