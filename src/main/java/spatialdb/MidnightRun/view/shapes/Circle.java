package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class Circle extends Polygon 
{
	public static final ShapeType type = ShapeType.CIRCLE;
	private SDOShape shape;
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Circle(SDOShape shape) 
	{
		super(shape);
		this.shape = shape;
		init();
	}
	
	@Override
	public ShapeType getShapeType() 
	{
		return type;
	}
	@Override
	public void draw(Graphics g)
	{
		int[] x = shape.getX();
		int[] y = shape.getY();
		drawCircle(x, y, g);
//		g.drawOval(x, y, width, height);
	}
	
	private void init()
	{
		
	}
	
	private double getDistance(int x1, int y1, int x2, int y2)
    {
		int dx = x2 - x1;
		int dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
    }
	
	private void drawCircle(int[] xPoints, int[] yPoints, Graphics g)
	{
		int x1 = xPoints[0];
		int y1 = yPoints[0];
		int x2 = xPoints[1];
		int y2 = yPoints[1];
		int x3 = xPoints[2];
		int y3 = yPoints[2];
		
		int lineACenterX;
		int lineACenterY;
		int dyLineA;
		int dxLineA;
		int lineBCenterX;
		int lineBCenterY;
		int dyLineB;
		int dxLineB;
		int intersectX; 
		int intersectY;
		double radius;
		

		lineACenterX = (x2 + x1) / 2;
		lineACenterY = (y2 + y1) / 2;
		dxLineA = x2 - x1;
		dyLineA = -(y2 - y1);
		lineBCenterX = (x3 + x2) / 2;
		lineBCenterY = (y3 + y2) / 2;
		dxLineB = x3 - x2;
		dyLineB = -(y3 - y2);
		intersectX = (lineACenterY * dyLineA * dyLineB + lineBCenterX * dyLineA * dxLineB - lineACenterX * dxLineA * dyLineB - lineBCenterY * dyLineA * dyLineB)/ (dyLineA * dxLineB - dxLineA * dyLineB);
		intersectY = (intersectX - lineACenterX) * dxLineA / dyLineA + lineACenterY;
		radius = getDistance(x1, y1, intersectX, intersectY);
		System.out.println(intersectX + " : " + intersectY + " : " + radius);
//		for( double t_Angle = 0; t_Angle <= 360; t_Angle += 0.01 )
//		{
//		int i = (int) (intersectX +(radius+1)*Math.cos( 3.14 ));
//		int j = (int) (intersectY +(radius+1)*Math.sin( 3.14*t_Angle ));
//		g.drawOval(i * DBShape.xScale, 500 -(j * DBShape.yScale), (int)radius*2*DBShape.xScale, (int)radius*2*DBShape.yScale);
//		System.out.println(i+ " " +j + " " + t_Angle);
//		} 
		
		int i = (int) (intersectX +(radius+1)*Math.cos(3.14*250));
		int j = (int) (intersectY +(radius+1)*Math.sin(3.14*-300));
		g.drawOval(i * SDOShape.xScale, 500-(j * SDOShape.yScale), (int)radius*2*SDOShape.xScale, (int)radius*2*SDOShape.yScale);
		System.out.println(i+ " " +j);
		
	}
	public int getX() 
	{
		return x;
	}
	public int getY() 
	{
		return y;
	}
	public int getWidth() 
	{
		return width;
	}
	public int getHeight() 
	{
		return height;
	}
//	private void compute(int[] xs, int[] ys)
//	{
//		int x1 = xs[0];
//		int x2 = xs[1];
//		int x3 = xs[2];
//		
//		int y1 = xs[0];
//		int y2 = xs[1];
//		int y3 = xs[2];
//		
//		double ma = ((y2-y1) / (x2-x1));
//		double mb = ((y3-y2) / (x3-x2));
//		
//	}
	
}
