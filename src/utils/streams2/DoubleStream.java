package utils.streams2;

import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.DoubleBinaryOperator;
import utils.streams.functions.DoubleConsumer;
import utils.streams.functions.DoubleFunction;
import utils.streams.functions.DoublePredicate;
import utils.streams.functions.DoubleSupplier;
import utils.streams.functions.DoubleToIntFunction;
import utils.streams.functions.DoubleToLongFunction;
import utils.streams.functions.DoubleUnaryOperator;
import utils.streams.functions.Function;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;

//*Q*
public final class DoubleStream extends AbstractDoubleStream<RuntimeException,
java.util.stream.DoubleStream,
Stream<Double>,
IntStream,
LongStream,
DoubleStream,
DoubleConsumer,
DoublePredicate,
DoubleBinaryOperator,
DoubleFunction<? extends java.util.stream.IntStream>,
DoubleFunction<? extends java.util.stream.LongStream>,
DoubleFunction<? extends java.util.stream.DoubleStream>,
ToIntFunction<Double>,
ToLongFunction<Double>,
ToDoubleFunction<Double>> {//*E*
	public DoubleStream(Supplier<java.util.stream.DoubleStream> supplier) {
		super(supplier);
	}
	<OLD> DoubleStream(Supplier<OLD> older, Function<OLD, java.util.stream.DoubleStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override java.util.stream.DoubleStream castToStream(java.util.stream.DoubleStream stream) {
		return stream;
	}
	protected @Override Stream<Double> asOS(
		Function<java.util.stream.DoubleStream, java.util.stream.Stream<Double>> func) {
		return new Stream<>(supplier, func);
	}
	protected @Override IntStream asIS(Function<java.util.stream.DoubleStream, java.util.stream.IntStream> func) {
		return new IntStream(supplier, func);
	}
	protected @Override LongStream asLS(Function<java.util.stream.DoubleStream, java.util.stream.LongStream> func) {
		return new LongStream(supplier, func);
	}
	protected @Override DoubleStream
		asSELF(Function<java.util.stream.DoubleStream, java.util.stream.DoubleStream> func) {
		return new DoubleStream(supplier, func);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.IntStream> castToIntStream(
		DoubleFunction<? extends java.util.stream.IntStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Double, ? extends java.util.stream.LongStream> castToLongStream(
		DoubleFunction<? extends java.util.stream.LongStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Double, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		DoubleFunction<? extends java.util.stream.DoubleStream> mapper) {
		return mapper::apply;
	}
	protected @Override DoubleToIntFunction castToInt(ToIntFunction<Double> mapper) {
		return mapper::applyAsInt;
	}
	protected @Override DoubleToLongFunction castToLong(ToLongFunction<Double> mapper) {
		return mapper::applyAsLong;
	}
	protected @Override DoubleUnaryOperator castToDouble(ToDoubleFunction<Double> mapper) {
		return mapper::applyAsDouble;
	}
	protected @Override DoubleBinaryOperator castToBinaryOperators(DoubleBinaryOperator combiner) {
		return combiner;
	}
	protected @Override DoubleConsumer castToConsumers(DoubleConsumer action) {
		return action;
	}
	protected @Override DoublePredicate castToPredicates(DoublePredicate test) {
		return test;
	}
	public <R> Stream<R> map(DoubleFunction<? extends R> mapper) {
		return mapInternal(mapper, cast());
	}
	public final @SafeVarargs <R> Stream<R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			DoubleStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> Stream<R> flatMap(DoubleFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream<R> flatMap(
		DoubleFunction<? extends Stream<? extends R>> mapper,
		DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			DoubleStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, double[]> toMap(DoubleFunction<? extends K> classifier) {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		DoubleFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<double[], L> intoList) {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	public IODoubleStream toIO() {
		return new IODoubleStream(supplier);
	}
	public static DoubleStream of() {
		return new DoubleStream(() -> java.util.stream.DoubleStream.empty());
	}
	public static DoubleStream of(double value) {
		return new DoubleStream(() -> java.util.stream.DoubleStream.of(value));
	}
	public static DoubleStream of(double... values) {
		return new DoubleStream(() -> java.util.stream.DoubleStream.of(values));
	}
	public static DoubleStream iterate(double seed, DoubleUnaryOperator function) {
		return new DoubleStream(() -> java.util.stream.DoubleStream.iterate(seed, function));
	}
	public static DoubleStream generate(DoubleSupplier supplier) {
		return new DoubleStream(() -> java.util.stream.DoubleStream.generate(supplier));
	}
	private static <K> Function<DoubleFunction<? extends K>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> DoubleFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		DoubleFunction<? extends Stream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.DoubleStream, java.util.stream.Stream<R>>, Stream<R>> cast() {
		return f -> new Stream<>(supplier, f);
	}
}