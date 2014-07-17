package utils.lists2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.IntFunction;
import utils.streams2.Stream;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;

class MapValuesCollection<V> extends List<V> {
	private final java.util.Collection<V> wrapped;

	MapValuesCollection(java.util.Collection<V> values) {
		wrapped = Collections.unmodifiableCollection(values);
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
	public @Override Iterator<V> iterator() {
		return wrapped.iterator();
	}
	public @Override <E extends Exception> List<V> each(ExConsumer<V, E> action) throws E {
		for(Iterator<V> iterator = wrapped.iterator(); iterator.hasNext();) {
			action.accept(iterator.next());
		}
		return this;
	}
	public @Override int size() {
		return wrapped.size();
	}
	public @Override boolean isEmpty() {
		return wrapped.isEmpty();
	}
	public @Override boolean notEmpty() {
		return !wrapped.isEmpty();
	}
	public @Override boolean contains(V o) {
		return wrapped.contains(o);
	}
	public @Override <C extends Collection<V, C>> boolean containsAll(C c) {
		return wrapped.containsAll(Arrays.asList(c.stream().toArray()));
	}
	public @Override Object[] toArray() {
		return wrapped.toArray();
	}
	public @Override V[] toArray(IntFunction<V[]> generator) {
		return wrapped.toArray(generator.apply(size()));
	}
	public @Override V[] toArray(V[] a) {
		return wrapped.toArray(a);
	}
	public @Override Stream<V> stream() {
		return new Stream<>(() -> wrapped.stream());
	}
	public @Override Stream<V> parallelStream() {
		return new Stream<>(() -> wrapped.stream()).parallel();
	}
	public @Override List<V> add(V e) {
		return new ArrayList<>(this).add(e).toList();
	}
	public @Override List<V> remove(V o) {
		return new ArrayList<>(this).remove(o).toList();
	}
	public @Override List<V> clear() {
		return List.of();
	}
	public @Override <C extends Collection<V, C>> List<V> addAll(C c) {
		return new ArrayList<>(this).addAll(c).toList();
	}
	public @Override <C extends Collection<V, C>> List<V> retainAll(C c) {
		return new ArrayList<>(this).retainAll(c).toList();
	}
	public @Override <C extends Collection<V, C>> List<V> removeAll(C c) {
		return new ArrayList<>(this).removeAll(c).toList();
	}
	public @Override <E extends Exception> List<V> filter(ExPredicate<V, E> filter) throws E {
		return new ArrayList<>(this).filter(filter).toList();
	}
	public @Override <E extends Exception> List<V> removeIf(ExPredicate<V, E> filter) throws E {
		return new ArrayList<>(this).removeIf(filter).toList();
	}
	public @Override <E extends Exception> List<V> replaceAll(ExUnaryOperator<V, E> mapper) throws E {
		return new ArrayList<>(this).replaceAll(mapper).toList();
	}
}
