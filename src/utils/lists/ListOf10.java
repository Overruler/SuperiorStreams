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
 * This is a ten element immutable ListOf10 which is created by calling
 * List.of(one, two, three, four, five, six, seven, eight, nine, ten) method.
 */
final class ListOf10<T> implements List<T> {
	private final T element0;
	private final T element1;
	private final T element2;
	private final T element3;
	private final T element4;
	private final T element5;
	private final T element6;
	private final T element7;
	private final T element8;
	private final T element9;

	ListOf10(T obj1, T obj2, T obj3, T obj4, T obj5, T obj6, T obj7, T obj8, T obj9, T obj10) {
		this.element0 = obj1;
		this.element1 = obj2;
		this.element2 = obj3;
		this.element3 = obj4;
		this.element4 = obj5;
		this.element5 = obj6;
		this.element6 = obj7;
		this.element7 = obj8;
		this.element8 = obj9;
		this.element9 = obj10;
	}
	public @Override String toString() {
		//*Q*
		return new StringBuilder().append('[')
			.append(element0 == this ? "(this List)" : String.valueOf(element0)).append(", ")
			.append(element1 == this ? "(this List)" : String.valueOf(element1)).append(", ")
			.append(element2 == this ? "(this List)" : String.valueOf(element2)).append(", ")
			.append(element3 == this ? "(this List)" : String.valueOf(element3)).append(", ")
			.append(element4 == this ? "(this List)" : String.valueOf(element4)).append(", ")
			.append(element5 == this ? "(this List)" : String.valueOf(element5)).append(", ")
			.append(element6 == this ? "(this List)" : String.valueOf(element6)).append(", ")
			.append(element7 == this ? "(this List)" : String.valueOf(element7)).append(", ")
			.append(element8 == this ? "(this List)" : String.valueOf(element8)).append(", ")
			.append(element9 == this ? "(this List)" : String.valueOf(element9))
			.append(']').toString();
		//*E*
	}
	public @Override int hashCode() {
		int code0 = element0 == null ? 0 : element0.hashCode();
		int code1 = element1 == null ? 0 : element1.hashCode();
		int code2 = element2 == null ? 0 : element2.hashCode();
		int code3 = element3 == null ? 0 : element3.hashCode();
		int code4 = element4 == null ? 0 : element4.hashCode();
		int code5 = element5 == null ? 0 : element5.hashCode();
		int code6 = element6 == null ? 0 : element6.hashCode();
		int code7 = element7 == null ? 0 : element7.hashCode();
		int code8 = element8 == null ? 0 : element8.hashCode();
		int code9 = element9 == null ? 0 : element9.hashCode();
		//*Q*
		return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * 1 + code0) + code1) + code2) + code3) + code4) + code5) + code6) + code7) + code8) + code9;
		//*E*
	}
	public @Override boolean equals(Object otherList) {
		if(otherList == this) {
			return true;
		}
		if(otherList instanceof ListOf10) {
			ListOf10<?> list = (ListOf10<?>) otherList;
			//*Q*
			return
				Objects.equals(element0, list.element0) &&
				Objects.equals(element1, list.element1) &&
				Objects.equals(element2, list.element2) &&
				Objects.equals(element3, list.element3) &&
				Objects.equals(element4, list.element4) &&
				Objects.equals(element5, list.element5) &&
				Objects.equals(element6, list.element6) &&
				Objects.equals(element7, list.element7) &&
				Objects.equals(element8, list.element8) &&
				Objects.equals(element9, list.element9)
			;
			//*E*
		}
		if(otherList instanceof List) {
			return false;
		}
		if(otherList instanceof ArrayList) {
			ArrayList<?> list = (ArrayList<?>) otherList;
			if(list.size() != 10) {
				return false;
			}
			//*Q*
			return
				Objects.equals(element0, list.get(0)) &&
				Objects.equals(element1, list.get(1)) &&
				Objects.equals(element2, list.get(2)) &&
				Objects.equals(element3, list.get(3)) &&
				Objects.equals(element4, list.get(4)) &&
				Objects.equals(element5, list.get(5)) &&
				Objects.equals(element6, list.get(6)) &&
				Objects.equals(element7, list.get(7)) &&
				Objects.equals(element8, list.get(8)) &&
				Objects.equals(element9, list.get(9))
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
				iterator.hasNext() && Objects.equals(element4, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element5, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element6, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element7, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element8, iterator.next()) &&
				iterator.hasNext() && Objects.equals(element9, iterator.next()) &&
				!iterator.hasNext();
			//*E*
		}
		return false;
	}
	public @Override int size() {
		return 10;
	}
	public @Override <E extends Exception> List<T> each(ExConsumer<T, E> procedure) throws E {
		procedure.accept(this.element0);
		procedure.accept(this.element1);
		procedure.accept(this.element2);
		procedure.accept(this.element3);
		procedure.accept(this.element4);
		procedure.accept(this.element5);
		procedure.accept(this.element6);
		procedure.accept(this.element7);
		procedure.accept(this.element8);
		procedure.accept(this.element9);
		return this;
	}
	public @Override T get(int index) {
		switch(index) {//*Q*
			case -10: case 0: return element0;
			case  -9: case 1: return element1;
			case  -8: case 2: return element2;
			case  -7: case 3: return element3;
			case  -6: case 4: return element4;
			case  -5: case 5: return element5;
			case  -4: case 6: return element6;
			case  -3: case 7: return element7;
			case  -2: case 8: return element8;
			case  -1: case 9: return element9;
			default: throw new IndexOutOfBoundsException("Index: " + index + ", Size: 10");
		}//*E*
	}
	public @Override List<T> add(T newItem) {
		return List.of(
			element0,
			element1,
			element2,
			element3,
			element4,
			element5,
			element6,
			element7,
			element8,
			element9,
			newItem);
	}
	public @Override boolean contains(T element) {
		return Objects.equals(element, element0) ||
		Objects.equals(element, element1) ||
		Objects.equals(element, element2) ||
		Objects.equals(element, element3) ||
		Objects.equals(element, element4) ||
		Objects.equals(element, element5) ||
		Objects.equals(element, element6) ||
		Objects.equals(element, element7) ||
		Objects.equals(element, element8) ||
		Objects.equals(element, element9);
	}
	public @Override Object[] toArray() {
		return new Object[] { element0, element1, element2, element3, element4, element5, element6, element7, element8,
			element9 };
	}
	public @Override T[] toArray(IntFunction<T[]> generator) {
		T[] array = generator.apply(10);
		array[0] = element0;
		array[1] = element1;
		array[2] = element2;
		array[3] = element3;
		array[4] = element4;
		array[5] = element5;
		array[6] = element6;
		array[7] = element7;
		array[8] = element8;
		array[9] = element9;
		return array;
	}
	public @Override <E extends Exception> List<T> eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		action.accept(element0, 0);
		action.accept(element1, 1);
		action.accept(element2, 2);
		action.accept(element3, 3);
		action.accept(element4, 4);
		action.accept(element5, 5);
		action.accept(element6, 6);
		action.accept(element7, 7);
		action.accept(element8, 8);
		action.accept(element9, 9);
		return this;
	}
	public @Override List<T> add(int index, T element) {
		switch(index) {//*Q*
			case -11: case  0: return List.of(element, element0, element1, element2, element3, element4, element5, element6, element7, element8, element9);
			case -10: case  1: return List.of(element0, element, element1, element2, element3, element4, element5, element6, element7, element8, element9);
			case  -9: case  2: return List.of(element0, element1, element, element2, element3, element4, element5, element6, element7, element8, element9);
			case  -8: case  3: return List.of(element0, element1, element2, element, element3, element4, element5, element6, element7, element8, element9);
			case  -7: case  4: return List.of(element0, element1, element2, element3, element, element4, element5, element6, element7, element8, element9);
			case  -6: case  5: return List.of(element0, element1, element2, element3, element4, element, element5, element6, element7, element8, element9);
			case  -5: case  6: return List.of(element0, element1, element2, element3, element4, element5, element, element6, element7, element8, element9);
			case  -4: case  7: return List.of(element0, element1, element2, element3, element4, element5, element6, element, element7, element8, element9);
			case  -3: case  8: return List.of(element0, element1, element2, element3, element4, element5, element6, element7, element, element8, element9);
			case  -2: case  9: return List.of(element0, element1, element2, element3, element4, element5, element6, element7, element8, element, element9);
			case  -1: case 10: return List.of(element0, element1, element2, element3, element4, element5, element6, element7, element8, element9, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 10");
		}//*E*
	}
	public @Override List<T> remove(int index) {
		switch(index) {//*Q*
			case -10: case 0: return List.of(element1, element2, element3, element4, element5, element6, element7, element8, element9);
			case  -9: case 1: return List.of(element0, element2, element3, element4, element5, element6, element7, element8, element9);
			case  -8: case 2: return List.of(element0, element1, element3, element4, element5, element6, element7, element8, element9);
			case  -7: case 3: return List.of(element0, element1, element2, element4, element5, element6, element7, element8, element9);
			case  -6: case 4: return List.of(element0, element1, element2, element3, element5, element6, element7, element8, element9);
			case  -5: case 5: return List.of(element0, element1, element2, element3, element4, element6, element7, element8, element9);
			case  -4: case 6: return List.of(element0, element1, element2, element3, element4, element5, element7, element8, element9);
			case  -3: case 7: return List.of(element0, element1, element2, element3, element4, element5, element6, element8, element9);
			case  -2: case 8: return List.of(element0, element1, element2, element3, element4, element5, element6, element7, element9);
			case  -1: case 9: return List.of(element0, element1, element2, element3, element4, element5, element6, element7, element8);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 10");
		}//*E*
	}
	public @Override List<T> set(int index, T element) {
		switch(index) {//*Q*
			case -10: case 0: return List.of(element, element1, element2, element3, element4, element5, element6, element7, element8, element9);
			case  -9: case 1: return List.of(element0, element, element2, element3, element4, element5, element6, element7, element8, element9);
			case  -8: case 2: return List.of(element0, element1, element, element3, element4, element5, element6, element7, element8, element9);
			case  -7: case 3: return List.of(element0, element1, element2, element, element4, element5, element6, element7, element8, element9);
			case  -6: case 4: return List.of(element0, element1, element2, element3, element, element5, element6, element7, element8, element9);
			case  -5: case 5: return List.of(element0, element1, element2, element3, element4, element, element6, element7, element8, element9);
			case  -4: case 6: return List.of(element0, element1, element2, element3, element4, element5, element, element7, element8, element9);
			case  -3: case 7: return List.of(element0, element1, element2, element3, element4, element5, element6, element, element8, element9);
			case  -2: case 8: return List.of(element0, element1, element2, element3, element4, element5, element6, element7, element, element9);
			case  -1: case 9: return List.of(element0, element1, element2, element3, element4, element5, element6, element7, element8, element);
			default: throw new IndexOutOfBoundsException("Index: " + index + " Size: 10");
		}//*E*
	}
	public @Override <U, E extends Exception> List<U> map(ExFunction<T, U, E> mapper) throws E {
		return List.of(
			mapper.apply(element0),
			mapper.apply(element1),
			mapper.apply(element2),
			mapper.apply(element3),
			mapper.apply(element4),
			mapper.apply(element5),
			mapper.apply(element6),
			mapper.apply(element7),
			mapper.apply(element8),
			mapper.apply(element9));
	}
	public @Override <E extends Exception> double[] mapToDouble(ExToDoubleFunction<? super T, E> doubleFunction)
		throws E {
		return new double[] { doubleFunction.applyAsDouble(element0), doubleFunction.applyAsDouble(element1),
			doubleFunction.applyAsDouble(element2), doubleFunction.applyAsDouble(element3),
			doubleFunction.applyAsDouble(element4), doubleFunction.applyAsDouble(element5),
			doubleFunction.applyAsDouble(element6), doubleFunction.applyAsDouble(element7),
			doubleFunction.applyAsDouble(element8), doubleFunction.applyAsDouble(element9), };
	}
	public @Override <E extends Exception> int[] mapToInt(ExToIntFunction<? super T, E> intFunction) throws E {
		return new int[] { intFunction.applyAsInt(element0), intFunction.applyAsInt(element1),
			intFunction.applyAsInt(element2), intFunction.applyAsInt(element3), intFunction.applyAsInt(element4),
			intFunction.applyAsInt(element5), intFunction.applyAsInt(element6), intFunction.applyAsInt(element7),
			intFunction.applyAsInt(element8), intFunction.applyAsInt(element9), };
	}
	public @Override <E extends Exception> long[] mapToLong(ExToLongFunction<? super T, E> longFunction) throws E {
		return new long[] { longFunction.applyAsLong(element0), longFunction.applyAsLong(element1),
			longFunction.applyAsLong(element2), longFunction.applyAsLong(element3), longFunction.applyAsLong(element4),
			longFunction.applyAsLong(element5), longFunction.applyAsLong(element6), longFunction.applyAsLong(element7),
			longFunction.applyAsLong(element8), longFunction.applyAsLong(element9), };
	}
	public @Override <E extends Exception> List<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		return List.of(
			mapper.apply(element0),
			mapper.apply(element1),
			mapper.apply(element2),
			mapper.apply(element3),
			mapper.apply(element4),
			mapper.apply(element5),
			mapper.apply(element6),
			mapper.apply(element7),
			mapper.apply(element8),
			mapper.apply(element9));
	}
	public @Override Stream<T> stream() {
		return Stream.of(
			element0,
			element1,
			element2,
			element3,
			element4,
			element5,
			element6,
			element7,
			element8,
			element9);
	}
}
