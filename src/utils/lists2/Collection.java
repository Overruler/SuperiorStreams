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


public interface Collection<T, C extends Collection<T, C>> extends CollectionAPI<T, C> {
	//	public @Override int size();
	//	public @Override boolean isEmpty();
	//	public @Override boolean notEmpty();
	//	public @Override boolean contains(T o);
	//	public @Override boolean containsAll(ReadOnly<T> c);
	//	public @Override Object[] toArray();
	//	public @Override T[] toArray(IntFunction<T[]> generator);
	//	public @Override T[] toArray(T[] a);
	//	public @Override Stream<T> stream();
	//	public @Override Stream<T> parallelStream();
	//	public @Override java.util.Collection<T> toJavaUtilCollection();
	//	public @Override SELF add(T e);
	//	public @Override SELF addAll(@SuppressWarnings("unchecked") T... es);
	//	public @Override SELF remove(T o);
	//	public @Override SELF clear();
	//	public @Override SELF addAll(ReadOnly<T> c);
	//	public @Override SELF retainAll(ReadOnly<T> c);
	//	public @Override SELF removeAll(ReadOnly<T> c);
	//	public @Override <E extends Exception> SELF filter(ExPredicate<T, E> filter) throws E;
	//	public @Override <E extends Exception> SELF removeIf(ExPredicate<T, E> filter) throws E;
	//	public @Override <E extends Exception> SELF replaceAll(ExUnaryOperator<T, E> mapper) throws E;
	//	public @Override <E extends Exception> SELF each(ExConsumer<T, E> action) throws E;
}