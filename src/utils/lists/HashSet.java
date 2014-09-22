package utils.lists;

import java.util.Iterator;
import java.util.Spliterator;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.ExUnaryOperator;
import utils.streams.functions.IntFunction;
import utils.streams2.Stream;

public class HashSet<T> implements Collection<T, HashSet<T>> {
	final java.util.Set<T> wrapped;

	public static <T> HashSet<T> of() {
		return new HashSet<>();
	}
	public static <T> HashSet<T> of(T item) {
		java.util.HashSet<T> set = new java.util.HashSet<>();
		set.add(item);
		return wrap(set);
	}
	public static @SafeVarargs <T> HashSet<T> of(T... elements) {
		return new HashSet<>(java.util.Arrays.asList(elements));
	}
	public static <T> HashSet<T> from(Iterable<T> set) {
		return new HashSet<>(set);
	}
	public static <T> HashSet<T> from(java.util.Collection<T> set) {
		return new HashSet<>(set);
	}
	public static <T> HashSet<T> fromIterable(java.lang.Iterable<T> set) {
		return new HashSet<>(set);
	}
	static <T> HashSet<T> wrap(java.util.Set<T> wrapped) {
		return new HashSet<>(wrapped);
	}
	public HashSet() {
		this(new java.util.HashSet<>());
	}
	public HashSet(int initialCapacity, float loadFactor) {
		this(new java.util.HashSet<>(initialCapacity, loadFactor));
	}
	public HashSet(int initialCapacity) {
		this(new java.util.HashSet<>(initialCapacity));
	}
	public HashSet(Iterable<T> m) {
		this(createInternalSetFromCollection(m));
	}
	private HashSet(java.util.Set<T> wrapped) {
		this.wrapped = wrapped;
	}
	public HashSet(java.util.Collection<T> iterable) {
		this(iterable.size());
		for(T item : iterable) {
			wrapped.add(item);
		}
	}
	private HashSet(java.lang.Iterable<T> iterable) {
		this();
		for(T item : iterable) {
			wrapped.add(item);
		}
	}
	public @Override String toString() {
		return wrapped.toString();
	}
	public @Override int hashCode() {
		return wrapped.hashCode();
	}
	public @Override boolean equals(Object o) {
		return wrapped.equals(o);
	}
	public @Override boolean notEmpty() {
		return !wrapped.isEmpty();
	}
	public @Override Stream<T> stream() {
		return new Stream<>(() -> wrapped.stream());
	}
	public @Override Stream<T> parallelStream() {
		return new Stream<>(() -> wrapped.parallelStream());
	}
	public @Override Iterator<T> iterator() {
		return wrapped.iterator();
	}
	public @Override Spliterator<T> spliterator() {
		return wrapped.spliterator();
	}
	public @Override <E extends Exception> HashSet<T> each(ExConsumer<T, E> action) throws E {
		for(Iterator<T> iterator = wrapped.iterator(); iterator.hasNext();) {
			action.accept(iterator.next());
		}
		return this;
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		return wrapped.toArray(generator.apply(size()));
	}
	public @Override Object[] toArray() {
		return wrapped.toArray();
	}
	public @Override T[] toArray(T[] a) {
		return wrapped.toArray(a);
	}
	public @Override int size() {
		return wrapped.size();
	}
	public @Override boolean isEmpty() {
		return wrapped.isEmpty();
	}
	public @Override boolean contains(T o) {
		return wrapped.contains(o);
	}
	public @Override boolean containsAll(Iterable<T> c) {
		return wrapped.containsAll(new java.util.HashSet<>(java.util.Arrays.asList(c.stream().toArray())));
	}
	public @Override HashSet<T> add(T item) {
		wrapped.add(item);
		return this;
	}
	public @Override HashSet<T> addAll(@SuppressWarnings("unchecked") T... values) {
		wrapped.addAll(java.util.Arrays.asList(values));
		return this;
	}
	public @Override HashSet<T> remove(T item) {
		wrapped.remove(item);
		return this;
	}
	public @Override HashSet<T> clear() {
		wrapped.clear();
		return this;
	}
	public @Override HashSet<T> addAll(Iterable<T> c) {
		if(c == this) {
			return this;
		}
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		wrapped.addAll(java.util.Arrays.asList(array));
		return this;
	}
	public @Override HashSet<T> retainAll(Iterable<T> c) {
		if(c == this) {
			return this;
		}
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		wrapped.retainAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return this;
	}
	public @Override HashSet<T> removeAll(Iterable<T> c) {
		if(c == this) {
			return clear();
		}
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		wrapped.removeAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return this;
	}
	public @Override <U, E extends Exception> HashSet<U> map(ExFunction<T, U, E> mapper) throws E {
		return new HashSet<>(ArrayList.from(wrapped).map(mapper));
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		return new ArrayList<>(wrapped).mapToDouble(doubleFunction);
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		return new ArrayList<>(wrapped).mapToInt(intFunction);
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		return new ArrayList<>(wrapped).mapToLong(longFunction);
	}
	public @Override <E extends Exception> HashSet<T> filter(ExPredicate<T, E> filter) throws E {
		for(Iterator<T> iterator = wrapped.iterator(); iterator.hasNext();) {
			if(!filter.test(iterator.next())) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> HashSet<T> removeIf(ExPredicate<T, E> filter) throws E {
		for(Iterator<T> iterator = wrapped.iterator(); iterator.hasNext();) {
			if(filter.test(iterator.next())) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> HashSet<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		HashSet<T> set = new HashSet<>(new ArrayList<>(wrapped).replaceAll(mapper));
		wrapped.clear();
		wrapped.addAll(set.wrapped);
		return this;
	}
	public @Override java.util.HashSet<T> toJavaUtilCollection() {
		return new java.util.HashSet<>(wrapped);
	}
	public @Override Set<T> toSet() {
		return Set.from(this);
	}
	public @Override HashSet<T> toHashSet() {
		return from(this);
	}
	public @Override List<T> toList() {
		return List.from(this);
	}
	public @Override ArrayList<T> toArrayList() {
		return ArrayList.from(this);
	}
	public @Override HashSet<T> identity() {
		return this;
	}
	private static <T> java.util.HashSet<T> createInternalSetFromCollection(Iterable<T> m) {
		if(m instanceof CSet) {
			CSet<T> set = (CSet<T>) m;
			return new java.util.HashSet<>(set.wrapped);
		}
		if(m instanceof HashSet) {
			HashSet<T> hashSet = (HashSet<T>) m;
			return new java.util.HashSet<>(hashSet.wrapped);
		}
		if(m.getClass() == ArrayList.class) {
			ArrayList<T> list = (ArrayList<T>) m;
			int localSize = list.size;
			java.util.HashSet<T> hashSet = new java.util.HashSet<>(localSize);
			hashSet.addAll(java.util.Arrays.asList(list.items).subList(0, localSize));
			return hashSet;
		}
		java.util.HashSet<T> hashSet = new java.util.HashSet<>(m.size());
		for(T item : m) {
			hashSet.add(item);
		}
		return hashSet;
	}
}
