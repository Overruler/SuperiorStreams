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
import java.util.RandomAccess;
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
public class ArrayList<T> implements CollectionListAPI<T, ArrayList<T>> {
	private static final Object[] DEFAULT_SIZED_EMPTY_ARRAY = {};
	private static final Object[] ZERO_SIZED_ARRAY = {};
	private static final int MAXIMUM_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	protected transient T[] items;
	protected int size;

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
	public static <T> ArrayList<T> fromJavaCollection(java.util.Collection<T> collection) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) collection.toArray();
		return of(array);
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
	public <C extends Collection<T, C>> ArrayList(Collection<T, C> source) {
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
	@Override
	public int hashCode() {
		int hashCode = 1;
		for(int i = 0, n = size; i < n; i++) {
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
		ListOfN<?> list = (ListOfN<?>) otherList;
		if(otherList instanceof ArrayList) {
			return fastListEquals((ArrayList<?>) otherList);
		}
		if(otherList instanceof RandomAccess) {
			return randomAccessListEquals(list);
		}
		if(!(otherList instanceof ListOfN)) {
			return false;
		}
		return regularListEquals(list);
	}
	@Override
	public String toString() {
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
	@Override
	public Iterator<T> iterator() {
		return new ArrayListIterator<>(this);
	}
	@Override
	public Spliterator<T> spliterator() {
		return Arrays.spliterator(items, 0, size);
	}
	@Override
	public <E extends Exception> ArrayList<T> each(ExConsumer<T, E> procedure) throws E {
		for(int i = 0, n = size; i < n; i++) {
			procedure.accept(items[i]);
		}
		return this;
	}
	@Override
	public <E extends Exception> ArrayList<T> eachWithIndex(ExObjIntConsumer<T, E> objectIntProcedure) throws E {
		for(int i = 0, n = size; i < n; i++) {
			objectIntProcedure.accept(items[i], i);
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
		for(Iterator<T> iterator = source.iterator(); iterator.hasNext();) {
			if(!contains(iterator.next())) {
				return false;
			}
		}
		return true;
	}
	@Override
	public Object[] toArray() {
		return copyItemsWithNewCapacity(size);
	}
	@Override
	public T[] toArray(IntFunction<T[]> generator) {
		T[] array = generator.apply(size());
		System.arraycopy(items, 0, array, 0, size);
		return array;
	}
	@Override
	public T[] toArray(T[] array) {
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
	@Override
	public Stream<T> stream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), false));
	}
	@Override
	public Stream<T> parallelStream() {
		return new Stream<>(() -> StreamSupport.stream(spliterator(), true));
	}
	@Override
	public ArrayList<T> add(T newItem) {
		if(items.length == size) {
			ensureCapacityForAdd();
		}
		items[size++] = newItem;
		return this;
	}
	@Override
	public ArrayList<T> add(int index, T element) {
		if(index > -1 && index < size) {
			addAtIndex(index, element);
		} else if(index == size || index == -1) {
			add(element);
		} else if(index >= -1 - size && index < -1) {
			addAtIndex(index + 1 + size, element);
		} else {
			throw outOfBounds(index);
		}
		return this;
	}
	@Override
	public ArrayList<T> addAll(@SuppressWarnings("unchecked") T... values) {
		int length = values.length;
		if(length == 0) {
			return this;
		}
		arraycopyAndAdjustSize(values, length);
		return this;
	}
	@Override
	public <C extends Collection<T, C>> ArrayList<T> addAll(C source) {
		if(source.isEmpty()) {
			return this;
		}
		ensureCapacity(size + source.size());
		for(Iterator<T> iterator = source.iterator(); iterator.hasNext();) {
			add(iterator.next());
		}
		return this;
	}
	@Override
	public ArrayList<T> addAll(ArrayList<T> source) {
		if(source.isEmpty()) {
			return this;
		}
		T[] values = source.items;
		int length = source.size();
		arraycopyAndAdjustSize(values, length);
		return this;
	}
	@Override
	public ArrayList<T> addAll(int index, @SuppressWarnings("unchecked") T... values) {
		int localSize = size;
		if(index > localSize || index < -1 - localSize) {
			throw outOfBounds(index);
		}
		int length = values.length;
		if(length == 0) {
			return this;
		}
		shiftItemsAndArraycopyAndAdjustSize(index, localSize, values, length);
		return this;
	}
	@Override
	public <C extends Collection<T, C>> ArrayList<T> addAll(int index, C source) {
		int localSize = size;
		if(index > localSize || index < -1 - localSize) {
			throw outOfBounds(index);
		}
		if(source.isEmpty()) {
			return this;
		}
		Object[] values = source.toArray();
		int length = values.length;
		shiftItemsAndArraycopyAndAdjustSize(index, localSize, values, length);
		return this;
	}
	@Override
	public ArrayList<T> addAll(int index, ArrayList<T> source) {
		int localSize = size;
		if(index > localSize || index < -1 - localSize) {
			throw outOfBounds(index);
		}
		if(source.isEmpty()) {
			return this;
		}
		Object[] values = source.items;
		int length = source.size();
		shiftItemsAndArraycopyAndAdjustSize(index, localSize, values, length);
		return this;
	}
	@Override
	public ArrayList<T> clear() {
		Arrays.fill(items, null);
		size = 0;
		return this;
	}
	@Override
	public ArrayList<T> remove(T object) {
		int index = indexOf(object);
		if(index >= 0) {
			remove(index);
		}
		return this;
	}
	@Override
	public ArrayList<T> remove(int index) {
		int totalOffset = size - index - 1;
		if(totalOffset > 0) {
			System.arraycopy(items, index + 1, items, index, totalOffset);
		}
		items[--size] = null;
		return this;
	}
	@Override
	public <C extends Collection<T, C>> ArrayList<T> removeAll(C collection) {
		removeIfNotFoundInCollection(collection, false);
		return this;
	}
	@Override
	public <C extends Collection<T, C>> ArrayList<T> retainAll(C collection) {
		removeIfNotFoundInCollection(collection, true);
		return this;
	}
	@Override
	public T get(int index) {
		if(index < size) {
			return items[index];
		}
		throw outOfBounds(index);
	}
	@Override
	public ArrayList<T> set(int index, T element) {
		items[index] = element;
		return this;
	}
	@Override
	public ArrayList<T> sort(Comparator<T> comparator) {
		Arrays.sort(items, 0, size, comparator);
		return this;
	}
	@Override
	public int indexOf(T object) {
		for(int i = 0, n = size; i < n; i++) {
			if(Objects.equals(items[i], object)) {
				return i;
			}
		}
		return -1;
	}
	@Override
	public int lastIndexOf(T object) {
		for(int i = size - 1; i >= 0; i--) {
			if(Objects.equals(items[i], object)) {
				return i;
			}
		}
		return -1;
	}
	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}
	@Override
	public ListIterator<T> listIterator(int index) {
		if(index < 0 || index > size()) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
		return new ArrayListListIterator<>(this, index);
	}
	@Override
	public ArrayList<T> subList(int fromIndex, int toIndex) {
		return new ArrayListSubList<>(this, fromIndex, toIndex);
	}
	@Override
	public <V, E extends Exception> ArrayList<V> map(ExFunction<T, V, E> function) throws E {
		return collect(function, ArrayList.<V> newList(size()));
	}
	@Override
	public <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction) throws E {
		double[] array = new double[size];
		for(int i = 0, n = size; i < n; i++) {
			array[i] = doubleFunction.applyAsDouble(items[i]);
		}
		return array;
	}
	@Override
	public <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		int[] array = new int[size];
		for(int i = 0, n = size; i < n; i++) {
			array[i] = intFunction.applyAsInt(items[i]);
		}
		return array;
	}
	@Override
	public <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		long[] array = new long[size];
		for(int i = 0, n = size; i < n; i++) {
			array[i] = longFunction.applyAsLong(items[i]);
		}
		return array;
	}
	@Override
	public <E extends Exception> ArrayList<T> filter(ExPredicate<T, E> predicate) throws E {
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
	@Override
	public <E extends Exception> ArrayList<T> replaceAll(ExUnaryOperator<T, E> function) throws E {
		for(int i = 0, n = size; i < n; i++) {
			items[i] = function.apply(items[i]);
		}
		return this;
	}
	@Override
	public <E extends Exception> ArrayList<T> removeIf(ExPredicate<T, E> predicate) throws E {
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
	public @Override java.util.ArrayList<T> toJavaList() {
		return new java.util.ArrayList<>(java.util.Arrays.asList(java.util.Arrays.copyOfRange(items, 0, size)));
	}
	public ArrayList<T> sort() {
		Arrays.sort(items, 0, size);
		return this;
	}
	public ArrayList<T> reverse() {
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
		return new ArrayList<>(this);
	}
	public @Override Set<T> toSet() {
		return Set.from(this);
	}
	public @Override HashSet<T> toHashSet() {
		return new HashSet<>(this);
	}
	private ArrayList(int size, T[] array) {
		this.size = size;
		items = array;
	}
	private void shiftItemsAndArraycopyAndAdjustSize(int index, int oldSize, Object[] values, int length) {
		if(index <= -1) {
			index += 1 + oldSize;
		}
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
		if(size() != otherFastList.size()) {
			return false;
		}
		for(int i = 0, n = size; i < n; i++) {
			T one = items[i];
			Object two = otherFastList.items[i];
			if(!Objects.equals(one, two)) {
				return false;
			}
		}
		return true;
	}
	private boolean regularListEquals(ListOfN<?> otherList) {
		Iterator<?> iterator = otherList.iterator();
		for(int i = 0, n = size; i < n; i++) {
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
	private boolean randomAccessListEquals(ListOfN<?> otherList) {
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
	private IndexOutOfBoundsException outOfBounds(int index) {
		throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
	}
	private void shiftElementsAtIndex(int index, int sourceSize) {
		int numberToMove = size - index;
		if(numberToMove > 0) {
			System.arraycopy(items, index, items, index + sourceSize, numberToMove);
		}
	}
	private <C extends Collection<T, C>> void
		removeIfNotFoundInCollection(Collection<T, C> collection, boolean notFound) {
		int currentFilledIndex = 0;
		for(int i = 0, n = size; i < n; i++) {
			T item = items[i];
			if(internalContains(collection, item, notFound)) {
				// keep it
				if(currentFilledIndex != i) {
					items[currentFilledIndex] = item;
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
	static <T> boolean internalContains(Iterable<T> iterable, T each, boolean ifFoundResult) {
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
}
