package utils.lists2;

import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.SortedMap;
import java.util.SortedSet;

public class Collections {//*Q*
	public static <T extends Comparable<? super T>>					void								sort(								java.util.List<T>							list		)                                                                                                       {       java.util.Collections.sort(								list		)	                 	                 ;}
	public static <T>												void								sort(								java.util.List<T>							list		, Comparator<? super T>			c				)                                                 		{       java.util.Collections.sort(								list		,	c				)	                 ;}
	public static <T>												java.util.Collection<T>				unmodifiableCollection(				java.util.Collection<? extends T>			c			)                                                                                                       {return java.util.Collections.unmodifiableCollection(			c			)	                 	                 ;}
	public static <T>												java.util.Set<T>					unmodifiableSet(					java.util.Set<? extends T>					s			)                                                                                                       {return java.util.Collections.unmodifiableSet(					s			)	                 	                 ;}
	public static <T>												SortedSet<T>						unmodifiableSortedSet(				SortedSet<T>								s			)                                                                                                       {return java.util.Collections.unmodifiableSortedSet(			s			)	                 	                 ;}
	public static <T>												NavigableSet<T>						unmodifiableNavigableSet(			NavigableSet<T>								s			)                                                                                                       {return java.util.Collections.unmodifiableNavigableSet(			s			)	                 	                 ;}
	public static <T>												java.util.List<T>					unmodifiableList(					java.util.List<? extends T>					list		)                                                                                                       {return java.util.Collections.unmodifiableList(					list		)	                 	                 ;}
	public static <K,V>												java.util.Map<K,V>					unmodifiableMap(					java.util.Map<? extends K, ? extends V>		m			)                                                                                                       {return java.util.Collections.unmodifiableMap(					m			)	                 	                 ;}
	public static <K,V>												SortedMap<K,V>						unmodifiableSortedMap(				SortedMap<K, ? extends V>					m			)                                                                                                       {return java.util.Collections.unmodifiableSortedMap(			m			)	                 	                 ;}
	public static <K,V>												NavigableMap<K,V>					unmodifiableNavigableMap(			NavigableMap<K, ? extends V>				m			)                                                                                                       {return java.util.Collections.unmodifiableNavigableMap(			m			)	                 	                 ;}
	public static <T>												java.util.Collection<T>				synchronizedCollection(				java.util.Collection<T>						c			)                                                                                                       {return java.util.Collections.synchronizedCollection(			c			)	                 	                 ;}
	public static <T>												java.util.Set<T>					synchronizedSet(					java.util.Set<T>							s			)                                                                                                       {return java.util.Collections.synchronizedSet(					s			)	                 	                 ;}
	public static <T>												SortedSet<T>						synchronizedSortedSet(				SortedSet<T>								s			)                                                                                                       {return java.util.Collections.synchronizedSortedSet(			s			)	                 	                 ;}
	public static <T>												NavigableSet<T>						synchronizedNavigableSet(			NavigableSet<T>								s			)                                                                                                       {return java.util.Collections.synchronizedNavigableSet(			s			)	                 	                 ;}
	public static <T>												java.util.List<T>					synchronizedList(					java.util.List<T>							list		)                                                                                                       {return java.util.Collections.synchronizedList(					list		)	                 	                 ;}
	public static <K,V>												java.util.Map<K,V>					synchronizedMap(					java.util.Map<K,V>							m			)                                                                                                       {return java.util.Collections.synchronizedMap(					m			)	                 	                 ;}
	public static <K,V>												SortedMap<K,V>						synchronizedSortedMap(				SortedMap<K,V>								m			)                                                                                                       {return java.util.Collections.synchronizedSortedMap(			m			)	                 	                 ;}
	public static <K,V>												NavigableMap<K,V>					synchronizedNavigableMap(			NavigableMap<K,V>							m			)                                                                                                       {return java.util.Collections.synchronizedNavigableMap(			m			)	                 	                 ;}
	public static <E>												java.util.Collection<E>				checkedCollection(					java.util.Collection<E>						c			, Class<E>						type			)                                                       {return java.util.Collections.checkedCollection(				c			,	type			)	                 ;}
	public static <E>												Queue<E>							checkedQueue(						Queue<E>									queue		, Class<E>						type			)                                                       {return java.util.Collections.checkedQueue(						queue		,	type			)	                 ;}
	public static <E>												java.util.Set<E>					checkedSet(							java.util.Set<E>							s			, Class<E>						type			)                                                       {return java.util.Collections.checkedSet(						s			,	type			)	                 ;}
	public static <E>												SortedSet<E>						checkedSortedSet(					SortedSet<E>								s			, Class<E>						type			)                                                       {return java.util.Collections.checkedSortedSet(					s			,	type			)	                 ;}
	public static <E>												NavigableSet<E>						checkedNavigableSet(				NavigableSet<E>								s			, Class<E>						type			)                                                       {return java.util.Collections.checkedNavigableSet(				s			,	type			)	                 ;}
	public static <E>												java.util.List<E>					checkedList(						java.util.List<E>							list		, Class<E>						type			)                                                       {return java.util.Collections.checkedList(						list		,	type			)	                 ;}
	public static <K,V>												java.util.Map<K,V>					checkedMap(							java.util.Map<K,V>							m			, Class<K>						keyType			, Class<V>						valueType		)		{return java.util.Collections.checkedMap(						m			,	keyType			,	valueType		);}
	public static <K,V>												SortedMap<K,V>						checkedSortedMap(					SortedMap<K,V>								m			, Class<K>						keyType			, Class<V>						valueType		)		{return java.util.Collections.checkedSortedMap(					m			,	keyType			,	valueType		);}
	public static <K,V>												NavigableMap<K,V>					checkedNavigableMap(				NavigableMap<K,V>							m			, Class<K>						keyType			, Class<V>						valueType		)		{return java.util.Collections.checkedNavigableMap(				m			,	keyType			,	valueType		);}
	public static <T>												Iterator<T>							emptyIterator(																				)                                                                                                       {return java.util.Collections.emptyIterator(								)	                 	                 ;}
	public static <T>												ListIterator<T>						emptyListIterator(																			)                                                                                                       {return java.util.Collections.emptyListIterator(							)	                 	                 ;}
	public static <T>												Enumeration<T>						emptyEnumeration(																			)                                                                                                       {return java.util.Collections.emptyEnumeration(								)	                 	                 ;}
	public static <E>												SortedSet<E>						emptySortedSet(																				)                                                                                                       {return java.util.Collections.emptySortedSet(								)	                 	                 ;}
	public static <E>												NavigableSet<E>						emptyNavigableSet(																			)                                                                                                       {return java.util.Collections.emptyNavigableSet(							)	                 	                 ;}
	public static <K,V>												SortedMap<K,V>						emptySortedMap(																				)                                                                                                       {return java.util.Collections.emptySortedMap(								)	                 	                 ;}
	public static <K,V>												NavigableMap<K,V>					emptyNavigableMap(																			)                                                                                                       {return java.util.Collections.emptyNavigableMap(							)	                 	                 ;}
	public static <T>												Comparator<T>						reverseOrder(																				)                                                                                                       {return java.util.Collections.reverseOrder(									)	                 	                 ;}
	public static <T>												Comparator<T>						reverseOrder(						Comparator<T>								cmp			)                                                                                                       {return java.util.Collections.reverseOrder(						cmp			)	                 	                 ;}
	public static <T>												Enumeration<T>						enumeration(						java.util.Collection<T>						c			)                                                                                                       {return java.util.Collections.enumeration(						c			)	                 	                 ;}
	public static <T>												java.util.ArrayList<T>				list(								Enumeration<T>								e			)                                                                                                       {return java.util.Collections.list(								e			)	                 	                 ;}
	public static 													int									frequency(							java.util.Collection<?>						c			, Object						o				)                                                       {return java.util.Collections.frequency(						c			,	o				)	                 ;}
	public static 													boolean								disjoint(							java.util.Collection<?>						c1			, java.util.Collection<?>		c2				)                                                       {return java.util.Collections.disjoint(							c1			,	c2				)	                 ;}
	public @SafeVarargs static <T>									boolean								addAll(								java.util.Collection<? super T>				c			, T...							elements		)                                                       {return java.util.Collections.addAll(							c			,	elements		)	                 ;}
	public static <E>												java.util.Set<E>					newSetFromMap(						java.util.Map<E, Boolean>					map			)                                                                                                       {return java.util.Collections.newSetFromMap(					map			)	                 	                 ;}
	public static <T>												Queue<T>							asLifoQueue(						Deque<T>									deque		)                                                                                                       {return java.util.Collections.asLifoQueue(						deque		)	                 	                 ;}
//*E*
	public static <T extends Comparable<T>> ArrayList<T> sort(ArrayList<T> list) {
		return list.sort((l, r) -> l.compareTo(r));
	}
	public static <T> ArrayList<T> sort(ArrayList<T> list, Comparator<T> c) {
		return list.sort(c);
	}
	public static <T extends Comparable<T>> List<T> sort(List<T> list) {
		return list.sort((l, r) -> l.compareTo(r));
	}
	public static <T> List<T> sort(List<T> list, Comparator<T> c) {
		return list.sort(c);
	}
	public static <T> List<T> reverse(List<T> list) {
		return list.toArrayList().reverse().toList();
	}
	public static <T> ArrayList<T> reverse(ArrayList<T> list) {
		return list.reverse();
	}
	public static <T> ArrayList<T> shuffle(ArrayList<T> list) {
		java.util.ArrayList<T> javaList = list.toJavaList();
		java.util.Collections.shuffle(javaList);
		return new ArrayList<>(javaList);
	}
	public static <T> List<T> shuffle(List<T> list) {
		java.util.ArrayList<T> javaList = list.toJavaList();
		java.util.Collections.shuffle(javaList);
		return List.from(javaList);
	}
	public static <T> ArrayList<T> shuffle(ArrayList<T> list, Random rnd) {
		java.util.ArrayList<T> javaList = list.toJavaList();
		java.util.Collections.shuffle(javaList, rnd);
		return new ArrayList<>(javaList);
	}
	public static <T> List<T> shuffle(List<T> list, Random rnd) {
		java.util.ArrayList<T> javaList = list.toJavaList();
		java.util.Collections.shuffle(javaList, rnd);
		return List.from(javaList);
	}
	public static <T> List<T> swap(List<T> list, int i, int j) {
		return swap(list.toArrayList(), i, j).toList();
	}
	public static <T> ArrayList<T> swap(ArrayList<T> list, int i, int j) {
		T i2 = list.get(i);
		T j2 = list.get(j);
		return list.set(i, j2).set(j, i2);
	}
	public static <T> List<T> fill(List<T> list, T obj) {
		return nCopies(list.size(), obj).toList();
	}
	public static <T> ArrayList<T> fill(ArrayList<T> list, T obj) {
		int size = list.size();
		list.clear();
		for(int i = 0; i < size; i++) {
			list.add(obj);
		}
		return list;
	}
	public static <T> ArrayList<T> copy(ArrayList<T> dest, ArrayList<T> src) {
		if(dest.getClass() == ArrayList.class && ArrayList.class == src.getClass()) {
			if(dest.size < src.size) {
				throw new IndexOutOfBoundsException("Destination too small: " + dest.size + " < " + src.size);
			}
			System.arraycopy(src.items, 0, dest.items, 0, src.size);
			return dest;
		}
		if(dest.size() < src.size()) {
			throw new IndexOutOfBoundsException("Destination too small: " + dest.size() + " < " + src.size());
		}
		ListIterator<T> srcIterator = src.listIterator();
		ListIterator<T> destIterator = dest.listIterator();
		for(int i = 0, n = src.size(); i < n; i++) {
			destIterator.next();
			destIterator.set(srcIterator.next());
		}
		return dest;
	}
	public static <T> List<T> rotate(List<T> list, int distance) {
		java.util.ArrayList<T> list2 = list.toJavaList();
		java.util.Collections.rotate(list2, distance);
		return List.from(list2);
	}
	public static <T> ArrayList<T> rotate(ArrayList<T> list, int distance) {
		java.util.ArrayList<T> list2 = list.toJavaList();
		java.util.Collections.rotate(list2, distance);
		return list.clear().addAll(ArrayList.from(list2));
	}
	public static <T extends Comparable<T>, A extends RandomAccess<T, A>> int binarySearch(
		RandomAccess<T, A> list,
		T key) {
		return java.util.Collections.binarySearch(list.toJavaList(), key);
	}
	public static <T, A extends RandomAccess<T, A>> int binarySearch(RandomAccess<T, A> list, T key, Comparator<T> c) {
		return java.util.Collections.binarySearch(list.toJavaList(), key, c);
	}
	public static <T extends Comparable<T>> T min(ReadOnly<T> coll) {
		return coll.stream().min(Comparator.naturalOrder()).orElseThrow(NoSuchElementException::new);
	}
	public static <T> T min(ReadOnly<T> coll, Comparator<T> comp) {
		return coll.stream().min(comp).orElseThrow(NoSuchElementException::new);
	}
	public static <T extends Comparable<T>> T max(ReadOnly<T> coll) {
		return coll.stream().max(Comparator.naturalOrder()).orElseThrow(NoSuchElementException::new);
	}
	public static <T> T max(ReadOnly<T> coll, Comparator<T> comp) {
		return coll.stream().max(comp).orElseThrow(NoSuchElementException::new);
	}
	public static <T> List<T> replaceAll(List<T> list, T oldVal, T newVal) {
		return list.replaceAll(old -> Objects.equals(old, oldVal) ? newVal : oldVal);
	}
	public static <T> ArrayList<T> replaceAll(ArrayList<T> list, T oldVal, T newVal) {
		return list.replaceAll(old -> Objects.equals(old, oldVal) ? newVal : oldVal);
	}
	public static <T, A extends RandomAccess<T, A>> int indexOfSubList(
		RandomAccess<T, A> source,
		RandomAccess<T, A> target) {
		return java.util.Collections.indexOfSubList(source.toJavaList(), target.toJavaList());
	}
	public static <T, A extends RandomAccess<T, A>> int lastIndexOfSubList(
		RandomAccess<T, A> source,
		RandomAccess<T, A> target) {
		return java.util.Collections.indexOfSubList(source.toJavaList(), target.toJavaList());
	}
	public static <T> Set<T> emptySet() {
		return Set.of();
	}
	public static <T> List<T> emptyList() {
		return List.of();
	}
	public static <K, V> Map<K, V> emptyMap() {
		return Map.of();
	}
	public static <T> Set<T> singleton(T o) {
		return Set.of(o);
	}
	public static <T> List<T> singletonList(T o) {
		return List.of(o);
	}
	public static <K, V> Map<K, V> singletonMap(K key, V value) {
		return Map.of(key, value);
	}
	public static <T> ArrayList<T> nCopies(int n, T o) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[n];
		Arrays.fill(array, o);
		return ArrayList.of(array);
	}
	public static <T> int frequency(ReadOnly<T> c, T o) {
		int result = 0;
		if(o == null) {
			for(T t : c) {
				if(t == null) {
					result++;
				}
			}
		} else {
			for(T t : c) {
				if(o.equals(t)) {
					result++;
				}
			}
		}
		return result;
	}
	public static <T, L extends RandomLookup<T, L>> boolean disjoint(RandomLookup<T, L> c1, ReadOnly<T> c2) {
		for(T t : c2) {
			if(c1.contains(t)) {
				return false;
			}
		}
		return true;
	}
	public static <T, A extends RandomAccess<T, A>> boolean disjoint(RandomAccess<T, A> c1, ReadOnly<T> c2) {
		for(T t : c2) {
			if(c1.contains(t)) {
				return false;
			}
		}
		return true;
	}
	public @SafeVarargs static <T> List<T> addAll(List<T> c, T... elements) {
		return c.addAll(elements);
	}
	public @SafeVarargs static <T> ArrayList<T> addAll(ArrayList<T> c, T... elements) {
		return c.addAll(elements);
	}
	public @SafeVarargs static <T> Set<T> addAll(Set<T> c, T... elements) {
		return c.toArrayList().addAll(elements).toSet();
	}
	public @SafeVarargs static <T> HashSet<T> addAll(HashSet<T> c, T... elements) {
		for(T t : elements) {
			c.add(t);
		}
		return c;
	}
}