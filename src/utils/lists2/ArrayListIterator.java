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
import java.util.NoSuchElementException;

class ArrayListIterator<T> implements Iterator<T> {
	/**
	 * Index of element to be returned by subsequent call to next.
	 */
	protected int currentIndex;
	/**
	 * Index of element returned by most recent call to next or previous.  Reset to -1 if this element is deleted by
	 * a call to remove.
	 */
	protected int lastIndex = -1;
	protected final ArrayList<T> list;

	public ArrayListIterator(ArrayList<T> arrayList) {
		this.list = arrayList;
	}
	@Override
	public boolean hasNext() {
		return this.currentIndex != this.list.size();
	}
	@Override
	public T next() {
		try {
			T next = this.list.get(this.currentIndex);
			this.lastIndex = this.currentIndex++;
			return next;
		} catch(IndexOutOfBoundsException ignored) {
			throw new NoSuchElementException();
		}
	}
	@Override
	public void remove() {
		if(this.lastIndex == -1) {
			throw new IllegalStateException();
		}
		this.list.remove(this.lastIndex);
		if(this.lastIndex < this.currentIndex) {
			this.currentIndex--;
		}
		this.lastIndex = -1;
	}
}
