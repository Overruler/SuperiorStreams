package utils.streams2;

import java.io.IOException;
import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.Function;
import utils.streams.functions.IOLongBinaryOperator;
import utils.streams.functions.IOLongConsumer;
import utils.streams.functions.IOLongFunction;
import utils.streams.functions.IOLongPredicate;
import utils.streams.functions.IOLongSupplier;
import utils.streams.functions.IOLongUnaryOperator;
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
	public <R> IOStream<R> map(IOLongFunction<? extends R> mapper) {
		return mapInternal(mapper.uncheck(), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			IOLongStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IOLongFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
		LongFunction<? extends Stream<? extends R>> mapper,
		LongPredicate... allowed) {
		LongFunction<? extends java.util.stream.Stream<? extends R>> mapper2 = castToFlatMapFunctions(mapper);
		if(allowed != null && allowed.length > 0) {
			IOLongStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(mapper2, stream.cast());
		}
		return flatMapInternal(mapper2, cast());
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
	public static IOLongStream iterate(long seed, IOLongUnaryOperator function) {
		return new IOLongStream(() -> java.util.stream.LongStream.iterate(seed, function.uncheck()));
	}
	public static IOLongStream generate(IOLongSupplier supplier) {
		return new IOLongStream(() -> java.util.stream.LongStream.generate(supplier.uncheck()));
	}
	private <K> Function<IOLongFunction<? extends K>, LongFunction<? extends K>> castToClassifier() {
		return c -> c.uncheck(classOfE());
	}
	private static <R> LongFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		LongFunction<? extends Stream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.LongStream, java.util.stream.Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}