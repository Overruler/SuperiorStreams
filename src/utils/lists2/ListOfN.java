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
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.ExUnaryOperator;
import utils.streams2.Stream;

/**
 * An ImmutableArrayList (here ListOfN) wraps a Java array but it cannot be modified after creation.
 */
class ListOfN<T> extends List<T> {
	private final T[] items;

	ListOfN(T[] newElements) {
		items = newElements;
	}
	@Override
	public String toString() {
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
	@Override
	public int hashCode() {
		int hashCode = 1;
		int localSize = size();
		for(int i = 0; i < localSize; i++) {
			T item = items[i];
			hashCode = 31 * hashCode + (item == null ? 0 : item.hashCode());
		}
		return hashCode;
	}
	@Override
	public boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof ListOfN) {
			return Arrays.equals(items, ((ListOfN<?>) otherList).items);
		}
		if(otherList instanceof ArrayList) {
			ArrayList<?> list = (ArrayList<?>) otherList;
			if(size() != list.size()) {
				return false;
			}
			for(int i = 0; i < size(); i++) {
				T one = items[i];
				Object two = list.get(i);
				if(!Objects.equals(one, two)) {
					return false;
				}
			}
			return true;
		}
		if(otherList instanceof Collection) {
			Collection<?, ?> list = (Collection<?, ?>) otherList;
			Iterator<?> iterator = list.iterator();
			for(int i = 0; i < size(); i++) {
				T one = items[i];
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
		return false;
	}
	@Override
	public Iterator<T> iterator() {
		return Arrays.asList(items).iterator();
	}
	@Override
	public Spliterator<T> spliterator() {
		return Arrays.spliterator(items);
	}
	@Override
	public int size() {
		return items.length;
	}
	@Override
	public boolean isEmpty() {
		return isEmpty2(items);
	}
	@Override
	public boolean notEmpty() {
		return notEmptyArrayIterate(items);
	}
	@Override
	public boolean contains(T o) {
		return anySatisfy(equal2(o));
	}
	@Override
	public <C extends Collection<T, C>> boolean containsAll(C collection) {
		return allSatisfyIterate(collection, inPredicates(items));
	}
	@Override
	public Object[] toArray() {
		return items.clone();
	}
	@Override
	public T[] toArray(IntFunction<T[]> generator) {
		int size = size();
		T[] a = generator.apply(size);
		System.arraycopy(items, 0, a, 0, size);
		return a;
	}
	@Override
	public T[] toArray(T[] a) {
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
	@Override
	public Stream<T> stream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), false));
	}
	@Override
	public Stream<T> parallelStream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), true));
	}
	@Override
	public <E extends Exception> ListOfN<T> each(ExConsumer<T, E> action) throws E {
		Objects.requireNonNull(action);
		if(notEmptyArrayIterate(items)) {
			for(T each1 : items) {
				action.accept(each1);
			}
		}
		return this;
	}
	@Override
	public <E extends Exception> ListOfN<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		Objects.requireNonNull(action);
		int size = items.length;
		for(int i = 0; i < size; i++) {
			action.accept(items[i], i);
		}
		return this;
	}
	@Override
	public List<T> add(T element) {
		int size = size();
		T[] array = newArray(size);
		System.arraycopy(items, 0, array, 0, size);
		array[size] = element;
		return new ListOfN<>(array);
	}
	@Override
	public List<T> add(int index, T element) {
		if(index > size() || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
		}
		int size = size();
		T[] array = newArray(size);
		System.arraycopy(items, 0, array, 0, index);
		array[index] = element;
		System.arraycopy(items, index, array, index + 1, size - index);
		return new ListOfN<>(array);
	}
	@Override
	public <C extends Collection<T, C>> List<T> addAll(C collection) {
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
	@Override
	public <C extends Collection<T, C>> List<T> addAll(int index, C collection) {
		if(index > size() || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
		}
		int size = size();
		int size2 = collection.size();
		T[] array = newArray(size + size2);
		System.arraycopy(items, 0, array, 0, index);
		System.arraycopy(collection.toArray(), 0, array, index, size2);
		System.arraycopy(items, index, array, index + size2, size - index - size2);
		return new ListOfN<>(array);
	}
	public List<T> addAll(int index, ListOfN<T> source) {
		if(index > size() || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
		}
		int size = size();
		int size2 = source.size();
		T[] array = newArray(size + size2);
		System.arraycopy(items, 0, array, 0, index);
		System.arraycopy(source.items, 0, array, index, size2);
		System.arraycopy(items, index, array, index + size2, size - index - size2);
		return new ListOfN<>(array);
	}
	@Override
	public List<T> clear() {
		return List.of();
	}
	@Override
	public List<T> remove(T element) {
		if(this.contains(element)) {
			return toArrayList().remove(element).toList();
		}
		return this;
	}
	@Override
	public List<T> remove(int index) {
		return toArrayList().remove(index).toList();
	}
	@Override
	public <C extends Collection<T, C>> List<T> removeAll(C collection) {
		return toArrayList().removeAll(collection).toList();
	}
	@Override
	public <C extends Collection<T, C>> List<T> retainAll(C collection) {
		return toArrayList().retainAll(collection).toList();
	}
	@Override
	public T get(int index) {
		return items[index];
	}
	@Override
	public List<T> set(int index, T element) {
		return toArrayList().set(index, element).toList();
	}
	@Override
	public List<T> sort(Comparator<T> comparator) {
		return toArrayList().sort(comparator).toList();
	}
	@Override
	public int indexOf(Object item) {
		return indexOfArrayIterate(items, item);
	}
	@Override
	public int lastIndexOf(Object item) {
		return Arrays.asList(items).lastIndexOf(item);
	}
	@Override
	public ListIterator<T> listIterator() {
		return new ImmutableListIterator<>(this, 0);
	}
	@Override
	public ListIterator<T> listIterator(int index) {
		return new ImmutableListIterator<>(this, index);
	}
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return toArrayList().subList(fromIndex, toIndex).toList();
	}
	@Override
	public <U, E extends Exception> List<U> map(ExFunction<T, U, E> mapper) throws E {
		T[] localItems = items;
		U[] target = newArray(localItems.length);
		for(int i = 0; i < localItems.length; i++) {
			target[i] = mapper.apply(localItems[i]);
		}
		return List.of(target);
	}
	@Override
	public <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction) throws E {
		T[] localItems = items;
		double[] target = new double[localItems.length];
		for(int i = 0; i < localItems.length; i++) {
			target[i] = doubleFunction.applyAsDouble(localItems[i]);
		}
		return target;
	}
	@Override
	public <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		T[] localItems = items;
		int[] target = new int[localItems.length];
		for(int i = 0; i < localItems.length; i++) {
			target[i] = intFunction.applyAsInt(localItems[i]);
		}
		return target;
	}
	@Override
	public <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		T[] localItems = items;
		long[] target = new long[localItems.length];
		for(int i = 0; i < localItems.length; i++) {
			target[i] = longFunction.applyAsLong(localItems[i]);
		}
		return target;
	}
	@Override
	public <E extends Exception> List<T> filter(ExPredicate<T, E> filter) throws E {
		return toArrayList().filter(filter).toList();
	}
	@Override
	public <E extends Exception> List<T> removeIf(ExPredicate<T, E> filter) throws E {
		return toArrayList().removeIf(filter).toList();
	}
	@Override
	public <E extends Exception> List<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		T[] target = items.clone();
		for(int i = 0; i < target.length; i++) {
			target[i] = mapper.apply(target[i]);
		}
		return List.of(target);
	}
	@SafeVarargs
	private static <T> ExPredicate<T, RuntimeException> inPredicates(T... array) {
		java.util.Collection<T> iterable = Arrays.asList(array);
		return anObject -> containsIterate(iterable, anObject);
	}
	/**
	 * Returns true if the iterable contains the value.  In the case of Collections and RichIterables, the method contains
	 * is called.  All other iterables will force a complete iteration to happen, which can be unnecessarily costly.
	 */
	private static <T> boolean containsIterate(java.util.Collection<T> iterable, T value) {
		return iterable.contains(value);
	}
	/**
	 * Returns true if the predicate evaluates to true for every element of the iterable, or returns false.
	 * Returns true if the iterable is empty.
	 */
	private static <T, E extends Exception> boolean
		allSatisfyIterate(Iterable<T> iterable, ExPredicate<T, E> predicate) throws E {
		if(iterable instanceof ArrayList) {
			return allSatisfyArrayListIterate((ArrayList<T>) iterable, predicate);
		}
		if(iterable instanceof List) {
			return allSatisfyRandomAccessListIterate((List<T>) iterable, predicate);
		}
		return allSatisfyIterableIterate(iterable, predicate);
	}
	/**
	 * @see Iterate#allSatisfy(Iterable, Predicate)
	 */
	private static <T, E extends Exception> boolean allSatisfyIterableIterate(
		Iterable<T> iterable,
		ExPredicate<? super T, E> predicate) throws E {
		return allSatisfyIteratorIterate(iterable.iterator(), predicate);
	}
	/**
	 * @see Iterate#allSatisfy(Iterable, Predicate)
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
	 * @see Iterate#allSatisfy(Iterable, Predicate)
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
	private static boolean notEmptyArrayIterate(Object[] array) {
		return array != null && array.length > 0;
	}
	private static boolean isEmpty2(Object[] array) {
		return array == null || array.length == 0;
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
}
