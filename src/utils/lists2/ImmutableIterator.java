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

import java.util.Iterator;
import java.util.NoSuchElementException;

class ImmutableIterator<T> implements Iterator<T> {
	/**
	 * Index of element to be returned by subsequent call to next.
	 */
	protected int currentIndex;
	protected final List<T> list;

	public ImmutableIterator(List<T> list) {
		this.list = list;
	}
	@Override
	public boolean hasNext() {
		return this.currentIndex != this.list.size();
	}
	@Override
	public T next() {
		try {
			T result = this.list.get(this.currentIndex);
			this.currentIndex++;
			return result;
		} catch(IndexOutOfBoundsException e) {
			throw new NoSuchElementException(e.getMessage());
		}
	}
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot call remove() on " + this.getClass().getSimpleName());
	}
}
