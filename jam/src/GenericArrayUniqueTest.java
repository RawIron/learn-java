import static org.junit.Assert.*;
import org.junit.Test;


public class GenericArrayUniqueTest {

	@Test
	public void test_isEmpty() {
		Integer[] buffer = new Integer[10];
		GenericArrayUnique<Integer> myList = new GenericArrayUnique<Integer>(buffer);
		assertTrue(myList.isEmpty());		
	}
	
	@Test
	public void test_append() {
		Integer[] buffer = new Integer[10];
		GenericArrayUnique<Integer> myList = new GenericArrayUnique<Integer>(buffer);
		int value = 4;
		myList.append(value);
		assertTrue(myList.find(value));
	}
	
	@Test
	public void test_remove() {
		Integer[] buffer = new Integer[10];
		GenericArrayUnique<Integer> myList = new GenericArrayUnique<Integer>(buffer);
		int value = 4;
		myList.append(value);
		value = 7;
		myList.append(value);		
		myList.remove(4);		
		assertFalse(myList.find(4));
		assertTrue(myList.find(7));
	}
	
	@Test
	public void test_removeAll() {
		Integer[] buffer = new Integer[10];
		GenericArrayUnique<Integer> myList = new GenericArrayUnique<Integer>(buffer);
		int value = 4;
		myList.append(value);
		value = 7;
		myList.append(value);		
		myList.remove(4);
		myList.remove(7);
		assertFalse(myList.find(4));
		assertFalse(myList.find(7));
		assertTrue(myList.isEmpty());
	}
}
