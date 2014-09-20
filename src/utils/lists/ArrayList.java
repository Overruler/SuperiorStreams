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
package utils.lists;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
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
import utils.streams2.Stream;

/**
 * FastList (here ArrayList) is an attempt to provide the same functionality as ArrayList without the support for concurrent
 * modification exceptions.  It also attempts to correct the problem with subclassing ArrayList
 * that the data elements are not protected, but private.  It is this issue that caused this class
 * to be created in the first place.  The intent was to provide optimized internal iterators which use direct access
 * against the array of items, which is currently not possible by subclassing ArrayList.
 * <p/>
 * An empty FastList created by calling the default constructor starts with a shared reference to a static
 * empty array (DEFAULT_SIZED_EMPTY_ARRAY).  This makes empty FastLists very memory efficient.  The
 * first call to add will lazily create an array of size 10.
 * <p/>
 * An empty FastList created by calling the pre-size constructor with a value of 0 (new FastList(0)) starts
 * with a shared reference to a static  empty array (ZERO_SIZED_ARRAY).  This makes FastLists presized to 0 very
 * memory efficient as well.  The first call to add will lazily create an array of size 1.
 * @param <T> type of elements.
 */
public class ArrayList<T> implements ReadWriteList<T, ArrayList<T>> {
	private static final Object[] DEFAULT_SIZED_EMPTY_ARRAY = {};
	private static final Object[] ZERO_SIZED_ARRAY = {};
	private static final int MAXIMUM_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	protected transient T[] items;
	protected int size;

	public static <T> ArrayList<T> of() {
		return new ArrayList<>();
	}
	public static <T> ArrayList<T> of(T element) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[] { element };
		return new ArrayList<>(1, array);
	}
	/**
	 * Creates a new list using the passed {@code elements} argument as the backing store.
	 * <p/>
	 * !!! WARNING: This method uses the passed in array, so can be very unsafe if the original
	 * array is held onto anywhere else. !!!
	 * @param elements for the returned ArrayList
	 * @return a new ArrayList
	 */
	public static @SafeVarargs <T> ArrayList<T> of(T... elements) {
		return new ArrayList<>(elements.length, elements);
	}
	public static <T> ArrayList<T> from(Iterable<T> collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) collection.toArray();
		return of(array);
	}
	public static <T> ArrayList<T> from(java.util.Collection<T> collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) collection.toArray();
		return of(array);
	}
	public static <T> ArrayList<T> fromIterable(java.lang.Iterable<T> iterable) {
		return new ArrayList<>(iterable);
	}
	public ArrayList() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) DEFAULT_SIZED_EMPTY_ARRAY;
		items = array;
	}
	public ArrayList(int initialCapacity) {
		@SuppressWarnings("unchecked")
		T[] array = initialCapacity == 0 ? (T[]) ZERO_SIZED_ARRAY : (T[]) new Object[initialCapacity];
		items = array;
	}
	public ArrayList(Iterable<T> source) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) source.toArray();
		items = array;
		size = items.length;
	}
	public ArrayList(java.util.Collection<T> collection) {
		this(collection.size());
		for(T item : collection) {
			add(item);
		}
	}
	private ArrayList(java.lang.Iterable<T> collection) {
		this();
		for(T item : collection) {
			add(item);
		}
	}
	public @Override int hashCode() {
		int hashCode = 1;
		for(int i = 0, n = size; i < n; i++) {
			T item = items[i];
			hashCode = 31 * hashCode + (item == null ? 0 : item.hashCode());
		}
		return hashCode;
	}
	public @Override boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof ArrayList) {
			return fastListEquals((ArrayList<?>) otherList);
		}
		if(otherList instanceof ListOfN) {
			return fastListEquals((ListOfN<?>) otherList);
		}
		if(otherList instanceof ReadOnlyList) {
			return randomAccessListEquals((ReadOnlyList<?>) otherList);
		}
		if(otherList instanceof java.lang.Iterable) {
			return regularListEquals((java.lang.Iterable<?>) otherList);
		}
		return false;
	}
	public @Override String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append('[');
		T[] localItems = items;
		for(int i = 0, n = size; i < n; i++) {
			T item = localItems[i];
			if(i > 0) {
				buf.append(", ");
			}
			buf.append(item == this ? "(this ArrayList)" : String.valueOf(item));
		}
		buf.append(']');
		return buf.toString();
	}
	public @Override Iterator<T> iterator() {
		return new Iterator<T>() {
			/**
			 * Index of element to be returned by subsequent call to next.
			 */
			protected int currentIndex;
			/**
			 * Index of element returned by most recent call to next or previous.  Reset to -1 if this element is deleted by
			 * a call to remove.
			 */
			protected int lastIndex = -1;

			public @Override boolean hasNext() {
				return this.currentIndex != size;
			}
			public @Override T next() {
				if(currentIndex == size) {
					throw new NoSuchElementException();
				}
				T next = items[this.currentIndex];
				this.lastIndex = this.currentIndex++;
				return next;
			}
			public @Override void remove() {
				if(this.lastIndex == -1) {
					throw new IllegalStateException();
				}
				ArrayList.this.remove(this.lastIndex);
				if(this.lastIndex < this.currentIndex) {
					this.currentIndex--;
				}
				this.lastIndex = -1;
			}
		};
	}
	public @Override Spliterator<T> spliterator() {
		return Arrays.spliterator(items, 0, size);
	}
	public @Override <E extends Exception> ArrayList<T> each(ExConsumer<T, E> procedure) throws E {
		for(int i = 0, n = size; i < n; i++) {
			procedure.accept(items[i]);
		}
		return this;
	}
	public @Override <E extends Exception> ArrayList<T> eachWithIndex(ExObjIntConsumer<T, E> objectIntProcedure)
		throws E {
		for(int i = 0, n = size; i < n; i++) {
			objectIntProcedure.accept(items[i], i);
		}
		return this;
	}
	public @Override int size() {
		return size;
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
	public @Override boolean containsAll(Iterable<T> source) {
		for(Iterator<T> iterator = source.iterator(); iterator.hasNext();) {
			if(!contains(iterator.next())) {
				return false;
			}
		}
		return true;
	}
	public @Override Object[] toArray() {
		return copyItemsWithNewCapacity(size);
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		T[] array = generator.apply(size());
		System.arraycopy(items, 0, array, 0, size);
		return array;
	}
	public @Override T[] toArray(T[] array) {
		if(array.length < size) {
			@SuppressWarnings("unchecked")
			T[] created = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
			array = created;
		}
		System.arraycopy(items, 0, array, 0, size);
		if(array.length > size) {
			array[size] = null;
		}
		return array;
	}
	public @Override Stream<T> stream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), false));
	}
	public @Override Stream<T> parallelStream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), true));
	}
	public @Override ArrayList<T> add(T newItem) {
		if(items.length == size) {
			ensureCapacityForAdd();
		}
		items[size++] = newItem;
		return this;
	}
	public @Override ArrayList<T> add(int index, T element) {
		int localSize = size;
		index = adjustIndexToPositiveInts(index, localSize);
		if(index == localSize) {
			add(element);
		} else {
			addAtIndex(index, element);
		}
		return this;
	}
	public @Override ArrayList<T> addAll(@SuppressWarnings("unchecked") T... values) {
		int length = values.length;
		if(length == 0) {
			return this;
		}
		arraycopyAndAdjustSize(values, length);
		return this;
	}
	public @Override ArrayList<T> addAll(Iterable<T> source) {
		if(source.isEmpty()) {
			return this;
		}
		ensureCapacity(size + source.size());
		for(Iterator<T> iterator = source.iterator(); iterator.hasNext();) {
			add(iterator.next());
		}
		return this;
	}
	public @Override ArrayList<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
		int localSize = size;
		index = adjustIndexToPositiveInts(index, localSize);
		int length = values.length;
		if(length == 0) {
			return this;
		}
		shiftItemsAndArraycopyAndAdjustSize(index, localSize, values, length);
		return this;
	}
	public @Override ArrayList<T> addAll(int index, Iterable<T> source) {
		int localSize = size;
		index = adjustIndexToPositiveInts(index, localSize);
		if(source.isEmpty()) {
			return this;
		}
		Object[] values = source.toArray();
		int length = values.length;
		shiftItemsAndArraycopyAndAdjustSize(index, localSize, values, length);
		return this;
	}
	public @Override ArrayList<T> clear() {
		Arrays.fill(items, null);
		size = 0;
		return this;
	}
	public @Override ArrayList<T> remove(T object) {
		int index = indexOf(object);
		if(index >= 0) {
			remove(index);
		}
		return this;
	}
	public @Override ArrayList<T> remove(int index) {
		int newSize = size - 1;
		index = adjustIndexToPositiveInts(index, newSize);
		int totalOffset = newSize - index;
		if(totalOffset > 0) {
			System.arraycopy(items, index + 1, items, index, totalOffset);
		}
		items[--size] = null;
		return this;
	}
	public @Override ArrayList<T> removeAll(Iterable<T> collection) {
		removeIfNotFoundInCollection(collection, false);
		return this;
	}
	public @Override ArrayList<T> retainAll(Iterable<T> collection) {
		removeIfNotFoundInCollection(collection, true);
		return this;
	}
	public @Override T get(int index) {
		index = adjustIndexToPositiveInts(index, size - 1);
		return items[index];
	}
	public @Override ArrayList<T> set(int index, T element) {
		index = adjustIndexToPositiveInts(index, size - 1);
		items[index] = element;
		return this;
	}
	public @Override ArrayList<T> sort(Comparator<T> comparator) {
		Arrays.sort(items, 0, size, comparator);
		return this;
	}
	public @Override int indexOf(T object) {
		for(int i = 0, n = size; i < n; i++) {
			if(Objects.equals(items[i], object)) {
				return i;
			}
		}
		return -1;
	}
	public @Override int lastIndexOf(T object) {
		for(int i = size - 1; i >= 0; i--) {
			if(Objects.equals(items[i], object)) {
				return i;
			}
		}
		return -1;
	}
	public @Override ListIterator<T> listIterator() {
		return listIterator(0);
	}
	public @Override ListIterator<T> listIterator(int index) {
		return new ArrayListListIterator<>(this, adjustIndexToPositiveInts(index, size()));
	}
	public @Override ArrayList<T> subList(int fromIndex, int toIndex) {
		int localSize = size();
		fromIndex = adjustIndexToPositiveInts(fromIndex, localSize);
		toIndex = adjustIndexToPositiveInts(toIndex, localSize);
		return new ArrayListSubList<>(this, fromIndex, toIndex);
	}
	public @Override <V, E extends Exception> ArrayList<V> map(ExFunction<T, V, E> function) throws E {
		return collect(function, ArrayList.<V> newList(size()));
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		double[] array = new double[size];
		for(int i = 0, n = size; i < n; i++) {
			array[i] = doubleFunction.applyAsDouble(items[i]);
		}
		return array;
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		int[] array = new int[size];
		for(int i = 0, n = size; i < n; i++) {
			array[i] = intFunction.applyAsInt(items[i]);
		}
		return array;
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		long[] array = new long[size];
		for(int i = 0, n = size; i < n; i++) {
			array[i] = longFunction.applyAsLong(items[i]);
		}
		return array;
	}
	public @Override <E extends Exception> ArrayList<T> filter(ExPredicate<T, E> predicate) throws E {
		int currentFilledIndex = 0;
		for(int i = 0, n = size; i < n; i++) {
			T item = items[i];
			if(predicate.test(item)) {
				// keep it
				if(currentFilledIndex != i) {
					items[currentFilledIndex] = item;
				}
				currentFilledIndex++;
			}
		}
		wipeAndResetTheEnd(currentFilledIndex);
		return this;
	}
	public @Override <E extends Exception> ArrayList<T> replaceAll(ExUnaryOperator<T, E> function) throws E {
		for(int i = 0, n = size; i < n; i++) {
			items[i] = function.apply(items[i]);
		}
		return this;
	}
	public @Override <E extends Exception> ArrayList<T> removeIf(ExPredicate<T, E> predicate) throws E {
		int currentFilledIndex = 0;
		for(int i = 0, n = size; i < n; i++) {
			T item = items[i];
			if(!predicate.test(item)) {
				// keep it
				if(currentFilledIndex != i) {
					items[currentFilledIndex] = item;
				}
				currentFilledIndex++;
			}
		}
		wipeAndResetTheEnd(currentFilledIndex);
		return this;
	}
	public @Override java.util.ArrayList<T> toJavaUtilCollection() {
		return new java.util.ArrayList<>(java.util.Arrays.asList(java.util.Arrays.copyOfRange(items, 0, size)));
	}
	public @Override ArrayList<T> sort() {
		Arrays.sort(items, 0, size);
		return this;
	}
	public @Override ArrayList<T> reverse() {
		int mid = size / 2;
		int j = size - 1;
		for(int i = 0; i < mid; i++, j--) {
			T one = items[i];
			T two = items[j];
			items[i] = two;
			items[j] = one;
		}
		return this;
	}
	public @Override List<T> toList() {
		return List.from(this);
	}
	public @Override ArrayList<T> toArrayList() {
		return from(this);
	}
	public @Override Set<T> toSet() {
		return Set.from(this);
	}
	public @Override HashSet<T> toHashSet() {
		return HashSet.from(this);
	}
	public @Override ArrayList<T> identity() {
		return this;
	}
	private ArrayList(int size, T[] array) {
		this.size = size;
		items = array;
	}
	private void shiftItemsAndArraycopyAndAdjustSize(int index, int oldSize, Object[] values, int length) {
		int newSize = oldSize + length;
		ensureCapacity(newSize);
		shiftElementsAtIndex(index, length);
		System.arraycopy(values, 0, items, index, length);
		size = newSize;
	}
	private void arraycopyAndAdjustSize(Object[] values, int length) {
		int oldSize = size;
		int newSize = oldSize + length;
		ensureCapacity(newSize);
		System.arraycopy(values, 0, items, oldSize, length);
		size = newSize;
	}
	private boolean fastListEquals(ArrayList<?> otherFastList) {
		int size2 = size();
		if(size2 != otherFastList.size()) {
			return false;
		}
		return arraysEqual(items, otherFastList.items, size2);
	}
	private boolean fastListEquals(ListOfN<?> otherFastList) {
		int size2 = size();
		if(size2 != otherFastList.size()) {
			return false;
		}
		return arraysEqual(items, otherFastList.items, size2);
	}
	private boolean regularListEquals(java.lang.Iterable<?> otherList) {
		int size2 = size;
		if(otherList instanceof Iterable && size2 != ((Iterable<?>) otherList).size()) {
			return false;
		}
		if(otherList instanceof java.util.Collection && size2 != ((java.util.Collection<?>) otherList).size()) {
			return false;
		}
		Iterator<?> iterator = otherList.iterator();
		T[] items1 = items;
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
		if(size() != otherList.size()) {
			return false;
		}
		for(int i = 0, n = size; i < n; i++) {
			T one = items[i];
			Object two = otherList.get(i);
			if(!Objects.equals(one, two)) {
				return false;
			}
		}
		return true;
	}
	private Object[] copyItemsWithNewCapacity(int newCapacity) {
		Object[] newItems = new Object[newCapacity];
		System.arraycopy(items, 0, newItems, 0, Math.min(size, newCapacity));
		return newItems;
	}
	private void ensureCapacityForAdd() {
		if(items == DEFAULT_SIZED_EMPTY_ARRAY) {
			@SuppressWarnings("unchecked")
			T[] array = (T[]) new Object[10];
			items = array;
		} else {
			transferItemsToNewArrayWithCapacity(sizePlusFiftyPercent(size));
		}
	}
	private static int sizePlusFiftyPercent(int oldSize) {
		int result = oldSize + (oldSize >> 1) + 1;
		return result < oldSize ? MAXIMUM_ARRAY_SIZE : result;
	}
	private void transferItemsToNewArrayWithCapacity(int newCapacity) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) copyItemsWithNewCapacity(newCapacity);
		items = array;
	}
	private void ensureCapacity(int minCapacity) {
		int oldCapacity = items.length;
		if(minCapacity > oldCapacity) {
			int newCapacity = Math.max(sizePlusFiftyPercent(oldCapacity), minCapacity);
			transferItemsToNewArrayWithCapacity(newCapacity);
		}
	}
	private void shiftElementsAtIndex(int index, int sourceSize) {
		int numberToMove = size - index;
		if(numberToMove > 0) {
			System.arraycopy(items, index, items, index + sourceSize, numberToMove);
		}
	}
	private void removeIfNotFoundInCollection(Iterable<T> collection, boolean notFound) {
		int currentFilledIndex = 0;
		T[] items2 = items;
		for(int i = 0, n = size; i < n; i++) {
			T item = items2[i];
			if(collection.contains(item) == notFound) {
				// keep it
				if(currentFilledIndex != i) {
					items2[currentFilledIndex] = item;
				}
				currentFilledIndex++;
			}
		}
		wipeAndResetTheEnd(currentFilledIndex);
	}
	private void wipeAndResetTheEnd(int newCurrentFilledIndex) {
		for(int i = newCurrentFilledIndex; i < size; i++) {
			items[i] = null;
		}
		size = newCurrentFilledIndex;
	}
	private static <E> ArrayList<E> newList(int initialCapacity) {
		return new ArrayList<>(initialCapacity);
	}
	private <V, E extends Exception> ArrayList<V> collect(ExFunction<T, V, E> function, ArrayList<V> target) throws E {
		for(int i = 0, n = size; i < n; i++) {
			target.add(function.apply(items[i]));
		}
		return target;
	}
	private void addAtIndex(int index, T element) {
		int oldSize = size++;
		if(items.length == oldSize) {
			@SuppressWarnings("unchecked")
			T[] array = (T[]) new Object[sizePlusFiftyPercent(oldSize)];
			T[] newItems = array;
			if(index > 0) {
				System.arraycopy(items, 0, newItems, 0, index);
			}
			System.arraycopy(items, index, newItems, index + 1, oldSize - index);
			items = newItems;
		} else {
			System.arraycopy(items, index, items, index + 1, oldSize - index);
		}
		items[index] = element;
	}
	static boolean arraysEqual(Object[] items1, Object[] items2, int n) {
		for(int i = 0; i < n; i++) {
			if(!Objects.equals(items1[i], items2[i])) {
				return false;
			}
		}
		return true;
	}
	static <T> boolean internalContains(java.lang.Iterable<T> iterable, T each, boolean ifFoundResult) {
		if(each == null) {
			for(Iterator<T> iterator = iterable.iterator(); iterator.hasNext();) {
				if(iterator.next() == null) {
					return ifFoundResult;
				}
			}
		} else {
			for(Iterator<T> iterator = iterable.iterator(); iterator.hasNext();) {
				if(each.equals(iterator.next())) {
					return ifFoundResult;
				}
			}
		}
		return !ifFoundResult;
	}
	static int adjustIndexToPositiveInts(int index, int endInclusive) {
		if(index < 0 && endInclusive >= 0) {
			index = index + 1 + endInclusive;
		}
		if(index >= 0 && index <= endInclusive) {
			return index;
		}
		throw new IndexOutOfBoundsException("Index: " + index + " Max: " + endInclusive);
	}

	static class ArrayListListIterator<T> implements ListIterator<T> {
		private final ArrayList<T> original;
		/**
		 * Index of element to be returned by subsequent call to next.
		 */
		protected int currentIndex;
		/**
		 * Index of element returned by most recent call to next or previous.  Reset to -1 if this element is deleted by
		 * a call to remove.
		 */
		protected int lastIndex = -1;

		ArrayListListIterator(ArrayList<T> original, int currentIndex) {
			this.currentIndex = currentIndex;
			this.original = original;
		}
		public @Override boolean hasNext() {
			return this.currentIndex != original.size();
		}
		public @Override T next() {
			if(currentIndex == original.size()) {
				throw new NoSuchElementException();
			}
			T next = original.get(this.currentIndex);
			this.lastIndex = this.currentIndex++;
			return next;
		}
		public @Override void remove() {
			if(this.lastIndex == -1) {
				throw new IllegalStateException();
			}
			original.remove(this.lastIndex);
			if(this.lastIndex < this.currentIndex) {
				this.currentIndex--;
			}
			this.lastIndex = -1;
		}
		public @Override boolean hasPrevious() {
			return currentIndex != 0;
		}
		public @Override T previous() {
			int i = currentIndex - 1;
			if(i < 0) {
				throw new NoSuchElementException();
			}
			T previous = original.get(i);
			currentIndex = i;
			lastIndex = i;
			return previous;
		}
		public @Override int nextIndex() {
			return currentIndex;
		}
		public @Override int previousIndex() {
			return currentIndex - 1;
		}
		public @Override void set(T o) {
			if(lastIndex == -1) {
				throw new IllegalStateException();
			}
			try {
				original.set(lastIndex, o);
			} catch(IndexOutOfBoundsException ignored) {
				throw new ConcurrentModificationException();
			}
		}
		public @Override void add(T o) {
			original.add(currentIndex++, o);
			lastIndex = -1;
		}
	}
	static class ArrayListSubList<T> extends ArrayList<T> {
		final ArrayList<T> original;
		final int offset;
		int subsize;

		public @Override ArrayListSubList<T> identity() {
			return this;
		}
		ArrayListSubList(ArrayList<T> fastList, int fromIndex, int toIndex) {
			if(fromIndex < 0) {
				throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
			}
			if(toIndex > fastList.size()) {
				throw new IndexOutOfBoundsException("toIndex = " + toIndex);
			}
			if(fromIndex > toIndex) {
				throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ')');
			}
			this.original = fastList;
			this.offset = fromIndex;
			subsize = toIndex - fromIndex;
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
		public @Override <E extends Exception> ArrayListSubList<T> eachWithIndex(
			ExObjIntConsumer<T, E> objectIntProcedure) throws E {
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
		public @Override ArrayListSubList<T> addAll(Iterable<T> collection) {
			return addAll(subsize, collection);
		}
		public @Override ArrayListSubList<T> addAll(int index, Iterable<T> collection) {
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
		public @Override ArrayListSubList<T> removeAll(Iterable<T> collection) {
			removeIfWith(collection, true);
			return this;
		}
		public @Override ArrayListSubList<T> retainAll(Iterable<T> collection) {
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
			return new ArrayListListIterator<>(this, index);
		}
		public @Override ArrayListSubList<T> subList(int fromIndex, int toIndex) {
			fromIndex = ArrayList.adjustIndexToPositiveInts(fromIndex, subsize);
			toIndex = ArrayList.adjustIndexToPositiveInts(toIndex, subsize);
			return new ArrayListSubList<>(this.original, offset + fromIndex, offset + toIndex);
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
		private void removeIfWith(Iterable<T> collection, boolean ifFoundResult) {
			for(int i = 0; i < size(); i++) {
				if(ArrayList.internalContains(collection, get(i), ifFoundResult)) {
					remove(i--);
				}
			}
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
		public @Override ArrayListSubList<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
			index = ArrayList.adjustIndexToPositiveInts(index, subsize);
			original.addAll(offset + index, values);
			subsize += values.length;
			return this;
		}
	}
}
