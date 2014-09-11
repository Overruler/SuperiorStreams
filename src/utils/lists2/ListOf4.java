/*
 * Copyright 2014 Timo Kinnunen.
 * Copyright 2011 Goldman Sachs.
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
import java.util.Objects;
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
 * This is a four element immutable ListOf4 which is created by calling
 * List.of(one, two, three, four) method.
 */
final class ListOf4<T> extends List<T> {
	private final T element0;
	private final T element1;
	private final T element2;
	private final T element3;

	ListOf4(T obj1, T obj2, T obj3, T obj4) {
		this.element0 = obj1;
		this.element1 = obj2;
		this.element2 = obj3;
		this.element3 = obj4;
	}
	@Override
	public String toString() {
		//*Q*
		return new StringBuilder().append('[')
			.append(element0 == this ? "(this List)" : String.valueOf(element0)).append(", ")
			.append(element1 == this ? "(this List)" : String.valueOf(element1)).append(", ")
			.append(element2 == this ? "(this List)" : String.valueOf(element2)).append(", ")
			.append(element3 == this ? "(this List)" : String.valueOf(element3))
			.append(']').toString();
		//*E*
	}
	@Override
	public int hashCode() {
		int code0 = element0 == null ? 0 : element0.hashCode();
		int code1 = element1 == null ? 0 : element1.hashCode();
		int code2 = element2 == null ? 0 : element2.hashCode();
		int code3 = element3 == null ? 0 : element3.hashCode();
		return 31 * (31 * (31 * (31 * 1 + code0) + code1) + code2) + code3;
	}
	@Override
	public boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof ListOf4) {
			ListOf4<?> list = (ListOf4<?>) otherList;
			//*Q*
			return
				Objects.equals(element0, list.element0) &&
				Objects.equals(element1, list.element1) &&
				Objects.equals(element2, list.element2) &&
				Objects.equals(element3, list.element3)
			;
			//*E*
		}
		if(otherList instanceof List) {
			return false;
		}
		if(otherList instanceof ArrayList) {
			ArrayList<?> list = (ArrayList<?>) otherList;
			if(list.size() != 4) {
				return false;
			}
			//*Q*
			return
				Objects.equals(element0, list.get(0)) &&
				Objects.equals(element1, list.get(1)) &&
				Objects.equals(element2, list.get(2)) &&
				Objects.equals(element3, list.get(3))
			;
			//*E*
		}
		if(otherList instanceof Collection) {
			Collection<?, ?> list = (Collection<?, ?>) otherList;
			Iterator<?> iterator = list.iterator();
			//*Q*
			return
				iterator.hasNext() && Objects.equals(element0, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element1, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element2, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element3, iterator.next()) &&
				!iterator.hasNext();
			//*E*
		}
		return false;
	}
	@Override
	public int size() {
		return 4;
	}
	@Override
	public <E extends Exception> List<T> each(ExConsumer<T, E> procedure) throws E {
		procedure.accept(this.element0);
		procedure.accept(this.element1);
		procedure.accept(this.element2);
		procedure.accept(this.element3);
		return this;
	}
	@Override
	public T get(int index) {
		switch(index) {//*Q*
			case -4: case 0: return element0;
			case -3: case 1: return element1;
			case -2: case 2: return element2;
			case -1: case 3: return element3;
			default: throw new IndexOutOfBoundsException("Index: " + index + ", Size: 4");
		}//*E*
	}
	@Override
	public List<T> add(T newItem) {
		return List.of(element0, element1, element2, element3, newItem);
	}
	@Override
	public Iterator<T> iterator() {
		return new ImmutableIterator<>(this);
	}
	@Override
	public Spliterator<T> spliterator() {
		return toArrayList().spliterator();
	}
	@Override
	public boolean isEmpty() {
		return false;
	}
	@Override
	public boolean notEmpty() {
		return true;
	}
	@Override
	public boolean contains(T element) {
		return Objects.equals(element, element0) ||
		Objects.equals(element, element1) ||
		Objects.equals(element, element2) ||
		Objects.equals(element, element3);
	}
	@Override
	public <C extends Collection<T, C>> boolean containsAll(C collection) {
		return toArrayList().containsAll(collection);
	}
	@Override
	public Object[] toArray() {
		return new Object[] { element0, element1, element2, element3 };
	}
	@Override
	public T[] toArray(IntFunction<T[]> generator) {
		T[] array = generator.apply(4);
		array[0] = element0;
		array[1] = element1;
		array[2] = element2;
		array[3] = element3;
		return array;
	}
	@Override
	public T[] toArray(T[] array) {
		return toArrayList().toArray(array);
	}
	@Override
	public Stream<T> stream() {
		return toArrayList().stream();
	}
	@Override
	public Stream<T> parallelStream() {
		return toArrayList().parallelStream();
	}
	@Override
	public <E extends Exception> List<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		action.accept(element0, 0);
		action.accept(element1, 1);
		action.accept(element2, 2);
		action.accept(element3, 3);
		return this;
	}
	@Override
	public List<T> add(int index, T element) {
		switch(index) {//*Q*
			case -5: case 0: return List.of(element, element0, element1, element2, element3);
			case -4: case 1: return List.of(element0, element, element1, element2, element3);
			case -3: case 2: return List.of(element0, element1, element, element2, element3);
			case -2: case 3: return List.of(element0, element1, element2, element, element3);
			case -1: case 4: return List.of(element0, element1, element2, element3, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 4");
		}//*E*
	}
	@Override
	public <C extends Collection<T, C>> List<T> addAll(C collection) {
		return toArrayList().addAll(collection).toList();
	}
	@Override
	public List<T> addAll(List<T> source) {
		return toArrayList().addAll(source).toList();
	}
	@Override
	public <C extends Collection<T, C>> List<T> addAll(int index, C collection) {
		return toArrayList().addAll(index, collection).toList();
	}
	@Override
	public List<T> addAll(int index, List<T> source) {
		return toArrayList().addAll(index, source).toList();
	}
	@Override
	public List<T> clear() {
		return List.of();
	}
	@Override
	public List<T> remove(T element) {
		return toArrayList().remove(element).toList();
	}
	@Override
	public List<T> remove(int index) {
		switch(index) {//*Q*
			case -4: case 0: return List.of(element1, element2, element3);
			case -3: case 1: return List.of(element0, element2, element3);
			case -2: case 2: return List.of(element0, element1, element3);
			case -1: case 3: return List.of(element0, element1, element2);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 4");
		}//*E*
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
	public List<T> set(int index, T element) {
		switch(index) {//*Q*
			case -4: case 0: return List.of(element, element1, element2, element3);
			case -3: case 1: return List.of(element0, element, element2, element3);
			case -2: case 2: return List.of(element0, element1, element, element3);
			case -1: case 3: return List.of(element0, element1, element2, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 4");
		}//*E*
	}
	@Override
	public List<T> sort(Comparator<T> comparator) {
		return toArrayList().sort(comparator).toList();
	}
	@Override
	public int indexOf(T item) {
		return toArrayList().indexOf(item);
	}
	@Override
	public int lastIndexOf(T item) {
		return toArrayList().lastIndexOf(item);
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
		return List.of(mapper.apply(element0), mapper.apply(element1), mapper.apply(element2), mapper.apply(element3));
	}
	@Override
	public <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction) throws E {
		return new double[] { doubleFunction.applyAsDouble(element0), doubleFunction.applyAsDouble(element1),
			doubleFunction.applyAsDouble(element2), doubleFunction.applyAsDouble(element3), };
	}
	@Override
	public <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		return new int[] { intFunction.applyAsInt(element0), intFunction.applyAsInt(element1),
			intFunction.applyAsInt(element2), intFunction.applyAsInt(element3), };
	}
	@Override
	public <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		return new long[] { longFunction.applyAsLong(element0), longFunction.applyAsLong(element1),
			longFunction.applyAsLong(element2), longFunction.applyAsLong(element3), };
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
		return List.of(mapper.apply(element0), mapper.apply(element1), mapper.apply(element2), mapper.apply(element3));
	}
}
