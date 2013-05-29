package spatialdb.MidnightRun.model;

import org.apache.log4j.Logger;

import spatialdb.MidnightRun.view.shapes.Circle;
import spatialdb.MidnightRun.view.shapes.ComplexLine;
import spatialdb.MidnightRun.view.shapes.ComplexPolygon;
import spatialdb.MidnightRun.view.shapes.JShape;
import spatialdb.MidnightRun.view.shapes.Line;
import spatialdb.MidnightRun.view.shapes.Point;
import spatialdb.MidnightRun.view.shapes.Polygon;
import spatialdb.MidnightRun.view.shapes.Rectangle;

public enum ShapeType {

	CIRCLE {
		@Override
		public JShape getJShape(SDOShape shape) {
			return new Circle(shape);
		}

		@Override
		public int getEType() {
			return 1003;
		}

		@Override
		public int getInterpretation() {
			return 4;
		}
	},
	COMPLEX_LINE {
		@Override
		public JShape getJShape(SDOShape shape) {
			return new ComplexLine(shape);
		}

		@Override
		public int getEType() {
			return 2;
		}

		@Override
		public int getInterpretation() {
			return 2;
		}
	},
	COMPLEX_POLYGON {
		@Override
		public JShape getJShape(SDOShape shape) {
			return new ComplexPolygon(shape);
		}

		@Override
		public int getEType() {
			return 1003;
		}

		@Override
		public int getInterpretation() {
			return 2;
		}
	},
	LINE {
		@Override
		public JShape getJShape(SDOShape shape) {
			return new Line(shape);
		}

		@Override
		public int getEType() {
			return 2;
		}

		@Override
		public int getInterpretation() {
			return 1;
		}
	},
	POINT {
		@Override
		public JShape getJShape(SDOShape shape) {
			return new Point(shape);
		}

		@Override
		public int getEType() {
			return 1;
		}

		@Override
		public int getInterpretation() {
			return 1;
		}
	},
	POLYGON {
		@Override
		public JShape getJShape(SDOShape shape) {
			return new Polygon(shape);
		}

		@Override
		public int getEType() {
			return 1003;
		}

		@Override
		public int getInterpretation() {
			return 1;
		}
	},
	RECTANGLE {
		@Override
		public JShape getJShape(SDOShape shape) {
			return new Rectangle(shape);
		}

		@Override
		public int getEType() {
			return 1003;
		}

		@Override
		public int getInterpretation() {
			return 3;
		}
	};
	
	private static final Logger logger = Logger.getLogger(ShapeType.class);
	public static ShapeType getShapeType(int elmType, int interp)
	{
		
		switch(elmType)
		{
			case 1:
				if (interp == 1)
				{
					return ShapeType.POINT;
				}
				else
				{
					logger.warn("Invalid interpretation." + " etype: " + elmType + " interp: " + interp);
				}
				break;
			case 2:
				if (interp == 1)
				{
					return ShapeType.LINE;
				}
				else if (interp == 2)
				{
					return ShapeType.COMPLEX_LINE;
				}
				else
				{
					logger.warn("Invalid interpretation." + " etype: " + elmType + " interp: " + interp);
				}
				break;
			case 1003:
			case 2003:
				if (interp == 1)
				{
					return ShapeType.POLYGON;
				}
				else if (interp == 2)
				{
					return ShapeType.COMPLEX_POLYGON;
				}
				else if (interp == 3)
				{
					return ShapeType.RECTANGLE;
				}
				else if (interp == 4)
				{
					return ShapeType.CIRCLE;
				}
				else
				{
					logger.warn("Invalid interpretation." + " etype: " + elmType + " interp: " + interp);
				}
				break;
			default:
				break;
		}
		
		return null;
	}
	
	public static boolean isCompoundShape(int[] elmArray)
	{
		return elmArray.length / 3 > 1;
	}

	public abstract JShape getJShape(SDOShape shape);

	public abstract int getEType();
	
	public abstract int getInterpretation();
}
