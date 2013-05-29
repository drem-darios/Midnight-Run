package spatialdb.MidnightRun.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CompoundSDO extends SDOShape 
{
	private List<SDOShape> shapes = new ArrayList<SDOShape>();
	
	@Override
	public boolean isCompound()
	{
		return true;
	}
	
	public void addShape(SDOShape shape)
	{
		shapes.add(shape);
	}
	
	public List<SDOShape> getShapes()
	{
		return shapes;
	}
	
}
