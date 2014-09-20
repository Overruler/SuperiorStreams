package utils.lists2;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import utils.lists2.HashMap.Entry;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;
import utils.streams.functions.IntFunction;
import utils.streams2.Stream;

class HashMapEntrySetHashSet<T, V> extends HashSet<HashMap.Entry<T, V>> {
	private final java.util.Set<java.util.Map.Entry<T, V>> entrySet;
	private final HashMap<T, V> origin;

	HashMapEntrySetHashSet(HashMap<T, V> origin, java.util.Set<java.util.Map.Entry<T, V>> entrySet) {
		this.origin = origin;
		this.entrySet = entrySet;
	}
	public @Override String toString() {
		return wrapped().toString();
	}
	public @Override int hashCode() {
		return wrapped().hashCode();
	}
	public @Override boolean equals(Object o) {
		return wrapped().equals(o);
	}
	public @Override boolean notEmpty() {
		return origin.notEmpty();
	}
	public @Override Stream<HashMap.Entry<T, V>> stream() {
		return wrapped().stream();
	}
	public @Override Stream<HashMap.Entry<T, V>> parallelStream() {
		return wrapped().parallelStream();
	}
	public @Override Iterator<HashMap.Entry<T, V>> iterator() {
		Iterator<java.util.Map.Entry<T, V>> iterator = entrySet.iterator();
		return new HashMapEntrySetHashSetIterator<>(iterator);
	}
	public @Override Spliterator<HashMap.Entry<T, V>> spliterator() {
		return new HashMapEntrySetHashSetSpliterator<>(entrySet.spliterator());
	}
	public @Override <E extends Exception> HashMapEntrySetHashSet<T, V> each(ExConsumer<HashMap.Entry<T, V>, E> action)
		throws E {
		for(Iterator<java.util.Map.Entry<T, V>> iterator = entrySet.iterator(); iterator.hasNext();) {
			action.accept(new HashMap.Entry<>(iterator.next()));
		}
		return this;
	}
	public @Override HashMap.Entry<T, V>[] toArray(IntFunction<HashMap.Entry<T, V>[]> generator) {
		return wrapped().toArray(generator.apply(size()));
	}
	public @Override Object[] toArray() {
		return wrapped().toArray();
	}
	public @Override HashMap.Entry<T, V>[] toArray(HashMap.Entry<T, V>[] a) {
		return wrapped().toArray(a);
	}
	public @Override List<HashMap.Entry<T, V>> toList() {
		return List.from(wrapped());
	}
	public @Override int size() {
		return origin.size();
	}
	public @Override boolean isEmpty() {
		return origin.isEmpty();
	}
	public @Override boolean contains(HashMap.Entry<T, V> o) {
		return o != null && Objects.equals(origin.get(o.lhs), o.rhs);
	}
	public @Override boolean containsAll(ReadOnly<HashMap.Entry<T, V>> c) {
		for(HashMap.Entry<T, V> entry : c) {
			if(contains(entry) == false) {
				return false;
			}
		}
		return true;
	}
	public @Override HashMapEntrySetHashSet<T, V> add(HashMap.Entry<T, V> item) {
		T key = item.lhs;
		V value = item.rhs;
		V old = origin.get(key);
		if(!Objects.equals(old, value)) {
			if(old != null) {
				throw new IllegalArgumentException(String.format(
					"Duplicate values for key: %s, old: %s, new: %s",
					key,
					old,
					value));
			}
			origin.put(key, value);
		}
		return this;
	}
	public @Override HashMapEntrySetHashSet<T, V> remove(HashMap.Entry<T, V> item) {
		entrySet.remove(item);
		return this;
	}
	public @Override HashMapEntrySetHashSet<T, V> clear() {
		origin.clear();
		return this;
	}
	public @Override HashMapEntrySetHashSet<T, V> addAll(ReadOnly<HashMap.Entry<T, V>> c) {
		for(HashMap.Entry<T, V> item : c) {
			add(item);
		}
		return this;
	}
	public @Override HashMapEntrySetHashSet<T, V> retainAll(ReadOnly<HashMap.Entry<T, V>> c) {
		entrySet.retainAll(c.toJavaUtilCollection());
		return this;
	}
	public @Override HashMapEntrySetHashSet<T, V> removeAll(ReadOnly<HashMap.Entry<T, V>> c) {
		entrySet.removeAll(c.toJavaUtilCollection());
		return this;
	}
	public @Override <U, E extends Exception> HashSet<U> map(ExFunction<HashMap.Entry<T, V>, U, E> mapper) throws E {
		return HashSet.from(ArrayList.from(wrapped()).map(mapper));
	}
	public @Override <E extends Exception> HashMapEntrySetHashSet<T, V> filter(
		ExPredicate<HashMap.Entry<T, V>, E> filter) throws E {
		for(Iterator<java.util.Map.Entry<T, V>> iterator = entrySet.iterator(); iterator.hasNext();) {
			if(!filter.test(new HashMap.Entry<>(iterator.next()))) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> HashMapEntrySetHashSet<T, V> removeIf(
		ExPredicate<HashMap.Entry<T, V>, E> filter) throws E {
		for(Iterator<java.util.Map.Entry<T, V>> iterator = entrySet.iterator(); iterator.hasNext();) {
			if(filter.test(new HashMap.Entry<>(iterator.next()))) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> HashMapEntrySetHashSet<T, V> replaceAll(
		ExUnaryOperator<HashMap.Entry<T, V>, E> mapper) throws E {
		ArrayList<Entry<T, V>> list = ArrayList.from(wrapped()).replaceAll(mapper);
		origin.clear();
		addAll(list);
		return this;
	}
	public @Override HashMapEntrySetHashSet<T, V> addAll(@SuppressWarnings("unchecked") HashMap.Entry<T, V>... es) {
		addAll(ArrayList.of(es));
		return this;
	}
	public @Override java.util.HashSet<HashMap.Entry<T, V>> toJavaUtilCollection() {
		return wrapped().toJavaSet();
	}
	public @Override java.util.HashSet<HashMap.Entry<T, V>> toJavaSet() {
		return wrapped().toJavaSet();
	}
	public @Override Set<HashMap.Entry<T, V>> toSet() {
		return wrapped().toSet();
	}
	public @Override HashSet<HashMap.Entry<T, V>> toHashSet() {
		return wrapped();
	}
	public @Override ArrayList<HashMap.Entry<T, V>> toArrayList() {
		return wrapped().toArrayList();
	}
	private HashSet<HashMap.Entry<T, V>> wrapped() {
		HashSet<HashMap.Entry<T, V>> hashSet = new HashSet<>();
		for(Iterator<HashMap.Entry<T, V>> iterator = iterator(); iterator.hasNext();) {
			hashSet.add(iterator.next());
		}
		return hashSet;
	}
}