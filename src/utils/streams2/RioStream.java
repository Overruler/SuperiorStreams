package utils.streams2;

import java.io.IOException;
import java.util.Comparator;
import utils.lists.ArrayList;
import utils.lists.Arrays;
import utils.lists.HashMap;
import utils.streams.functions.BiFunction;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.IOBiConsumer;
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IOBinaryOperator;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;

//*Q*
public final class RioStream<A extends AutoCloseable, T> extends AbstractStream<T, IOException,
AutoCloseableStrategy<A, java.util.stream.Stream<T>>,
RioStream<A, T>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOBiConsumer<A, ? super T>,
IOBiPredicate<A, ? super T>,
Function<A, IOBinaryOperator<T>>,
Function<A, Comparator<? super T>>,
IOBiFunction<A, ? super T, ? extends java.util.stream.IntStream>,
IOBiFunction<A, ? super T, ? extends java.util.stream.LongStream>,
IOBiFunction<A, ? super T, ? extends java.util.stream.DoubleStream>,
IOToIntBiFunction<A, ? super T>,
IOToLongBiFunction<A, ? super T>,
IOToDoubleBiFunction<A, ? super T>> {//*E*
	private final Supplier<AutoCloseableStrategy<A, java.util.stream.Stream<T>>> supplierAC;

	public RioStream(Supplier<A> allocator, Function<A, java.util.stream.Stream<T>> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
			allocator,
			converter,
			(a, s) -> s.onClose(() -> releaser.accept(a)),
			Function.identity())));
	}
	<OLD> RioStream(Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, java.util.stream.Stream<T>> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioStream(Supplier<AutoCloseableStrategy<A, java.util.stream.Stream<T>>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	private A getCached() {
		return supplierAC.get().resource;
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override java.util.stream.Stream<T> castToStream(
		AutoCloseableStrategy<A, java.util.stream.Stream<T>> strategy) {
		return strategy.user;
	}
	protected @Override RioStream<A, T>
		asSELF(Function<java.util.stream.Stream<T>, java.util.stream.Stream<T>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	protected @Override RioIntStream<A> asIS(Function<java.util.stream.Stream<T>, java.util.stream.IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	protected @Override RioLongStream<A>
		asLS(Function<java.util.stream.Stream<T>, java.util.stream.LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	protected @Override RioDoubleStream<A> asDS(
		Function<java.util.stream.Stream<T>, java.util.stream.DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	protected @Override Function<? super T, ? extends java.util.stream.IntStream> castToIntStream(
		IOBiFunction<A, ? super T, ? extends java.util.stream.IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super T, ? extends java.util.stream.LongStream> castToLongStream(
		IOBiFunction<A, ? super T, ? extends java.util.stream.LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override Function<? super T, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		IOBiFunction<A, ? super T, ? extends java.util.stream.DoubleStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	protected @Override ToIntFunction<? super T> castToInt(IOToIntBiFunction<A, ? super T> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	protected @Override ToLongFunction<? super T> castToLong(IOToLongBiFunction<A, ? super T> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	protected @Override ToDoubleFunction<? super T> castToDouble(IOToDoubleBiFunction<A, ? super T> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	protected @Override BinaryOperator<T> castToBinaryOperators(Function<A, IOBinaryOperator<T>> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE());
	}
	protected @Override Comparator<? super T> castToComparators(Function<A, Comparator<? super T>> comparator) {
		return comparator.apply(getCached());
	}
	protected @Override Consumer<? super T> castToConsumers(IOBiConsumer<A, ? super T> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	protected @Override Predicate<? super T> castToPredicates(IOBiPredicate<A, ? super T> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> RioStream<A, R> map(IOBiFunction<A, ? super T, ? extends R> mapper) {
		return mapInternal(castToMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> map(
		BiFunction<A, ? super T, ? extends R> mapper,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioStream<A, T> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(castToMapFunctions(mapper), stream.cast());
		}
		return mapInternal(castToMapFunctions(mapper), cast());
	}
	public <R> RioStream<A, R> flatMap(IOBiFunction<A, ? super T, ? extends RioStream<A, ? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> RioStream<A, R> flatMap(
		BiFunction<A, ? super T, ? extends RioStream<A, ? extends R>> mapper,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			RioStream<A, T> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, ArrayList<T>> toMap(IOBiFunction<A, ? super T, ? extends K> classifier) throws IOException {
		return toMapInternal(castToClassifier(classifier));
	}
	public final @SafeVarargs <K> HashMap<K, ArrayList<T>> toMap(
		Function<? super T, ? extends K> classifier,
		Predicate<T>... allowed) throws IOException {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMapInternal(classifier);
		}
		return toMapInternal(classifier);
	}
	public <K, V> HashMap<K, V> toMap(
		IOBiFunction<A, ? super T, ? extends K> keyMapper,
		IOBiFunction<A, ? super T, ? extends V> valueMapper) throws IOException {
		return collect(Collectors.toMap(castToClassifier(keyMapper), castToClassifier(valueMapper)));
	}
	public final @SafeVarargs <K, V> HashMap<K, V> toMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends V> valueMapper,
		Predicate<T>... allowed) throws IOException {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).collect(
				Collectors.toMap(keyMapper, valueMapper));
		}
		return collect(Collectors.toMap(keyMapper, valueMapper));
	}
	public <K, L, M> M toMultiMap(
		IOBiFunction<A, ? super T, ? extends K> classifier,
		Function<ArrayList<T>, L> intoList,
		Function<HashMap<K, L>, M> intoMap) throws IOException {
		return toMultiMapInternal(castToClassifier(classifier), intoList, intoMap);
	}
	public final @SafeVarargs <K, L, M> M toMultiMap(
		Function<? super T, ? extends K> classifier,
		Function<ArrayList<T>, L> intoList,
		Function<HashMap<K, L>, M> intoMap,
		Predicate<T>... allowed) throws IOException {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMultiMapInternal(
				classifier,
				intoList,
				intoMap);
		}
		return toMultiMapInternal(classifier, intoList, intoMap);
	}
	public <K, V> HashMap<K, ArrayList<V>> toMultiMap(
		IOBiFunction<A, ? super T, ? extends K> keyMapper,
		IOBiFunction<A, ? super T, ? extends V> valueMapper) throws IOException {
		return collect(Collectors.groupingBy(
			castToClassifier(keyMapper),
			Collectors.mapping(castToClassifier(valueMapper), Collectors.toList())));
	}
	public final @SafeVarargs <K, V> HashMap<K, ArrayList<V>> toMultiMap(
		Function<? super T, ? extends K> keyMapper,
		Function<? super T, ? extends V> valueMapper,
		Predicate<T>... allowed) throws IOException {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).collect(
				Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
		}
		return collect(Collectors.groupingBy(keyMapper, Collectors.mapping(valueMapper, Collectors.toList())));
	}
	public IOStream<T> toIO() {
		return new IOStream<>(() -> supplierAC.get().user);
	}
	private <K> Function<? super T, ? extends K> castToClassifier(IOBiFunction<A, ? super T, ? extends K> classifier) {
		return t -> classifier.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<? super T, ? extends java.util.stream.Stream<? extends R>> castToFlatMapFunctions(
		BiFunction<A, ? super T, ? extends RioStream<A, ? extends R>> mapper) {
		return t -> mapper.apply(getCached(), t).maker().get();
	}
	private <R> Function<? super T, ? extends R> castToMapFunctions(BiFunction<A, ? super T, ? extends R> mapper2) {
		return t -> mapper2.apply(getCached(), t);
	}
	private <R> Function<Function<java.util.stream.Stream<T>, java.util.stream.Stream<R>>, RioStream<A, R>> cast() {
		return f -> new RioStream<>(supplierAC, f);
	}
}
