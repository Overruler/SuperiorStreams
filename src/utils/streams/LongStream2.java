package utils.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//*Q*
public final class LongStream2 extends AbstractLongStream<RuntimeException,
LongStream,
Stream2<Long>,
IntStream2,
LongStream2,
DoubleStream2,
LongConsumer,
LongPredicate,
LongBinaryOperator,
LongFunction<? extends IntStream>,
LongFunction<? extends LongStream>,
LongFunction<? extends DoubleStream>,
ToIntFunction<Long>,
ToLongFunction<Long>,
ToDoubleFunction<Long>> {//*E*

	public LongStream2(Supplier<LongStream> supplier) {
		super(supplier);
	}
	<OLD> LongStream2(Supplier<OLD> older, Function<OLD, LongStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override LongStream castToStream(LongStream stream) {
		return stream;
	}
	protected @Override Stream2<Long> asOS(Function<LongStream, Stream<Long>> func) {
		return new Stream2<>(supplier, func);
	}
	protected @Override IntStream2 asIS(Function<LongStream, IntStream> func) {
		return new IntStream2(supplier, func);
	}
	protected @Override LongStream2 asSELF(Function<LongStream, LongStream> func) {
		return new LongStream2(supplier, func);
	}
	protected @Override DoubleStream2 asDS(Function<LongStream, DoubleStream> func) {
		return new DoubleStream2(supplier, func);
	}
	protected @Override Function<? super Long, ? extends IntStream> castToIntStream(
	  LongFunction<? extends IntStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Long, ? extends LongStream> castToLongStream(
	  LongFunction<? extends LongStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Long, ? extends DoubleStream> castToDoubleStream(
	  LongFunction<? extends DoubleStream> mapper) {
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
	public <R> Stream2<R> map(LongFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> Stream2<R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> Stream2<R> flatMap(LongFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream2<R> flatMap(
	  LongFunction<? extends Stream<? extends R>> mapper,
	  LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper::apply,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(
		  mapper::apply,
		  cast());
	}
	public <K> Map<K, long[]> toMap(LongFunction<? extends K> classifier) throws RuntimeException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  LongFunction<? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<long[], L> intoList) throws RuntimeException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private static <K> Function<LongFunction<? extends K>, LongFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> Function<Long, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  LongFunction<? extends Stream<? extends R>> mapper) {
		return mapper::apply;
	}
	private static <R> LongFunction<? extends R> castToMapFunctions(LongFunction<? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<LongStream, Stream<R>>, Stream2<R>> cast() {
		return f -> new Stream2<>(supplier, f);
	}
}