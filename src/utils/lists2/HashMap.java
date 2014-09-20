package utils.lists2;

import java.util.Iterator;
import java.util.Objects;
import utils.lists.Pair;
import utils.streams.functions.ExBiConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExFunction;
import utils.streams2.WrapperException;

public class HashMap<K, V>
	implements
	CollectionMapAPI<K, V, HashMap<K, V>, HashSet<K>, HashSet<HashMap.Entry<K, V>>, ReadOnly<V>, HashMap.Entry<K, V>> {
	public static class Entry<T, V> extends Pair<T, V> implements java.util.Map.Entry<T, V> {
		private final java.util.Map.Entry<T, V> entry;

		public static <T, V> Entry<T, V> of(java.util.Map.Entry<T, V> entry) {
			return new Entry<>(entry);
		}
		public Entry(java.util.Map.Entry<T, V> entry) {
			super(entry.getKey(), entry.getValue());
			this.entry = entry;
		}
		public @Override T getKey() {
			return lhs;
		}
		public @Override V getValue() {
			return entry.getValue();
		}
		public @Override V setValue(V value) {
			return entry.setValue(value);
		}
		public @Override int hashCode() {
			return entry.hashCode();
		}
		public @Override boolean equals(Object obj) {
			return entry.equals(obj);
		}
		public @Override String toString() {
			return entry.toString();
		}
	}

	final java.util.HashMap<K, V> wrapped;

	public static <K, V> HashMap<K, V> from(java.util.Map<K, V> source) {
		return new HashMap<>(source);
	}
	public static <K, V> HashMap<K, V> of() {
		return new HashMap<>();
	}
	public static <K, V> HashMap<K, V> of(K key, V value) {
		HashMap<K, V> map = new HashMap<>();
		return map.put(key, value);
	}
	/*Q*/
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2                                                                                                  ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2);                                                                                                   }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3                                                                                      ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3);                                                                                       }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4                                                                          ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4);                                                                           }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5                                                              ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5);                                                               }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6                                                  ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6);                                                   }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7                                      ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7);                                       }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8                          ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7).put(k8, v8);                           }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9              ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7).put(k8, v8).put(k9, v9);               }
	public static <K, V> HashMap<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7).put(k8, v8).put(k9, v9).put(k10, v10); }
	/*E*/
	public HashMap() {
		wrapped = new java.util.HashMap<>();
	}
	public HashMap(java.util.Map<K, V> source) {
		wrapped = new java.util.HashMap<>(source);
	}
	public HashMap(int initialCapacity, float loadFactor) {
		wrapped = new java.util.HashMap<>(initialCapacity, loadFactor);
	}
	public HashMap(int initialCapacity) {
		wrapped = new java.util.HashMap<>(initialCapacity);
	}
	public HashMap(Map<K, V> m) {
		wrapped = new java.util.HashMap<>(m.wrapped);
	}
	public HashMap(HashMap<K, V> m) {
		wrapped = new java.util.HashMap<>(m.wrapped);
	}
	public @Override String toString() {
		return wrapped.toString();
	}
	public @Override int hashCode() {
		return wrapped.hashCode();
	}
	public @Override boolean equals(Object o) {
		return wrapped.equals(o);
	}
	public @Override boolean contains(HashMap.Entry<K, V> o) {
		return o != null && Objects.equals(get(o.lhs), o.rhs);
	}
	public @Override Iterator<HashMap.Entry<K, V>> iterator() {
		return entrySet().iterator();
	}
	public @Override int size() {
		return wrapped.size();
	}
	public @Override boolean isEmpty() {
		return wrapped.isEmpty();
	}
	public @Override boolean notEmpty() {
		return wrapped.isEmpty() == false;
	}
	public @Override boolean containsKey(K key) {
		return wrapped.containsKey(key);
	}
	public @Override boolean containsValue(K value) {
		return wrapped.containsValue(value);
	}
	public @Override V get(K key) {
		return wrapped.get(key);
	}
	public @Override HashMap<K, V> put(K key, V value) {
		wrapped.put(key, value);
		return this;
	}
	public @Override HashMap<K, V> putAll(Map<K, V> m) {
		wrapped.putAll(m.wrapped);
		return this;
	}
	public @Override HashMap<K, V> putAll(HashMap<K, V> m) {
		wrapped.putAll(m.wrapped);
		return this;
	}
	public @Override HashSet<K> keySet() {
		return HashSet.wrap(wrapped.keySet());
	}
	public @Override ReadOnly<V> values() {
		return List.from(wrapped.values());
	}
	public @Override HashSet<Entry<K, V>> entrySet() {
		return new HashMapEntrySetHashSet<>(this, wrapped.entrySet());
	}
	public @Override V getOrDefault(K key, V defaultValue) {
		return wrapped.getOrDefault(key, defaultValue);
	}
	public @Override <E extends Exception> HashMap<K, V> each(ExBiConsumer<K, V, E> action) throws E {
		for(java.util.Map.Entry<K, V> entry : wrapped.entrySet()) {
			action.accept(entry.getKey(), entry.getValue());
		}
		return this;
	}
	public @Override <E extends Exception> HashMap<K, V> replaceAll(ExBiFunction<K, V, V, E> mapper) throws E {
		Class<E> classOfE = classForE();
		try {
			wrapped.replaceAll(mapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return this;
	}
	public @Override HashMap<K, V> putIfAbsent(K key, V value) {
		wrapped.putIfAbsent(key, value);
		return this;
	}
	public @Override HashMap<K, V> remove(K key) {
		wrapped.remove(key);
		return this;
	}
	public @Override HashMap<K, V> remove(K key, V value) {
		wrapped.remove(key, value);
		return this;
	}
	public @Override HashMap<K, V> replace(K key, V oldValue, V newValue) {
		wrapped.replace(key, oldValue, newValue);
		return this;
	}
	public @Override HashMap<K, V> replace(K key, V value) {
		wrapped.replace(key, value);
		return this;
	}
	public @Override HashMap<K, V> clear() {
		wrapped.clear();
		return this;
	}
	public @Override <E extends Exception> HashMap<K, V> computeIfAbsent(K key, ExFunction<K, V, E> mapper) throws E {
		Class<E> classOfE = classForE();
		try {
			wrapped.computeIfAbsent(key, mapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return this;
	}
	public @Override <E extends Exception> HashMap<K, V> computeIfPresent(K key, ExBiFunction<K, V, V, E> remapper)
		throws E {
		Class<E> classOfE = classForE();
		try {
			wrapped.computeIfPresent(key, remapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return this;
	}
	public @Override <E extends Exception> HashMap<K, V> compute(K key, ExBiFunction<K, V, V, E> remapper) throws E {
		Class<E> classOfE = classForE();
		try {
			wrapped.compute(key, remapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return this;
	}
	public @Override <E extends Exception> HashMap<K, V> merge(K key, V value, ExBiFunction<V, V, V, E> remapper)
		throws E {
		Class<E> classOfE = classForE();
		try {
			wrapped.merge(key, value, remapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return this;
	}
	public @Override java.util.HashMap<K, V> toJavaMap() {
		return new java.util.HashMap<>(wrapped);
	}
	public @Override Map<K, V> toMap() {
		return Map.fromHashMap(this);
	}
	public @Override HashMap<K, V> toHashMap() {
		return new HashMap<>(this);
	}
	@SuppressWarnings("unchecked")
	private static <E extends Exception> Class<E> classForE() {
		return (Class<E>) Exception.class;
	}
}
