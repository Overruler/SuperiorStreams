package utils.lists2;

import java.util.Iterator;

class HashMapEntrySetHashSetIterator<T, V> implements Iterator<HashMap.Entry<T, V>> {
	private final Iterator<java.util.Map.Entry<T, V>> iterator;

	public HashMapEntrySetHashSetIterator(Iterator<java.util.Map.Entry<T, V>> wrapped) {
		this.iterator = wrapped;
	}
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}
	public @Override HashMap.Entry<T, V> next() {
		return new HashMap.Entry<>(iterator.next());
	}
}
