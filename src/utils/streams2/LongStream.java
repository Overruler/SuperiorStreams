package utils.streams2;

import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.Function;
import utils.streams.functions.LongBinaryOperator;
import utils.streams.functions.LongConsumer;
import utils.streams.functions.LongFunction;
import utils.streams.functions.LongPredicate;
import utils.streams.functions.LongToDoubleFunction;
import utils.streams.functions.LongToIntFunction;
import utils.streams.functions.LongUnaryOperator;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;

//*Q*
public final class LongStream extends AbstractLongStream<RuntimeException,
java.util.stream.LongStream,
Stream<Long>,
IntStream,
LongStream,
DoubleStream,
LongConsumer,
LongPredicate,
LongBinaryOperator,
LongFunction<? extends java.util.stream.IntStream>,
LongFunction<? extends java.util.stream.LongStream>,
LongFunction<? extends java.util.stream.DoubleStream>,
ToIntFunction<Long>,
ToLongFunction<Long>,
ToDoubleFunction<Long>> {//*E*
	public LongStream(Supplier<java.util.stream.LongStream> supplier) {
		super(supplier);
	}
	<OLD> LongStream(Supplier<OLD> older, Function<OLD, java.util.stream.LongStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override java.util.stream.LongStream castToStream(java.util.stream.LongStream stream) {
		return stream;
	}
	protected @Override Stream<Long> asOS(Function<java.util.stream.LongStream, java.util.stream.Stream<Long>> func) {
		return new Stream<>(supplier, func);
	}
	protected @Override IntStream asIS(Function<java.util.stream.LongStream, java.util.stream.IntStream> func) {
		return new IntStream(supplier, func);
	}
	protected @Override LongStream asSELF(Function<java.util.stream.LongStream, java.util.stream.LongStream> func) {
		return new LongStream(supplier, func);
	}
	protected @Override DoubleStream asDS(Function<java.util.stream.LongStream, java.util.stream.DoubleStream> func) {
		return new DoubleStream(supplier, func);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.IntStream> castToIntStream(
		LongFunction<? extends java.util.stream.IntStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Long, ? extends java.util.stream.LongStream> castToLongStream(
		LongFunction<? extends java.util.stream.LongStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Long, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		LongFunction<? extends java.util.stream.DoubleStream> mapper) {
		return mapper::apply;
	}
	protected @Override LongToIntFunction castToInt(ToIntFunction<Long> mapper) {
		return mapper::applyAsInt;
	}
	protected @Override LongUnaryOperator castToLong(ToLongFunction<Long> mapper) {
		return mapper::applyAsLong;
	}
	protected @Override LongToDoubleFunction castToDouble(ToDoubleFunction<Long> mapper) {
		return mapper::applyAsDouble;
	}
	protected @Override LongBinaryOperator castToBinaryOperators(LongBinaryOperator combiner) {
		return combiner;
	}
	protected @Override LongConsumer castToConsumers(LongConsumer action) {
		return action;
	}
	protected @Override LongPredicate castToPredicates(LongPredicate test) {
		return test;
	}
	public <R> Stream<R> map(LongFunction<? extends R> mapper) {
		return mapInternal(mapper, cast());
	}
	public final @SafeVarargs <R> Stream<R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			LongStream filter = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, filter.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> Stream<R> flatMap(LongFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream<R> flatMap(
		LongFunction<? extends Stream<? extends R>> mapper,
		LongPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			LongStream filter = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), filter.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, long[]> toMap(LongFunction<? extends K> classifier) throws RuntimeException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		LongFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<long[], L> intoList) throws RuntimeException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	public IOLongStream toIO() {
		return new IOLongStream(supplier);
	}
	public static LongStream of() {
		return new LongStream(() -> java.util.stream.LongStream.empty());
	}
	public static LongStream of(long value) {
		return new LongStream(() -> java.util.stream.LongStream.of(value));
	}
	public static LongStream of(long... values) {
		return new LongStream(() -> java.util.stream.LongStream.of(values));
	}
	private static <K> Function<LongFunction<? extends K>, LongFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> LongFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		LongFunction<? extends Stream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.LongStream, java.util.stream.Stream<R>>, Stream<R>> cast() {
		return f -> new Stream<>(supplier, f);
	}
}