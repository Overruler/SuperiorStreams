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

import java.util.Iterator;
import java.util.Objects;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExToDoubleFunction;
import utils.streams.functions.ExToIntFunction;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.ExUnaryOperator;
import utils.streams.functions.IntFunction;
import utils.streams2.Stream;

/**
 * This is a four element immutable ListOf4 which is created by calling
 * List.of(one, two, three, four) method.
 */
final class ListOf4<T> implements List<T> {
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
	public @Override String toString() {
		//*Q*
		return new StringBuilder().append('[')
		.append(element0 == this ? "(this List)" : String.valueOf(element0)).append(", ")
		.append(element1 == this ? "(this List)" : String.valueOf(element1)).append(", ")
		.append(element2 == this ? "(this List)" : String.valueOf(element2)).append(", ")
		.append(element3 == this ? "(this List)" : String.valueOf(element3))
		.append(']').toString();
		//*E*
	}
	public @Override int hashCode() {
		int code0 = element0 == null ? 0 : element0.hashCode();
		int code1 = element1 == null ? 0 : element1.hashCode();
		int code2 = element2 == null ? 0 : element2.hashCode();
		int code3 = element3 == null ? 0 : element3.hashCode();
		return 31 * (31 * (31 * (31 * 1 + code0) + code1) + code2) + code3;
	}
	public @Override boolean equals(Object otherList) {
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
		if(otherList instanceof java.lang.Iterable) {
			java.lang.Iterable<?> list = (java.lang.Iterable<?>) otherList;
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
	public @Override int size() {
		return 4;
	}
	public @Override <E extends Exception> List<T> each(ExConsumer<T, E> procedure) throws E {
		procedure.accept(this.element0);
		procedure.accept(this.element1);
		procedure.accept(this.element2);
		procedure.accept(this.element3);
		return this;
	}
	public @Override T get(int index) {
		switch(index) {//*Q*
			case -4: case 0: return element0;
			case -3: case 1: return element1;
			case -2: case 2: return element2;
			case -1: case 3: return element3;
			default: throw new IndexOutOfBoundsException("Index: " + index + ", Size: 4");
		}//*E*
	}
	public @Override List<T> add(T newItem) {
		return List.of(element0, element1, element2, element3, newItem);
	}
	public @Override boolean contains(T element) {
		return Objects.equals(element, element0) ||
		Objects.equals(element, element1) ||
		Objects.equals(element, element2) ||
		Objects.equals(element, element3);
	}
	public @Override Object[] toArray() {
		return new Object[] { element0, element1, element2, element3 };
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		T[] array = generator.apply(4);
		array[0] = element0;
		array[1] = element1;
		array[2] = element2;
		array[3] = element3;
		return array;
	}
	public @Override <E extends Exception> List<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		action.accept(element0, 0);
		action.accept(element1, 1);
		action.accept(element2, 2);
		action.accept(element3, 3);
		return this;
	}
	public @Override List<T> add(int index, T element) {
		switch(index) {//*Q*
			case -5: case 0: return List.of(element, element0, element1, element2, element3);
			case -4: case 1: return List.of(element0, element, element1, element2, element3);
			case -3: case 2: return List.of(element0, element1, element, element2, element3);
			case -2: case 3: return List.of(element0, element1, element2, element, element3);
			case -1: case 4: return List.of(element0, element1, element2, element3, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 4");
		}//*E*
	}
	public @Override List<T> remove(int index) {
		switch(index) {//*Q*
			case -4: case 0: return List.of(element1, element2, element3);
			case -3: case 1: return List.of(element0, element2, element3);
			case -2: case 2: return List.of(element0, element1, element3);
			case -1: case 3: return List.of(element0, element1, element2);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 4");
		}//*E*
	}
	public @Override List<T> set(int index, T element) {
		switch(index) {//*Q*
			case -4: case 0: return List.of(element, element1, element2, element3);
			case -3: case 1: return List.of(element0, element, element2, element3);
			case -2: case 2: return List.of(element0, element1, element, element3);
			case -1: case 3: return List.of(element0, element1, element2, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 4");
		}//*E*
	}
	public @Override <U, E extends Exception> List<U> map(ExFunction<T, U, E> mapper) throws E {
		return List.of(mapper.apply(element0), mapper.apply(element1), mapper.apply(element2), mapper.apply(element3));
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		return new double[] { doubleFunction.applyAsDouble(element0), doubleFunction.applyAsDouble(element1),
			doubleFunction.applyAsDouble(element2), doubleFunction.applyAsDouble(element3), };
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		return new int[] { intFunction.applyAsInt(element0), intFunction.applyAsInt(element1),
			intFunction.applyAsInt(element2), intFunction.applyAsInt(element3), };
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		return new long[] { longFunction.applyAsLong(element0), longFunction.applyAsLong(element1),
			longFunction.applyAsLong(element2), longFunction.applyAsLong(element3), };
	}
	public @Override <E extends Exception> List<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		return List.of(mapper.apply(element0), mapper.apply(element1), mapper.apply(element2), mapper.apply(element3));
	}
	public @Override Stream<T> stream() {
		return Stream.of(element0, element1, element2, element3);
	}
}
