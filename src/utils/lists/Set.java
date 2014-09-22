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

public interface Set<T> extends Collection<T, Set<T>> {
	public static <T> Set<T> of() {
		return new CSet<>();
	}
	public static <T> Set<T> of(T item) {
		java.util.HashSet<T> set = new java.util.HashSet<>();
		set.add(item);
		return new CSet<>(set);
	}
	public @SafeVarargs static <T> Set<T> of(T... items) {
		return from(java.util.Arrays.asList(items));
	}
	public static <T> Set<T> from(Iterable<T> set) {
		return CSet.from(set);
	}
	public static <T> Set<T> from(java.util.Collection<T> set) {
		return CSet.from(set);
	}
	public static <T> Set<T> fromIterable(java.lang.Iterable<T> set) {
		return CSet.fromIterable(set);
	}
	default @Override Set<T> identity() {
		return this;
	}
	public @Override <U, E extends Exception> Set<U> map(ExFunction<T, U, E> mapper) throws E;
	public @Override java.util.HashSet<T> toJavaUtilCollection();
}
class CSet<T> implements Set<T> {
	final java.util.HashSet<T> wrapped;

	static <T> Set<T> from(Iterable<T> set) {
		return new CSet<>(set);
	}
	static <T> Set<T> from(java.util.Collection<T> set) {
		return new CSet<>(set);
	}
	static <T> Set<T> fromIterable(java.lang.Iterable<T> set) {
		return new CSet<>(set);
	}
	CSet() {
		this(new java.util.HashSet<>());
	}
	CSet(int initialCapacity, float loadFactor) {
		this(new java.util.HashSet<>(initialCapacity, loadFactor));
	}
	CSet(int initialCapacity) {
		this(new java.util.HashSet<>(initialCapacity));
	}
	CSet(CSet<T> m) {
		this(new java.util.HashSet<>(m.wrapped));
	}
	CSet(HashSet<T> m) {
		this(new java.util.HashSet<>(m.wrapped));
	}
	CSet(java.util.HashSet<T> wrapped) {
		this.wrapped = wrapped;
	}
	CSet(java.util.Collection<T> iterable) {
		this(iterable.size());
		for(T item : iterable) {
			wrapped.add(item);
		}
	}
	private CSet(Iterable<T> iterable) {
		this(iterable.size());
		for(T item : iterable) {
			wrapped.add(item);
		}
	}
	private CSet(java.lang.Iterable<T> iterable) {
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
	public @Override <E extends Exception> Set<T> each(ExConsumer<T, E> action) throws E {
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
		return wrapped.containsAll(c.toJavaUtilCollection());
	}
	public @Override Set<T> add(T item) {
		java.util.HashSet<T> copy = copy();
		copy.add(item);
		return next(copy);
	}
	public @Override Set<T> addAll(@SuppressWarnings("unchecked") T... values) {
		java.util.HashSet<T> copy = copy();
		copy.addAll(java.util.Arrays.asList(values));
		return next(copy);
	}
	public @Override Set<T> remove(T item) {
		java.util.HashSet<T> copy = copy();
		copy.remove(item);
		return next(copy);
	}
	public @Override Set<T> clear() {
		return Set.of();
	}
	public @Override Set<T> addAll(Iterable<T> c) {
		if(c == this) {
			return this;
		}
		java.util.HashSet<T> copy = copy();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.toArray();
		copy.addAll(java.util.Arrays.asList(array));
		return next(copy);
	}
	public @Override Set<T> retainAll(Iterable<T> c) {
		if(c == this) {
			return this;
		}
		java.util.HashSet<T> copy = copy();
		copy.retainAll(java.util.Arrays.asList(c.toArray()));
		return next(copy);
	}
	public @Override Set<T> removeAll(Iterable<T> c) {
		if(c == this) {
			return clear();
		}
		java.util.HashSet<T> copy = copy();
		copy.removeAll(java.util.Arrays.asList(c.toArray()));
		return next(copy);
	}
	public @Override <U, E extends Exception> Set<U> map(ExFunction<T, U, E> mapper) throws E {
		return new CSet<>(new ArrayList<>(wrapped).map(mapper));
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
	public @Override <E extends Exception> Set<T> filter(ExPredicate<T, E> filter) throws E {
		return new CSet<>(new ArrayList<>(wrapped).filter(filter));
	}
	public @Override <E extends Exception> Set<T> removeIf(ExPredicate<T, E> filter) throws E {
		return new CSet<>(new ArrayList<>(wrapped).removeIf(filter));
	}
	public @Override <E extends Exception> Set<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		return new CSet<>(new ArrayList<>(wrapped).replaceAll(mapper));
	}
	public @Override java.util.HashSet<T> toJavaUtilCollection() {
		return copy();
	}
	public @Override Set<T> toSet() {
		return this;
	}
	public @Override HashSet<T> toHashSet() {
		return HashSet.from(this);
	}
	public @Override List<T> toList() {
		return List.from(this);
	}
	public @Override ArrayList<T> toArrayList() {
		return ArrayList.from(this);
	}
	private java.util.HashSet<T> copy() {
		return new java.util.HashSet<>(wrapped);
	}
	private static <T> Set<T> next(java.util.HashSet<T> copy) {
		return new CSet<>(copy);
	}
}
