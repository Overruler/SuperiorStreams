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
public final class UnLongStream extends AbstractLongStream<RuntimeException,
LongStream,
UnStream<Long>,
UnIntStream,
UnLongStream,
UnDoubleStream,
LongConsumer,
LongPredicate,
LongBinaryOperator,
LongFunction<? extends IntStream>,
LongFunction<? extends LongStream>,
LongFunction<? extends DoubleStream>,
ToIntFunction<Long>,
ToLongFunction<Long>,
ToDoubleFunction<Long>> {//*E*

	public UnLongStream(Supplier<LongStream> supplier) {
		super(supplier);
	}
	<OLD> UnLongStream(Supplier<OLD> older, Function<OLD, LongStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	@Override
  public LongStream castToStream(LongStream stream) {
  	return stream;
  }
	@Override
	public Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	public @Override UnStream<Long> asOS(Function<LongStream, Stream<Long>> func) {
		return new UnStream<>(supplier, func);
	}
	public @Override UnIntStream asIS(Function<LongStream, IntStream> func) {
		return new UnIntStream(supplier, func);
	}
	public @Override UnLongStream asSELF(Function<LongStream, LongStream> func) {
		return new UnLongStream(supplier, func);
	}
	public @Override UnDoubleStream asDS(Function<LongStream, DoubleStream> func) {
		return new UnDoubleStream(supplier, func);
	}
	@Override
  public Function<? super Long, ? extends IntStream> castToIntStream(LongFunction<? extends IntStream> mapper) {
  	return mapper::apply;
  }
	@Override
  public Function<? super Long, ? extends LongStream> castToLongStream(LongFunction<? extends LongStream> mapper) {
  	return mapper::apply;
  }
	@Override
  public Function<? super Long, ? extends DoubleStream> castToDoubleStream(
    LongFunction<? extends DoubleStream> mapper) {
  	return mapper::apply;
  }
	@Override
  public LongToIntFunction castToInt(ToIntFunction<Long> mapper) {
  	return mapper::applyAsInt;
  }
	@Override
  public LongUnaryOperator castToLong(ToLongFunction<Long> mapper) {
  	return mapper::applyAsLong;
  }
	@Override
  public LongToDoubleFunction castToDouble(ToDoubleFunction<Long> mapper) {
  	return mapper::applyAsDouble;
  }
	@Override
  public LongBinaryOperator castToBinaryOperators(LongBinaryOperator combiner) {
  	return combiner;
  }
	@Override
  public LongConsumer castToConsumers(LongConsumer action) {
  	return action;
  }
	@Override
	public LongPredicate castToPredicates(LongPredicate test) {
		return test;
	}
	public <R> UnStream<R> map(LongFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> UnStream<R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> UnStream<R> flatMap(LongFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> UnStream<R> flatMap(
	  LongFunction<? extends Stream<? extends R>> mapper,
	  LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ?
			flatMapInternal(mapper::apply, filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) :
				flatMapInternal(mapper::apply, cast());
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
	private static <R> Function<Long,? extends Stream<? extends R>> castToFlatMapFunctions(
	  LongFunction<? extends Stream<? extends R>> mapper) {
		return mapper::apply;
	}
	private static <R> LongFunction<? extends R> castToMapFunctions(LongFunction<? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<LongStream, Stream<R>>, UnStream<R>> cast() {
		return f -> new UnStream<>(supplier, f);
	}
}