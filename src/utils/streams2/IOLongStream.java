package utils.streams2;

import java.io.IOException;
import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.Function;
import utils.streams.functions.IOLongBinaryOperator;
import utils.streams.functions.IOLongConsumer;
import utils.streams.functions.IOLongFunction;
import utils.streams.functions.IOLongPredicate;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;
import utils.streams.functions.LongBinaryOperator;
import utils.streams.functions.LongConsumer;
import utils.streams.functions.LongFunction;
import utils.streams.functions.LongPredicate;
import utils.streams.functions.LongToDoubleFunction;
import utils.streams.functions.LongToIntFunction;
import utils.streams.functions.LongUnaryOperator;
import utils.streams.functions.Supplier;

//*Q*
public final class IOLongStream extends AbstractLongStream<IOException,
java.util.stream.LongStream,
IOStream<Long>,
IOIntStream,
IOLongStream,
IODoubleStream,
IOLongConsumer,
IOLongPredicate,
IOLongBinaryOperator,
IOLongFunction<? extends java.util.stream.IntStream>,
IOLongFunction<? extends java.util.stream.LongStream>,
IOLongFunction<? extends java.util.stream.DoubleStream>,
IOToIntFunction<Long>,
IOToLongFunction<Long>,
IOToDoubleFunction<Long>> {//*E*
	public IOLongStream(Supplier<java.util.stream.LongStream> supplier) {
		super(supplier);
	}
	<OLD> IOLongStream(Supplier<OLD> older, Function<OLD, java.util.stream.LongStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override java.util.stream.LongStream castToStream(java.util.stream.LongStream stream) {
		return stream;
	}
	protected @Override IOStream<Long> asOS(Function<java.util.stream.LongStream, java.util.stream.Stream<Long>> func) {
		return new IOStream<>(supplier, func);
	}
	protected @Override IOIntStream asIS(Function<java.util.stream.LongStream, java.util.stream.IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	protected @Override IOLongStream asSELF(Function<java.util.stream.LongStream, java.util.stream.LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	protected @Override IODoubleStream asDS(Function<java.util.stream.LongStream, java.util.stream.DoubleStream> func) {
		return new IODoubleStream(supplier, func);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.IntStream> castToIntStream(
		IOLongFunction<? extends java.util.stream.IntStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Long, ? extends java.util.stream.LongStream> castToLongStream(
		IOLongFunction<? extends java.util.stream.LongStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Long, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IOLongFunction<? extends java.util.stream.DoubleStream> mapper) {
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
	public <R> IOStream<R> flatMap(IOLongFunction<? extends java.util.stream.Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
		LongFunction<? extends java.util.stream.Stream<? extends R>> mapper,
			LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
			mapper::apply,
			filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(
				mapper::apply,
				cast());
	}
	public <K> HashMap<K, long[]> toMap(IOLongFunction<? extends K> classifier) throws IOException {
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
	private <R> Function<Long, ? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		IOLongFunction<? extends java.util.stream.Stream<? extends R>> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	private <R> LongFunction<? extends R> castToMapFunctions(IOLongFunction<? extends R> mapping) {
		return mapping.uncheck(classOfE());
	}
	private <R> Function<Function<java.util.stream.LongStream, java.util.stream.Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}