package utils.lists2;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;
import utils.streams2.Stream;

public class Set<T> implements CollectionSetAPI<T, Set<T>> {
	final java.util.HashSet<T> wrapped;

	public @SafeVarargs static <T> Set<T> of(T... items) {
		return from(Arrays.asList(items));
	}
	public static <T, C extends Collection<T, C>> Set<T> from(C set) {
		return new Set<>(set);
	}
	public static <T> Set<T> from(java.util.Collection<T> set) {
		return new Set<>(set);
	}
	public static <T> Set<T> fromIterable(Iterable<T> set) {
		return new Set<>(set);
	}
	private Set() {
		this(new java.util.HashSet<>());
	}
	private Set(int initialCapacity, float loadFactor) {
		this(new java.util.HashSet<>(initialCapacity, loadFactor));
	}
	private Set(int initialCapacity) {
		this(new java.util.HashSet<>(initialCapacity));
	}
	private Set(Set<T> m) {
		this(new java.util.HashSet<>(m.wrapped));
	}
	private Set(HashSet<T> m) {
		this(new java.util.HashSet<>(m.wrapped));
	}
	private Set(java.util.HashSet<T> wrapped) {
		this.wrapped = wrapped;
	}
	private Set(java.util.Collection<T> iterable) {
		this(iterable.size());
		for(T item : iterable) {
			wrapped.add(item);
		}
	}
	private <C extends Collection<T, C>> Set(C iterable) {
		this(iterable.size());
		for(T item : iterable) {
			wrapped.add(item);
		}
	}
	private Set(Iterable<T> iterable) {
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
	public @Override <C extends Collection<T, C>> boolean containsAll(C c) {
		return wrapped.containsAll(new java.util.HashSet<>(java.util.Arrays.asList(c.stream().toArray())));
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
		return new Set<>();
	}
	public @Override <C extends Collection<T, C>> Set<T> addAll(C c) {
		java.util.HashSet<T> copy = copy();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		copy.addAll(java.util.Arrays.asList(array));
		return next(copy);
	}
	public @Override <C extends Collection<T, C>> Set<T> retainAll(C c) {
		java.util.HashSet<T> copy = copy();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		copy.retainAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return next(copy);
	}
	public @Override <C extends Collection<T, C>> Set<T> removeAll(C c) {
		java.util.HashSet<T> copy = copy();
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		copy.removeAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return next(copy);
	}
	public @Override <U, E extends Exception> Set<U> map(ExFunction<T, U, E> mapper) throws E {
		return new Set<>(new ArrayList<>(wrapped).map(mapper));
	}
	public @Override <E extends Exception> Set<T> filter(ExPredicate<T, E> filter) throws E {
		return new Set<>(new ArrayList<>(wrapped).filter(filter));
	}
	public @Override <E extends Exception> Set<T> removeIf(ExPredicate<T, E> filter) throws E {
		return new Set<>(new ArrayList<>(wrapped).removeIf(filter));
	}
	public @Override <E extends Exception> Set<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		return new Set<>(new ArrayList<>(wrapped).replaceAll(mapper));
	}
	public @Override java.util.HashSet<T> toJavaSet() {
		return copy();
	}
	public @Override Set<T> toSet() {
		return this;
	}
	public @Override HashSet<T> toHashSet() {
		return new HashSet<>(this);
	}
	public @Override List<T> toList() {
		return List.from(this);
	}
	public @Override ArrayList<T> toArrayList() {
		return new ArrayList<>(this);
	}
	private java.util.HashSet<T> copy() {
		return new java.util.HashSet<>(wrapped);
	}
	private static <T> Set<T> next(java.util.HashSet<T> copy) {
		return new Set<>(copy);
	}
}
