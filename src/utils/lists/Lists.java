package utils.lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;

public class Lists {

  public static interface MapBuilder<K, V> {

    HashMap<K, V> toMap();
    default MapBuilder<K, V> add(K key, V value) {
      toMap().put(key, value);
      return this;
    }
  }

  public @SafeVarargs static <V> ArrayList<V> of(V... values) {
    return new ArrayList<>(Arrays.asList(values));
  }
  public static <K, V> MapBuilder<K, V> buildMap(K key, V value) {
    HashMap<K, V> map = new HashMap<>();
    MapBuilder<K, V> mapBuilder = () -> map;
    return mapBuilder.add(key, value);
  }
  public static <K, V> HashMap<K, V> toMap(K key, V value) {
    HashMap<K, V> map = new HashMap<>();
    map.put(key, value);
    return map;
  }
  /*Q*/
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2) {                                                                                                   HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2);                                                                                                                                           return map; }
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3) {                                                                                       HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3);                                                                                                                          return map; } 
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4) {                                                                           HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3); map.put(k4, v4);                                                                                                         return map; } 
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {                                                               HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3); map.put(k4, v4); map.put(k5, v5);                                                                                        return map; } 
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {                                                   HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3); map.put(k4, v4); map.put(k5, v5); map.put(k6, v6);                                                                       return map; } 
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {                                       HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3); map.put(k4, v4); map.put(k5, v5); map.put(k6, v6); map.put(k7, v7);                                                      return map; } 
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {                           HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3); map.put(k4, v4); map.put(k5, v5); map.put(k6, v6); map.put(k7, v7); map.put(k8, v8);                                     return map; } 
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {               HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3); map.put(k4, v4); map.put(k5, v5); map.put(k6, v6); map.put(k7, v7); map.put(k8, v8); map.put(k9, v9);                    return map; } 
	public static <K, V> HashMap<K, V> toMap(K key, V value, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) { HashMap<K, V> map = new HashMap<>(); map.put(key, value); map.put(k2, v2); map.put(k3, v3); map.put(k4, v4); map.put(k5, v5); map.put(k6, v6); map.put(k7, v7); map.put(k8, v8); map.put(k9, v9); map.put(k10, v10); return map; }
	/*E*/
  public static <A, B> Map<B, List<A>> invert(Map<A, List<B>> dag) {
    return dag.entrySet().stream().<Pair<List<B>, A>> map(e -> new Pair<>(e.getValue(), e.getKey())).<Pair<B, A>> flatMap(
      p -> p.lhs.stream().map(p::keepingRhs)).collect(groupingBy(Pair<B, A>::lhs, mapping(Pair<B, A>::rhs, toList())));
  }
  public @SafeVarargs static <T> ArrayList<T> add(Collection<T> original, T... added) {
    return add(original, Arrays.asList(added));
  }
  public static <T> ArrayList<T> add(Collection<T> original, Collection<T> added) {
    ArrayList<T> list = new ArrayList<>(original);
    list.addAll(added);
    return list;
  }
  public static <K, V> HashMap<K, V> add(Map<K, V> original, Map<K, V> added) {
    HashMap<K, V> map = new HashMap<>(original);
    map.putAll(added);
    return map;
  }
  public @SafeVarargs static <T> ArrayList<T> minus(Collection<T> original, T... removed) {
    return minus(original, Arrays.asList(removed));
  }
  public static <T> ArrayList<T> minus(Collection<T> original, Collection<T> removed) {
    ArrayList<T> list = new ArrayList<>(original);
    list.removeAll(removed);
    return list;
  }
  public @SafeVarargs static <K, V> HashMap<K, V> minus(Map<K, V> original, K... removed) {
    return minus(original, Arrays.asList(removed));
  }
  public static <K, V> HashMap<K, V> minus(Map<K, V> original, Collection<K> removed) {
    HashMap<K, V> map = new HashMap<>(original);
    map.keySet().removeAll(removed);
    return map;
  }
  public static <T> ArrayList<T> intersect(List<T> source, Collection<T> other) {
    ArrayList<T> list = new ArrayList<>(source);
    list.retainAll(other);
    return list;
  }
  public static <K, V> HashMap<K, V> intersect(Map<K, V> source, Collection<K> other) {
    HashMap<K, V> map = new HashMap<>(source);
    map.keySet().retainAll(other);
    return map;
  }
  public static <T> HashSet<T> union(Set<T> source, Collection<T> other) {
    HashSet<T> set = new HashSet<>(source);
    set.addAll(other);
    return set;
  }
  public static <T extends Comparable<T>> ArrayList<T> sorted(Collection<T> source) {
    ArrayList<T> list = new ArrayList<>(source);
    Collections.sort(list);
    return list;
  }
  public @SafeVarargs static <T,V> ArrayList<V> select(Function<?super T,?extends V> mapper, T... original) {
    return select(mapper, Arrays.asList(original));
  }
  public static <T,V> ArrayList<V> select(Function<?super T,?extends V> mapper, Collection<T> original) {
    return original.stream().map(mapper).collect(Collectors.toCollection(ArrayList::new));
  }
}
