import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Date;


public class LinkedListAppendOnlyTest {

	@Test
	public void test_create() {
		LinkedListAppendOnly<Integer> list = new LinkedListAppendOnly<Integer>();
		assertNotNull(list);
	}
	
	@Test
	public void test_createdIsEmpty() {
		LinkedListAppendOnly<Date> list = new LinkedListAppendOnly<Date>();
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void test_append() {
		LinkedListAppendOnly<Date> list = new LinkedListAppendOnly<Date>();
		Date aDate = new Date();
		Date retrievedDate = null;
		
		list.append(aDate);
		assertFalse(list.isEmpty());
		assertTrue(list.hasNext());
		retrievedDate = list.next();
		assertEquals(aDate, retrievedDate);
	}

}
