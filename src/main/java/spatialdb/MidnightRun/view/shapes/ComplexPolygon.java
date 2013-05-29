package spatialdb.MidnightRun.view.shapes;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class ComplexPolygon extends Polygon 
{
	public ComplexPolygon(SDOShape shape) {
		super(shape);
		// TODO Auto-generated constructor stub
	}
	public static final ShapeType type = ShapeType.COMPLEX_POLYGON;
	@Override
	public ShapeType getShapeType() 
	{
		return type;
	}
}
