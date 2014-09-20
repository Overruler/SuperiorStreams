package utils.lists2;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import utils.lists.Pair;
import utils.streams.functions.ExBiConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExFunction;
import utils.streams2.WrapperException;

public class Map<K, V> implements CollectionMapAPI<K, V, Map<K, V>, Set<K>, Set<Pair<K, V>>, ReadOnly<V>, Pair<K, V>> {
	final java.util.HashMap<K, V> wrapped;

	static <K, V> Map<K, V> fromHashMap(HashMap<K, V> source) {
		return new Map<>(source);
	}
	public static <K, V> Map<K, V> from(java.util.Map<K, V> source) {
		return new Map<>(source);
	}
	public static <K, V> Map<K, V> of() {
		return new Map<>();
	}
	public static <K, V> Map<K, V> of(K key, V value) {
		Map<K, V> map = new Map<>();
		return map.put(key, value);
	}
	/*Q*/
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2                                                                                                  ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).toMap();                                                                                                   }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3                                                                                      ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).toMap();                                                                                       }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4                                                                          ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).toMap();                                                                           }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5                                                              ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).toMap();                                                               }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6                                                  ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).toMap();                                                   }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7                                      ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7).toMap();                                       }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8                          ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7).put(k8, v8).toMap();                           }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9              ) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7).put(k8, v8).put(k9, v9).toMap();               }
	public static <K, V> Map<K, V> of(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) { HashMap<K,V> map = new HashMap<>(); return map.put(key, value).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).put(k6, v6).put(k7, v7).put(k8, v8).put(k9, v9).put(k10, v10).toMap(); }
	/*E*/
	private Map() {
		wrapped = new java.util.HashMap<>();
	}
	private Map(int initialCapacity, float loadFactor) {
		wrapped = new java.util.HashMap<>(initialCapacity, loadFactor);
	}
	private Map(int initialCapacity) {
		wrapped = new java.util.HashMap<>(initialCapacity);
	}
	private Map(Map<K, V> m) {
		wrapped = new java.util.HashMap<>(m.wrapped);
	}
	private Map(HashMap<K, V> m) {
		wrapped = new java.util.HashMap<>(m.wrapped);
	}
	private Map(java.util.Map<K, V> m) {
		wrapped = new java.util.HashMap<>(m);
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
	public @Override boolean contains(Pair<K, V> o) {
		return o != null && Objects.equals(get(o.lhs), o.rhs);
	}
	public @Override Iterator<Pair<K, V>> iterator() {
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
	public @Override Map<K, V> put(K key, V value) {
		Map<K, V> next = next();
		next.wrapped.put(key, value);
		return next;
	}
	public @Override Map<K, V> putAll(Map<K, V> m) {
		Map<K, V> next = next();
		next.wrapped.putAll(m.wrapped);
		return next;
	}
	public @Override Map<K, V> putAll(HashMap<K, V> m) {
		Map<K, V> next = next();
		next.wrapped.putAll(m.wrapped);
		return next;
	}
	public @Override Set<K> keySet() {
		return Set.from(wrapped.keySet());
	}
	public @Override List<V> values() {
		return List.from(wrapped.values());
	}
	public @Override Set<Pair<K, V>> entrySet() {
		Set<Entry<K, V>> from = Set.from(wrapped.entrySet());
		ExFunction<Entry<K, V>, Pair<K, V>, RuntimeException> mapper =
			entry -> new Pair<>(entry.getKey(), entry.getValue());
		Set<Pair<K, V>> map = from.map(mapper);
		return map;
	}
	public @Override V getOrDefault(K key, V defaultValue) {
		return wrapped.getOrDefault(key, defaultValue);
	}
	public @Override <E extends Exception> Map<K, V> each(ExBiConsumer<K, V, E> action) throws E {
		for(java.util.Map.Entry<K, V> entry : wrapped.entrySet()) {
			action.accept(entry.getKey(), entry.getValue());
		}
		return this;
	}
	public @Override <E extends Exception> Map<K, V> replaceAll(ExBiFunction<K, V, V, E> mapper) throws E {
		Class<E> classOfE = classForE();
		Map<K, V> next = next();
		try {
			next.wrapped.replaceAll(mapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return next;
	}
	public @Override Map<K, V> putIfAbsent(K key, V value) {
		Map<K, V> next = next();
		next.wrapped.putIfAbsent(key, value);
		return next;
	}
	public @Override Map<K, V> remove(K key) {
		Map<K, V> next = next();
		next.wrapped.remove(key);
		return next;
	}
	public @Override Map<K, V> remove(K key, V value) {
		Map<K, V> next = next();
		next.wrapped.remove(key, value);
		return next;
	}
	public @Override Map<K, V> replace(K key, V oldValue, V newValue) {
		Map<K, V> next = next();
		next.wrapped.replace(key, oldValue, newValue);
		return next;
	}
	public @Override Map<K, V> replace(K key, V value) {
		Map<K, V> next = next();
		next.wrapped.replace(key, value);
		return next;
	}
	public @Override Map<K, V> clear() {
		return of();
	}
	public @Override <E extends Exception> Map<K, V> computeIfAbsent(K key, ExFunction<K, V, E> mapper) throws E {
		Class<E> classOfE = classForE();
		try {
			next().wrapped.computeIfAbsent(key, mapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return next();
	}
	public @Override <E extends Exception> Map<K, V> computeIfPresent(K key, ExBiFunction<K, V, V, E> remapper)
		throws E {
		Class<E> classOfE = classForE();
		Map<K, V> next = next();
		try {
			next.wrapped.computeIfPresent(key, remapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return next;
	}
	public @Override <E extends Exception> Map<K, V> compute(K key, ExBiFunction<K, V, V, E> remapper) throws E {
		Class<E> classOfE = classForE();
		Map<K, V> next = next();
		try {
			next.wrapped.compute(key, remapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return next;
	}
	public @Override <E extends Exception> Map<K, V> merge(K key, V value, ExBiFunction<V, V, V, E> remapper) throws E {
		Class<E> classOfE = classForE();
		Map<K, V> next = next();
		try {
			next.wrapped.merge(key, value, remapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		}
		return next;
	}
	public @Override java.util.HashMap<K, V> toJavaMap() {
		return new java.util.HashMap<>(wrapped);
	}
	public @Override Map<K, V> toMap() {
		return new Map<>(this);
	}
	public @Override HashMap<K, V> toHashMap() {
		return new HashMap<>(this);
	}
	@SuppressWarnings("unchecked")
	private static <E extends Exception> Class<E> classForE() {
		return (Class<E>) Exception.class;
	}
	private Map<K, V> next() {
		return new Map<>(this);
	}
}
