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

import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExUnaryOperator;

interface CollectionAPI<T, C extends CollectionAPI<T, C>> extends ReadOnly<T> {
	C add(T e);
	C addAll(@SuppressWarnings("unchecked") T... es);
	C remove(T o);
	C clear();
	C addAll(ReadOnly<T> c);
	C retainAll(ReadOnly<T> c);
	C removeAll(ReadOnly<T> c);
	<E extends Exception> C filter(ExPredicate<T, E> filter) throws E;
	<E extends Exception> C removeIf(ExPredicate<T, E> filter) throws E;
	<E extends Exception> C replaceAll(ExUnaryOperator<T, E> mapper) throws E;
	<E extends Exception> C each(ExConsumer<T, E> action) throws E;
}