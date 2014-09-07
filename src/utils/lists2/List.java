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

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.IntFunction;
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
 * This is an immutable zero element {@link java.util.List} replacement which is created by calling the List.of() method.
 * @param <T> type of the elements.
 */
public class List<T> implements CollectionListAPI<T, List<T>> {
	//*Q*
	public static <T> List<T> of(                                                                             ) { return new List    <>(                                                         ); }
	public static <T> List<T> of(T one                                                                        ) { return new ListOf1 <>(one                                                      ); }
	public static <T> List<T> of(T one, T two                                                                 ) { return new ListOf2 <>(one, two                                                 ); }
	public static <T> List<T> of(T one, T two, T three                                                        ) { return new ListOf3 <>(one, two, three                                          ); }
	public static <T> List<T> of(T one, T two, T three, T four                                                ) { return new ListOf4 <>(one, two, three, four                                    ); }
	public static <T> List<T> of(T one, T two, T three, T four, T five                                        ) { return new ListOf5 <>(one, two, three, four, five                              ); }
	public static <T> List<T> of(T one, T two, T three, T four, T five, T six                                 ) { return new ListOf6 <>(one, two, three, four, five, six                         ); }
	public static <T> List<T> of(T one, T two, T three, T four, T five, T six, T seven                        ) { return new ListOf7 <>(one, two, three, four, five, six, seven                  ); }
	public static <T> List<T> of(T one, T two, T three, T four, T five, T six, T seven, T eight               ) { return new ListOf8 <>(one, two, three, four, five, six, seven, eight           ); }
	public static <T> List<T> of(T one, T two, T three, T four, T five, T six, T seven, T eight, T nine       ) { return new ListOf9 <>(one, two, three, four, five, six, seven, eight, nine     ); }
	public static <T> List<T> of(T one, T two, T three, T four, T five, T six, T seven, T eight, T nine, T ten) { return new ListOf10<>(one, two, three, four, five, six, seven, eight, nine, ten); }

	public @SafeVarargs static <T> List<T> of(T... items) {
		switch(items.length) {
			case 0:  return of();
			case 1:  return of(items[0]);
			case 2:  return of(items[0], items[1]);
			case 3:  return of(items[0], items[1], items[2]);
			case 4:  return of(items[0], items[1], items[2], items[3]);
			case 5:  return of(items[0], items[1], items[2], items[3], items[4]);
			case 6:  return of(items[0], items[1], items[2], items[3], items[4], items[5]);
			case 7:  return of(items[0], items[1], items[2], items[3], items[4], items[5], items[6]);
			case 8:  return of(items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7]);
			case 9:  return of(items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7], items[8]);
			case 10: return of(items[0], items[1], items[2], items[3], items[4], items[5], items[6], items[7], items[8], items[9]);
			default: return new ListOfN<>(items);
		}
	}
	//*E*
	public static <T, C extends Collection<T, C>> List<T> from(C collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) collection.toArray();
		return of(array);
	}
	public static <T> List<T> from(java.util.Collection<T> collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) collection.toArray();
		return of(array);
	}
	public static <T> List<T> fromIterable(Iterable<T> iterable) {
		return ArrayList.fromIterable(iterable).toList();
	}
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
			return otherList.getClass() == List.class;
		}
		if(otherList instanceof Collection) {
			return ((Collection<?, ?>) otherList).isEmpty();
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
		return new ImmutableIterator<>(this);
	}
	public @Override Spliterator<T> spliterator() {
		return toArrayList().spliterator();
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
	public @Override <C extends Collection<T, C>> boolean containsAll(C collection) {
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
		return toArrayList().stream();
	}
	public @Override Stream<T> parallelStream() {
		return toArrayList().parallelStream();
	}
	public @Override <E extends Exception> List<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
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
	public @Override <C extends Collection<T, C>> List<T> addAll(C collection) {
		return toArrayList().addAll(collection).toList();
	}
	public @Override List<T> addAll(List<T> source) {
		return toArrayList().addAll(source).toList();
	}
	public @Override List<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
		return toArrayList().addAll(index, values).toList();
	}
	public @Override <C extends Collection<T, C>> List<T> addAll(int index, C collection) {
		return toArrayList().addAll(index, collection).toList();
	}
	public @Override List<T> addAll(int index, List<T> source) {
		return toArrayList().addAll(index, source).toList();
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
	public @Override <C extends Collection<T, C>> List<T> removeAll(C collection) {
		return toArrayList().removeAll(collection).toList();
	}
	public @Override <C extends Collection<T, C>> List<T> retainAll(C collection) {
		return toArrayList().retainAll(collection).toList();
	}
	public @Override List<T> set(int index, T element) {
		throw new IndexOutOfBoundsException("Index: " + index + " Size: 0");
	}
	public @Override List<T> sort(Comparator<T> comparator) {
		return this;
	}
	public @Override int indexOf(T item) {
		return -1;
	}
	public @Override int lastIndexOf(T item) {
		return -1;
	}
	public @Override ListIterator<T> listIterator() {
		return new ImmutableListIterator<>(this, 0);
	}
	public @Override ListIterator<T> listIterator(int index) {
		return new ImmutableListIterator<>(this, index);
	}
	public @Override List<T> subList(int fromIndex, int toIndex) {
		return this;
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
		return new ArrayList<>(this);
	}
	public @Override Set<T> toSet() {
		return Set.from(this);
	}
	public @Override HashSet<T> toHashSet() {
		return new HashSet<>(this);
	}
	public @Override java.util.ArrayList<T> toJavaList() {
		return toArrayList().toJavaList();
	}
}
