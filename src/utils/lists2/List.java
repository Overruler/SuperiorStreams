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
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams2.Stream;

public interface List<T> extends ReadWriteList<T, List<T>> {
	//*Q*
	public static <T> List<T> of(                                                                             ) { return new ListOf0 <>(                                                         ); }
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
	public static <T> List<T> from(Iterable<T> collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) collection.toArray();
		return of(array);
	}
	public static <T> List<T> from(java.util.Collection<T> collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) collection.toArray();
		return of(array);
	}
	public static <T> List<T> fromIterable(java.lang.Iterable<T> iterable) {
		return ArrayList.fromIterable(iterable).toList();
	}
	default @Override Iterator<T> iterator() {
		return new ListOfN.ImmutableIterator<>(this);
	}
	default @Override Spliterator<T> spliterator() {
		return toArrayList().spliterator();
	}
	default @Override boolean isEmpty() {
		return false;
	}
	default @Override boolean notEmpty() {
		return true;
	}
	default @Override boolean containsAll(Iterable<T> collection) {
		return toArrayList().containsAll(collection);
	}
	default @Override T[] toArray(T[] array) {
		return toArrayList().toArray(array);
	}
	default @Override List<T> addAll(Iterable<T> collection) {
		return toArrayList().addAll(collection).toList();
	}
	default @Override List<T> addAll(int index, Iterable<T> collection) {
		return toArrayList().addAll(index, collection).toList();
	}
	default @Override int indexOf(T item) {
		return toArrayList().indexOf(item);
	}
	default @Override int lastIndexOf(T item) {
		return toArrayList().lastIndexOf(item);
	}
	default @Override ListIterator<T> listIterator() {
		return listIterator(0);
	}
	default @Override ListIterator<T> listIterator(int index) {
		return new ListOfN.ImmutableListIterator<>(this, index);
	}
	default @Override Stream<T> parallelStream() {
		return stream().parallel();
	}
	default @Override List<T> clear() {
		return List.of();
	}
	default @Override List<T> remove(T element) {
		if(contains(element)) {
			return toArrayList().remove(element).toList();
		}
		return this;
	}
	default @Override List<T> remove(int index) {
		return toArrayList().remove(index).toList();
	}
	default @Override List<T> removeAll(Iterable<T> collection) {
		return toArrayList().removeAll(collection).toList();
	}
	default @Override List<T> retainAll(Iterable<T> collection) {
		return toArrayList().retainAll(collection).toList();
	}
	default @Override List<T> set(int index, T element) {
		return toArrayList().set(index, element).toList();
	}
	default @Override List<T> sort(Comparator<T> comparator) {
		return toArrayList().sort(comparator).toList();
	}
	default @Override List<T> subList(int fromIndex, int toIndex) {
		return toArrayList().subList(fromIndex, toIndex).toList();
	}
	default @Override <E extends Exception> List<T> filter(ExPredicate<T, E> filter) throws E {
		return toArrayList().filter(filter).toList();
	}
	default @Override <E extends Exception> List<T> removeIf(ExPredicate<T, E> filter) throws E {
		return toArrayList().removeIf(filter).toList();
	}
	default @Override java.util.ArrayList<T> toJavaUtilCollection() {
		return ArrayList.from(this).toJavaUtilCollection();
	}
	default @Override List<T> addAll(@SuppressWarnings("unchecked") T... values) {
		return ArrayList.from(this).addAll(values).toList();
	}
	default @Override List<T> toList() {
		return this;
	}
	default @Override ArrayList<T> toArrayList() {
		return ArrayList.from(this);
	}
	default @Override List<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
		return ArrayList.from(this).addAll(index, values).toList();
	}
	default @Override List<T> sort() {
		return ArrayList.from(this).sort().toList();
	}
	default @Override List<T> reverse() {
		return ArrayList.from(this).reverse().toList();
	}
	default @Override Set<T> toSet() {
		return Set.from(this);
	}
	default @Override HashSet<T> toHashSet() {
		return HashSet.from(this);
	}
	default @Override List<T> identity() {
		return this;
	}
	public @Override <V, E extends Exception> List<V> map(ExFunction<T, V, E> function) throws E;
}
