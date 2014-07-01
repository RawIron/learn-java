
public class GenericArrayUnique<E> {
	private int next = 0;
	private E[] c;
	
	public GenericArrayUnique(E[] values) {
		this.c = values;
	}
	
	public void append(E value) {
		if (find(value)) {
			return;
		}
		c[next] = value;
		++next;
	}
	
	public void remove(E value) {
		int position = next;
		for (int i = 0; i < next; ++i) {
			if (c[i] == value) {
				position = i;
				break;
			}
		}
		for (int i = position; i < next; ++i) {
			c[i] = c[i+1];
		}
		--next;
	}
	
	public boolean find(E value) {
		for (int i = 0; i < next; ++i) {
			if (c[i] == value) {
				return true;
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return (next == 0);
	}
}
