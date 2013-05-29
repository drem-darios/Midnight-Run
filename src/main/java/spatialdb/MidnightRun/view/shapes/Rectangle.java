package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class Rectangle extends Polygon 
{
	public static final ShapeType type = ShapeType.RECTANGLE;
	private SDOShape shape;
	private int x;
	private int y;
	private int[] xPoints = new int[4];
	private int[] yPoints = new int[4];
	private int width;
	private int height;
	
	public Rectangle(SDOShape shape) 
	{
		super(shape);
		this.shape = shape;
		init();
	}
	
	private void init()
	{
		int x1 = shape.getX()[0];
		int y1 = shape.getY()[0];
		int x2 = shape.getX()[1];
		int y2 = shape.getY()[1];
		
		xPoints[0] = x1;
		xPoints[1] = x2;
		xPoints[2] = x2;
		xPoints[3] = x1;
		
		yPoints[0] = y1;
		yPoints[1] = y1;
		yPoints[2] = y2;
		yPoints[3] = y2;
		
		// derived points
		if (x2 > x1)
		{
			if (y2 > y1)
			{
				//x1, y1 is bottom left corner x2, y2 is top right
				this.x = x1;
				this.y = y2;
			}
			else
			{
				// x1, y1 is top left corner x2, y2 is bottom right
				this.x = x1;
				this.y = y1;
			}
		}
		else // if (x2 < x1)
		{
			if (y2 > y1)
			{
				//x1, y1 is bottom right corner x2, y2 is top left
				this.x = x2;
				this.y = y2;
			}
			else
			{
				//x1, y1 is top right corner x2, y2 is bottom left
				this.x = x2;
				this.y = y1;
			}
		}
		
		//calculate height and width
		this.width = Math.abs(x2 - x1);
		this.height = Math.abs(y2 - y1);
	}
	
	@Override
	public ShapeType getShapeType() 
	{
		return type;
	}
	@Override
	public void draw(Graphics g)
	{
		g.drawRect(x*SDOShape.xScale, 500 - (y*SDOShape.yScale), width*SDOShape.xScale, height*SDOShape.yScale);
	}
}
