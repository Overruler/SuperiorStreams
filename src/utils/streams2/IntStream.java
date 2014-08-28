package utils.streams2;

import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.Function;
import utils.streams.functions.IntBinaryOperator;
import utils.streams.functions.IntConsumer;
import utils.streams.functions.IntFunction;
import utils.streams.functions.IntPredicate;
import utils.streams.functions.IntToDoubleFunction;
import utils.streams.functions.IntToLongFunction;
import utils.streams.functions.IntUnaryOperator;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;

//*Q*
public final class IntStream extends AbstractIntStream<RuntimeException,
java.util.stream.IntStream,
Stream<Integer>,
IntStream,
LongStream,
DoubleStream,
IntConsumer,
IntPredicate,
IntBinaryOperator,
IntFunction<? extends java.util.stream.IntStream>,
IntFunction<? extends java.util.stream.LongStream>,
IntFunction<? extends java.util.stream.DoubleStream>,
ToIntFunction<Integer>,
ToLongFunction<Integer>,
ToDoubleFunction<Integer>> {//*E*
	public IntStream(Supplier<java.util.stream.IntStream> supplier) {
		super(supplier);
	}
	<OLD> IntStream(Supplier<OLD> older, Function<OLD, java.util.stream.IntStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override java.util.stream.IntStream castToStream(java.util.stream.IntStream stream) {
		return stream;
	}
	protected @Override Stream<Integer>
		asOS(Function<java.util.stream.IntStream, java.util.stream.Stream<Integer>> func) {
		return new Stream<>(supplier, func);
	}
	protected @Override IntStream asSELF(Function<java.util.stream.IntStream, java.util.stream.IntStream> func) {
		return new IntStream(supplier, func);
	}
	protected @Override LongStream asLS(Function<java.util.stream.IntStream, java.util.stream.LongStream> func) {
		return new LongStream(supplier, func);
	}
	protected @Override DoubleStream asDS(Function<java.util.stream.IntStream, java.util.stream.DoubleStream> func) {
		return new DoubleStream(supplier, func);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.IntStream> castToIntStream(
		IntFunction<? extends java.util.stream.IntStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.LongStream> castToLongStream(
		IntFunction<? extends java.util.stream.LongStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IntFunction<? extends java.util.stream.DoubleStream> mapper) {
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
	public <R> Stream<R> map(IntFunction<? extends R> mapper) {
		return mapInternal(mapper, cast());
	}
	public final @SafeVarargs <R> Stream<R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			IntStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> Stream<R> flatMap(IntFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream<R> flatMap(
		IntFunction<? extends Stream<? extends R>> mapper,
		IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			IntStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, int[]> toMap(IntFunction<? extends K> classifier) {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		IntFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<int[], L> intoList) {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	public IOIntStream toIO() {
		return new IOIntStream(supplier);
	}
	public static IntStream of() {
		return new IntStream(() -> java.util.stream.IntStream.empty());
	}
	public static IntStream of(int value) {
		return new IntStream(() -> java.util.stream.IntStream.of(value));
	}
	public static IntStream of(int... values) {
		return new IntStream(() -> java.util.stream.IntStream.of(values));
	}
	private static <K> Function<IntFunction<? extends K>, IntFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> IntFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		IntFunction<? extends Stream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.IntStream, java.util.stream.Stream<R>>, Stream<R>> cast() {
		return f -> new Stream<>(supplier, f);
	}
}