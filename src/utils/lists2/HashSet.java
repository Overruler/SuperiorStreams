package utils.lists2;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;
import utils.streams2.Stream;

public class HashSet<T> implements CollectionSetAPI<T, HashSet<T>> {
	final java.util.Set<T> wrapped;

	public static @SafeVarargs <T> HashSet<T> of(T... elements) {
		return new HashSet<>(java.util.Arrays.asList(elements));
	}
	public static <T, C extends Collection<T, C>> HashSet<T> from(C set) {
		return new HashSet<>(set);
	}
	public static <T> HashSet<T> from(java.util.Collection<T> set) {
		return new HashSet<>(set);
	}
	public static <T> HashSet<T> fromIterable(Iterable<T> set) {
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
	public <C extends Collection<T, C>> HashSet(C m) {
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
	private HashSet(Iterable<T> iterable) {
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
	public @Override <C extends Collection<T, C>> boolean containsAll(C c) {
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
	public @Override <C extends Collection<T, C>> HashSet<T> addAll(C c) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		wrapped.addAll(java.util.Arrays.asList(array));
		return this;
	}
	public @Override <C extends Collection<T, C>> HashSet<T> retainAll(C c) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		wrapped.retainAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return this;
	}
	public @Override <C extends Collection<T, C>> HashSet<T> removeAll(C c) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) c.stream().toArray();
		wrapped.removeAll(new java.util.HashSet<>(java.util.Arrays.asList(array)));
		return this;
	}
	public @Override <U, E extends Exception> HashSet<U> map(ExFunction<T, U, E> mapper) throws E {
		return new HashSet<>(new ArrayList<>(wrapped).map(mapper));
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
	public @Override java.util.HashSet<T> toJavaSet() {
		return new java.util.HashSet<>(wrapped);
	}
	public @Override Set<T> toSet() {
		return Set.from(this);
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
	private static <T, C extends Collection<T, C>> java.util.HashSet<T> createInternalSetFromCollection(C m) {
		if(m instanceof Set) {
			@SuppressWarnings("unchecked")
			Set<T> set = (Set<T>) m;
			return new java.util.HashSet<>(set.wrapped);
		}
		if(m instanceof HashSet) {
			@SuppressWarnings("unchecked")
			HashSet<T> hashSet = (HashSet<T>) m;
			return new java.util.HashSet<>(hashSet.wrapped);
		}
		if(m.getClass() == ArrayList.class) {
			@SuppressWarnings("unchecked")
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
