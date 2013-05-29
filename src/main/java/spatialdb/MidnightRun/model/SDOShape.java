package spatialdb.MidnightRun.model;


public class SDOShape 
{
	public static int xScale = 30;
	public static int yScale = 25;
	public static int xOffset = 100;
	public static int yOffset = 200;
	
	private ShapeType type;
	private int id;
	private String name;
	private int[] x;
	private int[] y;
	private int eType;
	private int interpretation;
	public ShapeType getShapeType()
	{
		return this.type;
	}
	
	public void setShapeType(ShapeType type)
	{
		this.type = type;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getX() {
		return x;
	}

	public void setX(int[] x) {
		this.x = x;
	}

	public int[] getY() {
		return y;
	}

	public void setY(int[] y) {
		this.y = y;
	}

	public int getEType() {
		return eType;
	}

	public void setEType(int eType) {
		this.eType = eType;
	}

	public int getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(int interpretation) {
		this.interpretation = interpretation;
	}

	public boolean isCompound()
	{
		return false;
	}
}
