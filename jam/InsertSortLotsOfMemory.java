
interface Sorter {
	public int[] sort(int[] a);
}


public class InsertSortLotsOfMemory implements Sorter {
	private int[] sortedGoesHere = null;
	private int[] takeFromHere = null;
	boolean inserted = false;
	boolean swapped = false;

	private void insert(int inOrder, int outOfOrder) {
		sortedGoesHere[inOrder+1] = sortedGoesHere[inOrder];
		sortedGoesHere[inOrder] = takeFromHere[outOfOrder];
		inserted = true;		
	}
	private void copy(int nextIteration, int notYet) {
		sortedGoesHere[nextIteration] = takeFromHere[notYet];		
	}
	private boolean skipInserted(int copyTo, int current) {
		if (copyTo == current && (inserted) && (!swapped)) {
			return true;
		}
		return false;
	}
	private boolean swapFirstTwo(int k, int i) {
		return ((k == 0) && (i == 1) && sortedGoesHere[k] > takeFromHere[i]);
	}
	private boolean insertInOrder(int k, int i) {
		return ((k<i) && sortedGoesHere[k] > takeFromHere[i] && (!inserted));
	}
	
	public int[] sort(int[] unsorted) {
		this.takeFromHere = unsorted;
		sortedGoesHere = new int[takeFromHere.length];
		sortedGoesHere = takeFromHere.clone();	

		for (int i=1; i<takeFromHere.length; ++i) {
			inserted = false;
			swapped = false;
			int k, source;

			for (k=0, source=0; k<takeFromHere.length; ++k, ++source) {
				if (swapFirstTwo(k,i)) {
					++source;
					swapped = true;
					insert(k,i);
					++k;
				}
				else if (insertInOrder(k,i)) {
					insert(k,i);
					++k;
				} else {
					if (skipInserted(source, i)) {
						++source;
					}
					copy(k,source);
				}
			}
			takeFromHere = sortedGoesHere.clone();
		}
		return takeFromHere;
	}
	
	public static void main(String[] arg) {
		InsertSortLotsOfMemory sorter = new InsertSortLotsOfMemory();
		int[] a = {9,8,7,6,5};
		int[] sorted;
		sorted = sorter.sort(a);
		for (int i=0; i<sorted.length; ++i) {
			System.out.print(sorted[i]);
		}
	}
}

