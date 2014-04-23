package utils.streams;

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
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExDoubleBinaryOperator;
import utils.streams.functions.ExObjDoubleConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RexDoubleStream<E extends Exception, A extends AutoCloseable> extends AbstractDoubleStream<E,
AutoCloseableStrategy<A, DoubleStream>,
RexStream<E, A, Double>,
RexIntStream<E, A>,
RexLongStream<E, A>,
RexDoubleStream<E, A>,
ExObjDoubleConsumer<A, E>,
ExBiPredicate<A, Double, E>,
Function<A, ExDoubleBinaryOperator<E>>,
ExBiFunction<A, Double, ? extends IntStream, E>,
ExBiFunction<A, Double, ? extends LongStream, E>,
ExBiFunction<A, Double, ? extends DoubleStream, E>,
ExToIntBiFunction<A, Double, E>,
ExToLongBiFunction<A, Double, E>,
ExToDoubleBiFunction<A, Double, E>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, DoubleStream>> supplierAC;
	private final Class<E> classOfE;

	public RexDoubleStream(Class<E> classOfE, Supplier<A> allocator, Function<A, DoubleStream> converter,
	  Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())), classOfE);
	}
	<OLD> RexDoubleStream(Class<E> classOfE, Supplier<AutoCloseableStrategy<A, OLD>> old,
	  Function<OLD, DoubleStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)), classOfE);
	}
	private RexDoubleStream(Supplier<AutoCloseableStrategy<A, DoubleStream>> supplierAC, Class<E> classOfE) {
		super(supplierAC);
		this.supplierAC = supplierAC;
		this.classOfE = classOfE;
	}
	public A getCached() {
		return supplierAC.get().resource;
	}
	public @Override DoubleStream castToStream(AutoCloseableStrategy<A, DoubleStream> strategy) {
		return strategy.user;
	}
	public @Override Class<E> classOfE() {
		return classOfE;
	}
	@Override
	public RexStream<E, A, Double> asOS(Function<DoubleStream, Stream<Double>> convert) {
		return new RexStream<>(classOfE, supplierAC, convert);
	}
	@Override
	public RexIntStream<E, A> asIS(Function<DoubleStream, IntStream> convert) {
		return new RexIntStream<>(classOfE, supplierAC, convert);
	}
	@Override
	public RexLongStream<E, A> asLS(Function<DoubleStream, LongStream> convert) {
		return new RexLongStream<>(classOfE, supplierAC, convert);
	}
	@Override
	public RexDoubleStream<E, A> asSELF(Function<DoubleStream, DoubleStream> convert) {
		return new RexDoubleStream<>(classOfE, supplierAC, convert);
	}
	@Override
	public Function<? super Double, ? extends IntStream> castToIntStream(
	  ExBiFunction<A, Double, ? extends IntStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	@Override
	public Function<? super Double, ? extends LongStream> castToLongStream(
	  ExBiFunction<A, Double, ? extends LongStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	@Override
	public Function<? super Double, ? extends DoubleStream> castToDoubleStream(
	  ExBiFunction<A, Double, ? extends DoubleStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	@Override
	public DoubleToIntFunction castToInt(ExToIntBiFunction<A, Double, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsInt(getCached(), t);
	}
	@Override
	public DoubleToLongFunction castToLong(ExToLongBiFunction<A, Double, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsLong(getCached(), t);
	}
	@Override
	public DoubleUnaryOperator castToDouble(ExToDoubleBiFunction<A, Double, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsDouble(getCached(), t);
	}
	@Override
	public DoubleConsumer castToConsumers(ExObjDoubleConsumer<A, E> action) {
		return t -> action.uncheck(classOfE).accept(getCached(), t);
	}
	@Override
	public DoublePredicate castToPredicates(ExBiPredicate<A, Double, E> test) {
		return t -> test.uncheck(classOfE).test(getCached(), t);
	}
	@Override
	public DoubleBinaryOperator castToBinaryOperators(Function<A, ExDoubleBinaryOperator<E>> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE);
	}
	public <R> RexStream<E, A, R> map(ExBiFunction<A, Double, ? extends R, E> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> map(DoubleFunction<? extends R> mapper, DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RexStream<E, A, R> flatMap(ExBiFunction<A, Double, ? extends Stream<? extends R>, E> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> flatMap(
	  Function<Double, ? extends Stream<? extends R>> mapper,
	  DoublePredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<? extends K, double[]> toMap(ExBiFunction<A, Double, ? extends K, E> classifier) throws E {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  ExBiFunction<A, Double, ? extends K, E> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<double[], L> intoList) throws E {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<ExBiFunction<A, Double, ? extends K, E>, DoubleFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Double, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  ExBiFunction<A, Double, ? extends Stream<? extends R>, E> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> DoubleFunction<? extends R> castToMapFunctions(ExBiFunction<A, Double, ? extends R, E> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<DoubleStream, Stream<R>>, RexStream<E, A, R>> cast() {
		return f -> new RexStream<>(classOfE, supplierAC, f);
	}
}