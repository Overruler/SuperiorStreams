package utils.lists2;

import utils.lists.Pair;
import utils.streams.functions.ExBiConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExFunction;

//*Q*
interface CollectionMapAPI<K, V,
	MAP     extends CollectionMapAPI<K, V, MAP, KEY_SET, ALL_SET, VALUES, PAIR>,
	KEY_SET extends CollectionSetAPI<K, KEY_SET>,
	ALL_SET extends CollectionSetAPI<PAIR, ALL_SET>,
	VALUES  extends ReadOnly<V>,
	PAIR    extends Pair<K, V>
> extends ReadOnly<PAIR>
{//*E*
	public @Override String toString();
	public @Override int hashCode();
	public @Override boolean equals(Object o);
	boolean containsKey(K key);
	boolean containsValue(K value);
	V get(K key);
	MAP put(K key, V value);
	MAP putAll(Map<K, V> m);
	MAP putAll(HashMap<K, V> m);
	KEY_SET keySet();
	VALUES values();
	ALL_SET entrySet();
	V getOrDefault(K key, V defaultValue);
	<E extends Exception> MAP each(ExBiConsumer<K, V, E> action) throws E;
	<E extends Exception> MAP replaceAll(ExBiFunction<K, V, V, E> function) throws E;
	MAP putIfAbsent(K key, V value);
	MAP remove(K key);
	MAP remove(K key, V value);
	MAP replace(K key, V oldValue, V newValue);
	MAP replace(K key, V value);
	MAP clear();
	<E extends Exception> MAP computeIfAbsent(K key, ExFunction<K, V, E> mappingFunction) throws E;
	<E extends Exception> MAP computeIfPresent(K key, ExBiFunction<K, V, V, E> remappingFunction) throws E;
	<E extends Exception> MAP compute(K key, ExBiFunction<K, V, V, E> remappingFunction) throws E;
	<E extends Exception> MAP merge(K key, V value, ExBiFunction<V, V, V, E> remappingFunction) throws E;
	java.util.HashMap<K, V> toJavaMap();
	public Map<K, V> toMap();
	public HashMap<K, V> toHashMap();
}
