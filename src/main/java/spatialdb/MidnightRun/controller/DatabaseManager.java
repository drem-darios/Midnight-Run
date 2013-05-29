package spatialdb.MidnightRun.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.model.ShapeType;

public class DatabaseManager {

	private static Logger logger = Logger.getLogger(DatabaseManager.class);
	private ConnectionManager connManager;
	private String table;
	private String primaryKey;
	public DatabaseManager(ConnectionManager connManager)
	{
		Properties props = new Properties();
		try 
		{
			props.load(ConnectionManager.class.getResourceAsStream("/database.properties"));
		} catch (IOException e) 
		{
			logger.error("Could not load properties.", e);
		}
		this.table = props.getProperty("db.table.name");
		this.primaryKey = props.getProperty("db.table.primary_key");
		if (table == null)
		{
			logger.error("Could not load table name. Please add property db.table.name");
		}
		else
		{
			logger.info("Using table: " + table);
		}
	
		this.connManager = connManager;
		if (!connManager.isConnected())
		{
			connManager.connect();
		}
	}
	
	public ResultSet getShapes() throws SQLException
	{
		StringBuilder sb = new StringBuilder("SELECT * FROM ");
		sb.append(table);
		
		ResultSet result = connManager.executeQuery(sb.toString());
		return result;
	}
	
	public ResultSet getShape(String shapeName) throws SQLException
	{
		StringBuilder sb = new StringBuilder("SELECT * FROM ");
		sb.append(table);
		sb.append(" WHERE name = '");
		sb.append(shapeName);
		sb.append("'");
		
		ResultSet result = connManager.executeQuery(sb.toString());
		return result;
	}
	
	public void deleteShape(String shapeName) throws SQLException
	{
		StringBuilder sb = new StringBuilder("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE name = '");
		sb.append(shapeName);
		sb.append("'");
		
		connManager.executeQuery(sb.toString());
	}
	
	private void deleteShape(int shapeId) throws SQLException
	{
		StringBuilder sb = new StringBuilder("DELETE FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(primaryKey);
		sb.append(" = '");
		sb.append(shapeId);
		sb.append("'");
		
		connManager.executeQuery(sb.toString());
	}
	
	public void updateShape(SDOShape shape, String points) throws SQLException
	{
		createShape(shape, points);
		deleteShape(shape.getId());
	}
	
	public int createShape(SDOShape shape, String points) throws SQLException {
		Random generator = new Random();
		int id = generator.nextInt(1000);
		System.out.println(id);
	    StringBuilder sb = new StringBuilder("INSERT INTO ");
	    sb.append(table);
	    sb.append(" VALUES( ");
	    sb.append(id);
	    sb.append(", '");
	    sb.append(shape.getName());
	    if (shape.getShapeType() == ShapeType.LINE || shape.getShapeType() == ShapeType.COMPLEX_LINE)
	    {
	    	sb.append("', MDSYS.SDO_GEOMETRY( 2002, NULL, NULL, MDSYS.SDO_ELEM_INFO_ARRAY(1,");
	    }
	    else
	    {
	    	sb.append("', MDSYS.SDO_GEOMETRY( 2003, NULL, NULL, MDSYS.SDO_ELEM_INFO_ARRAY(1,");
	    }
	    sb.append(shape.getEType());
	    sb.append(", ");
	    sb.append(shape.getInterpretation());
	    sb.append("), MDSYS.SDO_ORDINATE_ARRAY(");
	    sb.append(points);
	    sb.append(")))");
	    
	    System.out.println(sb.toString());
	    connManager.executeQuery(sb.toString());
	    
	    return id;
	}
	
	public ResultSet applyOperator(SDOOperator operator, String...shapeNames)
	{
		ResultSet result = null;
		StringBuilder sb;
		switch(operator)
		{
			case AREA:
				// returns name and double
				sb = new StringBuilder("SELECT SDO_GEOM.SDO_AREA(shape, 0.005) AS result FROM ");
				sb.append(table);
				sb.append( " WHERE name = '");
				sb.append(shapeNames[0]);
				sb.append("'");
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case CONVEXHULL:
				//returns name and shape
				sb = new StringBuilder("SELECT SDO_GEOM.SDO_CONVEXHULL(shape, 0.005) AS result FROM ");
				sb.append(table);
				sb.append( " WHERE name = '");
				sb.append(shapeNames[0]);
				sb.append("'");
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case COVERS:
				// returns name and boolean
				sb = new StringBuilder("SELECT SDO_GEOM.RELATE(t.shape, 'covers', t2.shape, 0.005) AS result FROM ");
				sb.append(table);
				sb.append(" t, ");
				sb.append(table);
				sb.append(" t2 WHERE t.name = '");
				sb.append(shapeNames[0]);
				sb.append("' AND t2.name = '");
				sb.append(shapeNames[1]);
				sb.append("'");
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case DISTANCE:
				// return name and distance double
				sb = new StringBuilder("SELECT SDO_GEOM.SDO_DISTANCE(t.shape, t2.shape, 0.005) AS result FROM ");
				sb.append(table);
				sb.append(" t, ");
				sb.append(table);
				sb.append(" t2 WHERE t.name = '");
				sb.append(shapeNames[0]);
				sb.append("' AND t2.name = '");
				sb.append(shapeNames[1]);
				sb.append("'");
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case MBR:
				// returns name and shape
				sb = new StringBuilder("SELECT SDO_GEOM.SDO_MBR(t.shape, m.diminfo) AS result " +
						" FROM midnight_run t, user_sdo_geom_metadata m " +
						" WHERE m.table_name = '");
				sb.append(table.toUpperCase());
				sb.append("' AND m.column_name = 'SHAPE' AND t.name = '");
				sb.append(shapeNames[0]);
				sb.append("'");
				
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case NN:
				// returns names and shapes
				sb = new StringBuilder("SELECT t.name FROM ");
				sb.append(table);
				sb.append(" t, ");
				sb.append(table);
				sb.append(" t2 WHERE t2.name = '");
				sb.append(shapeNames[0]);
				sb.append("' AND t.name <> '");
				sb.append(shapeNames[0]);
				sb.append("' AND SDO_NN(t.shape, t2.shape,'SDO_NUM_RES=3') = 'TRUE'");
				
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TOUCHES:
				//returns name and boolean
				sb = new StringBuilder("SELECT SDO_GEOM.RELATE(t.shape, 'touches', t2.shape, 0.005) AS result FROM ");
				sb.append(table);
				sb.append(" t, ");
				sb.append(table);
				sb.append(" t2 WHERE t.name = '");
				sb.append(shapeNames[0]);
				sb.append("' AND t2.name = '");
				sb.append(shapeNames[1]);
				sb.append("'");
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case UNION:
				//return name and shape
				sb = new StringBuilder("SELECT SDO_GEOM.SDO_UNION(t.shape, m.diminfo, t2.shape, m.diminfo) AS result FROM ");
				sb.append(table);
				sb.append(" t, ");
				sb.append(table);
				sb.append(" t2, user_sdo_geom_metadata m WHERE m.table_name = '");
				sb.append(table.toUpperCase());
				sb.append("' AND m.column_name = 'SHAPE' AND t.name = '");
				sb.append(shapeNames[0]);
				sb.append("' AND t2.name = '");
				sb.append(shapeNames[1]);
				sb.append("'");
				
				System.out.println(sb.toString());
				try {
					result = connManager.executeQuery(sb.toString());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
		
		return result;
	}
}
