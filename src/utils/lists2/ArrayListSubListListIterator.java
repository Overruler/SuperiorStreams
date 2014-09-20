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

final class ArrayListSubListListIterator<T> implements ListIterator<T> {
	/**
	 *
	 */
	private final ArrayListSubList<T> arrayListSubList;
	private final ListIterator<T> listIterator;

	ArrayListSubListListIterator(ArrayListSubList<T> arrayListSubList, int index) {
		this.arrayListSubList = arrayListSubList;
		listIterator = arrayListSubList.original.listIterator(index + arrayListSubList.offset);
	}
	@Override
	public boolean hasNext() {
		return nextIndex() < arrayListSubList.subsize;
	}
	@Override
	public T next() {
		if(hasNext()) {
			return listIterator.next();
		}
		throw new NoSuchElementException();
	}
	@Override
	public boolean hasPrevious() {
		return previousIndex() >= 0;
	}
	@Override
	public T previous() {
		if(hasPrevious()) {
			return listIterator.previous();
		}
		throw new NoSuchElementException();
	}
	@Override
	public int nextIndex() {
		return listIterator.nextIndex() - arrayListSubList.offset;
	}
	@Override
	public int previousIndex() {
		return listIterator.previousIndex() - arrayListSubList.offset;
	}
	@Override
	public void remove() {
		listIterator.remove();
		arrayListSubList.subsize--;
	}
	@Override
	public void set(T o) {
		listIterator.set(o);
	}
	@Override
	public void add(T o) {
		listIterator.add(o);
		arrayListSubList.subsize++;
	}
}