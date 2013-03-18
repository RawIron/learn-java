
interface Sorter {
	public int[] sort(int[] a);
}


public class InsertSortLotsOfMemory implements Sorter {
	private int[] sortedGoesHere = null;
	private int[] takeFromHere = null;

	public int[] sort(int[] unsorted) {
		this.takeFromHere = unsorted;
		sortedGoesHere = new int[takeFromHere.length];
		sortedGoesHere = takeFromHere.clone();	

		for (int i=1; i<takeFromHere.length; ++i) {
			boolean inserted = false;
			boolean swapped = false;
			int k, source;

			for (k=0, source=0; k<takeFromHere.length; ++k, ++source) {
				if ((k<i) && sortedGoesHere[k] > takeFromHere[i] && (!inserted)) {
					sortedGoesHere[k+1] = sortedGoesHere[k];
					sortedGoesHere[k] = takeFromHere[i];
					inserted = true;
					if ((k == 0) && (i == 1)) {
						swapped = true;
						++source;
					}
					++k;
					continue;
				} else {
					if (source == i && (inserted) && (!swapped)) {
						++source;
					}
					sortedGoesHere[k] = takeFromHere[source];
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

