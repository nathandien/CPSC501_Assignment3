import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
public class TestObjectCreator extends TestCase {
	
	private ObjectCreator objectCreator;
	
	@Test
	public void testSimpleObject() {
		SimpleObject a = new SimpleObject();
		a.setIntegerNum(10);
		a.setDoubleNum(10.0);
		a.setBoolVal(true);
		
		
		
		assertEquals(a.getIntegerNum(), 10);
		assertEquals(a.getDoubleNum(), 10.0);
		assertTrue(a.isBoolVal());
	}
	
	@Test
	public void testRefObject() {
		RefObject a = new RefObject();

		SimpleObject s1 = new SimpleObject();
		SimpleObject s2 = new SimpleObject();
		s1.setIntegerNum(1);
		s2.setIntegerNum(1);
		
		s1.setDoubleNum(2.0);
		s2.setDoubleNum(2.0);
		
		s1.setBoolVal(true);
		s2.setBoolVal(true);
		
		a.setObj(s1);
		a.setObj2(s2);
		
		assertSame(a.getObj(), a.getObj2());
	}
	
	@Test
	public void testArrObject() {
		ArrayObject a = new ArrayObject();
		
		a = (ArrayObject) objectCreator.makeArrObj();
		
	}
	
	@Test
	public void testArrayRefObject() {
		ArrayRefObject a = new ArrayRefObject();
		objectCreator = new ObjectCreator();
		
		a = (ArrayRefObject) objectCreator.makeArrRefObj();
		ArrayRefObject b = new ArrayRefObject();
		assertSame(b, a);

	}
	
	
}
