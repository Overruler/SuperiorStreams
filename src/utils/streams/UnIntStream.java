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
public final class UnIntStream extends AbstractIntStream<RuntimeException,
IntStream,
UnStream<Integer>,
UnIntStream,
UnLongStream,
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

	public UnIntStream(Supplier<IntStream> supplier) {
		super(supplier);
	}
	<OLD> UnIntStream(Supplier<OLD> older, Function<OLD, IntStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	@Override
  public IntStream castToStream(IntStream stream) {
  	return stream;
  }
	@Override
	public Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	public @Override UnStream<Integer> asOS(Function<IntStream, Stream<Integer>> func) {
		return new UnStream<>(supplier, func);
	}
	public @Override UnIntStream asSELF(Function<IntStream, IntStream> func) {
		return new UnIntStream(supplier, func);
	}
	public @Override UnLongStream asLS(Function<IntStream, LongStream> func) {
		return new UnLongStream(supplier, func);
	}
	public @Override UnDoubleStream asDS(Function<IntStream, DoubleStream> func) {
		return new UnDoubleStream(supplier, func);
	}
	@Override
	public Function<? super Integer, ? extends IntStream> castToIntStream(IntFunction<? extends IntStream> mapper) {
		return mapper::apply;
	}
	@Override
	public Function<? super Integer, ? extends LongStream> castToLongStream(IntFunction<? extends LongStream> mapper) {
		return mapper::apply;
	}
	@Override
	public Function<? super Integer, ? extends DoubleStream> castToDoubleStream(
	  IntFunction<? extends DoubleStream> mapper) {
		return mapper::apply;
	}
	@Override
	public IntUnaryOperator castToInt(ToIntFunction<Integer> mapper) {
		return mapper::applyAsInt;
	}
	@Override
	public IntToLongFunction castToLong(ToLongFunction<Integer> mapper) {
		return mapper::applyAsLong;
	}
	@Override
	public IntToDoubleFunction castToDouble(ToDoubleFunction<Integer> mapper) {
		return mapper::applyAsDouble;
	}
	@Override
  public IntBinaryOperator castToBinaryOperators(IntBinaryOperator combiner) {
  	return combiner;
  }
	@Override
  public IntConsumer castToConsumers(IntConsumer action) {
  	return action;
  }
	@Override
  public IntPredicate castToPredicates(IntPredicate test) {
  	return test;
  }
	public <R> UnStream<R> map(IntFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> UnStream<R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> UnStream<R> flatMap(IntFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> UnStream<R> flatMap(
	  IntFunction<? extends Stream<? extends R>> mapper,
	  IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ?
			flatMapInternal(mapper::apply, filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) :
				flatMapInternal(mapper::apply, cast());
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
	private static <R> Function<Integer,? extends Stream<? extends R>> castToFlatMapFunctions(
	  IntFunction<? extends Stream<? extends R>> mapper) {
		return mapper::apply;
	}
	private static <R> IntFunction<? extends R> castToMapFunctions(IntFunction<? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<IntStream, Stream<R>>, UnStream<R>> cast() {
		return f -> new UnStream<>(supplier, f);
	}
}