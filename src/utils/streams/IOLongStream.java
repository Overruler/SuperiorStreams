package utils.streams;

import java.io.IOException;
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
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.IOLongBinaryOperator;
import utils.streams.functions.IOLongConsumer;
import utils.streams.functions.IOLongFunction;
import utils.streams.functions.IOLongPredicate;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;

//*Q*
public final class IOLongStream extends AbstractLongStream<IOException,
LongStream,
IOStream<Long>,
IOIntStream,
IOLongStream,
IODoubleStream,
IOLongConsumer,
IOLongPredicate,
IOLongBinaryOperator,
IOLongFunction<? extends IntStream>,
IOLongFunction<? extends LongStream>,
IOLongFunction<? extends DoubleStream>,
IOToIntFunction<Long>,
IOToLongFunction<Long>,
IOToDoubleFunction<Long>> {//*E*

	public IOLongStream(Supplier<LongStream> supplier) {
		super(supplier);
	}
	<OLD> IOLongStream(Supplier<OLD> older, Function<OLD, LongStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override LongStream castToStream(LongStream stream) {
		return stream;
	}
	protected @Override IOStream<Long> asOS(Function<LongStream, Stream<Long>> func) {
		return new IOStream<>(supplier, func);
	}
	protected @Override IOIntStream asIS(Function<LongStream, IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	protected @Override IOLongStream asSELF(Function<LongStream, LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	protected @Override IODoubleStream asDS(Function<LongStream, DoubleStream> func) {
		return new IODoubleStream(supplier, func);
	}
	protected @Override Function<? super Long, ? extends IntStream> castToIntStream(
	  IOLongFunction<? extends IntStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Long, ? extends LongStream> castToLongStream(
	  IOLongFunction<? extends LongStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Long, ? extends DoubleStream> castToDoubleStream(
	  IOLongFunction<? extends DoubleStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override LongToIntFunction castToInt(IOToIntFunction<Long> mapper) {
		return mapper.uncheck(classOfE())::applyAsInt;
	}
	protected @Override LongUnaryOperator castToLong(IOToLongFunction<Long> mapper) {
		return mapper.uncheck(classOfE())::applyAsLong;
	}
	protected @Override LongToDoubleFunction castToDouble(IOToDoubleFunction<Long> mapper) {
		return mapper.uncheck(classOfE())::applyAsDouble;
	}
	protected @Override LongBinaryOperator castToBinaryOperators(IOLongBinaryOperator combiner) {
		return combiner.uncheck(classOfE());
	}
	protected @Override LongConsumer castToConsumers(IOLongConsumer action) {
		return action.uncheck(classOfE());
	}
	protected @Override LongPredicate castToPredicates(IOLongPredicate test) {
		return test.uncheck(classOfE());
	}
	public <R> IOStream<R> map(IOLongFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IOLongFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
	  LongFunction<? extends Stream<? extends R>> mapper,
	  LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper::apply,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(
		  mapper::apply,
		  cast());
	}
	public <K> Map<K, long[]> toMap(IOLongFunction<? extends K> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  IOLongFunction<? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<long[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<IOLongFunction<? extends K>, LongFunction<? extends K>> castToClassifier() {
		return c -> c.uncheck(classOfE());
	}
	private <R> Function<Long, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IOLongFunction<? extends Stream<? extends R>> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	private <R> LongFunction<? extends R> castToMapFunctions(IOLongFunction<? extends R> mapping) {
		return mapping.uncheck(classOfE());
	}
	private <R> Function<Function<LongStream, Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}