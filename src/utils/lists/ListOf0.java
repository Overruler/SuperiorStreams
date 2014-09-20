package utils.lists2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import utils.lists2.Collections;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.ExUnaryOperator;
import utils.streams.functions.IntFunction;
import utils.streams2.Stream;

/**
 * This is an immutable zero element {@link java.util.List} replacement which is created by calling the List.of() method.
 * @param <T> type of the elements.
 */
class ListOf0<T> implements List<T> {
	public @Override String toString() {
		return "[]";
	}
	public @Override int hashCode() {
		return 1;
	}
	public @Override boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof List) {
			return otherList.getClass() == ListOf0.class;
		}
		if(otherList instanceof java.lang.Iterable) {
			return ((java.lang.Iterable<?>) otherList).iterator().hasNext();
		}
		return false;
	}
	public @Override int size() {
		return 0;
	}
	public @Override <E extends Exception> List<T> each(ExConsumer<T, E> procedure) throws E {
		return this;
	}
	public @Override T get(int index) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
	}
	public @Override List<T> add(T newItem) {
		return List.of(newItem);
	}
	public @Override Iterator<T> iterator() {
		return Collections.emptyIterator();
	}
	public @Override Spliterator<T> spliterator() {
		return Spliterators.emptySpliterator();
	}
	public @Override boolean isEmpty() {
		return true;
	}
	public @Override boolean notEmpty() {
		return false;
	}
	public @Override boolean contains(T element) {
		return false;
	}
	public @Override boolean containsAll(Iterable<T> collection) {
		return false;
	}
	public @Override Object[] toArray() {
		return new Object[0];
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		return generator.apply(0);
	}
	public @Override T[] toArray(T[] array) {
		if(array.length > 0) {
			array[0] = null;
		}
		return array;
	}
	public @Override Stream<T> stream() {
		return Stream.of();
	}
	public @Override Stream<T> parallelStream() {
		return Stream.of();
	}
	public @Override <E extends Exception> ListOf0<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		return this;
	}
	public @Override List<T> add(int index, T element) {
		switch(index) {//*Q*
			case -1: case 0: return List.of(element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 0");
		}//*E*
	}
	public @Override List<T> addAll(@SuppressWarnings("unchecked") T... values) {
		return toArrayList().addAll(values).toList();
	}
	public @Override List<T> addAll(Iterable<T> collection) {
		return toArrayList().addAll(collection).toList();
	}
	public @Override List<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
		return toArrayList().addAll(index, values).toList();
	}
	public @Override List<T> addAll(int index, Iterable<T> collection) {
		return toArrayList().addAll(index, collection).toList();
	}
	public @Override List<T> clear() {
		return List.of();
	}
	public @Override List<T> remove(T element) {
		return toArrayList().remove(element).toList();
	}
	public @Override List<T> remove(int index) {
		throw new IndexOutOfBoundsException("Index: " + index + " Size: 0");
	}
	public @Override List<T> removeAll(Iterable<T> collection) {
		return toArrayList().removeAll(collection).toList();
	}
	public @Override List<T> retainAll(Iterable<T> collection) {
		return toArrayList().retainAll(collection).toList();
	}
	public @Override List<T> set(int index, T element) {
		throw new IndexOutOfBoundsException("Index: " + index + " Size: 0");
	}
	public @Override List<T> sort(Comparator<T> comparator) {
		return this;
	}
	public @Override List<T> sort() {
		return toArrayList().sort().toList();
	}
	public @Override List<T> reverse() {
		return toArrayList().reverse().toList();
	}
	public @Override int indexOf(T item) {
		return -1;
	}
	public @Override int lastIndexOf(T item) {
		return -1;
	}
	public @Override ListIterator<T> listIterator() {
		return Collections.emptyListIterator();
	}
	public @Override ListIterator<T> listIterator(int index) {
		index = ArrayList.adjustIndexToPositiveInts(index, 0);
		return Collections.emptyListIterator();
	}
	public @Override List<T> subList(int fromIndex, int toIndex) {
		return toArrayList().subList(fromIndex, toIndex).toList();
	}
	public @Override <U, E extends Exception> List<U> map(ExFunction<T, U, E> mapper) throws E {
		return List.of();
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		return new double[0];
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		return new int[0];
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		return new long[0];
	}
	public @Override <E extends Exception> List<T> filter(ExPredicate<T, E> filter) throws E {
		return this;
	}
	public @Override <E extends Exception> List<T> removeIf(ExPredicate<T, E> filter) throws E {
		return this;
	}
	public @Override <E extends Exception> List<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		return this;
	}
	public @Override List<T> toList() {
		return this;
	}
	public @Override ArrayList<T> toArrayList() {
		return ArrayList.of();
	}
	public @Override Set<T> toSet() {
		return Set.of();
	}
	public @Override HashSet<T> toHashSet() {
		return HashSet.of();
	}
	public @Override java.util.ArrayList<T> toJavaUtilCollection() {
		return new java.util.ArrayList<>();
	}
}