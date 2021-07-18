package spatialdb.MidnightRun.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import oracle.sql.ARRAY;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import spatialdb.MidnightRun.model.CompoundSDO;
import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;
import spatialdb.MidnightRun.view.shapes.CompoundShape;
import spatialdb.MidnightRun.view.shapes.JShape;

public class SDOManager 
{
	private DatabaseManager dbManager;
	private String primaryKey;
	private Logger logger =  LogManager.getLogger(SDOManager.class);
	
	public SDOManager(DatabaseManager dbManager)
	{
		Properties props = new Properties();
		try 
		{
			props.load(SDOManager.class.getResourceAsStream("/database.properties"));
		} catch (IOException e) 
		{
			logger.error("Could not load properties.", e);
		}
		
		this.primaryKey = props.getProperty("db.table.primary_key");
		
		if (primaryKey == null)
		{
			logger.error("Could not load primary key. Please add property db.table.primary_key");
		}
		else
		{
			logger.info("Primary key: " + primaryKey);
		}
		
		this.dbManager = dbManager;
	}
	
	public SDOManager(ConnectionManager connection)
	{
		this(new DatabaseManager(connection));
	}
	
	public boolean deleteShape(String shapeName)
	{
		try 
		{
			dbManager.deleteShape(shapeName);
			return true;
		} catch (SQLException e) 
		{
			logger.error("Could not delete shape: " + shapeName, e);
			return false;
		}
	}
	
	public boolean editShape(String shapeName, String points)
	{
		try 
		{
			SDOShape shape = getShapes(dbManager.getShape(shapeName)).get(0);
			dbManager.updateShape(shape, points);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<JShape> getJShapes()
	{
		try 
		{
			return toJShape(getShapes(dbManager.getShapes()));

		} catch (SQLException e) 
		{
			logger.error("Could not get shapes.", e);
			return new ArrayList<JShape>();
		}

	}
	public List<SDOShape> getSDOShapes()
	{
		try 
		{
			return getShapes(dbManager.getShapes());

		} catch (SQLException e) 
		{
			logger.error("Could not get shapes.", e);
			return new ArrayList<SDOShape>();
		}
		
	}
	
	private int[] combinePoints(int[] xs, int[] ys) 
	{
		int[] points = new int[xs.length + ys.length];
		int counter = 0;
		for (int i = 0; i < xs.length; i++)
		{
			points[counter] = xs[i];
			counter++;
			points[counter] = ys[i];
			counter++;
		}
		return points;
	}
	
	private List<SDOShape> getShapes(ResultSet results)
	{
		List<SDOShape> shapes = new ArrayList<SDOShape>();
		try {
			while (results.next()) 
			{
			    String shapeId = results.getString(primaryKey);
			    String shapeName = results.getString("NAME");		        
			    Struct shapeObject = (Struct) results.getObject("shape");
			    logger.info("ID: " + shapeId + " SHAPE: " + shapeName);
			    
			    ARRAY elmInfoArray = (ARRAY) shapeObject.getAttributes()[3];
			    ARRAY ordinatesArray = (ARRAY) shapeObject.getAttributes()[4];
			    logger.debug("ELM_INFO LENGTH: "+elmInfoArray.length());
			    logger.debug("ORDINATES LENGTH: "+ordinatesArray.length());
			    int [] elmInfo = elmInfoArray.getIntArray();
			    int [] ordinates = ordinatesArray.getIntArray();
			    
			    SDOShape shape = createShape(elmInfo, ordinates);
			    shape.setId(Integer.parseInt(shapeId));
			    shape.setName(shapeName);
			    
			    shapes.add(shape);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return shapes;
	}

	private SDOShape createShape(int[] elmInfo, int[] ordinates) 
	{
		if (ShapeType.isCompoundShape(elmInfo))
		{
			CompoundSDO compoundShape = new CompoundSDO();
			int numSegments = elmInfo.length / 3; // each segment is a triplet
			int segCounter = 0;
			logger.info("Creating shape from " + numSegments +" segments.");
			for (int segmentOffset = 0 ; segCounter < numSegments; segmentOffset += 3)
			{
				SDOShape shape = new SDOShape();
				int startIndex = elmInfo[segmentOffset+0];
				int eType = elmInfo[segmentOffset+1];
				int interpretation = elmInfo[segmentOffset+2];
				logger.debug("SEGMENT: " + segmentOffset + " Offset: " + startIndex + " E-Type: " + eType + " Interpretation: " + interpretation);
				shape.setShapeType(ShapeType.getShapeType(eType, interpretation));
				shape.setEType(eType);
				shape.setInterpretation(interpretation);
				
				int endIndex = ordinates.length; // ends at end of ordinates array
				if (numSegments > 1 && segmentOffset < (numSegments - 1))
				{
					endIndex = elmInfo[segmentOffset+3]; // ends where next segment begins
				}
				setCoordinates(shape, startIndex, endIndex, ordinates);
				compoundShape.addShape(shape);
				segCounter++;
			}	
			compoundShape.setX(getX(ordinates));
			compoundShape.setY(getY(ordinates));
			
			return compoundShape;
		}
		else
		{
			SDOShape shape = new SDOShape();
			int startIndex = elmInfo[0];
			int eType = elmInfo[1];
			int interpretation = elmInfo[2];
			int endIndex = ordinates.length;
			logger.debug("Offset: " + startIndex + " E-Type: " + eType + " Interpretation: " + interpretation);
			
			shape.setShapeType(ShapeType.getShapeType(eType, interpretation));
			shape.setEType(eType);
			shape.setInterpretation(interpretation);
			setCoordinates(shape, startIndex, endIndex, ordinates);
			
			return shape;	
		}
	}
	
	private int[] getX(int[] ordinates)
	{
		int[] xs = new int[(ordinates.length / 2)];
		int counter = 0;
		for (int i = 0; i < ordinates.length; i+= 2)
		{
			xs[counter] = ordinates[i];
			counter++;
		}
		
		return xs;
	}
	
	private int[] getY(int[] ordinates)
	{
		int[] ys = new int[(ordinates.length / 2)];
		int counter = 0;
		for (int i = 1; i < ordinates.length; i+= 2)
		{
			ys[counter] = ordinates[i];
			counter++;
		}
		
		return ys;
	}
	
	public void setCoordinates(SDOShape shape, int startIndex, int endIndex, int[] ordinates)
	{        
		int size = (int)Math.ceil((endIndex - (startIndex-1)) /2.0);
		int[] xCoord = new int[size];
		int[] yCoord = new int[size];
		int counter = 0;
		for (int i = startIndex-1; i < endIndex; i+=2)
		{
			int x = ordinates[i];
			int y = ordinates[i+1];
			
			xCoord[counter] = x;
			yCoord[counter] = y;
			counter ++;
		}
		shape.setX(xCoord);
		shape.setY(yCoord);
	}

	public List<JShape> toJShape(List<SDOShape> shapes) 
	{
		List<JShape> jshapes = new ArrayList<JShape>();
		for (SDOShape shape : shapes)
		{
			if (shape.isCompound())
			{
				JShape compoundShape = new CompoundShape((CompoundSDO)shape);
				jshapes.add(compoundShape);
			}
			else
			{
				JShape jshape = getShape(shape);
				jshapes.add(jshape);
			}
		}
		
		return jshapes;
	}
	
	public Object applyOperator(SDOOperator operator, String...shapeNames)
	{
		ResultSet results = dbManager.applyOperator(operator, shapeNames);
		try {
			switch(operator)
			{
				case AREA:
					while (results.next()) 
					{
						BigDecimal area = (BigDecimal)results.getObject("result");
						return area;
					}
					break;
				case CONVEXHULL:
					while(results.next())
					{
						Struct shapeObject = (Struct) results.getObject("result");
					    
					    ARRAY elmInfoArray = (ARRAY) shapeObject.getAttributes()[3];
					    ARRAY ordinatesArray = (ARRAY) shapeObject.getAttributes()[4];
					    logger.debug("ELM_INFO LENGTH: "+elmInfoArray.length());
					    logger.debug("ORDINATES LENGTH: "+ordinatesArray.length());
					    int [] elmInfo = elmInfoArray.getIntArray();
					    int [] ordinates = ordinatesArray.getIntArray();
					    
					    SDOShape shape = createShape(elmInfo, ordinates);
					    shape.setId(0);
					    shape.setName("ConvexHull");
					    return getShape(shape);
					}
					break;
				case COVERS:
					while (results.next()) 
					{
						String covers = results.getString("result");
						return Boolean.valueOf(covers);
					}
					break;
				case DISTANCE:
					while (results.next()) 
					{
						BigDecimal distance = (BigDecimal)results.getObject("result");
						return distance;
					}
					break;
				case MBR:
					while(results.next())
					{
						Struct shapeObject = (Struct) results.getObject("result");
					    
					    ARRAY elmInfoArray = (ARRAY) shapeObject.getAttributes()[3];
					    ARRAY ordinatesArray = (ARRAY) shapeObject.getAttributes()[4];
					    logger.debug("ELM_INFO LENGTH: "+elmInfoArray.length());
					    logger.debug("ORDINATES LENGTH: "+ordinatesArray.length());
					    int [] elmInfo = elmInfoArray.getIntArray();
					    int [] ordinates = ordinatesArray.getIntArray();
					    
					    SDOShape shape = createShape(elmInfo, ordinates);
					    shape.setId(0);
					    shape.setName("MBR");
					    return getShape(shape);
					}
					break;
				case NN:
					List<JShape> resultShapes = new ArrayList<JShape>();
				try {
					while (results.next()) 
					{	    
						String shapeName = results.getString("NAME");
						SDOShape shape = getShapes(dbManager.getShape(shapeName)).get(0);
					    resultShapes.add(getShape(shape));
					}
					
					return resultShapes;
				} catch (SQLException e) {
					e.printStackTrace();
				}
					break;
				case TOUCHES:
					while (results.next()) 
					{
						String touches = results.getString("result");
						return Boolean.valueOf(touches);
					}
					break;
				case UNION:
					while(results.next())
					{
						Struct shapeObject = (Struct) results.getObject("result");
					    
					    ARRAY elmInfoArray = (ARRAY) shapeObject.getAttributes()[3];
					    ARRAY ordinatesArray = (ARRAY) shapeObject.getAttributes()[4];
					    logger.debug("ELM_INFO LENGTH: "+elmInfoArray.length());
					    logger.debug("ORDINATES LENGTH: "+ordinatesArray.length());
					    int [] elmInfo = elmInfoArray.getIntArray();
					    int [] ordinates = ordinatesArray.getIntArray();
					    
					    SDOShape shape = createShape(elmInfo, ordinates);
					    shape.setId(0);
					    shape.setName("Union");
					    return getShape(shape);
					}
					break;
				default:
					break;
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return null;
	}
	
	
	private static JShape getShape(SDOShape shape)
	{
		return shape.getShapeType().getJShape(shape);
	}

	public void createShape(ShapeType type, String shapeName, String points) 
	{
		SDOShape shape = new SDOShape();
		shape.setName(shapeName);
		shape.setShapeType(type);
		shape.setEType(type.getEType());
		shape.setInterpretation(type.getInterpretation());
		
		try 
		{
			dbManager.createShape(shape, points);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}
}
