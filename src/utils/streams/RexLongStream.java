package utils.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExLongBinaryOperator;
import utils.streams.functions.ExObjLongConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RexLongStream<E extends Exception, A extends AutoCloseable> extends AbstractLongStream<E,
AutoCloseableStrategy<A, LongStream>,
RexStream<E, A, Long>,
RexIntStream<E, A>,
RexLongStream<E, A>,
RexDoubleStream<E, A>,
ExObjLongConsumer<A, E>,
ExBiPredicate<A, Long, E>,
Function<A, ExLongBinaryOperator<E>>,
ExBiFunction<A, Long, ? extends IntStream, E>,
ExBiFunction<A, Long, ? extends LongStream, E>,
ExBiFunction<A, Long, ? extends DoubleStream, E>,
ExToIntBiFunction<A, Long, E>,
ExToLongBiFunction<A, Long, E>,
ExToDoubleBiFunction<A, Long, E>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, LongStream>> supplierAC;
	private final Class<E> classOfE;

	public RexLongStream(Class<E> classOfE, Supplier<A> allocator, Function<A, LongStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())), classOfE);
	}
	<OLD> RexLongStream(Class<E> classOfE, Supplier<AutoCloseableStrategy<A, OLD>> old,
	  Function<OLD, LongStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)), classOfE);
	}
	private RexLongStream(Supplier<AutoCloseableStrategy<A, LongStream>> supplierAC, Class<E> classOfE) {
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
	protected @Override LongStream castToStream(AutoCloseableStrategy<A, LongStream> strategy) {
		return strategy.user;
	}
	protected @Override RexStream<E, A, Long> asOS(Function<LongStream, Stream<Long>> convert) {
		return new RexStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexIntStream<E, A> asIS(Function<LongStream, IntStream> convert) {
		return new RexIntStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexLongStream<E, A> asSELF(Function<LongStream, LongStream> convert) {
		return new RexLongStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexDoubleStream<E, A> asDS(Function<LongStream, DoubleStream> convert) {
		return new RexDoubleStream<>(classOfE, supplierAC, convert);
	}
	protected @Override Function<? super Long, ? extends IntStream> castToIntStream(
	  ExBiFunction<A, Long, ? extends IntStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends LongStream> castToLongStream(
	  ExBiFunction<A, Long, ? extends LongStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Long, ? extends DoubleStream> castToDoubleStream(
	  ExBiFunction<A, Long, ? extends DoubleStream, E> mapper) {
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
	public <R> RexStream<E, A, R> map(ExBiFunction<A, Long, ? extends R, E> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> map(LongFunction<? extends R> mapper, LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RexStream<E, A, R> flatMap(ExBiFunction<A, Long, ? extends Stream<? extends R>, E> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> flatMap(
	  Function<Long, ? extends Stream<? extends R>> mapper,
	  LongPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> HashMap<? extends K, long[]> toMap(ExBiFunction<A, Long, ? extends K, E> classifier) throws E {
		return toMapInternal(classifier, castToClassifier());
	}
	public <K, L, M> M toMultiMap(
	  ExBiFunction<A, Long, ? extends K, E> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<long[], L> intoList) throws E {
		return toMultiMapInternal(classifier, castToClassifier(), intoMap, intoList);
	}
	private <K> Function<ExBiFunction<A, Long, ? extends K, E>, LongFunction<? extends K>> castToClassifier() {
		return c -> t -> c.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Long, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  ExBiFunction<A, Long, ? extends Stream<? extends R>, E> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> LongFunction<? extends R> castToMapFunctions(ExBiFunction<A, Long, ? extends R, E> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<LongStream, Stream<R>>, RexStream<E, A, R>> cast() {
		return f -> new RexStream<>(classOfE, supplierAC, f);
	}
}