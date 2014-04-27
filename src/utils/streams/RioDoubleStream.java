package utils.streams;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
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
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IODoubleBinaryOperator;
import utils.streams.functions.IOObjDoubleConsumer;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;

//*Q*
public final class RioDoubleStream<A extends AutoCloseable> extends AbstractDoubleStream<IOException,
AutoCloseableStrategy<A, DoubleStream>,
RioStream<A, Double>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOObjDoubleConsumer<A>,
IOBiPredicate<A, Double>,
Function<A, IODoubleBinaryOperator>,
IOBiFunction<A, Double, ? extends IntStream>,
IOBiFunction<A, Double, ? extends LongStream>,
IOBiFunction<A, Double, ? extends DoubleStream>,
IOToIntBiFunction<A, Double>,
IOToLongBiFunction<A, Double>,
IOToDoubleBiFunction<A, Double>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, DoubleStream>> supplierAC;

	public RioDoubleStream(Supplier<A> allocator, Function<A, DoubleStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())));
	}
	<OLD> RioDoubleStream(Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, DoubleStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioDoubleStream(Supplier<AutoCloseableStrategy<A, DoubleStream>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	public A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override DoubleStream castToStream(AutoCloseableStrategy<A, DoubleStream> strategy) {
		return strategy.user;
	}
	protected @Override RioStream<A, Double> asOS(Function<DoubleStream, Stream<Double>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	protected @Override RioIntStream<A> asIS(Function<DoubleStream, IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	protected @Override RioLongStream<A> asLS(Function<DoubleStream, LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	protected @Override RioDoubleStream<A> asSELF(Function<DoubleStream, DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	protected @Override Function<? super Double, ? extends IntStream> castToIntStream(
	  IOBiFunction<A, Double, ? extends IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Double, ? extends LongStream> castToLongStream(
	  IOBiFunction<A, Double, ? extends LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Double, ? extends DoubleStream> castToDoubleStream(
	  IOBiFunction<A, Double, ? extends DoubleStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override DoubleToIntFunction castToInt(IOToIntBiFunction<A, Double> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	protected @Override DoubleToLongFunction castToLong(IOToLongBiFunction<A, Double> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	protected @Override DoubleUnaryOperator castToDouble(IOToDoubleBiFunction<A, Double> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	protected @Override DoubleBinaryOperator castToBinaryOperators(Function<A, IODoubleBinaryOperator> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE());
	}
	protected @Override DoubleConsumer castToConsumers(IOObjDoubleConsumer<A> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	protected @Override DoublePredicate castToPredicates(IOBiPredicate<A, Double> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> RioStream<A, R> map(IOBiFunction<A, Double, ? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RioStream<A, R> flatMap(IOBiFunction<A, Double, ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
	  Function<Double, ? extends Stream<? extends R>> mapper,
	  DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<? extends K, double[]> toMap(IOBiFunction<A, Double, ? extends K> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  IOBiFunction<A, Double, ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<double[], L> intoList) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<IOBiFunction<A, Double, ? extends K>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Double, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IOBiFunction<A, Double, ? extends Stream<? extends R>> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> DoubleFunction<? extends R> castToMapFunctions(IOBiFunction<A, Double, ? extends R> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<DoubleStream, Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}