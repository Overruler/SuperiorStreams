package utils.streams2;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.*;
import utils.lists.Pair;
import utils.lists2.ArrayList;
import utils.lists2.Collection;
import utils.lists2.HashMap;
import utils.lists2.HashSet;
import utils.lists2.Map;
import utils.streams.functions.BiConsumer;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Function;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;

public final class Collectors {
	private Collectors() {}
	public static <T, C extends Collection<T, C>> Collector<T, C, C> toCollection(Supplier<C> collectionFactory) {
		BiConsumer<C, T> accumulator = Collection<T, C>::add;
		BinaryOperator<C> combiner = (r1, r2) -> r1.addAll(r2);
		return Collector.of(collectionFactory, accumulator, combiner, IDENTITY_FINISH);
	}
	public static <T> Collector<T, ArrayList<T>, ArrayList<T>> toList() {
		Supplier<ArrayList<T>> supplier = ArrayList<T>::new;
		BiConsumer<ArrayList<T>, T> accumulator = ArrayList<T>::add;
		BinaryOperator<ArrayList<T>> combiner = (r1, r2) -> r1.addAll(r2);
		return Collector.of(supplier, accumulator, combiner, IDENTITY_FINISH);
	}
	public static <T> Collector<T, HashSet<T>, HashSet<T>> toSet() {
		Supplier<HashSet<T>> supplier = HashSet<T>::new;
		BiConsumer<HashSet<T>, T> accumulator = HashSet<T>::add;
		BinaryOperator<HashSet<T>> combiner = (r1, r2) -> r1.addAll(r2);
		return Collector.of(supplier, accumulator, combiner, IDENTITY_FINISH);
	}
	public static Collector<CharSequence, ?, String> joining() {
		return java.util.stream.Collectors.joining();
	}
	public static Collector<CharSequence, ?, String> joining(CharSequence delimiter) {
		return java.util.stream.Collectors.joining(delimiter);
	}
	public static Collector<CharSequence, ?, String> joining(
		CharSequence delimiter,
		CharSequence prefix,
		CharSequence suffix) {
		return java.util.stream.Collectors.joining(delimiter, prefix, suffix);
	}
	public static <T, U, A, R> Collector<T, ?, R> mapping(
		Function<? super T, ? extends U> mapper,
		Collector<? super U, A, R> downstream) {
		return java.util.stream.Collectors.mapping(mapper, downstream);
	}
	public static <T, A, R, RR> Collector<T, A, RR> collectingAndThen(
		Collector<T, A, R> downstream,
		Function<R, RR> finisher) {
		return java.util.stream.Collectors.collectingAndThen(downstream, finisher);
	}
	public static <T> Collector<T, ?, Long> counting() {
		return java.util.stream.Collectors.counting();
	}
	public static <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator) {
		return java.util.stream.Collectors.minBy(comparator);
	}
	public static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator) {
		return java.util.stream.Collectors.maxBy(comparator);
	}
	public static <T> Collector<T, ?, Integer> summingInt(ToIntFunction<? super T> mapper) {
		return java.util.stream.Collectors.summingInt(mapper);
	}
	public static <T> Collector<T, ?, Long> summingLong(ToLongFunction<? super T> mapper) {
		return java.util.stream.Collectors.summingLong(mapper);
	}
	public static <T> Collector<T, ?, Double> summingDouble(ToDoubleFunction<? super T> mapper) {
		return java.util.stream.Collectors.summingDouble(mapper);
	}
	public static <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> mapper) {
		return java.util.stream.Collectors.averagingInt(mapper);
	}
	public static <T> Collector<T, ?, Double> averagingLong(ToLongFunction<? super T> mapper) {
		return java.util.stream.Collectors.averagingLong(mapper);
	}
	public static <T> Collector<T, ?, Double> averagingDouble(ToDoubleFunction<? super T> mapper) {
		return java.util.stream.Collectors.averagingDouble(mapper);
	}
	public static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op) {
		return java.util.stream.Collectors.reducing(identity, op);
	}
	public static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op) {
		return java.util.stream.Collectors.reducing(op);
	}
	public static <T, U> Collector<T, ?, U> reducing(
		U identity,
		Function<? super T, ? extends U> mapper,
		BinaryOperator<U> op) {
		return java.util.stream.Collectors.reducing(identity, mapper, op);
	}
	public static <T, K> Collector<T, HashMap<K, ArrayList<T>>, HashMap<K, ArrayList<T>>> groupingBy(
		Function<? super T, ? extends K> classifier) {
		Collector<T, ArrayList<T>, ArrayList<T>> list = toList();
		return groupingBy(classifier, list);
	}
	public static <T, K, A, D> Collector<T, HashMap<K, A>, HashMap<K, D>> groupingBy(
		Function<? super T, ? extends K> classifier,
		Collector<? super T, A, D> downstream) {
		Supplier<HashMap<K, D>> mapFactory = HashMap::new;
		return groupingBy(classifier, mapFactory, downstream);
	}
	public static <T, K, D, A, M extends HashMap<K, D>> Collector<T, HashMap<K, A>, M> groupingBy(
		Function<? super T, ? extends K> classifier,
		Supplier<M> mapFactory,
		Collector<? super T, A, D> downstream) {
		java.util.function.Supplier<A> downstreamSupplier = downstream.supplier();
		java.util.function.BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
		java.util.function.BiConsumer<HashMap<K, A>, T> accumulator = (m, t) -> {
			K key = classifier.apply(t);
			A container;
			if(m.containsKey(key)) {
				container = m.get(key);
			} else {
				container = downstreamSupplier.get();
				m.put(key, container);
			}
			downstreamAccumulator.accept(container, t);
		};
		java.util.function.BinaryOperator<A> combiner = downstream.combiner();
		java.util.function.BinaryOperator<HashMap<K, A>> merger = (m1, m2) -> {
			for(HashMap.Entry<K, A> e : m2.entrySet()) {
				m1.merge(e.getKey(), e.getValue(), combiner::apply);
			}
			return m1;
		};
		@SuppressWarnings("unchecked")
		java.util.function.Supplier<HashMap<K, A>> mangledFactory =
		(java.util.function.Supplier<HashMap<K, A>>) mapFactory;
		if(downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
			@SuppressWarnings("unchecked")
			java.util.function.Function<HashMap<K, A>, M> castingIdentity = i -> (M) i;
			Collector<T, HashMap<K, A>, M> collectorImpl2 =
			Collector.of(
				mangledFactory,
				accumulator,
				merger,
				castingIdentity,
				Collector.Characteristics.IDENTITY_FINISH);
			return collectorImpl2;
		}
		@SuppressWarnings("unchecked")
		java.util.function.Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
		java.util.function.Function<HashMap<K, A>, M> finisher = intermediate -> {
			intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
			@SuppressWarnings("unchecked")
			M castResult = (M) intermediate;
			return castResult;
		};
		Collector<T, HashMap<K, A>, M> collectorImpl3 = Collector.of(mangledFactory, accumulator, merger, finisher);
		return collectorImpl3;
	}
	public static <T, K> Collector<T, ?, ConcurrentMap<K, ArrayList<T>>> groupingByConcurrent(
		Function<? super T, ? extends K> classifier) {
		Function<? super T, ? extends K> classifier2 = classifier;
		Supplier<ConcurrentMap<K, ArrayList<T>>> mapFactory = ConcurrentHashMap::new;
		Collector<T, ArrayList<T>, ArrayList<T>> list = toList();
		return groupingByConcurrentInternal(classifier2, mapFactory, list);
	}
	public static <T, K, D> Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(
		Function<? super T, ? extends K> classifier,
		Collector<? super T, D, D> downstream) {
		Function<? super T, ? extends K> classifier2 = classifier;
		Collector<? super T, D, D> downstream2 = downstream;
		Supplier<ConcurrentMap<K, D>> mapFactory = ConcurrentHashMap::new;
		return groupingByConcurrentInternal(classifier2, mapFactory, downstream2);
	}
	public static <T, K, D> Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(
		Function<? super T, ? extends K> classifier,
		Supplier<ConcurrentMap<K, D>> mapFactory,
		Collector<? super T, D, D> downstream) {
		return groupingByConcurrentInternal(classifier, mapFactory, downstream);
	}
	private static <T, D, K> Collector<T, ConcurrentMap<K, D>, ConcurrentMap<K, D>> groupingByConcurrentInternal(
		Function<? super T, ? extends K> classifier,
		Supplier<ConcurrentMap<K, D>> mapFactory,
		Collector<? super T, D, D> downstream) {
		java.util.function.Supplier<D> downstreamSupplier = downstream.supplier();
		java.util.function.BiConsumer<D, ? super T> downstreamAccumulator = downstream.accumulator();
		java.util.function.BinaryOperator<D> combiner = downstream.combiner();
		BinaryOperator<ConcurrentMap<K, D>> merger = (m1, m2) -> {
			for(java.util.Map.Entry<K, D> e : m2.entrySet()) {
				m1.merge(e.getKey(), e.getValue(), combiner);
			}
			return m1;
		};
		BiConsumer<ConcurrentMap<K, D>, T> accumulator;
		if(downstream.characteristics().contains(Collector.Characteristics.CONCURRENT)) {
			accumulator =
			(m, t) -> downstreamAccumulator.accept(
				m.computeIfAbsent(classifier.apply(t), k -> downstreamSupplier.get()),
				t);
		} else {
			accumulator = (m, t) -> {
				D resultContainer = m.computeIfAbsent(classifier.apply(t), k -> downstreamSupplier.get());
				synchronized(resultContainer) {
					downstreamAccumulator.accept(resultContainer, t);
				}
			};
		}
		if(downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
			return Collector.of(
				mapFactory,
				accumulator,
				merger,
				Collector.Characteristics.CONCURRENT,
				Collector.Characteristics.UNORDERED,
				Collector.Characteristics.IDENTITY_FINISH);
		}
		java.util.function.Function<D, D> downstreamFinisher = downstream.finisher();
		Function<ConcurrentMap<K, D>, ConcurrentMap<K, D>> finisher = intermediate -> {
			intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
			return intermediate;
		};
		return Collector.of(
			mapFactory,
			accumulator,
			merger,
			finisher,
			Collector.Characteristics.CONCURRENT,
			Collector.Characteristics.UNORDERED);
	}
	public static <T> Collector<T, ?, Map<Boolean, ArrayList<T>>> partitioningBy(Predicate<? super T> predicate) {
		return partitioningBy(predicate, toList());
	}
	public static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(
		Predicate<? super T> predicate,
		Collector<? super T, A, D> downstream) {
		return partitioningByInternal(predicate, downstream);
	}
	private static <D, A, T> Collector<T, Pair<A, A>, Map<Boolean, D>> partitioningByInternal(
		Predicate<? super T> predicate,
		Collector<? super T, A, D> downstream) {
		java.util.function.BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
		java.util.function.BinaryOperator<A> op = downstream.combiner();
		BiConsumer<Pair<A, A>, T> accumulator =
		(result, t) -> downstreamAccumulator.accept(predicate.test(t) ? result.lhs : result.rhs, t);
		BinaryOperator<Pair<A, A>> merger =
		(left, right) -> new Pair<>(op.apply(left.lhs, right.lhs), op.apply(left.rhs, right.rhs));
		Supplier<Pair<A, A>> supplier = () -> new Pair<>(downstream.supplier().get(), downstream.supplier().get());
		Function<Pair<A, A>, Map<Boolean, D>> finisher =
		pair -> Map.of(true, downstream.finisher().apply(pair.lhs), false, downstream.finisher().apply(pair.rhs));
		return Collector.of(supplier, accumulator, merger, finisher);
	}
	public static <T, K, U> Collector<T, HashMap<K, U>, HashMap<K, U>> toMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends U> valueMapper) {
		BinaryOperator<U> throwingMerger = (u, v) -> {
			throw new IllegalStateException(String.format("Duplicate %s & %s", u, v));
		};
		return toMap(keyMapper, valueMapper, throwingMerger, HashMap::new);
	}
	public static <T, K, U> Collector<T, HashMap<K, U>, HashMap<K, U>> toMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends U> valueMapper,
		BinaryOperator<U> mergeFunction) {
		return toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
	}
	public static <T, K, U, M extends HashMap<K, U>> Collector<T, M, M> toMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends U> valueMapper,
		BinaryOperator<U> mergeFunction,
		Supplier<M> mapSupplier) {
		BiConsumer<M, T> accumulator =
		(map, element) -> map.merge(keyMapper.apply(element), valueMapper.apply(element), mergeFunction);
		BinaryOperator<M> mapMerger = (m1, m2) -> {
			for(HashMap.Entry<K, U> e : m2.entrySet()) {
				m1.merge(e.getKey(), e.getValue(), mergeFunction);
			}
			return m1;
		};
		return Collector.of(mapSupplier, accumulator, mapMerger, IDENTITY_FINISH);
	}
	public static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends U> valueMapper) {
		return java.util.stream.Collectors.toConcurrentMap(keyMapper, valueMapper);
	}
	public static <T, K, U> Collector<T, ?, ConcurrentMap<K, U>> toConcurrentMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends U> valueMapper,
		BinaryOperator<U> mergeFunction) {
		return java.util.stream.Collectors.toConcurrentMap(keyMapper, valueMapper, mergeFunction);
	}
	public static <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends U> valueMapper,
		BinaryOperator<U> mergeFunction,
		Supplier<M> mapSupplier) {
		return java.util.stream.Collectors.toConcurrentMap(keyMapper, valueMapper, mergeFunction, mapSupplier);
	}
	public static <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper) {
		return java.util.stream.Collectors.summarizingInt(mapper);
	}
	public static <T> Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> mapper) {
		return java.util.stream.Collectors.summarizingLong(mapper);
	}
	public static <T> Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> mapper) {
		return java.util.stream.Collectors.summarizingDouble(mapper);
	}
}
