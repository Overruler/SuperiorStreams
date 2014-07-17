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
package utils.lists2;

import java.util.function.IntFunction;
import utils.streams2.Stream;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;

public interface Collection<T, C extends Collection<T, C>> extends CollectionAPI<T, C>, Iterable<T> {
	public @Override C add(T e);
	public @Override C remove(T o);
	public @Override C clear();
	public @Override <COLLECTION extends Collection<T, COLLECTION>> C addAll(COLLECTION c);
	public @Override <COLLECTION extends Collection<T, COLLECTION>> C retainAll(COLLECTION c);
	public @Override <COLLECTION extends Collection<T, COLLECTION>> C removeAll(COLLECTION c);
	public @Override <E extends Exception> C filter(ExPredicate<T, E> filter) throws E;
	public @Override <E extends Exception> C removeIf(ExPredicate<T, E> filter) throws E;
	public @Override <E extends Exception> C replaceAll(ExUnaryOperator<T, E> mapper) throws E;
	<E extends Exception> C each(ExConsumer<T, E> action) throws E;
	int size();
	boolean isEmpty();
	boolean notEmpty();
	boolean contains(T o);
	<COLLECTION extends Collection<T, COLLECTION>> boolean containsAll(COLLECTION c);
	Object[] toArray();
	T[] toArray(IntFunction<T[]> generator);
	T[] toArray(T[] a);
	Stream<T> stream();
	Stream<T> parallelStream();
}