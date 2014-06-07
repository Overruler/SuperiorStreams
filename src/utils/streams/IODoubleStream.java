package utils.streams;

import java.io.IOException;
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
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.IODoubleBinaryOperator;
import utils.streams.functions.IODoubleConsumer;
import utils.streams.functions.IODoubleFunction;
import utils.streams.functions.IODoublePredicate;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;

//*Q*
public final class IODoubleStream extends AbstractDoubleStream<IOException,
DoubleStream,
IOStream<Double>,
IOIntStream,
IOLongStream,
IODoubleStream,
IODoubleConsumer,
IODoublePredicate,
IODoubleBinaryOperator,
IODoubleFunction<? extends IntStream>,
IODoubleFunction<? extends LongStream>,
IODoubleFunction<? extends DoubleStream>,
IOToIntFunction<Double>,
IOToLongFunction<Double>,
IOToDoubleFunction<Double>> {//*E*

	public IODoubleStream(Supplier<DoubleStream> supplier) {
		super(supplier);
	}
	<OLD> IODoubleStream(Supplier<OLD> older, Function<OLD, DoubleStream> converter) {
		this(() -> converter.apply(older.get()));
	}
	protected @Override DoubleStream castToStream(DoubleStream stream) {
		return stream;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override IOStream<Double> asOS(Function<DoubleStream, Stream<Double>> func) {
		return new IOStream<>(supplier, func);
	}
	protected @Override IOIntStream asIS(Function<DoubleStream, IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	protected @Override IOLongStream asLS(Function<DoubleStream, LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	protected @Override IODoubleStream asSELF(Function<DoubleStream, DoubleStream> func) {
		return new IODoubleStream(supplier, func);
	}
	protected @Override Function<? super Double, ? extends IntStream> castToIntStream(
	  IODoubleFunction<? extends IntStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Double, ? extends LongStream> castToLongStream(
	  IODoubleFunction<? extends LongStream> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	protected @Override Function<? super Double, ? extends DoubleStream> castToDoubleStream(
	  IODoubleFunction<? extends DoubleStream> mapper) {
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
	public <R> IOStream<R> map(IODoubleFunction<? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IODoubleFunction<? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
	  DoubleFunction<? extends Stream<? extends R>> mapper,
	  DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper::apply,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(
		  mapper::apply,
		  cast());
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
	private <K> Function<IODoubleFunction<? extends K>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> c.uncheck(classOfE());
	}
	private <R> Function<Double, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IODoubleFunction<? extends Stream<? extends R>> mapper) {
		return mapper.uncheck(classOfE())::apply;
	}
	private <R> DoubleFunction<? extends R> castToMapFunctions(IODoubleFunction<? extends R> mapping) {
		return mapping.uncheck(classOfE());
	}
	private <R> Function<Function<DoubleStream, Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}