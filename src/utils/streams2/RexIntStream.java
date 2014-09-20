package utils.streams2;

import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.BiFunction;
import utils.streams.functions.Consumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExIntBinaryOperator;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;
import utils.streams.functions.Function;
import utils.streams.functions.IntBinaryOperator;
import utils.streams.functions.IntConsumer;
import utils.streams.functions.IntFunction;
import utils.streams.functions.IntPredicate;
import utils.streams.functions.IntToDoubleFunction;
import utils.streams.functions.IntToLongFunction;
import utils.streams.functions.IntUnaryOperator;
import utils.streams.functions.Supplier;

//*Q*
public final class RexIntStream<E extends Exception, A extends AutoCloseable> extends AbstractIntStream<E,
AutoCloseableStrategy<A, java.util.stream.IntStream>,
RexStream<E, A, Integer>,
RexIntStream<E, A>,
RexLongStream<E, A>,
RexDoubleStream<E, A>,
ExObjIntConsumer<A, E>,
ExBiPredicate<A, Integer, E>,
Function<A, ExIntBinaryOperator<E>>,
ExBiFunction<A, Integer, ? extends java.util.stream.IntStream, E>,
ExBiFunction<A, Integer, ? extends java.util.stream.LongStream, E>,
ExBiFunction<A, Integer, ? extends java.util.stream.DoubleStream, E>,
ExToIntBiFunction<A, Integer, E>,
ExToLongBiFunction<A, Integer, E>,
ExToDoubleBiFunction<A, Integer, E>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, java.util.stream.IntStream>> supplierAC;
	private final Class<E> classOfE;

	public RexIntStream(Class<E> classOfE, Supplier<A> allocator, Function<A, java.util.stream.IntStream> converter,
		Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())), classOfE);
	}
	<OLD> RexIntStream(Class<E> classOfE, Supplier<AutoCloseableStrategy<A, OLD>> old,
		Function<OLD, java.util.stream.IntStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)), classOfE);
	}
	private RexIntStream(Supplier<AutoCloseableStrategy<A, java.util.stream.IntStream>> supplierAC, Class<E> classOfE) {
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
	protected @Override java.util.stream.IntStream castToStream(
		AutoCloseableStrategy<A, java.util.stream.IntStream> strategy) {
		return strategy.user;
	}
	protected @Override RexStream<E, A, Integer> asOS(
		Function<java.util.stream.IntStream, java.util.stream.Stream<Integer>> convert) {
		return new RexStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexIntStream<E, A> asSELF(
		Function<java.util.stream.IntStream, java.util.stream.IntStream> convert) {
		return new RexIntStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexLongStream<E, A> asLS(
		Function<java.util.stream.IntStream, java.util.stream.LongStream> convert) {
		return new RexLongStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexDoubleStream<E, A> asDS(
		Function<java.util.stream.IntStream, java.util.stream.DoubleStream> convert) {
		return new RexDoubleStream<>(classOfE, supplierAC, convert);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.IntStream> castToIntStream(
		ExBiFunction<A, Integer, ? extends java.util.stream.IntStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.LongStream> castToLongStream(
		ExBiFunction<A, Integer, ? extends java.util.stream.LongStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		ExBiFunction<A, Integer, ? extends java.util.stream.DoubleStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override IntUnaryOperator castToInt(ExToIntBiFunction<A, Integer, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsInt(getCached(), t);
	}
	protected @Override IntToLongFunction castToLong(ExToLongBiFunction<A, Integer, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsLong(getCached(), t);
	}
	protected @Override IntToDoubleFunction castToDouble(ExToDoubleBiFunction<A, Integer, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsDouble(getCached(), t);
	}
	protected @Override IntBinaryOperator castToBinaryOperators(Function<A, ExIntBinaryOperator<E>> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE);
	}
	protected @Override IntConsumer castToConsumers(ExObjIntConsumer<A, E> action) {
		return t -> action.uncheck(classOfE).accept(getCached(), t);
	}
	protected @Override IntPredicate castToPredicates(ExBiPredicate<A, Integer, E> test) {
		return t -> test.uncheck(classOfE).test(getCached(), t);
	}
	public <R> RexStream<E, A, R> map(ExBiFunction<A, Integer, ? extends R, E> mapping) {
		return mapInternal(castToMapFunctions(mapping.uncheck(classOfE)), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> map(
		BiFunction<A, Integer, ? extends R> mapper,
		IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RexIntStream<E, A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(castToMapFunctions(mapper), stream.cast());
		}
		return mapInternal(castToMapFunctions(mapper), cast());
	}
	public <R> RexStream<E, A, R> flatMap(ExBiFunction<A, Integer, ? extends RexStream<E, A, ? extends R>, E> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck(classOfE)), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> flatMap(
		BiFunction<A, Integer, ? extends RexStream<E, A, ? extends R>> mapper,
		IntPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RexIntStream<E, A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, int[]> toMap(ExBiFunction<A, Integer, ? extends K, E> classifier) throws E {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		ExBiFunction<A, Integer, ? extends K, E> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<int[], L> intoList) throws E {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<ExBiFunction<A, Integer, ? extends K, E>, IntFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> IntFunction<? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		BiFunction<A, Integer, ? extends RexStream<E, A, ? extends R>> mapper) {
		return t -> mapper.apply(getCached(), t).maker().get();
	}
	private <R> IntFunction<? extends R> castToMapFunctions(BiFunction<A, Integer, ? extends R> mapping) {
		return t -> mapping.apply(getCached(), t);
	}
	private <R> Function<Function<java.util.stream.IntStream, java.util.stream.Stream<R>>, RexStream<E, A, R>> cast() {
		return f -> new RexStream<>(classOfE, supplierAC, f);
	}
}