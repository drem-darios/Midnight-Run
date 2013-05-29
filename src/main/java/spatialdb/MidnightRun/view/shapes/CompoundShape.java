package spatialdb.MidnightRun.view.shapes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import spatialdb.MidnightRun.model.CompoundSDO;
import spatialdb.MidnightRun.model.SDOShape;

public class CompoundShape implements JShape
{
	private CompoundSDO shape;
	private List<JShape> shapes = new ArrayList<JShape>();
	
	public CompoundShape(CompoundSDO shape)
	{
		this.shape = shape;
		init();
	}
	
	public void draw(Graphics g) 
	{
		for (JShape jshape : shapes)
		{
			jshape.draw(g);
		}
	}

	public int getId() {
		return shape.getId();
	}

	public String getName() {
		return shape.getName();
	}
	
	private void init() 
	{
		shapes.clear();
		List<SDOShape> compoundShapes = shape.getShapes();
		for (SDOShape cShape : compoundShapes)
		{
			if (cShape.isCompound())
			{
				JShape jshape = new CompoundShape((CompoundSDO)cShape);
				shapes.add(jshape);
			}
			else
			{
				JShape jshape = getShape(cShape);
				shapes.add(jshape);	
			}
		}
	}
	
	private static  JShape getShape(SDOShape shape)
	{
		return shape.getShapeType().getJShape(shape);
	}
}
