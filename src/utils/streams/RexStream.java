package utils.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.streams.functions.ExBiConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExBinaryOperator;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RexStream<E extends Exception, A extends AutoCloseable, T> extends AbstractStream<T, E,
AutoCloseableStrategy<A, Stream<T>>,
RexStream<E, A, T>,
RexIntStream<E, A>,
RexLongStream<E, A>,
RexDoubleStream<E, A>,
ExBiConsumer<A, ? super T, E>,
ExBiPredicate<A, ? super T, E>, Function<A,
ExBinaryOperator<T, E>>,
Function<A, Comparator<? super T>>,
ExBiFunction<A, ? super T, ? extends IntStream, E>,
ExBiFunction<A, ? super T, ? extends LongStream, E>,
ExBiFunction<A, ? super T, ? extends DoubleStream, E>,
ExToIntBiFunction<A, ? super T, E>,
ExToLongBiFunction<A, ? super T, E>,
ExToDoubleBiFunction<A, ? super T, E>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, Stream<T>>> supplierAC;
	private final Class<E> classOfE;

	public RexStream(Class<E> classOfE, Supplier<A> allocator, Function<A, Stream<T>> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())), classOfE);
	}
	<OLD> RexStream(Class<E> classOfE, Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, Stream<T>> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)), classOfE);
	}
	private RexStream(Supplier<AutoCloseableStrategy<A, Stream<T>>> supplierAC, Class<E> classOfE) {
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
	protected @Override Stream<T> castToStream(AutoCloseableStrategy<A, Stream<T>> strategy) {
		return strategy.user;
	}
	protected @Override RexStream<E, A, T> asSELF(Function<Stream<T>, Stream<T>> convert) {
		return new RexStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexIntStream<E, A> asIS(Function<Stream<T>, IntStream> convert) {
		return new RexIntStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexLongStream<E, A> asLS(Function<Stream<T>, LongStream> convert) {
		return new RexLongStream<>(classOfE, supplierAC, convert);
	}
	protected @Override RexDoubleStream<E, A> asDS(Function<Stream<T>, DoubleStream> convert) {
		return new RexDoubleStream<>(classOfE, supplierAC, convert);
	}
	protected @Override Function<? super T, ? extends IntStream> castToIntStream(
	  ExBiFunction<A, ? super T, ? extends IntStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super T, ? extends LongStream> castToLongStream(
	  ExBiFunction<A, ? super T, ? extends LongStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override Function<? super T, ? extends DoubleStream> castToDoubleStream(
	  ExBiFunction<A, ? super T, ? extends DoubleStream, E> mapper) {
		return t -> mapper.uncheck(classOfE).apply(getCached(), t);
	}
	protected @Override ToIntFunction<? super T> castToInt(ExToIntBiFunction<A, ? super T, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsInt(getCached(), t);
	}
	protected @Override ToLongFunction<? super T> castToLong(ExToLongBiFunction<A, ? super T, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsLong(getCached(), t);
	}
	protected @Override ToDoubleFunction<? super T> castToDouble(ExToDoubleBiFunction<A, ? super T, E> mapper) {
		return t -> mapper.uncheck(classOfE).applyAsDouble(getCached(), t);
	}
	protected @Override BinaryOperator<T> castToBinaryOperators(Function<A, ExBinaryOperator<T, E>> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE);
	}
	protected @Override Comparator<? super T> castToComparators(Function<A, Comparator<? super T>> comparator) {
		return comparator.apply(getCached());
	}
	protected @Override Consumer<? super T> castToConsumers(ExBiConsumer<A, ? super T, E> action) {
		return t -> action.uncheck(classOfE).accept(getCached(), t);
	}
	protected @Override Predicate<? super T> castToPredicates(ExBiPredicate<A, ? super T, E> test) {
		return t -> test.uncheck(classOfE).test(getCached(), t);
	}
	public <R> RexStream<E, A, R> map(ExBiFunction<A, ? super T, ? extends R, E> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R>
	  map(Function<? super T, ? extends R> mapper, Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> RexStream<E, A, R> flatMap(ExBiFunction<A, ? super T, ? extends Stream<? extends R>, E> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> RexStream<E, A, R> flatMap(
	  Function<? super T, ? extends Stream<? extends R>> mapper,
	  Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> HashMap<K, ArrayList<T>> toMap(ExBiFunction<A, ? super T, ? extends K, E> classifier) throws E {
		return toMapInternal(castToClassifier(classifier));
	}
	public final @SafeVarargs <K> HashMap<K, ArrayList<T>> toMap(
	  Function<? super T, ? extends K> classifier,
	  Predicate<T>... allowed) throws E {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMapInternal(classifier);
		}
		return toMapInternal(classifier);
	}
	public <K, L, M> M toMultiMap(
	  ExBiFunction<A, ? super T, ? extends K, E> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList) throws E {
		return toMultiMapInternal(castToClassifier(classifier), intoMap, intoList);
	}
	public final @SafeVarargs <K, L, M> M toMultiMap(
	  Function<? super T, ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList,
	  Predicate<T>... allowed) throws E {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMultiMapInternal(
			  classifier,
			  intoMap,
			  intoList);
		}
		return toMultiMapInternal(classifier, intoMap, intoList);
	}
	private <K> Function<? super T, ? extends K> castToClassifier(ExBiFunction<A, ? super T, ? extends K, E> classifier) {
		return t -> classifier.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<? super T, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  ExBiFunction<A, ? super T, ? extends Stream<? extends R>, E> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<? super T, ? extends R> castToMapFunctions(ExBiFunction<A, ? super T, ? extends R, E> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<Stream<T>, Stream<R>>, RexStream<E, A, R>> cast() {
		return f -> new RexStream<>(classOfE(), supplierAC, f);
	}
}
