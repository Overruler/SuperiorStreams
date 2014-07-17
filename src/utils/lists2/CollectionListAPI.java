/*
 * Copyright 2014 Timo Kinnunen.
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
import utils.streams2.Stream;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.ExUnaryOperator;

interface CollectionListAPI<T, LIST extends CollectionListAPI<T, LIST>> extends RandomAccess<T, LIST> {
	public @Override String toString();
	public @Override int hashCode();
	public @Override boolean equals(Object otherList);
	public @Override Iterator<T> iterator();
	public @Override Spliterator<T> spliterator();
	public @Override int size();
	public @Override boolean isEmpty();
	public @Override boolean notEmpty();
	public @Override boolean contains(T o);
	public @Override <COLLECTION extends Collection<T, COLLECTION>> boolean containsAll(COLLECTION collection);
	public @Override Object[] toArray();
	public @Override T[] toArray(IntFunction<T[]> generator);
	public @Override T[] toArray(T[] a);
	public @Override Stream<T> stream();
	public @Override Stream<T> parallelStream();
	public @Override <E extends Exception> LIST each(ExConsumer<T, E> action) throws E;
	public @Override <E extends Exception> LIST eachWithIndex(ExObjIntConsumer<T, E> action) throws E;
	public @Override LIST add(T element);
	public @Override <COLLECTION extends Collection<T, COLLECTION>> LIST addAll(COLLECTION collection);
	public @Override LIST clear();
	public @Override LIST remove(T element);
	public @Override <COLLECTION extends Collection<T, COLLECTION>> LIST removeAll(COLLECTION collection);
	public @Override <COLLECTION extends Collection<T, COLLECTION>> LIST retainAll(COLLECTION collection);
	public @Override <E extends Exception> LIST filter(ExPredicate<T, E> filter) throws E;
	public @Override <E extends Exception> LIST removeIf(ExPredicate<T, E> filter) throws E;
	public @Override <E extends Exception> LIST replaceAll(ExUnaryOperator<T, E> mapper) throws E;
	public @Override T get(int index);
	public @Override int indexOf(T item);
	public @Override int lastIndexOf(T item);
	public @Override ListIterator<T> listIterator();
	public @Override ListIterator<T> listIterator(int index);
	public @Override java.util.ArrayList<T> toJavaList();
	public @Override List<T> toList();
	public @Override ArrayList<T> toArrayList();
	public LIST add(int index, T element);
	public LIST addAll(@SuppressWarnings("unchecked") T... values);
	public LIST addAll(LIST source);
	public LIST addAll(int index, @SuppressWarnings("unchecked") T... values);
	public <COLLECTION extends Collection<T, COLLECTION>> LIST addAll(int index, COLLECTION collection);
	public LIST addAll(int index, LIST source);
	public LIST remove(int index);
	public LIST set(int index, T element);
	public LIST sort(Comparator<T> comparator);
	public LIST subList(int fromIndex, int toIndex);
	public <U, E extends Exception> Object map(ExFunction<T, U, E> mapper) throws E;
	public <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction) throws E;
	public <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E;
	public <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E;
	public Set<T> toSet();
	public HashSet<T> toHashSet();
}
