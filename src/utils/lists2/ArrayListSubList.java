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
import java.util.function.IntFunction;
import java.util.stream.StreamSupport;
import utils.streams2.Stream;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.ExUnaryOperator;

class ArrayListSubList<T> extends ArrayList<T> {
	final ArrayList<T> original;
	final int offset;

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
		size = toIndex - fromIndex;
		items = null;
	}
	ArrayListSubList(ArrayList<T> fastList, int fromIndex, int toIndex) {
		this(fastList, fromIndex, toIndex, 0);
	}
	private ArrayListSubList(ArrayListSubList<T> fastList, int fromIndex, int toIndex) {
		this(fastList.original, fromIndex, toIndex, fastList.offset);
	}
	@Override
	public Iterator<T> iterator() {
		return listIterator();
	}
	@Override
	public Spliterator<T> spliterator() {
		return Arrays.spliterator(original.items, offset, offset + size);
	}
	@Override
	public <E extends Exception> ArrayListSubList<T> each(ExConsumer<T, E> procedure) throws E {
		for(int i = 0, n = size(); i < n; i++) {
			procedure.accept(get(i));
		}
		return this;
	}
	@Override
	public <E extends Exception> ArrayListSubList<T> eachWithIndex(ExObjIntConsumer<T, E> objectIntProcedure) throws E {
		for(int i = 0, n = size(); i < n; i++) {
			objectIntProcedure.accept(get(i), i);
		}
		return this;
	}
	@Override
	public int size() {
		return size;
	}
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	@Override
	public boolean notEmpty() {
		return size() > 0;
	}
	@Override
	public boolean contains(T object) {
		return indexOf(object) > -1;
	}
	@Override
	public <C extends Collection<T, C>> boolean containsAll(C source) {
		return ArrayListSubList.internalContainsAll(source, this);
	}
	@Override
	public Object[] toArray() {
		final Object[] result = new Object[size()];
		eachWithIndex((each, index) -> result[index] = each);
		return result;
	}
	@Override
	public T[] toArray(IntFunction<T[]> generator) {
		int size2 = size;
		T[] array = generator.apply(size2);
		System.arraycopy(original.items, offset, array, 0, size2);
		return array;
	}
	@Override
	public T[] toArray(T[] array) {
		@SuppressWarnings("unchecked")
		final T[] result =
			array.length < size() ? (T[]) Array.newInstance(array.getClass().getComponentType(), size()) : array;
		eachWithIndex((each, index) -> result[index] = each);
		if(result.length > size()) {
			result[size()] = null;
		}
		return result;
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
	public ArrayListSubList<T> add(T o) {
		original.add(offset + size, o);
		size++;
		return this;
	}
	@Override
	public ArrayListSubList<T> add(int index, T element) {
		checkIfOutOfBounds(index);
		original.add(index + offset, element);
		size++;
		return this;
	}
	@Override
	public <C extends Collection<T, C>> ArrayList<T> addAll(C collection) {
		return addAll(size, collection);
	}
	@Override
	public ArrayList<T> addAll(ArrayList<T> source) {
		return addAll(size, source);
	}
	@Override
	public <C extends Collection<T, C>> ArrayList<T> addAll(int index, C collection) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		int cSize = collection.size();
		if(cSize == 0) {
			return this;
		}
		original.addAll(offset + index, collection);
		size += cSize;
		return this;
	}
	@Override
	public ArrayList<T> addAll(int index, ArrayList<T> source) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		int cSize = source.size();
		if(cSize == 0) {
			return this;
		}
		original.addAll(offset + index, source);
		size += cSize;
		return this;
	}
	@Override
	public ArrayListSubList<T> clear() {
		for(Iterator<T> iterator = iterator(); iterator.hasNext();) {
			iterator.next();
			iterator.remove();
		}
		return this;
	}
	@Override
	public ArrayListSubList<T> remove(T o) {
		Iterator<T> iterator = iterator();
		while(iterator.hasNext()) {
			if(Objects.equals(o, iterator.next())) {
				iterator.remove();
				return this;
			}
		}
		return this;
	}
	@Override
	public ArrayListSubList<T> remove(int index) {
		checkIfOutOfBounds(index);
		original.remove(index + offset);
		size--;
		return this;
	}
	@Override
	public <C extends Collection<T, C>> ArrayListSubList<T> removeAll(C collection) {
		boolean ifFoundResult = true;
		removeIfWith(collection, ifFoundResult);
		return this;
	}
	@Override
	public <C extends Collection<T, C>> ArrayListSubList<T> retainAll(C collection) {
		boolean ifFoundResult = false;
		removeIfWith(collection, ifFoundResult);
		return this;
	}
	@Override
	public T get(int index) {
		checkIfOutOfBounds(index);
		return original.get(index + offset);
	}
	@Override
	public ArrayListSubList<T> set(int index, T element) {
		checkIfOutOfBounds(index);
		original.set(index + offset, element);
		return this;
	}
	@Override
	public ArrayListSubList<T> sort(Comparator<T> comparator) {
		Arrays.sort(original.items, offset, size, comparator);
		return this;
	}
	@Override
	public int indexOf(Object o) {
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
	@Override
	public int lastIndexOf(Object o) {
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
	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}
	@Override
	public ListIterator<T> listIterator(final int index) {
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		return new ArrayListSubListListIterator<>(this, index);
	}
	@Override
	public ArrayList<T> subList(int fromIndex, int toIndex) {
		return new ArrayListSubList<>(this, fromIndex, toIndex);
	}
	@Override
	public <U, E extends Exception> ArrayList<U> map(ExFunction<T, U, E> mapper) throws E {
		ArrayList<U> newList = new ArrayList<>(size());
		T[] items2 = original.items;
		for(int i = 0, n = size; i < n; i++) {
			newList.add(mapper.apply(items2[i + offset]));
		}
		return newList;
	}
	@Override
	public <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction) throws E {
		double[] array = new double[size];
		T[] items2 = original.items;
		for(int i = 0, n = size; i < n; i++) {
			array[i] = doubleFunction.applyAsDouble(items2[i + offset]);
		}
		return array;
	}
	@Override
	public <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		int[] array = new int[size];
		T[] items2 = original.items;
		for(int i = 0, n = size; i < n; i++) {
			array[i] = intFunction.applyAsInt(items2[i + offset]);
		}
		return array;
	}
	@Override
	public <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		long[] array = new long[size];
		T[] items2 = original.items;
		for(int i = 0, n = size; i < n; i++) {
			array[i] = longFunction.applyAsLong(items2[i + offset]);
		}
		return array;
	}
	@Override
	public <E extends Exception> ArrayList<T> filter(ExPredicate<T, E> filter) throws E {
		for(int i = 0; i < size(); i++) {
			T each = get(i);
			if(!filter.test(each)) {
				remove(i--);
			}
		}
		return this;
	}
	@Override
	public <E extends Exception> ArrayList<T> removeIf(ExPredicate<T, E> filter) throws E {
		for(int i = 0; i < size(); i++) {
			T each = get(i);
			if(filter.test(each)) {
				remove(i--);
			}
		}
		return this;
	}
	@Override
	public <E extends Exception> ArrayList<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		T[] items2 = original.items;
		for(int i = offset, n = offset + size; i < n; i++) {
			items2[i] = mapper.apply(items2[i]);
		}
		return this;
	}
	@Override
	public ArrayList<T> sort() {
		Arrays.sort(original.items, offset, size);
		return this;
	}
	@Override
	public ArrayList<T> reverse() {
		int mid = offset + size / 2;
		int j = offset + size - 1;
		T[] items2 = original.items;
		for(int i = offset; i < mid; i++, j--) {
			T one = items2[i];
			T two = items2[j];
			items2[i] = two;
			items2[j] = one;
		}
		return this;
	}
	private <C extends Collection<T, C>> void removeIfWith(C collection, boolean ifFoundResult) {
		for(int i = 0; i < size(); i++) {
			if(internalContains(collection, get(i), ifFoundResult)) {
				remove(i--);
			}
		}
	}
	private void checkIfOutOfBounds(int index) {
		if(index >= size || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
		}
	}
	private static <T, C extends Collection<T, C>> boolean internalContainsAll(C source, ArrayListSubList<T> parameter) {
		for(Iterator<T> iterator = source.iterator(); iterator.hasNext();) {
			if(!internalContains(parameter, iterator.next(), true)) {
				return false;
			}
		}
		return true;
	}
}