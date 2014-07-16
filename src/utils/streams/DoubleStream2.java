package utils.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//*Q*
public final class DoubleStream2 extends AbstractDoubleStream<RuntimeException,
DoubleStream,
Stream2<Double>,
IntStream2,
LongStream2,
DoubleStream2,
DoubleConsumer,
DoublePredicate,
DoubleBinaryOperator,
DoubleFunction<? extends IntStream>,
DoubleFunction<? extends LongStream>,
DoubleFunction<? extends DoubleStream>,
ToIntFunction<Double>,
ToLongFunction<Double>,
ToDoubleFunction<Double>> {//*E*
	public DoubleStream2(Supplier<DoubleStream> supplier) {
		super(supplier);
	}
	<OLD> DoubleStream2(Supplier<OLD> older, Function<OLD, DoubleStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override DoubleStream castToStream(DoubleStream stream) {
		return stream;
	}
	protected @Override Stream2<Double> asOS(Function<DoubleStream, Stream<Double>> func) {
		return new Stream2<>(supplier, func);
	}
	protected @Override IntStream2 asIS(Function<DoubleStream, IntStream> func) {
		return new IntStream2(supplier, func);
	}
	protected @Override LongStream2 asLS(Function<DoubleStream, LongStream> func) {
		return new LongStream2(supplier, func);
	}
	protected @Override DoubleStream2 asSELF(Function<DoubleStream, DoubleStream> func) {
		return new DoubleStream2(supplier, func);
	}
	protected @Override Function<? super Double, ? extends IntStream> castToIntStream(
		DoubleFunction<? extends IntStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Double, ? extends LongStream> castToLongStream(
		DoubleFunction<? extends LongStream> mapper) {
		return mapper::apply;
	}
	protected @Override Function<? super Double, ? extends DoubleStream> castToDoubleStream(
		DoubleFunction<? extends DoubleStream> mapper) {
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
	public <R> Stream2<R> map(DoubleFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> Stream2<R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
			mapper,
			filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> Stream2<R> flatMap(DoubleFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream2<R> flatMap(
		DoubleFunction<? extends Stream<? extends R>> mapper,
		DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
			mapper::apply,
			filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(
			mapper::apply,
			cast());
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
	private static <K> Function<DoubleFunction<? extends K>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> Function<Double, ? extends Stream<? extends R>> castToFlatMapFunctions(
		DoubleFunction<? extends Stream<? extends R>> mapper) {
		return mapper::apply;
	}
	private static <R> DoubleFunction<? extends R> castToMapFunctions(DoubleFunction<? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<DoubleStream, Stream<R>>, Stream2<R>> cast() {
		return f -> new Stream2<>(supplier, f);
	}
}