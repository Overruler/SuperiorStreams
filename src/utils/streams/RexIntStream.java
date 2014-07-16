package utils.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExIntBinaryOperator;
import utils.streams.functions.ExObjIntConsumer;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RexIntStream<E extends Exception, A extends AutoCloseable> extends AbstractIntStream<E,
AutoCloseableStrategy<A, IntStream>,
RexStream<E, A, Integer>,
RexIntStream<E, A>,
RexLongStream<E, A>,
RexDoubleStream<E, A>,
ExObjIntConsumer<A, E>,
ExBiPredicate<A, Integer, E>,
Function<A, ExIntBinaryOperator<E>>,
ExBiFunction<A, Integer, ? extends IntStream, E>,
ExBiFunction<A, Integer, ? extends LongStream, E>,
ExBiFunction<A, Integer, ? extends DoubleStream, E>,
ExToIntBiFunction<A, Integer, E>,
ExToLongBiFunction<A, Integer, E>,
ExToDoubleBiFunction<A, Integer, E>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, IntStream>> supplierAC;
	private final Class<E> classOfE;

	public RexIntStream(Class<E> classOfE, Supplier<A> allocator, Function<A, IntStream> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())), classOfE);
	}
	<OLD> RexIntStream(Class<E> classOfE, Supplier<AutoCloseableStrategy<A, OLD>> old,
		Function<OLD, IntStream> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)), classOfE);
	}
	private RexIntStream(Supplier<AutoCloseableStrategy<A, IntStream>> supplierAC, Class<E> classOfE) {
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
	protected @Override IntStream castToStream(AutoCloseableStrategy<A, IntStream> strategy) {
		return strategy.user;
	}
	protected @Override RexStream<E, A, Integer> asOS(Function<IntStream, Stream<Integer>> convert) {
		return new RexStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexIntStream<E, A> asSELF(Function<IntStream, IntStream> convert) {
		return new RexIntStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexLongStream<E, A> asLS(Function<IntStream, LongStream> convert) {
		return new RexLongStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexDoubleStream<E, A> asDS(Function<IntStream, DoubleStream> convert) {
		return new RexDoubleStream<>(classOfE, supplierAC, convert);
	}
	protected @Override Function<? super Integer, ? extends IntStream> castToIntStream(
		ExBiFunction<A, Integer, ? extends IntStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends LongStream> castToLongStream(
		ExBiFunction<A, Integer, ? extends LongStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super Integer, ? extends DoubleStream> castToDoubleStream(
		ExBiFunction<A, Integer, ? extends DoubleStream, E> mapper) {
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
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> map(IntFunction<? extends R> mapper, IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
			mapper,
			filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RexStream<E, A, R> flatMap(ExBiFunction<A, Integer, ? extends Stream<? extends R>, E> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> flatMap(
		Function<Integer, ? extends Stream<? extends R>> mapper,
		IntPredicate... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
			mapper,
			filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast())
			: flatMapInternal(mapper, cast());
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
	private <R> Function<Integer, ? extends Stream<? extends R>> castToFlatMapFunctions(
		ExBiFunction<A, Integer, ? extends Stream<? extends R>, E> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> IntFunction<? extends R> castToMapFunctions(ExBiFunction<A, Integer, ? extends R, E> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<IntStream, Stream<R>>, RexStream<E, A, R>> cast() {
		return f -> new RexStream<>(classOfE, supplierAC, f);
	}
}