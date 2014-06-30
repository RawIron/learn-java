import static org.junit.Assert.*;

import org.junit.Test;


public class RingBufferOfArrayTest {

	@Test
	public void test_inIsOut() {
		Integer[] buf = new Integer[2];
		RingBufferOfArray<Integer> r = new RingBufferOfArray<Integer>(buf);
		r.in(4);
		assertEquals((int) r.out(), 4);
	}
	
	@Test
	public void test_inIsFull() {
		Integer[] buf = new Integer[2];
		RingBufferOfArray<Integer> r = new RingBufferOfArray<Integer>(buf);
		r.in(4);
		assertTrue(r.in(5));
		assertFalse(r.in(6));
	}
	
	@Test
	public void test_createdIsEmpty() {
		Integer[] buf = new Integer[2];
		RingBufferOfArray<Integer> r = new RingBufferOfArray<Integer>(buf);
		assertNull(r.out());
	}
	
	@Test
	public void test_afterInoutIsEmpty() {
		Integer[] buf = new Integer[2];
		RingBufferOfArray<Integer> r = new RingBufferOfArray<Integer>(buf);
		r.in(4);
		r.out();
		assertNull(r.out());
	}
	
	@Test
	public void test_ring() {
		Integer[] buf = new Integer[2];
		RingBufferOfArray<Integer> r = new RingBufferOfArray<Integer>(buf);
		r.in(4);
		r.in(5);
		r.out();
		r.in(2);
		assertFalse(r.in(4));
		assertEquals((int) r.out(), 5);
		assertEquals((int) r.out(), 2);
	}	
}
