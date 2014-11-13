package utils.streams2;

import java.io.IOException;
import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.BiFunction;
import utils.streams.functions.Consumer;
import utils.streams.functions.DoubleBinaryOperator;
import utils.streams.functions.DoubleConsumer;
import utils.streams.functions.DoubleFunction;
import utils.streams.functions.DoublePredicate;
import utils.streams.functions.DoubleToIntFunction;
import utils.streams.functions.DoubleToLongFunction;
import utils.streams.functions.DoubleUnaryOperator;
import utils.streams.functions.Function;
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IODoubleBinaryOperator;
import utils.streams.functions.IOObjDoubleConsumer;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;
import utils.streams.functions.Supplier;

//*Q*
public final class RioDoubleStream<A extends AutoCloseable> extends AbstractDoubleStream<IOException,
AutoCloseableStrategy<A, java.util.stream.DoubleStream>,
RioStream<A, Double>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOObjDoubleConsumer<A>,
IOBiPredicate<A, Double>,
Function<A, IODoubleBinaryOperator>,
IOBiFunction<A, Double, ? extends java.util.stream.IntStream>,
IOBiFunction<A, Double, ? extends java.util.stream.LongStream>,
IOBiFunction<A, Double, ? extends java.util.stream.DoubleStream>,
IOToIntBiFunction<A, Double>,
IOToLongBiFunction<A, Double>,
IOToDoubleBiFunction<A, Double>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, java.util.stream.DoubleStream>> supplierAC;

	public RioDoubleStream(Supplier<A> allocator, Function<A, java.util.stream.DoubleStream> converter,
		Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())));
	}
	<OLD> RioDoubleStream(Supplier<AutoCloseableStrategy<A, OLD>> old,
		Function<OLD, java.util.stream.DoubleStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioDoubleStream(Supplier<AutoCloseableStrategy<A, java.util.stream.DoubleStream>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	public A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override java.util.stream.DoubleStream castToStream(
		AutoCloseableStrategy<A, java.util.stream.DoubleStream> strategy) {
		return strategy.user;
	}
	protected @Override RioStream<A, Double> asOS(
		Function<java.util.stream.DoubleStream, java.util.stream.Stream<Double>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	protected @Override RioIntStream<A>
		asIS(Function<java.util.stream.DoubleStream, java.util.stream.IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	protected @Override RioLongStream<A> asLS(
		Function<java.util.stream.DoubleStream, java.util.stream.LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	protected @Override RioDoubleStream<A> asSELF(
		Function<java.util.stream.DoubleStream, java.util.stream.DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.IntStream> castToIntStream(
		IOBiFunction<A, Double, ? extends java.util.stream.IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.LongStream> castToLongStream(
		IOBiFunction<A, Double, ? extends java.util.stream.LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IOBiFunction<A, Double, ? extends java.util.stream.DoubleStream> mapper) {
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
	public <R> RioStream<A, R> map(IOBiFunction<A, Double, ? extends R> mapper) {
		return mapInternal(castToMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(
		BiFunction<A, Double, ? extends R> mapper,
		DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioDoubleStream<A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(castToMapFunctions(mapper), stream.cast());
		}
		return mapInternal(castToMapFunctions(mapper), cast());
	}
	public <R> RioStream<A, R> flatMap(IOBiFunction<A, Double, ? extends RioStream<A, ? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
		BiFunction<A, Double, ? extends RioStream<A, ? extends R>> mapper,
		DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioDoubleStream<A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, double[]> toMap(IOBiFunction<A, Double, K> classifier) throws IOException {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		IOBiFunction<A, Double, K> classifier,
		Function<double[], L> intoList,
		Function<HashMap<K, L>, M> intoMap) throws IOException {
		return toMultiMapInternal(classifier, castToClassifier(), intoList, intoMap);
	}
	public IODoubleStream toIO() {
		return new IODoubleStream(() -> supplierAC.get().user);
	}
	private <K> Function<IOBiFunction<A, Double, K>, DoubleFunction<K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> DoubleFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		BiFunction<A, Double, ? extends RioStream<A, ? extends R>> mapper) {
		return t -> mapper.apply(getCached(), t).maker().get();
	}
	private <R> DoubleFunction<? extends R> castToMapFunctions(BiFunction<A, Double, ? extends R> mapping) {
		return t -> mapping.apply(getCached(), t);
	}
	private <R> Function<Function<java.util.stream.DoubleStream, java.util.stream.Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}