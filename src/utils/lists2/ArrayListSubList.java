/*
 * Copyright 2014 Timo Kinnunen.
 * Copyright 2014 Goldman Sachs.
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
import utils.streams2.Stream;

class ArrayListSubList<T> extends ArrayList<T> {
	final ArrayList<T> original;
	final int offset;
	int subsize;

	private ArrayListSubList(ArrayList<T> fastList, int fromIndex, int toIndex, int offset) {
		if(fromIndex < 0) {
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		}
		if(offset + toIndex > fastList.size()) {
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		}
		if(fromIndex > toIndex) {
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ')');
		}
		this.original = fastList;
		this.offset = fromIndex;
		subsize = toIndex - fromIndex;
	}
	ArrayListSubList(ArrayList<T> fastList, int fromIndex, int toIndex) {
		this(fastList, fromIndex, toIndex, 0);
	}
	private ArrayListSubList(ArrayListSubList<T> fastList, int fromIndex, int toIndex) {
		this(fastList.original, fromIndex, toIndex, fastList.offset);
	}
	public @Override Iterator<T> iterator() {
		return listIterator();
	}
	public @Override Spliterator<T> spliterator() {
		return Arrays.spliterator(original.items, offset, offset + subsize);
	}
	public @Override <E extends Exception> ArrayListSubList<T> each(ExConsumer<T, E> procedure) throws E {
		for(int i = 0, n = size(); i < n; i++) {
			procedure.accept(get(i));
		}
		return this;
	}
	public @Override <E extends Exception> ArrayListSubList<T> eachWithIndex(ExObjIntConsumer<T, E> objectIntProcedure)
		throws E {
		for(int i = 0, n = size(); i < n; i++) {
			objectIntProcedure.accept(get(i), i);
		}
		return this;
	}
	public @Override int size() {
		return subsize;
	}
	public @Override boolean isEmpty() {
		return size() == 0;
	}
	public @Override boolean notEmpty() {
		return size() > 0;
	}
	public @Override boolean contains(T object) {
		return indexOf(object) > -1;
	}
	public @Override boolean containsAll(ReadOnly<T> source) {
		return ArrayListSubList.internalContainsAll(source, this);
	}
	public @Override Object[] toArray() {
		final Object[] result = new Object[size()];
		eachWithIndex((each, index) -> result[index] = each);
		return result;
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		int size2 = subsize;
		T[] array = generator.apply(size2);
		System.arraycopy(original.items, offset, array, 0, size2);
		return array;
	}
	public @Override T[] toArray(T[] array) {
		@SuppressWarnings("unchecked")
		final T[] result =
			array.length < size() ? (T[]) Array.newInstance(array.getClass().getComponentType(), size()) : array;
		eachWithIndex((each, index) -> result[index] = each);
		if(result.length > size()) {
			result[size()] = null;
		}
		return result;
	}
	public @Override Stream<T> stream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), false));
	}
	public @Override Stream<T> parallelStream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), true));
	}
	public @Override ArrayListSubList<T> add(T o) {
		original.add(offset + subsize, o);
		subsize++;
		return this;
	}
	public @Override ArrayListSubList<T> add(int index, T element) {
		index = ArrayList.adjustIndexToPositiveInts(index, subsize);
		original.add(index + offset, element);
		subsize++;
		return this;
	}
	public @Override ArrayListSubList<T> addAll(ReadOnly<T> collection) {
		return addAll(subsize, collection);
	}
	public @Override ArrayListSubList<T> addAll(int index, ReadOnly<T> collection) {
		index = ArrayList.adjustIndexToPositiveInts(index, subsize);
		int cSize = collection.size();
		if(cSize == 0) {
			return this;
		}
		original.addAll(offset + index, collection);
		subsize += cSize;
		return this;
	}
	public @Override ArrayListSubList<T> clear() {
		for(Iterator<T> iterator = iterator(); iterator.hasNext();) {
			iterator.next();
			iterator.remove();
		}
		return this;
	}
	public @Override ArrayListSubList<T> remove(T o) {
		Iterator<T> iterator = iterator();
		while(iterator.hasNext()) {
			if(Objects.equals(o, iterator.next())) {
				iterator.remove();
				return this;
			}
		}
		return this;
	}
	public @Override ArrayListSubList<T> remove(int index) {
		index = ArrayList.adjustIndexToPositiveInts(index, subsize - 1);
		original.remove(index + offset);
		subsize--;
		return this;
	}
	public @Override ArrayListSubList<T> removeAll(ReadOnly<T> collection) {
		removeIfWith(collection, true);
		return this;
	}
	public @Override ArrayListSubList<T> retainAll(ReadOnly<T> collection) {
		removeIfWith(collection, false);
		return this;
	}
	public @Override T get(int index) {
		index = ArrayList.adjustIndexToPositiveInts(index, subsize - 1);
		return original.get(index + offset);
	}
	public @Override ArrayListSubList<T> set(int index, T element) {
		index = ArrayList.adjustIndexToPositiveInts(index, subsize - 1);
		original.set(index + offset, element);
		return this;
	}
	public @Override ArrayListSubList<T> sort(Comparator<T> comparator) {
		Arrays.sort(original.items, offset, subsize, comparator);
		return this;
	}
	public @Override int indexOf(Object o) {
		if(o == null) {
			for(int i = 0; i < size(); i++) {
				if(get(i) == null) {
					return i;
				}
			}
		} else {
			for(int i = 0; i < size(); i++) {
				if(o.equals(get(i))) {
					return i;
				}
			}
		}
		return -1;
	}
	public @Override int lastIndexOf(Object o) {
		if(o == null) {
			for(int i = size(); i-- > 0;) {
				if(get(i) == null) {
					return i;
				}
			}
		} else {
			for(int i = size(); i-- > 0;) {
				if(o.equals(get(i))) {
					return i;
				}
			}
		}
		return -1;
	}
	public @Override ListIterator<T> listIterator() {
		return listIterator(0);
	}
	public @Override ListIterator<T> listIterator(int index) {
		index = ArrayList.adjustIndexToPositiveInts(index, subsize);
		return new ArrayListSubListListIterator<>(this, index);
	}
	public @Override ArrayListSubList<T> subList(int fromIndex, int toIndex) {
		fromIndex = ArrayList.adjustIndexToPositiveInts(fromIndex, subsize);
		toIndex = ArrayList.adjustIndexToPositiveInts(toIndex, subsize);
		return new ArrayListSubList<>(this, fromIndex, toIndex);
	}
	public @Override <U, E extends Exception> ArrayList<U> map(ExFunction<T, U, E> mapper) throws E {
		ArrayList<U> newList = new ArrayList<>(size());
		T[] items2 = original.items;
		for(int i = 0, n = subsize; i < n; i++) {
			newList.add(mapper.apply(items2[i + offset]));
		}
		return newList;
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		double[] array = new double[subsize];
		T[] items2 = original.items;
		for(int i = 0, n = subsize; i < n; i++) {
			array[i] = doubleFunction.applyAsDouble(items2[i + offset]);
		}
		return array;
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		int[] array = new int[subsize];
		T[] items2 = original.items;
		for(int i = 0, n = subsize; i < n; i++) {
			array[i] = intFunction.applyAsInt(items2[i + offset]);
		}
		return array;
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		long[] array = new long[subsize];
		T[] items2 = original.items;
		for(int i = 0, n = subsize; i < n; i++) {
			array[i] = longFunction.applyAsLong(items2[i + offset]);
		}
		return array;
	}
	public @Override <E extends Exception> ArrayListSubList<T> filter(ExPredicate<T, E> filter) throws E {
		for(int i = 0; i < size(); i++) {
			T each = get(i);
			if(!filter.test(each)) {
				remove(i--);
			}
		}
		return this;
	}
	public @Override <E extends Exception> ArrayListSubList<T> removeIf(ExPredicate<T, E> filter) throws E {
		for(int i = 0; i < size(); i++) {
			T each = get(i);
			if(filter.test(each)) {
				remove(i--);
			}
		}
		return this;
	}
	public @Override <E extends Exception> ArrayListSubList<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		T[] items2 = original.items;
		for(int i = offset, n = offset + subsize; i < n; i++) {
			items2[i] = mapper.apply(items2[i]);
		}
		return this;
	}
	public @Override ArrayListSubList<T> sort() {
		Arrays.sort(original.items, offset, subsize);
		return this;
	}
	public @Override ArrayListSubList<T> reverse() {
		int mid = offset + subsize / 2;
		int j = offset + subsize - 1;
		T[] items2 = original.items;
		for(int i = offset; i < mid; i++, j--) {
			T one = items2[i];
			T two = items2[j];
			items2[i] = two;
			items2[j] = one;
		}
		return this;
	}
	private void removeIfWith(ReadOnly<T> collection, boolean ifFoundResult) {
		for(int i = 0; i < size(); i++) {
			if(ArrayList.internalContains(collection, get(i), ifFoundResult)) {
				remove(i--);
			}
		}
	}
	private static <T> boolean internalContainsAll(ReadOnly<T> source, ArrayListSubList<T> parameter) {
		for(Iterator<T> iterator = source.iterator(); iterator.hasNext();) {
			if(!ArrayList.internalContains(parameter, iterator.next(), true)) {
				return false;
			}
		}
		return true;
	}
	public @Override ArrayListSubList<T> addAll(@SuppressWarnings("unchecked") T... values) {
		original.addAll(offset, values);
		subsize += values.length;
		return this;
	}
	public @Override java.util.ArrayList<T> toJavaUtilCollection() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) toArray();
		return new java.util.ArrayList<>(java.util.Arrays.asList(array));
	}
	public @Override java.util.ArrayList<T> toJavaList() {
		return toJavaUtilCollection();
	}
	public @Override List<T> toList() {
		return List.from(this);
	}
	public @Override ArrayList<T> toArrayList() {
		return ArrayList.from(this);
	}
	public @Override ArrayListSubList<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
		index = ArrayList.adjustIndexToPositiveInts(index, subsize);
		original.addAll(offset + index, values);
		subsize += values.length;
		return this;
	}
	public @Override Set<T> toSet() {
		return Set.from(this);
	}
	public @Override HashSet<T> toHashSet() {
		return HashSet.from(this);
	}
}