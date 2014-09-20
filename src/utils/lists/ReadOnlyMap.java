package utils.lists;

public interface ReadOnlyMap<K, V> {
	boolean containsKey(K key);
	boolean containsValue(K value);
	V get(K key);
	V getOrDefault(K key, V defaultValue);
	Iterable<K> keySet();
	Iterable<V> values();
	Map<K, V> toMap();
	HashMap<K, V> toHashMap();
}
