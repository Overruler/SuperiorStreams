package utils.lists2;

import java.util.Spliterator;
import java.util.function.Consumer;

class HashMapEntrySetHashSetSpliterator<T, V> implements Spliterator<HashMap.Entry<T, V>> {
	private final Spliterator<java.util.Map.Entry<T, V>> iterator;

	public HashMapEntrySetHashSetSpliterator(Spliterator<java.util.Map.Entry<T, V>> wrapped) {
		this.iterator = wrapped;
	}
	public @Override boolean tryAdvance(Consumer<? super HashMap.Entry<T, V>> action) {
		return iterator.tryAdvance(entry -> action.accept(new HashMap.Entry<>(entry)));
	}
	public @Override Spliterator<HashMap.Entry<T, V>> trySplit() {
		return new HashMapEntrySetHashSetSpliterator<>(iterator.trySplit());
	}
	public @Override long estimateSize() {
		return iterator.estimateSize();
	}
	public @Override int characteristics() {
		return iterator.characteristics();
	}
}
