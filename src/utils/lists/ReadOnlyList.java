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
package utils.lists;

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.Objects;
import utils.streams.functions.ExFunction;

public interface ReadOnlyList<T> extends Iterable<T> {
	default @Override java.util.List<T> toJavaUtilCollection() {
		return new AbstractList<T>() {
			public @Override T get(int index) {
				return ReadOnlyList.this.get(index);
			}
			public @Override int size() {
				return ReadOnlyList.this.size();
			}
		};
	}
	default @Override boolean contains(T item) {
		return indexOf(item) > -1;
	}
	default ListIterator<T> listIterator() {
		return listIterator(0);
	}
	default int indexOf(T item) {
		for(int i = 0, n = size(); i < n; i++) {
			if(Objects.equals(get(i), item)) {
				return i;
			}
		}
		return -1;
	}
	default int lastIndexOf(T item) {
		for(int i = size() - 1; i >= 0; i--) {
			if(Objects.equals(get(i), item)) {
				return i;
			}
		}
		return -1;
	}
	public @Override <U, E extends Exception> ReadOnlyList<U> map(ExFunction<T, U, E> mapper) throws E;
	public T get(int index);
	public ListIterator<T> listIterator(int index);
}
