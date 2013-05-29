package spatialdb.MidnightRun;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import spatialdb.MidnightRun.controller.ConnectionManager;
import spatialdb.MidnightRun.controller.DatabaseManager;
import spatialdb.MidnightRun.controller.SDOManager;


/**
 * Unit test for simple App.
 */
public class MidnightRunTest 
{
	private static ConnectionManager connManager;
	
	@BeforeClass
	public static void before()
	{
		connManager = new ConnectionManager();
		connManager.connect();
	}
	
	@AfterClass
	public static void after()
	{
		connManager.closeConnection();
	}
	
	@Test
	public void testConvertToJavaCoordinates()
	{
		
	}
	
	@Test
	public void testCreateShape()
	{
		
	}
	
	@Test
	public void testGetShape() throws Exception
	{
		DatabaseManager dbManager = new DatabaseManager(connManager);
		dbManager.getShape("");
	}
	
	@Test
	public void testGetShapes() throws Exception
	{
		DatabaseManager dbManager = new DatabaseManager(connManager);
		SDOManager shpManager = new SDOManager(dbManager);
		shpManager.getSDOShapes();
	}
	
	@Test
	public void testUpdateShape() throws Exception
	{
		
	}
	
	@Test
	public void testDeleteShape() throws Exception
	{
		
	}
	
	@Test
	public void testPerformRelateOperation()
	{
		
	}
	
	@Test
	public void testPerformIntersectOperation()
	{
		
	}
	
	@Test
	public void testPerformOperation3()
	{
		
	}
	
	@Test
	public void testPerformOperation4()
	{
		
	}
	
	@Test
	public void testPerformOperation5()
	{
		
	}
	
	@Test
	public void testPerformOperation6()
	{
		
	}
	
	@Test
	public void testPerformOperation7()
	{
		
	}
	
	@Test
	public void testPerformOperation8()
	{
		
	}
}
