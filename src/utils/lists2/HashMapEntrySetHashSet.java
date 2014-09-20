package utils.lists2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import utils.lists2.HashMap.Entry;
import utils.streams2.Stream;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;

public class HashSetEntriesHashSet<T, V> extends HashSet<HashMap.Entry<T, V>> {
	private final java.util.Set<java.util.Map.Entry<T, V>> entrySet;

	public HashSetEntriesHashSet(java.util.Set<java.util.Map.Entry<T, V>> entrySet) {
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
		return !entrySet.isEmpty();
	}
	public @Override Stream<HashMap.Entry<T, V>> stream() {
		return wrapped().stream();
	}
	public @Override Stream<HashMap.Entry<T, V>> parallelStream() {
		return wrapped().parallelStream();
	}
	public @Override Iterator<HashMap.Entry<T, V>> iterator() {
		Iterator<java.util.Map.Entry<T, V>> iterator = entrySet.iterator();
		return new HashSetEntriesHashSetIterator<>(iterator);
	}
	public @Override Spliterator<HashMap.Entry<T, V>> spliterator() {
		return new HashSetEntriesHashSetSpliterator<>(entrySet.spliterator());
	}
	public @Override <E extends Exception> HashSet<HashMap.Entry<T, V>> each(ExConsumer<HashMap.Entry<T, V>, E> action)
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
	public @Override int size() {
		return entrySet.size();
	}
	public @Override boolean isEmpty() {
		return entrySet.isEmpty();
	}
	public @Override boolean contains(HashMap.Entry<T, V> o) {
		return entrySet.contains(o);
	}
	public @Override <C extends Collection<HashMap.Entry<T, V>, C>> boolean containsAll(C c) {
		return entrySet.containsAll(Arrays.asList(c.stream().toArray()));
	}
	public @Override HashSet<HashMap.Entry<T, V>> add(HashMap.Entry<T, V> item) {
		entrySet.add(item);
		return this;
	}
	public @Override HashSet<HashMap.Entry<T, V>> remove(HashMap.Entry<T, V> item) {
		entrySet.remove(item);
		return this;
	}
	public @Override HashSet<HashMap.Entry<T, V>> clear() {
		entrySet.clear();
		return this;
	}
	public @Override <C extends Collection<HashMap.Entry<T, V>, C>> HashSet<HashMap.Entry<T, V>> addAll(C c) {
		@SuppressWarnings("unchecked")
		HashMap.Entry<T, V>[] array = (HashMap.Entry<T, V>[]) c.stream().toArray();
		entrySet.addAll(Arrays.asList(array));
		return this;
	}
	public @Override <C extends Collection<HashMap.Entry<T, V>, C>> HashSet<HashMap.Entry<T, V>> retainAll(C c) {
		@SuppressWarnings("unchecked")
		HashMap.Entry<T, V>[] array = (HashMap.Entry<T, V>[]) c.stream().toArray();
		entrySet.retainAll(Arrays.asList(array));
		return this;
	}
	public @Override <C extends Collection<HashMap.Entry<T, V>, C>> HashSet<HashMap.Entry<T, V>> removeAll(C c) {
		@SuppressWarnings("unchecked")
		HashMap.Entry<T, V>[] array = (HashMap.Entry<T, V>[]) c.stream().toArray();
		entrySet.removeAll(Arrays.asList(array));
		return this;
	}
	public @Override <U, E extends Exception> HashSet<U> map(ExFunction<HashMap.Entry<T, V>, U, E> mapper) throws E {
		return new HashSet<>(new ArrayList<>(wrapped()).map(mapper));
	}
	public @Override <E extends Exception> HashSet<HashMap.Entry<T, V>> filter(
		ExPredicate<HashMap.Entry<T, V>, E> filter) throws E {
		for(Iterator<java.util.Map.Entry<T, V>> iterator = entrySet.iterator(); iterator.hasNext();) {
			if(!filter.test(new HashMap.Entry<>(iterator.next()))) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> HashSet<HashMap.Entry<T, V>> removeIf(
		ExPredicate<HashMap.Entry<T, V>, E> filter) throws E {
		for(Iterator<java.util.Map.Entry<T, V>> iterator = entrySet.iterator(); iterator.hasNext();) {
			if(filter.test(new HashMap.Entry<>(iterator.next()))) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> HashSet<HashMap.Entry<T, V>> replaceAll(
		ExUnaryOperator<HashMap.Entry<T, V>, E> mapper) throws E {
		HashSet<HashMap.Entry<T, V>> set = new HashSet<>(new ArrayList<>(wrapped()).replaceAll(mapper));
		entrySet.clear();
		entrySet.addAll(set.wrapped);
		return this;
	}
	private HashSet<HashMap.Entry<T, V>> wrapped() {
		HashSet<Entry<T, V>> hashSet = new HashSet<>();
		for(Iterator<Entry<T, V>> iterator = iterator(); iterator.hasNext();) {
			hashSet.add(iterator.next());
		}
		return hashSet;
	}
}