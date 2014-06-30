import static org.junit.Assert.*;

import org.junit.Test;


public class InsertSortLotsOfMemoryTest {
	@Test
	public void test_ArrayLastOnly() {
		Sorter sorter = new InsertSortLotsOfMemory();
		int[] a = {3,5,8,1};
		int[] expected = {1,3,5,8};
		int[] sorted;
		sorted = sorter.sort(a);
		assertArrayEquals(sorted, expected);
	}
	@Test
	public void test_ArrayFirstOnly() {
		Sorter sorter = new InsertSortLotsOfMemory();
		int[] a = {5,2,6,7,8};
		int[] expected = {2,5,6,7,8};
		int[] sorted;
		sorted = sorter.sort(a);
		assertArrayEquals(sorted, expected);
	}
	@Test
	public void test_ArrayMidOnly() {
		Sorter sorter = new InsertSortLotsOfMemory();
		int[] a = {2,3,5,4,6,7};
		int[] expected = {2,3,4,5,6,7};
		int[] sorted;
		sorted = sorter.sort(a);
		assertArrayEquals(sorted, expected);
	}	
	@Test
	public void test_ArrayReverse() {
		Sorter sorter = new InsertSortLotsOfMemory();
		int[] a = {9,8,7,6,5};
		int[] expected = {5,6,7,8,9};
		int[] sorted;
		sorted = sorter.sort(a);
		assertArrayEquals(sorted, expected);
	}
	@Test
	public void test_ArrayInOrder() {
		Sorter sorter = new InsertSortLotsOfMemory();
		int[] a = {5,6,7,8,9};
		int[] expected = {5,6,7,8,9};
		int[] sorted;
		sorted = sorter.sort(a);
		assertArrayEquals(sorted, expected);
	}
	@Test
	public void test_ArrayAllTheSame() {
		Sorter sorter = new InsertSortLotsOfMemory();
		int[] a = {2,2,2,2,2,};
		int[] expected = {2,2,2,2,2};
		int[] sorted;
		sorted = sorter.sort(a);
		assertArrayEquals(sorted, expected);
	}
	@Test
	public void test_ArrayGroups() {
		Sorter sorter = new InsertSortLotsOfMemory();
		int[] a = {2,2,3,3,1,1,7,7,4,4};
		int[] expected = {1,1,2,2,3,3,4,4,7,7};
		int[] sorted;
		sorted = sorter.sort(a);
		assertArrayEquals(sorted, expected);
	}
}
