package utils.streams;

import java.io.IOException;
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
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.IOIntBinaryOperator;
import utils.streams.functions.IOIntConsumer;
import utils.streams.functions.IOIntFunction;
import utils.streams.functions.IOIntPredicate;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;
//*Q*
public final class IOIntStream extends AbstractIntStream<IOException,
IntStream,
IOStream<Integer>,
IOIntStream,
IOLongStream,
IODoubleStream,
IOIntConsumer,
IOIntPredicate,
IOIntBinaryOperator,
IOIntFunction<? extends IntStream>,
IOIntFunction<? extends LongStream>,
IOIntFunction<? extends DoubleStream>,
IOToIntFunction<Integer>,
IOToLongFunction<Integer>,
IOToDoubleFunction<Integer>> {//*E*

	public IOIntStream(Supplier<IntStream> supplier) {
		super(supplier);
	}
	<OLD> IOIntStream(Supplier<OLD> older, Function<OLD, IntStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	@Override
  public IntStream castToStream(IntStream stream) {
  	return stream;
  }
	@Override
	public Class<IOException> classOfE() {
		return IOException.class;
	}
	public @Override IOStream<Integer> asOS(Function<IntStream, Stream<Integer>> func) {
		return new IOStream<>(supplier, func);
	}
	public @Override IOIntStream asSELF(Function<IntStream, IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	public @Override IOLongStream asLS(Function<IntStream, LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	public @Override IODoubleStream asDS(Function<IntStream, DoubleStream> func) {
		return new IODoubleStream(supplier, func);
	}
	@Override
	public Function<? super Integer, ? extends IntStream> castToIntStream(IOIntFunction<? extends IntStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	@Override
	public Function<? super Integer, ? extends LongStream> castToLongStream(IOIntFunction<? extends LongStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	@Override
	public Function<? super Integer, ? extends DoubleStream> castToDoubleStream(
	  IOIntFunction<? extends DoubleStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	@Override
	public IntUnaryOperator castToInt(IOToIntFunction<Integer> mapper) {
		return mapper.uncheck(classOfE())::applyAsInt;
	}
	@Override
	public IntToLongFunction castToLong(IOToLongFunction<Integer> mapper) {
		return mapper.uncheck(classOfE())::applyAsLong;
	}
	@Override
	public IntToDoubleFunction castToDouble(IOToDoubleFunction<Integer> mapper) {
		return mapper.uncheck(classOfE())::applyAsDouble;
	}
	@Override
  public IntBinaryOperator castToBinaryOperators(IOIntBinaryOperator combiner) {
  	return combiner.uncheck(classOfE());
  }
	@Override
  public IntConsumer castToConsumers(IOIntConsumer action) {
  	return action.uncheck(classOfE());
  }
	@Override
  public IntPredicate castToPredicates(IOIntPredicate test) {
  	return test.uncheck(classOfE());
  }
	public <R> IOStream<R> map(IOIntFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IOIntFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
	  IntFunction<? extends Stream<? extends R>> mapper,
	  IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ?
			flatMapInternal(mapper::apply, filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) :
				flatMapInternal(mapper::apply, cast());
	}
	public <K> Map<K, int[]> toMap(IOIntFunction<? extends K> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  IOIntFunction<? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<int[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<IOIntFunction<? extends K>, IntFunction<? extends K>> castToClassifier() {
		return c -> c.uncheck(classOfE());
	}
	private <R> Function<Integer,? extends Stream<? extends R>> castToFlatMapFunctions(
	  IOIntFunction<? extends Stream<? extends R>> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	private <R> IntFunction<? extends R> castToMapFunctions(IOIntFunction<? extends R> mapping) {
		return mapping.uncheck(classOfE());
	}
	private <R> Function<Function<IntStream, Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}