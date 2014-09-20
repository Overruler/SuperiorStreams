package utils.lists2;

import java.util.AbstractCollection;
import java.util.Iterator;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.IntFunction;
import utils.streams2.Stream;

public interface Iterable<T> extends java.lang.Iterable<T> {
	default boolean isEmpty() {
		return size() == 0;
	}
	default boolean notEmpty() {
		return size() != 0;
	}
	default boolean containsAll(Iterable<T> c) {
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
		return Stream.from(this);
	}
	default Stream<T> parallelStream() {
		return Stream.from(this).parallel();
	}
	default java.util.Collection<T> toJavaUtilCollection() {
		return new AbstractCollection<T>() {
			public @Override Iterator<T> iterator() {
				return Iterable.this.iterator();
			}
			public @Override int size() {
				return Iterable.this.size();
			}
		};
	}
	default List<T> toList() {
		return List.from(this);
	}
	default Set<T> toSet() {
		return Set.from(this);
	}
	default ArrayList<T> toArrayList() {
		return ArrayList.from(this);
	}
	default HashSet<T> toHashSet() {
		return HashSet.from(this);
	}
	default <U, E extends Exception> Iterable<U> map(ExFunction<T, U, E> mapper) throws E {
		return toArrayList().map(mapper);
	}
	default <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> mapper) throws E {
		return toArrayList().mapToInt(mapper);
	}
	default <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> mapper) throws E {
		return toArrayList().mapToLong(mapper);
	}
	default <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> mapper) throws E {
		return toArrayList().mapToDouble(mapper);
	}
	default boolean contains(T item) {
		Iterator<T> iter = iterator();
		if(item == null) {
			while(iter.hasNext()) {
				if(iter.next() == null) {
					return true;
				}
			}
		} else {
			while(iter.hasNext()) {
				if(item.equals(iter.next())) {
					return true;
				}
			}
		}
		return false;
	}
	public int size();
}
