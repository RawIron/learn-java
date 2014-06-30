

public class RingBufferOfArray<T> {
	protected int BUFFERSIZE;
	protected T[] messages;
	protected int readFrom = 0;
	protected int writeTo = 0;
	
	public RingBufferOfArray(T[] buffer) {
		this.messages = buffer;
		this.BUFFERSIZE = buffer.length;
	}

	public boolean in(T message) {
		if (writeTo - readFrom == BUFFERSIZE) {
			return false;				
		}
		messages[writeTo&(BUFFERSIZE-1)] = message;
		++writeTo;
		return true;
	}
	public T out() {
		if (writeTo - readFrom == 0) {
			return null;
		}
		T message = messages[readFrom&(BUFFERSIZE-1)];
		++readFrom;
		return message;
	}
}
