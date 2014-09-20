/*
 * Copyright 2014 Timo Kinnunen.
 * Copyright 2013 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package utils.lists2;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.ExUnaryOperator;
import utils.streams.functions.IntFunction;
import utils.streams.functions.Predicate;
import utils.streams2.Stream;

/**
 * An ImmutableArrayList (here ListOfN) wraps a Java array but it cannot be modified after creation.
 */
class ListOfN<T> implements List<T> {
	final T[] items;

	ListOfN(T[] newElements) {
		items = newElements;
	}
	public @Override String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append('[');
		T[] localItems = items;
		for(int i = 0, n = localItems.length; i < n; i++) {
			T item = localItems[i];
			if(i > 0) {
				buf.append(", ");
			}
			buf.append(item == this ? "(this List)" : String.valueOf(item));
		}
		buf.append(']');
		return buf.toString();
	}
	public @Override int hashCode() {
		int hashCode = 1;
		int localSize = size();
		for(int i = 0; i < localSize; i++) {
			T item = items[i];
			hashCode = 31 * hashCode + (item == null ? 0 : item.hashCode());
		}
		return hashCode;
	}
	public @Override boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof ListOfN) {
			return Arrays.equals(items, ((ListOfN<?>) otherList).items);
		}
		if(otherList instanceof ArrayList) {
			ArrayList<?> list = (ArrayList<?>) otherList;
			if(items.length != list.size) {
				return false;
			}
			return ArrayList.arraysEqual(items, list.items, items.length);
		}
		if(otherList instanceof ReadOnlyList) {
			return randomAccessListEquals((ReadOnlyList<?>) otherList);
		}
		if(otherList instanceof java.lang.Iterable) {
			return regularListEquals((java.lang.Iterable<?>) otherList);
		}
		return false;
	}
	private boolean regularListEquals(java.lang.Iterable<?> otherList) {
		T[] items1 = items;
		int size2 = items1.length;
		if(otherList instanceof Iterable && size2 != ((Iterable<?>) otherList).size()) {
			return false;
		}
		if(otherList instanceof java.util.Collection && size2 != ((java.util.Collection<?>) otherList).size()) {
			return false;
		}
		Iterator<?> iterator = otherList.iterator();
		for(int i = 0; i < size2; i++) {
			T one = items1[i];
			if(!iterator.hasNext()) {
				return false;
			}
			Object two = iterator.next();
			if(!Objects.equals(one, two)) {
				return false;
			}
		}
		return !iterator.hasNext();
	}
	private boolean randomAccessListEquals(ReadOnlyList<?> otherList) {
		T[] items2 = items;
		if(items2.length != otherList.size()) {
			return false;
		}
		for(int i = 0, n = items2.length; i < n; i++) {
			if(!Objects.equals(items2[i], otherList.get(i))) {
				return false;
			}
		}
		return true;
	}
	public @Override Stream<T> stream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), false));
	}
	public @Override Stream<T> parallelStream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), true));
	}
	public @Override Iterator<T> iterator() {
		return Arrays.asList(items).iterator();
	}
	public @Override Spliterator<T> spliterator() {
		return Arrays.spliterator(items);
	}
	public @Override int size() {
		return items.length;
	}
	public @Override boolean isEmpty() {
		return items.length == 0;
	}
	public @Override boolean notEmpty() {
		return items.length > 0;
	}
	public @Override boolean contains(T o) {
		return anySatisfy(equal2(o));
	}
	public @Override boolean containsAll(Iterable<T> collection) {
		return allSatisfyIterate(collection, inPredicates(items));
	}
	public @Override Object[] toArray() {
		return items.clone();
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		int size = size();
		T[] a = generator.apply(size);
		System.arraycopy(items, 0, a, 0, size);
		return a;
	}
	public @Override T[] toArray(T[] a) {
		int size = size();
		if(a.length < size) {
			@SuppressWarnings("unchecked")
			T[] array = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
			a = array;
		}
		System.arraycopy(items, 0, a, 0, size);
		if(a.length > size) {
			a[size] = null;
		}
		return a;
	}
	public @Override <E extends Exception> ListOfN<T> each(ExConsumer<T, E> action) throws E {
		Objects.requireNonNull(action);
		if(items.length > 0) {
			for(T each1 : items) {
				action.accept(each1);
			}
		}
		return this;
	}
	public @Override <E extends Exception> ListOfN<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		Objects.requireNonNull(action);
		int size = items.length;
		for(int i = 0; i < size; i++) {
			action.accept(items[i], i);
		}
		return this;
	}
	public @Override List<T> add(T element) {
		int size = size();
		T[] array = newArray(size + 1);
		System.arraycopy(items, 0, array, 0, size);
		array[size] = element;
		return new ListOfN<>(array);
	}
	public @Override List<T> add(int index, T element) {
		int size = size();
		index = ArrayList.adjustIndexToPositiveInts(index, size);
		T[] array = newArray(size + 1);
		System.arraycopy(items, 0, array, 0, index);
		array[index] = element;
		System.arraycopy(items, index, array, index + 1, size - index);
		return new ListOfN<>(array);
	}
	public @Override List<T> addAll(Iterable<T> collection) {
		int size = size();
		int size2 = collection.size();
		T[] array = newArray(size + size2);
		Object[] array2 = collection.toArray();
		System.arraycopy(items, 0, array, 0, size);
		System.arraycopy(array2, 0, array, size, size2);
		return new ListOfN<>(array);
	}
	public List<T> addAll(ListOfN<T> source) {
		int size = size();
		int size2 = source.size();
		T[] array = newArray(size + size2);
		System.arraycopy(items, 0, array, 0, size);
		System.arraycopy(source.items, 0, array, size, size2);
		return new ListOfN<>(array);
	}
	public @Override List<T> addAll(int index, Iterable<T> collection) {
		int size = size();
		index = ArrayList.adjustIndexToPositiveInts(index, size);
		int size2 = collection.size();
		T[] array = newArray(size + size2);
		System.arraycopy(items, 0, array, 0, index);
		System.arraycopy(collection.toArray(), 0, array, index, size2);
		System.arraycopy(items, index, array, index + size2, size - index - size2);
		return new ListOfN<>(array);
	}
	public List<T> addAll(int index, ListOfN<T> source) {
		int size = size();
		index = ArrayList.adjustIndexToPositiveInts(index, size);
		int size2 = source.size();
		T[] array = newArray(size + size2);
		System.arraycopy(items, 0, array, 0, index);
		System.arraycopy(source.items, 0, array, index, size2);
		System.arraycopy(items, index, array, index + size2, size - index - size2);
		return new ListOfN<>(array);
	}
	public @Override T get(int index) {
		index = ArrayList.adjustIndexToPositiveInts(index, size());
		return items[index];
	}
	public @Override int indexOf(Object item) {
		return indexOfArrayIterate(items, item);
	}
	public @Override int lastIndexOf(Object item) {
		return Arrays.asList(items).lastIndexOf(item);
	}
	public @Override ListIterator<T> listIterator() {
		return new ImmutableListIterator<>(this, 0);
	}
	public @Override ListIterator<T> listIterator(int index) {
		return new ImmutableListIterator<>(this, index);
	}
	public @Override <U, E extends Exception> List<U> map(ExFunction<T, U, E> mapper) throws E {
		T[] localItems = items;
		U[] target = newArray(localItems.length);
		for(int i = 0; i < localItems.length; i++) {
			target[i] = mapper.apply(localItems[i]);
		}
		return List.of(target);
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		T[] localItems = items;
		double[] target = new double[localItems.length];
		for(int i = 0; i < localItems.length; i++) {
			target[i] = doubleFunction.applyAsDouble(localItems[i]);
		}
		return target;
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		T[] localItems = items;
		int[] target = new int[localItems.length];
		for(int i = 0; i < localItems.length; i++) {
			target[i] = intFunction.applyAsInt(localItems[i]);
		}
		return target;
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		T[] localItems = items;
		long[] target = new long[localItems.length];
		for(int i = 0; i < localItems.length; i++) {
			target[i] = longFunction.applyAsLong(localItems[i]);
		}
		return target;
	}
	public @Override <E extends Exception> List<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		T[] target = items.clone();
		for(int i = 0; i < target.length; i++) {
			target[i] = mapper.apply(target[i]);
		}
		return List.of(target);
	}
	@SafeVarargs
	private static <T> ExPredicate<T, RuntimeException> inPredicates(T... array) {
		java.util.Collection<?> iterable = Arrays.asList(array);
		return anObject -> containsIterate(iterable, anObject);
	}
	/**
	 * Returns true if the iterable contains the value.  In the case of Collections and RichIterables, the method contains
	 * is called.  All other iterables will force a complete iteration to happen, which can be unnecessarily costly.
	 */
	private static <T> boolean containsIterate(java.util.Collection<?> iterable, T value) {
		return iterable.contains(value);
	}
	/**
	 * Returns true if the predicate evaluates to true for every element of the iterable, or returns false.
	 * Returns true if the iterable is empty.
	 */
	private static <T, E extends Exception> boolean
 allSatisfyIterate(
		java.lang.Iterable<T> iterable,
		ExPredicate<T, E> predicate) throws E {
		if(iterable instanceof ArrayList) {
			return allSatisfyArrayListIterate((ArrayList<T>) iterable, predicate);
		}
		if(iterable instanceof List) {
			return allSatisfyRandomAccessListIterate((List<T>) iterable, predicate);
		}
		return allSatisfyIterableIterate(iterable, predicate);
	}
	/**
	 * @see Iterate#allSatisfy(java.lang.Iterable, Predicate)
	 */
	private static <T, E extends Exception> boolean allSatisfyIterableIterate(
		java.lang.Iterable<T> iterable,
		ExPredicate<? super T, E> predicate) throws E {
		return allSatisfyIteratorIterate(iterable.iterator(), predicate);
	}
	/**
	 * @see Iterate#allSatisfy(java.lang.Iterable, Predicate)
	 */
	private static <T, E extends Exception> boolean allSatisfyIteratorIterate(
		Iterator<T> iterator,
		ExPredicate<? super T, E> predicate) throws E {
		while(iterator.hasNext()) {
			if(!predicate.test(iterator.next())) {
				return false;
			}
		}
		return true;
	}
	private static <T, E extends Exception> boolean allSatisfyRandomAccessListIterate(
		List<T> list,
		ExPredicate<? super T, E> predicate) throws E {
		for(int i = 0, n = list.size(); i < n; i++) {
			if(!predicate.test(list.get(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * @see Iterate#allSatisfy(java.lang.Iterable, Predicate)
	 */
	private static <T, E extends Exception> boolean allSatisfyArrayListIterate(
		ArrayList<T> list,
		ExPredicate<T, E> predicate) throws E {
		T[] elements = list.items;
		for(int i = 0, n = list.size(); i < n; i++) {
			if(!predicate.test(elements[i])) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Searches for the first occurrence of the given argument, testing
	 * for equality using the <tt>equals</tt> method.
	 */
	private static <T> int indexOfArrayIterate(T[] objectArray, T elem) {
		return detectIndexArrayIterate(objectArray, equalPredicates(elem));
	}
	/**
	 * Tests for equality.
	 */
	private static <T> ExPredicate<T, RuntimeException> equalPredicates(T object) {
		if(object == null) {
			return (tested) -> tested == null;
		}
		return (tested) -> object.equals(tested);
	}
	/**
	 * Returns the first index where the predicate evaluates to true.  Returns -1 for no matches.
	 */
	private static <T, E extends Exception> int detectIndexArrayIterate(T[] objectArray, ExPredicate<T, E> predicate)
		throws E {
		for(int i = 0; i < objectArray.length; i++) {
			if(predicate.test(objectArray[i])) {
				return i;
			}
		}
		return -1;
	}
	private static <T> T[] newArray(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[size];
		return array;
	}
	private static <T> ExPredicate<T, RuntimeException> equal2(T object) {
		if(object == null) {
			return anObject -> anObject == null;
		}
		return anObject -> object.equals(anObject);
	}
	private <E extends Exception> boolean anySatisfy(ExPredicate<T, E> predicate) throws E {
		return anySatisfy2(items, predicate);
	}
	private static <T, E extends Exception> boolean anySatisfy2(T[] objectArray, ExPredicate<T, E> predicate) throws E {
		for(T each : objectArray) {
			if(predicate.test(each)) {
				return true;
			}
		}
		return false;
	}
	public @Override List<T> addAll(@SuppressWarnings("unchecked") T... values) {
		return ArrayList.from(this).addAll(values).toList();
	}
	public @Override List<T> toList() {
		return this;
	}
	public @Override ArrayList<T> toArrayList() {
		return ArrayList.from(this);
	}
	public @Override List<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
		return ArrayList.from(this).addAll(index, values).toList();
	}
	public @Override List<T> sort() {
		return ArrayList.from(this).sort().toList();
	}
	public @Override List<T> reverse() {
		return ArrayList.from(this).reverse().toList();
	}
	public @Override Set<T> toSet() {
		return Set.from(this);
	}
	public @Override HashSet<T> toHashSet() {
		return HashSet.from(this);
	}

	static class ImmutableIterator<T> implements Iterator<T> {
		/**
		 * Index of element to be returned by subsequent call to next.
		 */
		protected int currentIndex;
		protected final List<T> list;

		public ImmutableIterator(List<T> list) {
			this.list = list;
		}
		public @Override boolean hasNext() {
			return this.currentIndex != this.list.size();
		}
		public @Override T next() {
			try {
				T result = this.list.get(this.currentIndex);
				this.currentIndex++;
				return result;
			} catch(IndexOutOfBoundsException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}
		public @Override void remove() {
			// remove doesn't change original immutable list
		}
	}
	static final class ImmutableListIterator<T> extends ImmutableIterator<T> implements ListIterator<T> {
		public ImmutableListIterator(List<T> list, int index) {
			super(list);
			currentIndex = ArrayList.adjustIndexToPositiveInts(index, list.size());
		}
		public @Override boolean hasPrevious() {
			return currentIndex != 0;
		}
		public @Override T previous() {
			try {
				int i = currentIndex - 1;
				T previous = list.get(i);
				currentIndex = i;
				return previous;
			} catch(IndexOutOfBoundsException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}
		public @Override int nextIndex() {
			return currentIndex;
		}
		public @Override int previousIndex() {
			return currentIndex - 1;
		}
		public @Override void set(T o) {
			// set doesn't change original immutable list
		}
		public @Override void add(T o) {
			// add doesn't change original immutable list
		}
	}
}
