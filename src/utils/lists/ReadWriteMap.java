package utils.lists;

import utils.streams.functions.ExBiConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExFunction;

public interface ReadWriteMap<K, V, T, C extends ReadWriteMap<K, V, T, C>> extends ReadOnlyMap<K, V>,
	Iterable<T> {
	C put(K key, V value);
	C putAll(ReadOnlyMap<K, V> m);
	<E extends Exception> C each(ExBiConsumer<K, V, E> action) throws E;
	<E extends Exception> C replaceAll(ExBiFunction<K, V, V, E> function) throws E;
	C putIfAbsent(K key, V value);
	C remove(K key);
	C remove(K key, V value);
	C replace(K key, V oldValue, V newValue);
	C replace(K key, V value);
	C clear();
	Iterable<T> entrySet();
	<E extends Exception> C computeIfAbsent(K key, ExFunction<K, V, E> mappingFunction) throws E;
	<E extends Exception> C computeIfPresent(K key, ExBiFunction<K, V, V, E> remappingFunction) throws E;
	<E extends Exception> C compute(K key, ExBiFunction<K, V, V, E> remappingFunction) throws E;
	<E extends Exception> C merge(K key, V value, ExBiFunction<V, V, V, E> remappingFunction) throws E;
}
