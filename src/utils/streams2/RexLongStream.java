package utils.streams2;

import java.util.stream.Stream;
import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.BiFunction;
import utils.streams.functions.Consumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExLongBinaryOperator;
import utils.streams.functions.ExObjLongConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;
import utils.streams.functions.Function;
import utils.streams.functions.LongBinaryOperator;
import utils.streams.functions.LongConsumer;
import utils.streams.functions.LongFunction;
import utils.streams.functions.LongPredicate;
import utils.streams.functions.LongToDoubleFunction;
import utils.streams.functions.LongToIntFunction;
import utils.streams.functions.LongUnaryOperator;
import utils.streams.functions.Supplier;

//*Q*
public final class RexLongStream<E extends Exception, A extends AutoCloseable> extends AbstractLongStream<E,
AutoCloseableStrategy<A, java.util.stream.LongStream>,
RexStream<E, A, Long>,
RexIntStream<E, A>,
RexLongStream<E, A>,
RexDoubleStream<E, A>,
ExObjLongConsumer<A, E>,
ExBiPredicate<A, Long, E>,
Function<A, ExLongBinaryOperator<E>>,
ExBiFunction<A, Long, ? extends java.util.stream.IntStream, E>,
ExBiFunction<A, Long, ? extends java.util.stream.LongStream, E>,
ExBiFunction<A, Long, ? extends java.util.stream.DoubleStream, E>,
ExToIntBiFunction<A, Long, E>,
ExToLongBiFunction<A, Long, E>,
ExToDoubleBiFunction<A, Long, E>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, java.util.stream.LongStream>> supplierAC;
	private final Class<E> classOfE;

	public RexLongStream(Class<E> classOfE, Supplier<A> allocator, Function<A, java.util.stream.LongStream> converter,
		Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())), classOfE);
	}
	<OLD> RexLongStream(Class<E> classOfE, Supplier<AutoCloseableStrategy<A, OLD>> old,
		Function<OLD, java.util.stream.LongStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)), classOfE);
	}
	private RexLongStream(Supplier<AutoCloseableStrategy<A, java.util.stream.LongStream>> supplierAC, Class<E> classOfE) {
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
	protected @Override java.util.stream.LongStream castToStream(
		AutoCloseableStrategy<A, java.util.stream.LongStream> strategy) {
		return strategy.user;
	}
	protected @Override RexStream<E, A, Long> asOS(
		Function<java.util.stream.LongStream, java.util.stream.Stream<Long>> convert) {
		return new RexStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexIntStream<E, A> asIS(
		Function<java.util.stream.LongStream, java.util.stream.IntStream> convert) {
		return new RexIntStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexLongStream<E, A> asSELF(
		Function<java.util.stream.LongStream, java.util.stream.LongStream> convert) {
		return new RexLongStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexDoubleStream<E, A> asDS(
		Function<java.util.stream.LongStream, java.util.stream.DoubleStream> convert) {
		return new RexDoubleStream<>(classOfE, supplierAC, convert);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.IntStream> castToIntStream(
		ExBiFunction<A, Long, ? extends java.util.stream.IntStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.LongStream> castToLongStream(
		ExBiFunction<A, Long, ? extends java.util.stream.LongStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		ExBiFunction<A, Long, ? extends java.util.stream.DoubleStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override LongToIntFunction castToInt(ExToIntBiFunction<A, Long, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsInt(getCached(), t);
	}
	protected @Override LongUnaryOperator castToLong(ExToLongBiFunction<A, Long, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsLong(getCached(), t);
	}
	protected @Override LongToDoubleFunction castToDouble(ExToDoubleBiFunction<A, Long, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsDouble(getCached(), t);
	}
	protected @Override LongBinaryOperator castToBinaryOperators(Function<A, ExLongBinaryOperator<E>> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE);
	}
	protected @Override LongConsumer castToConsumers(ExObjLongConsumer<A, E> action) {
		return t -> action.uncheck(classOfE).accept(getCached(), t);
	}
	protected @Override LongPredicate castToPredicates(ExBiPredicate<A, Long, E> test) {
		return t -> test.uncheck(classOfE).test(getCached(), t);
	}
	public <R> RexStream<E, A, R> map(ExBiFunction<A, Long, ? extends R, E> mapper) {
		return mapInternal(castToMapFunctions(mapper.uncheck(classOfE())), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> map(
		BiFunction<A, Long, ? extends R> mapper,
		LongPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RexLongStream<E, A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(castToMapFunctions(mapper), stream.cast());
		}
		return mapInternal(castToMapFunctions(mapper), cast());
	}
	public <R> RexStream<E, A, R> flatMap(ExBiFunction<A, Long, ? extends RexStream<E, A, ? extends R>, E> mapper) {
		BiFunction<A, Long, ? extends RexStream<E, A, ? extends R>> mapper2 = mapper.uncheck(classOfE());
		LongFunction<? extends Stream<? extends R>> mapper3 = castToFlatMapFunctions(mapper2);
		return flatMapInternal(mapper3, cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> flatMap(
		BiFunction<A, Long, ? extends RexStream<E, A, ? extends R>> mapper,
		LongPredicate... allowed) {
		if(allowed != null && allowed.length > 0) {
			RexLongStream<E, A> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, long[]> toMap(ExBiFunction<A, Long, K, E> classifier) throws E {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
		ExBiFunction<A, Long, K, E> classifier,
		Function<long[], L> intoList,
		Function<HashMap<K, L>, M> intoMap) throws E {
		return toMultiMapInternal(classifier, castToClassifier(), intoList, intoMap);
	}
	private <K> Function<ExBiFunction<A, Long, K, E>, LongFunction<K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> LongFunction<? extends Stream<? extends R>> castToFlatMapFunctions(
		BiFunction<A, Long, ? extends RexStream<E, A, ? extends R>> mapper2) {
		return t -> mapper2.apply(getCached(), t).maker().get();
	}
	private <R> LongFunction<? extends R> castToMapFunctions(BiFunction<A, Long, ? extends R> mapper2) {
		return t -> mapper2.apply(getCached(), t);
	}
	private <R> Function<Function<java.util.stream.LongStream, java.util.stream.Stream<R>>, RexStream<E, A, R>> cast() {
		return f -> new RexStream<>(classOfE, supplierAC, f);
	}
}