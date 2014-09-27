package utils.lists;

import java.util.Iterator;
import java.util.Objects;
import utils.lists.HashMap.Entry;
import utils.streams.functions.ExBiConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExUnaryOperator;
import utils.streams2.WrapperException;

public class HashMap<K, V> implements ReadWriteMap<K, V, Entry<K, V>, HashMap<K, V>> {
	public static class IllegalMergeException extends IllegalStateException {
		private Object value1;
		private Object value2;
		Object key;

		public <T> IllegalMergeException(T value1, T value2) {
			this.value1 = value1;
			this.value2 = value2;
		}
		public @Override String getMessage() {
			if(key != null) {
				return String.format("Merging key %s and values %s & %s", key, value1, value2);
			}
			return String.format("Merging value %s & %s", value1, value2);
		}
	}
	public static class EntrySet<K, V> implements Collection<Entry<K, V>, EntrySet<K, V>> {
		private final java.util.Set<java.util.Map.Entry<K, V>> wrapped;
		private final HashMap<K, V> original;

		EntrySet(java.util.Set<java.util.Map.Entry<K, V>> wrapped, HashMap<K, V> original) {
			this.wrapped = wrapped;
			this.original = original;
		}
		public @Override int size() {
			return wrapped.size();
		}
		public @Override Iterator<Entry<K, V>> iterator() {
			return new Iterator<HashMap.Entry<K, V>>() {
				Iterator<java.util.Map.Entry<K, V>> iter = wrapped.iterator();

				public @Override boolean hasNext() {
					return iter.hasNext();
				}
				public @Override Entry<K, V> next() {
					return new Entry<>(iter.next());
				}
				public @Override void remove() {
					iter.remove();
				}
			};
		}
		public @Override EntrySet<K, V> add(Entry<K, V> item) {
			wrapped.add(item);
			return this;
		}
		public @Override EntrySet<K, V> remove(Entry<K, V> item) {
			wrapped.remove(item);
			return this;
		}
		public @Override <E extends Exception> EntrySet<K, V> replaceAll(ExUnaryOperator<Entry<K, V>, E> mapper)
			throws E {
			for(Entry<K, V> oldEntry : original.stream().toList()) {
				Entry<K, V> entry = mapper.apply(oldEntry);
				if(entry == null) {
					original.remove(oldEntry.lhs);
				} else if(Objects.equals(oldEntry, entry) == false) {
					if(Objects.equals(oldEntry.lhs, entry.lhs) == false) {
						original.remove(oldEntry.lhs, oldEntry.rhs);
					}
					original.put(entry.lhs, entry.rhs);
				}
			}
			return this;
		}
		public @Override EntrySet<K, V> identity() {
			return this;
		}
	}
	public static class Values<T> implements Collection<T, Values<T>> {
		private final java.util.Collection<T> wrapped;
		private final HashMap<?, T> original;

		Values(java.util.Collection<T> wrapped, HashMap<?, T> original) {
			this.wrapped = wrapped;
			this.original = original;
		}
		public @Override int size() {
			return wrapped.size();
		}
		public @Override boolean contains(T o) {
			return wrapped.contains(o);
		}
		public @Override Iterator<T> iterator() {
			return wrapped.iterator();
		}
		public @Override Values<T> add(T item) {
			wrapped.add(item);
			return this;
		}
		public @Override Values<T> remove(T item) {
			wrapped.remove(item);
			return this;
		}
		public @Override <E extends Exception> Values<T> replaceAll(ExUnaryOperator<T, E> mapper) throws E {
			for(java.util.Map.Entry<?, T> entry : original.wrapped.entrySet()) {
				entry.setValue(mapper.apply(entry.getValue()));
			}
			return this;
		}
		public @Override Values<T> identity() {
			return this;
		}
	}
	public static class Entry<K, V> extends Pair<K, V> implements java.util.Map.Entry<K, V> {
		private final java.util.Map.Entry<K, V> entry;

		Entry(java.util.Map.Entry<K, V> entry) {
			super(entry.getKey(), entry.getValue());
			this.entry = entry;
		}
		private Entry(java.util.Map.Entry<K, V> entry, K key) {
			super(key, entry.getValue());
			this.entry = entry;
		}
		public Pair<K, V> toPair() {
			return this;
		}
		public Entry<K, V> keepingValue(K newKey) {
			return new Entry<>(entry, newKey);
		}
		public @Override K getKey() {
			return lhs;
		}
		public @Override V getValue() {
			return entry.getValue();
		}
		public @Override V setValue(V value) {
			return entry.setValue(value);
		}
		public final @Override int hashCode() {
			return Objects.hashCode(entry.getKey()) ^ Objects.hashCode(entry.getValue());
		}
		public final @Override boolean equals(Object o) {
			if(o == this) {
				return true;
			}
			if(o instanceof java.util.Map.Entry) {
				java.util.Map.Entry<?, ?> e = (java.util.Map.Entry<?, ?>) o;
				if(Objects.equals(entry.getKey(), e.getKey()) && Objects.equals(entry.getValue(), e.getValue())) {
					return true;
				}
			}
			return false;
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
		if(o instanceof HashMap) {
			HashMap<?, ?> hashMap = (HashMap<?, ?>) o;
			return wrapped.equals(hashMap.wrapped);
		}
		if(o instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) o;
			return wrapped.equals(map.wrapped);
		}
		return wrapped.equals(o);
	}
	public @Override boolean contains(Entry<K, V> o) {
		return o != null && Objects.equals(get(o.lhs), o.rhs);
	}
	public @Override Iterator<Entry<K, V>> iterator() {
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
	public @Override HashMap<K, V> putAll(ReadOnlyMap<K, V> m) {
		if(m instanceof HashMap) {
			HashMap<K, V> map = (HashMap<K, V>) m;
			wrapped.putAll(map.wrapped);
		} else if(m instanceof Map) {
			Map<K, V> map = (Map<K, V>) m;
			wrapped.putAll(map.wrapped);
		} else {
			for(K key : m.keySet()) {
				wrapped.put(key, m.get(key));
			}
		}
		return this;
	}
	public @Override HashSet<K> keySet() {
		return HashSet.wrap(wrapped.keySet());
	}
	public @Override Values<V> values() {
		return new Values<>(wrapped.values(), this);
	}
	public @Override EntrySet<K, V> entrySet() {
		return new EntrySet<>(wrapped.entrySet(), this);
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
		if(value == null) {
			V newValue = remapper.apply(wrapped.get(key), value);
			if(newValue == null) {
				wrapped.remove(key);
			} else {
				wrapped.put(key, newValue);
			}
			return this;
		}
		Class<E> classOfE = classForE();
		try {
			wrapped.merge(key, value, remapper.uncheck(classOfE));
		} catch(WrapperException e) {
			throw WrapperException.show(e, classOfE);
		} catch(IllegalMergeException e) {
			e.key = key;
			throw e;
		}
		return this;
	}
	public @Override Map<K, V> toMap() {
		return Map.fromHashMap(this);
	}
	public @Override HashMap<K, V> toHashMap() {
		return new HashMap<>(this);
	}
	public @Override <U, E extends Exception> ArrayList<U> map(ExFunction<HashMap.Entry<K, V>, U, E> mapper) throws E {
		return toArrayList().map(mapper);
	}
	@SuppressWarnings("unchecked")
	private static <E extends Exception> Class<E> classForE() {
		return (Class<E>) Exception.class;
	}
}
