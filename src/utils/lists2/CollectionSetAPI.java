package utils.lists2;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;

public interface CollectionSetAPI<T, SET extends CollectionSetAPI<T, SET>> extends RandomLookup<T, SET> {
	public @Override String toString();
	public @Override int hashCode();
	public @Override boolean equals(Object o);
	public @Override Iterator<T> iterator();
	public @Override Spliterator<T> spliterator();
	public @Override <E extends Exception> SET each(ExConsumer<T, E> procedure) throws E;
	public @Override T[] toArray(IntFunction<T[]> generator);
	public @Override Object[] toArray();
	public @Override T[] toArray(T[] a);
	public @Override int size();
	public @Override boolean isEmpty();
	public @Override boolean notEmpty();
	public @Override boolean contains(T o);
	public @Override <C extends Collection<T, C>> boolean containsAll(C c);
	public @Override SET add(T e);
	public @Override SET remove(T o);
	public @Override SET clear();
	public @Override <C extends Collection<T, C>> SET addAll(C c);
	public @Override <C extends Collection<T, C>> SET retainAll(C c);
	public @Override <C extends Collection<T, C>> SET removeAll(C c);
	public @Override <E extends Exception> SET filter(ExPredicate<T, E> filter) throws E;
	public @Override <E extends Exception> SET removeIf(ExPredicate<T, E> filter) throws E;
	public @Override <E extends Exception> SET replaceAll(ExUnaryOperator<T, E> mapper) throws E;
	public @Override java.util.HashSet<T> toJavaSet();
	public @Override Set<T> toSet();
	public @Override HashSet<T> toHashSet();
	<U, E extends Exception> Object map(ExFunction<T, U, E> mapper) throws E;
	List<T> toList();
	ArrayList<T> toArrayList();
}
