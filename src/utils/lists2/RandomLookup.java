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

public interface RandomLookup<T, R extends RandomLookup<T, R>> extends Collection<T, R> {
	public @Override <E extends Exception> R each(ExConsumer<T, E> procedure) throws E;
	java.util.HashSet<T> toJavaSet();
	Set<T> toSet();
	HashSet<T> toHashSet();
}