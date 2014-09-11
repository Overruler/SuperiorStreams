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

import java.util.ListIterator;
import java.util.NoSuchElementException;

final class ImmutableListIterator<T> extends ImmutableIterator<T> implements ListIterator<T> {
	public ImmutableListIterator(List<T> list, int index) {
		super(list);
		currentIndex = ArrayList.adjustIndexToPositiveInts(index, list.size());
	}
	@Override
	public boolean hasPrevious() {
		return currentIndex != 0;
	}
	@Override
	public T previous() {
		try {
			int i = currentIndex - 1;
			T previous = list.get(i);
			currentIndex = i;
			return previous;
		} catch(IndexOutOfBoundsException e) {
			throw new NoSuchElementException(e.getMessage());
		}
	}
	@Override
	public int nextIndex() {
		return currentIndex;
	}
	@Override
	public int previousIndex() {
		return currentIndex - 1;
	}
	@Override
	public void set(T o) {
		throw new UnsupportedOperationException("Cannot call set() on " + this.getClass().getSimpleName());
	}
	@Override
	public void add(T o) {
		throw new UnsupportedOperationException("Cannot call add() on " + this.getClass().getSimpleName());
	}
}
