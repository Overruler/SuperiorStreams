package utils.lists2;

import java.util.Iterator;
import java.util.Spliterator;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;
import utils.streams.functions.IntFunction;
import utils.streams2.Stream;

public interface Set<T> extends CollectionSetAPI<T, Set<T>> {
	public @SafeVarargs static <T> Set<T> of(T... items) {
		return from(Arrays.asList(items));
	}
	public static <T> Set<T> from(ReadOnly<T> set) {
		return CSet.from(set);
	}
	public static <T> Set<T> from(java.util.Collection<T> set) {
		return CSet.from(set);
	}
	public static <T> Set<T> fromIterable(Iterable<T> set) {
		return CSet.fromIterable(set);
	}
	public @Override <U, E extends Exception> Set<U> map(ExFunction<T, U, E> mapper) throws E;
}
class CSet<T> implements Set<T> {
	final java.util.HashSet<T> wrapped;

	static <T> Set<T> from(ReadOnly<T> set) {
		return new CSet<>(set);
	}
	static <T> Set<T> from(java.util.Collection<T> set) {
		return new CSet<>(set);
	}
	static <T> Set<T> fromIterable(Iterable<T> set) {
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
	private CSet(ReadOnly<T> iterable) {
		this(iterable.size());
		for(T item : iterable) {
			wrapped.add(item);
		}
	}
	private CSet(Iterable<T> iterable) {
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
	public @Override boolean containsAll(ReadOnly<T> c) {
		return wrapped.containsAll(new java.util.HashSet<>(c.toJavaUtilCollection()));
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
		return new CSet<>();
	}
	public @Override Set<T> addAll(ReadOnly<T> c) {
		java.util.HashSet<T> copy = copy();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		copy.addAll(java.util.Arrays.asList(array));
		return next(copy);
	}
	public @Override Set<T> retainAll(ReadOnly<T> c) {
		java.util.HashSet<T> copy = copy();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		copy.retainAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return next(copy);
	}
	public @Override Set<T> removeAll(ReadOnly<T> c) {
		java.util.HashSet<T> copy = copy();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		copy.removeAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return next(copy);
	}
	public @Override <U, E extends Exception> Set<U> map(ExFunction<T, U, E> mapper) throws E {
		return new CSet<>(new ArrayList<>(wrapped).map(mapper));
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
	public @Override java.util.HashSet<T> toJavaSet() {
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
