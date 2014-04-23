package utils.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
public final class UnDoubleStream extends AbstractDoubleStream<RuntimeException,
DoubleStream,
UnStream<Double>,
UnIntStream,
UnLongStream,
UnDoubleStream,
DoubleConsumer,
DoublePredicate,
DoubleBinaryOperator,
DoubleFunction<? extends IntStream>,
DoubleFunction<? extends LongStream>,
DoubleFunction<? extends DoubleStream>,
ToIntFunction<Double>,
ToLongFunction<Double>,
ToDoubleFunction<Double>> {//*E*

	public UnDoubleStream(Supplier<DoubleStream> supplier) {
		super(supplier);
	}
	<OLD> UnDoubleStream(Supplier<OLD> older, Function<OLD, DoubleStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	@Override
  public DoubleStream castToStream(DoubleStream stream) {
  	return stream;
  }
	@Override
	public Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	public @Override UnStream<Double> asOS(Function<DoubleStream, Stream<Double>> func) {
		return new UnStream<>(supplier, func);
	}
	public @Override UnIntStream asIS(Function<DoubleStream, IntStream> func) {
		return new UnIntStream(supplier, func);
	}
	public @Override UnLongStream asLS(Function<DoubleStream, LongStream> func) {
		return new UnLongStream(supplier, func);
	}
	public @Override UnDoubleStream asSELF(Function<DoubleStream, DoubleStream> func) {
		return new UnDoubleStream(supplier, func);
	}
	@Override
	public Function<? super Double, ? extends IntStream> castToIntStream(DoubleFunction<? extends IntStream> mapper) {
		return mapper::apply;
	}
	@Override
	public Function<? super Double, ? extends LongStream> castToLongStream(DoubleFunction<? extends LongStream> mapper) {
		return mapper::apply;
	}
	@Override
	public Function<? super Double, ? extends DoubleStream> castToDoubleStream(
	  DoubleFunction<? extends DoubleStream> mapper) {
		return mapper::apply;
	}
	@Override
	public DoubleToIntFunction castToInt(ToIntFunction<Double> mapper) {
		return mapper::applyAsInt;
	}
	@Override
	public DoubleToLongFunction castToLong(ToLongFunction<Double> mapper) {
		return mapper::applyAsLong;
	}
	@Override
	public DoubleUnaryOperator castToDouble(ToDoubleFunction<Double> mapper) {
		return mapper::applyAsDouble;
	}
	@Override
  public DoubleBinaryOperator castToBinaryOperators(DoubleBinaryOperator combiner) {
  	return combiner;
  }
	@Override
  public DoubleConsumer castToConsumers(DoubleConsumer action) {
  	return action;
  }
	@Override
  public DoublePredicate castToPredicates(DoublePredicate test) {
  	return test;
  }
	public <R> UnStream<R> map(DoubleFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> UnStream<R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> UnStream<R> flatMap(DoubleFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> UnStream<R> flatMap(
	  DoubleFunction<? extends Stream<? extends R>> mapper,
	  DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ?
			flatMapInternal(mapper::apply, filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) :
				flatMapInternal(mapper::apply, cast());
	}
	public <K> Map<K, double[]> toMap(DoubleFunction<? extends K> classifier) {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  DoubleFunction<? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<double[], L> intoList) {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private static <K> Function<DoubleFunction<? extends K>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> c;
	}
	private static <R> Function<Double,? extends Stream<? extends R>> castToFlatMapFunctions(
	  DoubleFunction<? extends Stream<? extends R>> mapper) {
		return mapper::apply;
	}
	private static <R> DoubleFunction<? extends R> castToMapFunctions(DoubleFunction<? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<DoubleStream, Stream<R>>, UnStream<R>> cast() {
		return f -> new UnStream<>(supplier, f);
	}
}