package utils.streams2;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import utils.lists.ArrayList;
import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;
import utils.streams.functions.UnaryOperator;

//*Q*
public class Stream<T> extends AbstractStream<T, RuntimeException,
java.util.stream.Stream<T>,
Stream<T>,
IntStream,
LongStream,
DoubleStream,
Consumer<? super T>,
Predicate<? super T>,
BinaryOperator<T>,
Comparator<? super T>,
Function<? super T, ? extends java.util.stream.IntStream>,
Function<? super T, ? extends java.util.stream.LongStream>,
Function<? super T, ? extends java.util.stream.DoubleStream>,
ToIntFunction<? super T>,
ToLongFunction<? super T>,
ToDoubleFunction<? super T>> {//*E*
	public Stream(Supplier<java.util.stream.Stream<T>> supplier) {
		super(supplier);
	}
	<OLD> Stream(Supplier<OLD> older, Function<OLD, java.util.stream.Stream<T>> converter) {
		super(older, converter);
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override java.util.stream.Stream<T> castToStream(java.util.stream.Stream<T> stream) {
		return stream;
	}
	protected @Override Stream<T> asSELF(Function<java.util.stream.Stream<T>, java.util.stream.Stream<T>> func) {
		return new Stream<>(supplier, func);
	}
	protected @Override IntStream asIS(Function<java.util.stream.Stream<T>, java.util.stream.IntStream> func) {
		return new IntStream(supplier, func);
	}
	protected @Override LongStream asLS(Function<java.util.stream.Stream<T>, java.util.stream.LongStream> func) {
		return new LongStream(supplier, func);
	}
	protected @Override DoubleStream asDS(Function<java.util.stream.Stream<T>, java.util.stream.DoubleStream> func) {
		return new DoubleStream(supplier, func);
	}
	protected @Override Function<? super T, ? extends java.util.stream.IntStream> castToIntStream(
		Function<? super T, ? extends java.util.stream.IntStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super T, ? extends java.util.stream.LongStream> castToLongStream(
		Function<? super T, ? extends java.util.stream.LongStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super T, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		Function<? super T, ? extends java.util.stream.DoubleStream> mapper) {
		return mapper;
	}
	protected @Override ToIntFunction<? super T> castToInt(ToIntFunction<? super T> mapper) {
		return mapper;
	}
	protected @Override ToLongFunction<? super T> castToLong(ToLongFunction<? super T> mapper) {
		return mapper;
	}
	protected @Override ToDoubleFunction<? super T> castToDouble(ToDoubleFunction<? super T> mapper) {
		return mapper;
	}
	protected @Override BinaryOperator<T> castToBinaryOperators(BinaryOperator<T> combiner) {
		return combiner;
	}
	protected @Override Comparator<? super T> castToComparators(Comparator<? super T> comparator) {
		return comparator;
	}
	protected @Override Consumer<? super T> castToConsumers(Consumer<? super T> action) {
		return action;
	}
	protected @Override Predicate<? super T> castToPredicates(Predicate<? super T> allowed) {
		return allowed;
	}
	public <R> Stream<R> map(Function<? super T, ? extends R> mapping) {
		return mapInternal(mapping, cast());
	}
	public final @SafeVarargs <R> Stream<R> map(Function<? super T, ? extends R> mapper, Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			Stream<T> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream<R> flatMap(
		Function<? super T, ? extends Stream<? extends R>> mapper,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			Stream<T> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, ArrayList<T>> toMap(Function<? super T, ? extends K> classifier) {
		return toMapInternal(castToClassifier(classifier));
	}
	public final @SafeVarargs <K> HashMap<K, ArrayList<T>> toMap(
		Function<? super T, ? extends K> classifier,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			Stream<T> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return stream.toMapInternal(classifier);
		}
		return toMapInternal(classifier);
	}
	public <K, V> HashMap<K, V> toMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends V> valueMapper) {
		return collect(Collectors.toMap(castToClassifier(keyMapper), castToClassifier(valueMapper)));
	}
	public final @SafeVarargs <K, V> HashMap<K, V> toMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends V> valueMapper,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).collect(
				Collectors.toMap(keyMapper, valueMapper));
		}
		return collect(Collectors.toMap(keyMapper, valueMapper));
	}
	public <K, L, M> M toMultiMap(
		Function<? super T, ? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<ArrayList<T>, L> intoList) {
		return toMultiMapInternal(castToClassifier(classifier), intoMap, intoList);
	}
	public final @SafeVarargs <K, L, M> M toMultiMap(
		Function<? super T, ? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<ArrayList<T>, L> intoList,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMultiMapInternal(
				classifier,
				intoMap,
				intoList);
		}
		return toMultiMapInternal(classifier, intoMap, intoList);
	}
	public <K, V> HashMap<K, ArrayList<V>> toMultiMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends V> valueMapper) {
		return collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
	}
	public final @SafeVarargs <K, V> HashMap<K, ArrayList<V>> toMultiMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends V> valueMapper,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).collect(
				Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
		}
		return collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
	}
	public IOStream<T> toIO() {
		return new IOStream<>(supplier);
	}
	public static <T> Stream<T> of() {
		return new Stream<>(() -> java.util.stream.Stream.empty());
	}
	public static <T> Stream<T> of(T value) {
		return new Stream<>(() -> java.util.stream.Stream.of(value));
	}
	public static @SafeVarargs <T> Stream<T> of(T... values) {
		return new Stream<>(() -> java.util.stream.Stream.of(values));
	}
	public static <T> Stream<T> from(Iterable<T> iterable) {
		return new Stream<>(() -> iterableToStream(iterable));
	}
	public static <T> Stream<T> iterate(T seed, UnaryOperator<T> function) {
		return new Stream<>(() -> java.util.stream.Stream.iterate(seed, function));
	}
	public static <T> Stream<T> generate(Supplier<T> supplier) {
		return new Stream<>(() -> java.util.stream.Stream.generate(supplier));
	}
	private static <T> java.util.stream.Stream<T> iterableToStream(Iterable<T> iterable) {
		Spliterator<T> spliterator = iterable.spliterator();
		return StreamSupport.stream(() -> spliterator, spliterator.characteristics(), false);
	}
	private <K> Function<? super T, ? extends K> castToClassifier(Function<? super T, ? extends K> classifier) {
		return classifier;
	}
	private <R> Function<? super T, ? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		Function<? super T, ? extends Stream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.Stream<T>, java.util.stream.Stream<R>>, Stream<R>> cast() {
		return f -> new Stream<>(supplier, f);
	}
}
