package utils.lists2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.IntFunction;
import utils.streams2.Stream;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;

class HashMapValuesCollection<V> extends ArrayList<V> {
	private final java.util.Collection<V> wrapped;

	HashMapValuesCollection(java.util.Collection<V> values) {
		this.wrapped = values;
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
	public @Override <E extends Exception> ArrayList<V> each(ExConsumer<V, E> action) throws E {
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
	public @Override ArrayList<V> add(V e) {
		return new ArrayList<>(this).add(e);
	}
	public @Override ArrayList<V> remove(V o) {
		wrapped.remove(o);
		return this;
	}
	public @Override ArrayList<V> clear() {
		wrapped.clear();
		return this;
	}
	public @Override <C extends Collection<V, C>> ArrayList<V> addAll(C c) {
		return new ArrayList<>(this).addAll(c);
	}
	public @Override <C extends Collection<V, C>> ArrayList<V> retainAll(C c) {
		@SuppressWarnings("unchecked")
		V[] array = (V[]) c.toArray();
		wrapped.retainAll(Arrays.asList(array));
		return this;
	}
	public @Override <C extends Collection<V, C>> ArrayList<V> removeAll(C c) {
		@SuppressWarnings("unchecked")
		V[] array = (V[]) c.toArray();
		wrapped.removeAll(Arrays.asList(array));
		return this;
	}
	public @Override <E extends Exception> ArrayList<V> filter(ExPredicate<V, E> filter) throws E {
		for(Iterator<V> iterator = wrapped.iterator(); iterator.hasNext();) {
			if(!filter.test(iterator.next())) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> ArrayList<V> removeIf(ExPredicate<V, E> filter) throws E {
		for(Iterator<V> iterator = wrapped.iterator(); iterator.hasNext();) {
			if(filter.test(iterator.next())) {
				iterator.remove();
			}
		}
		return this;
	}
	public @Override <E extends Exception> ArrayList<V> replaceAll(ExUnaryOperator<V, E> mapper) throws E {
		return new ArrayList<>(this).replaceAll(mapper);
	}
}
