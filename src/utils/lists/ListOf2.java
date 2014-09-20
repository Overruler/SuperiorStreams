/*
 * Copyright 2012 Timo Kinnunen.
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
package utils.lists;

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
 * This is a two element immutable ListOf2 which is created by calling
 * List.of(one, two) method.
 */
final class ListOf2<T> implements List<T> {
	private final T element0;
	private final T element1;

	ListOf2(T obj1, T obj2) {
		this.element0 = obj1;
		this.element1 = obj2;
	}
	public @Override String toString() {
		//*Q*
		return new StringBuilder().append('[')
		.append(element0 == this ? "(this List)" : String.valueOf(element0)).append(", ")
		.append(element1 == this ? "(this List)" : String.valueOf(element1))
		.append(']').toString();
		//*E*
	}
	public @Override int hashCode() {
		int code0 = element0 == null ? 0 : element0.hashCode();
		int code1 = element1 == null ? 0 : element1.hashCode();
		return 31 * (31 * 1 + code0) + code1;
	}
	public @Override boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof ListOf2) {
			ListOf2<?> list = (ListOf2<?>) otherList;
			//*Q*
			return
			Objects.equals(element0, list.element0) &&
			Objects.equals(element1, list.element1)
			;
			//*E*
		}
		if(otherList instanceof List) {
			return false;
		}
		if(otherList instanceof ArrayList) {
			ArrayList<?> list = (ArrayList<?>) otherList;
			if(list.size() != 2) {
				return false;
			}
			//*Q*
			return
			Objects.equals(element0, list.get(0)) &&
			Objects.equals(element1, list.get(1))
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
			!iterator.hasNext();
			//*E*
		}
		return false;
	}
	public @Override int size() {
		return 2;
	}
	public @Override <E extends Exception> List<T> each(ExConsumer<T, E> procedure) throws E {
		procedure.accept(this.element0);
		procedure.accept(this.element1);
		return this;
	}
	public @Override T get(int index) {
		switch(index) {//*Q*
			case -2: case 0: return element0;
			case -1: case 1: return element1;
			default: throw new IndexOutOfBoundsException("Index: " + index + ", Size: 2");
		}//*E*
	}
	public @Override List<T> add(T newItem) {
		return List.of(element0, element1, newItem);
	}
	public @Override boolean contains(T element) {
		return Objects.equals(element, element0) || Objects.equals(element, element1);
	}
	public @Override Object[] toArray() {
		return new Object[] { element0, element1 };
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		T[] array = generator.apply(2);
		array[0] = element0;
		array[1] = element1;
		return array;
	}
	public @Override <E extends Exception> List<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		action.accept(element0, 0);
		action.accept(element1, 1);
		return this;
	}
	public @Override List<T> add(int index, T element) {
		switch(index) {//*Q*
			case -3: case 0: return List.of(element, element0, element1);
			case -2: case 1: return List.of(element0, element, element1);
			case -1: case 2: return List.of(element0, element1, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 2");
		}//*E*
	}
	public @Override List<T> remove(int index) {
		switch(index) {//*Q*
			case -2: case 0: return List.of(element1);
			case -1: case 1: return List.of(element0);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 2");
		}//*E*
	}
	public @Override List<T> set(int index, T element) {
		switch(index) {//*Q*
			case -2: case 0: return List.of(element, element1);
			case -1: case 1: return List.of(element0, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 2");
		}//*E*
	}
	public @Override <U, E extends Exception> List<U> map(ExFunction<T, U, E> mapper) throws E {
		return List.of(mapper.apply(element0), mapper.apply(element1));
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		return new double[] { doubleFunction.applyAsDouble(element0), doubleFunction.applyAsDouble(element1), };
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		return new int[] { intFunction.applyAsInt(element0), intFunction.applyAsInt(element1), };
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		return new long[] { longFunction.applyAsLong(element0), longFunction.applyAsLong(element1), };
	}
	public @Override <E extends Exception> List<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		return List.of(mapper.apply(element0), mapper.apply(element1));
	}
	public @Override Stream<T> stream() {
		return Stream.of(element0, element1);
	}
}
