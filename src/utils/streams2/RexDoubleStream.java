package utils.streams2;

import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.BiFunction;
import utils.streams.functions.Consumer;
import utils.streams.functions.DoubleBinaryOperator;
import utils.streams.functions.DoubleConsumer;
import utils.streams.functions.DoubleFunction;
import utils.streams.functions.DoublePredicate;
import utils.streams.functions.DoubleToIntFunction;
import utils.streams.functions.DoubleToLongFunction;
import utils.streams.functions.DoubleUnaryOperator;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExDoubleBinaryOperator;
import utils.streams.functions.ExObjDoubleConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;
import utils.streams.functions.Function;
import utils.streams.functions.Supplier;

//*Q*
public final class RexDoubleStream<E extends Exception, A extends AutoCloseable> extends AbstractDoubleStream<E,
AutoCloseableStrategy<A, java.util.stream.DoubleStream>,
RexStream<E, A, Double>,
RexIntStream<E, A>,
RexLongStream<E, A>,
RexDoubleStream<E, A>,
ExObjDoubleConsumer<A, E>,
ExBiPredicate<A, Double, E>,
Function<A, ExDoubleBinaryOperator<E>>,
ExBiFunction<A, Double, ? extends java.util.stream.IntStream, E>,
ExBiFunction<A, Double, ? extends java.util.stream.LongStream, E>,
ExBiFunction<A, Double, ? extends java.util.stream.DoubleStream, E>,
ExToIntBiFunction<A, Double, E>,
ExToLongBiFunction<A, Double, E>,
ExToDoubleBiFunction<A, Double, E>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, java.util.stream.DoubleStream>> supplierAC;
	private final Class<E> classOfE;

	public RexDoubleStream(Class<E> classOfE, Supplier<A> allocator,
		Function<A, java.util.stream.DoubleStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())), classOfE);
	}
	<OLD> RexDoubleStream(Class<E> classOfE, Supplier<AutoCloseableStrategy<A, OLD>> old,
		Function<OLD, java.util.stream.DoubleStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)), classOfE);
	}
	private RexDoubleStream(Supplier<AutoCloseableStrategy<A, java.util.stream.DoubleStream>> supplierAC,
		Class<E> classOfE) {
		super(supplierAC);
		this.supplierAC = supplierAC;
		this.classOfE = classOfE;
	}
	private A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<E> classOfE() {
		return classOfE;
	}
	protected @Override java.util.stream.DoubleStream castToStream(
		AutoCloseableStrategy<A, java.util.stream.DoubleStream> strategy) {
		return strategy.user;
	}
	protected @Override RexStream<E, A, Double> asOS(
		Function<java.util.stream.DoubleStream, java.util.stream.Stream<Double>> convert) {
		return new RexStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexIntStream<E, A> asIS(
		Function<java.util.stream.DoubleStream, java.util.stream.IntStream> convert) {
		return new RexIntStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexLongStream<E, A> asLS(
		Function<java.util.stream.DoubleStream, java.util.stream.LongStream> convert) {
		return new RexLongStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexDoubleStream<E, A> asSELF(
		Function<java.util.stream.DoubleStream, java.util.stream.DoubleStream> convert) {
		return new RexDoubleStream<>(classOfE, supplierAC, convert);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.IntStream> castToIntStream(
		ExBiFunction<A, Double, ? extends java.util.stream.IntStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.LongStream> castToLongStream(
		ExBiFunction<A, Double, ? extends java.util.stream.LongStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Double, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		ExBiFunction<A, Double, ? extends java.util.stream.DoubleStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override DoubleToIntFunction castToInt(ExToIntBiFunction<A, Double, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsInt(getCached(), t);
	}
	protected @Override DoubleToLongFunction castToLong(ExToLongBiFunction<A, Double, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsLong(getCached(), t);
	}
	protected @Override DoubleUnaryOperator castToDouble(ExToDoubleBiFunction<A, Double, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsDouble(getCached(), t);
	}
	protected @Override DoubleConsumer castToConsumers(ExObjDoubleConsumer<A, E> action) {
		return t -> action.uncheck(classOfE).accept(getCached(), t);
	}
	protected @Override DoublePredicate castToPredicates(ExBiPredicate<A, Double, E> test) {
		return t -> test.uncheck(classOfE).test(getCached(), t);
	}
	protected @Override DoubleBinaryOperator castToBinaryOperators(Function<A, ExDoubleBinaryOperator<E>> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE);
	}
	public <R> RexStream<E, A, R> map(ExBiFunction<A, Double, ? extends R, E> mapping) {
		return mapInternal(castToMapFunctions(mapping.uncheck(classOfE)), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> map(
		BiFunction<A, Double, ? extends R> mapper,
		DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RexDoubleStream<E, A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(castToMapFunctions(mapper), stream.cast());
		}
		return mapInternal(castToMapFunctions(mapper), cast());
	}
	public <R> RexStream<E, A, R> flatMap(ExBiFunction<A, Double, ? extends RexStream<E, A, ? extends R>, E> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck(classOfE)), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> flatMap(
		BiFunction<A, Double, ? extends RexStream<E, A, ? extends R>> mapper,
		DoublePredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RexDoubleStream<E, A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<? extends K, double[]> toMap(ExBiFunction<A, Double, ? extends K, E> classifier) throws E {
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
	private <R> DoubleFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		BiFunction<A, Double, ? extends RexStream<E, A, ? extends R>> mapper) {
		return t -> mapper.apply(getCached(), t).maker().get();
	}
	private <R> DoubleFunction<? extends R> castToMapFunctions(BiFunction<A, Double, ? extends R> mapping) {
		return t -> mapping.apply(getCached(), t);
	}
	private <R> Function<Function<java.util.stream.DoubleStream, java.util.stream.Stream<R>>, RexStream<E, A, R>>
		cast() {
		return f -> new RexStream<>(classOfE, supplierAC, f);
	}
}