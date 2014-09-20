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

import java.util.Comparator;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExUnaryOperator;

public interface ReadWriteList<T, C extends ReadWriteList<T, C>> extends Collection<T, C>, ReadOnlyList<T> {
	default <E extends Exception> C eachWithIndex(ExObjIntConsumer<T, E> action) throws E {
		C self = identity();
		for(int i = 0, n = self.size(); i < n; i++) {
			action.accept(self.get(i), i);
		}
		return self;
	}
	default C addAll(int index, @SuppressWarnings("unchecked") T... items) {
		return addAll(index, ArrayList.of(items));
	}
	default C addAll(int index, Iterable<T> items) {
		C self = identity();
		index = ArrayList.adjustIndexToPositiveInts(index, self.size());
		for(T item : items) {
			self = self.add(index++, item);
		}
		return self;
	}
	default @Override <E extends Exception> C replaceAll(ExUnaryOperator<T, E> mapper) throws E {
		C self = identity();
		for(int i = 0, n = self.size(); i < n; i++) {
			self = self.set(i, self.get(i));
		}
		return self;
	}
	default C reverse() {
		C self = identity();
		int mid = self.size() / 2;
		int j = self.size() - 1;
		for(int i = 0; i < mid; i++, j--) {
			T one = self.get(i);
			T two = self.get(j);
			self = self.set(i, two).set(j, one);
		}
		return self;
	}
	public C add(int index, T element);
	public C remove(int index);
	public C set(int index, T element);
	public C sort(Comparator<T> comparator);
	public C sort();
	public C subList(int fromIndex, int toIndex);
}
