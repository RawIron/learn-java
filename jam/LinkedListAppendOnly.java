
class LinkedListElement<E> {
	public LinkedListElement<E> next = null;
	public E value = null;
	
	public LinkedListElement(E value) {
		this.value = value;
	}
}

public class LinkedListAppendOnly<E> {
	protected LinkedListElement<E> head = null;
	protected LinkedListElement<E> tail = null;
	protected LinkedListElement<E> current = null;
	
	public LinkedListAppendOnly() {
		head = new LinkedListElement<E>(null);
		current = head;
		tail = head;
	}
	
	public void append(E value) {
		LinkedListElement<E> element = new LinkedListElement<E>(value);
		tail.next = element;
		tail = element;
	}
	public void rewind() {
		current = head;
	}
	public boolean hasNext() {
		return (current.next != null);
	}
	public E next() {
		E value = current.next.value;
		current = current.next;
		return value;
	}
	public boolean isEmpty() {
		return (head.next == null);
	}
}

