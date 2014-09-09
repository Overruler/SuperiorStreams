package utils.streams2;

import java.io.IOException;
import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.DoubleBinaryOperator;
import utils.streams.functions.DoubleConsumer;
import utils.streams.functions.DoubleFunction;
import utils.streams.functions.DoublePredicate;
import utils.streams.functions.DoubleToIntFunction;
import utils.streams.functions.DoubleToLongFunction;
import utils.streams.functions.DoubleUnaryOperator;
import utils.streams.functions.Function;
import utils.streams.functions.IODoubleBinaryOperator;
import utils.streams.functions.IODoubleConsumer;
import utils.streams.functions.IODoubleFunction;
import utils.streams.functions.IODoublePredicate;
import utils.streams.functions.IODoubleSupplier;
import utils.streams.functions.IODoubleUnaryOperator;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;
import utils.streams.functions.Supplier;

//*Q*
public final class IODoubleStream extends AbstractDoubleStream<IOException,
java.util.stream.DoubleStream,
IOStream<Double>,
IOIntStream,
IOLongStream,
IODoubleStream,
IODoubleConsumer,
IODoublePredicate,
IODoubleBinaryOperator,
IODoubleFunction<? extends java.util.stream.IntStream>,
IODoubleFunction<? extends java.util.stream.LongStream>,
IODoubleFunction<? extends java.util.stream.DoubleStream>,
IOToIntFunction<Double>,
IOToLongFunction<Double>,
IOToDoubleFunction<Double>> {//*E*
	public IODoubleStream(Supplier<java.util.stream.DoubleStream> supplier) {
		super(supplier);
	}
	<OLD> IODoubleStream(Supplier<OLD> older, Function<OLD, java.util.stream.DoubleStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override java.util.stream.DoubleStream castToStream(java.util.stream.DoubleStream stream) {
		return stream;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override IOStream<Double> asOS(
		Function<java.util.stream.DoubleStream, java.util.stream.Stream<Double>> func) {
		return new IOStream<>(supplier, func);
	}
	protected @Override IOIntStream asIS(Function<java.util.stream.DoubleStream, java.util.stream.IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	protected @Override IOLongStream asLS(Function<java.util.stream.DoubleStream, java.util.stream.LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	protected @Override IODoubleStream asSELF(
		Function<java.util.stream.DoubleStream, java.util.stream.DoubleStream> func) {
		return new IODoubleStream(supplier, func);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.IntStream> castToIntStream(
		IODoubleFunction<? extends java.util.stream.IntStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Double, ? extends java.util.stream.LongStream> castToLongStream(
		IODoubleFunction<? extends java.util.stream.LongStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Double, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IODoubleFunction<? extends java.util.stream.DoubleStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override DoubleToIntFunction castToInt(IOToIntFunction<Double> mapper) {
		return mapper.uncheck(classOfE())::applyAsInt;
	}
	protected @Override DoubleToLongFunction castToLong(IOToLongFunction<Double> mapper) {
		return mapper.uncheck(classOfE())::applyAsLong;
	}
	protected @Override DoubleUnaryOperator castToDouble(IOToDoubleFunction<Double> mapper) {
		return mapper.uncheck(classOfE())::applyAsDouble;
	}
	protected @Override DoubleBinaryOperator castToBinaryOperators(IODoubleBinaryOperator combiner) {
		return combiner.uncheck(classOfE());
	}
	protected @Override DoubleConsumer castToConsumers(IODoubleConsumer action) {
		return action.uncheck(classOfE());
	}
	protected @Override DoublePredicate castToPredicates(IODoublePredicate test) {
		return test.uncheck(classOfE());
	}
	public <R> IOStream<R> map(IODoubleFunction<? extends R> mapper) {
		return mapInternal(mapper.uncheck(), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			IODoubleStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IODoubleFunction<? extends IOStream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
		DoubleFunction<? extends IOStream<? extends R>> mapper,
		DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			IODoubleStream stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, double[]> toMap(IODoubleFunction<? extends K> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		IODoubleFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<double[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	public static IODoubleStream iterate(double seed, IODoubleUnaryOperator function) {
		return new IODoubleStream(() -> java.util.stream.DoubleStream.iterate(seed, function.uncheck()));
	}
	public static IODoubleStream generate(IODoubleSupplier supplier) {
		return new IODoubleStream(() -> java.util.stream.DoubleStream.generate(supplier.uncheck()));
	}
	private <K> Function<IODoubleFunction<? extends K>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> c.uncheck(classOfE());
	}
	private static <R> DoubleFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		DoubleFunction<? extends IOStream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.DoubleStream, java.util.stream.Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}