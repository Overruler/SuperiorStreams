package utils.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//*Q*
public final class IntStream2 extends AbstractIntStream<RuntimeException,
IntStream,
Stream2<Integer>,
IntStream2,
LongStream2,
UnDoubleStream,
IntConsumer,
IntPredicate,
IntBinaryOperator,
IntFunction<? extends IntStream>,
IntFunction<? extends LongStream>,
IntFunction<? extends DoubleStream>,
ToIntFunction<Integer>,
ToLongFunction<Integer>,
ToDoubleFunction<Integer>> {//*E*

	public IntStream2(Supplier<IntStream> supplier) {
		super(supplier);
	}
	<OLD> IntStream2(Supplier<OLD> older, Function<OLD, IntStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override IntStream castToStream(IntStream stream) {
		return stream;
	}
	protected @Override Stream2<Integer> asOS(Function<IntStream, Stream<Integer>> func) {
		return new Stream2<>(supplier, func);
	}
	protected @Override IntStream2 asSELF(Function<IntStream, IntStream> func) {
		return new IntStream2(supplier, func);
	}
	protected @Override LongStream2 asLS(Function<IntStream, LongStream> func) {
		return new LongStream2(supplier, func);
	}
	protected @Override UnDoubleStream asDS(Function<IntStream, DoubleStream> func) {
		return new UnDoubleStream(supplier, func);
	}
	protected @Override Function<? super Integer, ? extends IntStream> castToIntStream(
	  IntFunction<? extends IntStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Integer, ? extends LongStream> castToLongStream(
	  IntFunction<? extends LongStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Integer, ? extends DoubleStream> castToDoubleStream(
	  IntFunction<? extends DoubleStream> mapper) {
		return mapper::apply;
	}
	protected @Override IntUnaryOperator castToInt(ToIntFunction<Integer> mapper) {
		return mapper::applyAsInt;
	}
	protected @Override IntToLongFunction castToLong(ToLongFunction<Integer> mapper) {
		return mapper::applyAsLong;
	}
	protected @Override IntToDoubleFunction castToDouble(ToDoubleFunction<Integer> mapper) {
		return mapper::applyAsDouble;
	}
	protected @Override IntBinaryOperator castToBinaryOperators(IntBinaryOperator combiner) {
		return combiner;
	}
	protected @Override IntConsumer castToConsumers(IntConsumer action) {
		return action;
	}
	protected @Override IntPredicate castToPredicates(IntPredicate test) {
		return test;
	}
	public <R> Stream2<R> map(IntFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> Stream2<R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> Stream2<R> flatMap(IntFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream2<R> flatMap(
	  IntFunction<? extends Stream<? extends R>> mapper,
	  IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper::apply,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(
		  mapper::apply,
		  cast());
	}
	public <K> Map<K, int[]> toMap(IntFunction<? extends K> classifier) {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  IntFunction<? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<int[], L> intoList) {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private static <K> Function<IntFunction<? extends K>, IntFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> Function<Integer, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IntFunction<? extends Stream<? extends R>> mapper) {
		return mapper::apply;
	}
	private static <R> IntFunction<? extends R> castToMapFunctions(IntFunction<? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<IntStream, Stream<R>>, Stream2<R>> cast() {
		return f -> new Stream2<>(supplier, f);
	}
}