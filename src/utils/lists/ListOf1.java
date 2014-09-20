/*
 * Copyright 2014 Timo Kinnunen.
 * Copyright 1011 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-1.0
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
 * This is a one element immutable ListOf1 which is created by calling
 * List.of(one) method.
 */
final class ListOf1<T> implements List<T> {
	private final T element0;

	ListOf1(T obj1) {
		this.element0 = obj1;
	}
	public @Override String toString() {
		//*Q*
		return new StringBuilder().append('[')
			.append(element0 == this ? "(this List)" : String.valueOf(element0))
			.append(']').toString();
		//*E*
	}
	public @Override int hashCode() {
		return 31 * 1 + (element0 == null ? 0 : element0.hashCode());
	}
	public @Override boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof ListOf1) {
			ListOf1<?> list = (ListOf1<?>) otherList;
			return Objects.equals(element0, list.element0);
		}
		if(otherList instanceof List) {
			return false;
		}
		if(otherList instanceof ArrayList) {
			ArrayList<?> list = (ArrayList<?>) otherList;
			if(list.size() != 1) {
				return false;
			}
			return Objects.equals(element0, list.get(0));
		}
		if(otherList instanceof java.lang.Iterable) {
			java.lang.Iterable<?> list = (java.lang.Iterable<?>) otherList;
			Iterator<?> iterator = list.iterator();
			//*Q*
			return
				iterator.hasNext() && Objects.equals(element0, iterator.next()) &&
				!iterator.hasNext();
			//*E*
		}
		return false;
	}
	public @Override int size() {
		return 1;
	}
	public @Override <E extends Exception> List<T> each(ExConsumer<T, E> procedure) throws E {
		procedure.accept(this.element0);
		return this;
	}
	public @Override T get(int index) {
		switch(index) {//*Q*
			case -1: case 0: return element0;
			default: throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
		}//*E*
	}
	public @Override List<T> add(T newItem) {
		return List.of(element0, newItem);
	}
	public @Override boolean contains(T element) {
		return Objects.equals(element, element0);
	}
	public @Override Object[] toArray() {
		return new Object[] { element0 };
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		T[] array = generator.apply(1);
		array[0] = element0;
		return array;
	}
	public @Override <E extends Exception> List<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		action.accept(element0, 0);
		return this;
	}
	public @Override List<T> add(int index, T element) {
		switch(index) {//*Q*
			case -2: case 0: return List.of(element, element0);
			case -1: case 1: return List.of(element0, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 1");
		}//*E*
	}
	public @Override List<T> remove(int index) {
		switch(index) {//*Q*
			case -1: case 0: return List.of();
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 1");
		}//*E*
	}
	public @Override List<T> set(int index, T element) {
		switch(index) {//*Q*
			case -1: case 0: return List.of(element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 1");
		}//*E*
	}
	public @Override <U, E extends Exception> List<U> map(ExFunction<T, U, E> mapper) throws E {
		return List.of(mapper.apply(element0));
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		return new double[] { doubleFunction.applyAsDouble(element0), };
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		return new int[] { intFunction.applyAsInt(element0), };
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		return new long[] { longFunction.applyAsLong(element0), };
	}
	public @Override <E extends Exception> List<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		return List.of(mapper.apply(element0));
	}
	public @Override Stream<T> stream() {
		return Stream.of(element0);
	}
	public @Override Stream<T> parallelStream() {
		return Stream.of(element0);
	}
}
