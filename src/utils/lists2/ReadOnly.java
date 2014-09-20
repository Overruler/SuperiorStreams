package utils.lists2;

import java.util.AbstractCollection;
import java.util.Iterator;
import utils.streams.functions.IntFunction;
import utils.streams2.Stream;

public interface ReadOnly<T> extends Iterable<T> {
	default boolean isEmpty() {
		return size() == 0;
	}
	default boolean notEmpty() {
		return size() != 0;
	}
	default boolean containsAll(ReadOnly<T> c) {
		for(T t : c) {
			if(!contains(t)) {
				return false;
			}
		}
		return true;
	}
	default Object[] toArray() {
		return stream().toArray();
	}
	default T[] toArray(IntFunction<T[]> generator) {
		return stream().toArray(generator);
	}
	default T[] toArray(T[] a) {
		return toJavaUtilCollection().toArray(a);
	}
	default Stream<T> stream() {
		return Stream.from(this).parallel();
	}
	default Stream<T> parallelStream() {
		return Stream.from(this).parallel();
	}
	default java.util.Collection<T> toJavaUtilCollection() {
		return new AbstractCollection<T>() {
			public @Override Iterator<T> iterator() {
				return ReadOnly.this.iterator();
			}
			public @Override int size() {
				return ReadOnly.this.size();
			}
		};
	}
	int size();
	boolean contains(T o);
}
