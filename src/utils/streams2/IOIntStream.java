package utils.streams2;

import java.io.IOException;
import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.Function;
import utils.streams.functions.IOIntBinaryOperator;
import utils.streams.functions.IOIntConsumer;
import utils.streams.functions.IOIntFunction;
import utils.streams.functions.IOIntPredicate;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;
import utils.streams.functions.IntBinaryOperator;
import utils.streams.functions.IntConsumer;
import utils.streams.functions.IntFunction;
import utils.streams.functions.IntPredicate;
import utils.streams.functions.IntToDoubleFunction;
import utils.streams.functions.IntToLongFunction;
import utils.streams.functions.IntUnaryOperator;
import utils.streams.functions.Supplier;

//*Q*
public final class IOIntStream extends AbstractIntStream<IOException,
java.util.stream.IntStream,
IOStream<Integer>,
IOIntStream,
IOLongStream,
IODoubleStream,
IOIntConsumer,
IOIntPredicate,
IOIntBinaryOperator,
IOIntFunction<? extends java.util.stream.IntStream>,
IOIntFunction<? extends java.util.stream.LongStream>,
IOIntFunction<? extends java.util.stream.DoubleStream>,
IOToIntFunction<Integer>,
IOToLongFunction<Integer>,
IOToDoubleFunction<Integer>> {//*E*
	public IOIntStream(Supplier<java.util.stream.IntStream> supplier) {
		super(supplier);
	}
	<OLD> IOIntStream(Supplier<OLD> older, Function<OLD, java.util.stream.IntStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override java.util.stream.IntStream castToStream(java.util.stream.IntStream stream) {
		return stream;
	}
	protected @Override IOStream<Integer> asOS(
		Function<java.util.stream.IntStream, java.util.stream.Stream<Integer>> func) {
		return new IOStream<>(supplier, func);
	}
	protected @Override IOIntStream asSELF(Function<java.util.stream.IntStream, java.util.stream.IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	protected @Override IOLongStream asLS(Function<java.util.stream.IntStream, java.util.stream.LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	protected @Override IODoubleStream asDS(Function<java.util.stream.IntStream, java.util.stream.DoubleStream> func) {
		return new IODoubleStream(supplier, func);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.IntStream> castToIntStream(
		IOIntFunction<? extends java.util.stream.IntStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.LongStream> castToLongStream(
		IOIntFunction<? extends java.util.stream.LongStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IOIntFunction<? extends java.util.stream.DoubleStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override IntUnaryOperator castToInt(IOToIntFunction<Integer> mapper) {
		return mapper.uncheck(classOfE())::applyAsInt;
	}
	protected @Override IntToLongFunction castToLong(IOToLongFunction<Integer> mapper) {
		return mapper.uncheck(classOfE())::applyAsLong;
	}
	protected @Override IntToDoubleFunction castToDouble(IOToDoubleFunction<Integer> mapper) {
		return mapper.uncheck(classOfE())::applyAsDouble;
	}
	protected @Override IntBinaryOperator castToBinaryOperators(IOIntBinaryOperator combiner) {
		return combiner.uncheck(classOfE());
	}
	protected @Override IntConsumer castToConsumers(IOIntConsumer action) {
		return action.uncheck(classOfE());
	}
	protected @Override IntPredicate castToPredicates(IOIntPredicate test) {
		return test.uncheck(classOfE());
	}
	public <R> IOStream<R> map(IOIntFunction<? extends R> mapper) {
		return mapInternal(mapper.uncheck(), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			IOIntStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IOIntFunction<? extends IOStream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
		IntFunction<? extends IOStream<? extends R>> mapper,
		IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			IOIntStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, int[]> toMap(IOIntFunction<? extends K> classifier) throws IOException {
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
	private static <R> IntFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		IntFunction<? extends IOStream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.IntStream, java.util.stream.Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}