package utils.streams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
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
import utils.streams.functions.IOBiConsumer;
import utils.streams.functions.IOBiFunction;
import utils.streams.functions.IOBiPredicate;
import utils.streams.functions.IOBinaryOperator;
import utils.streams.functions.IOToDoubleBiFunction;
import utils.streams.functions.IOToIntBiFunction;
import utils.streams.functions.IOToLongBiFunction;

//*Q*
public final class RioStream<A extends AutoCloseable, T> extends AbstractStream<T, IOException,
AutoCloseableStrategy<A, Stream<T>>,
RioStream<A, T>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
IOBiConsumer<A, ? super T>,
IOBiPredicate<A, ? super T>,
Function<A, IOBinaryOperator<T>>,
Function<A, Comparator<? super T>>,
IOBiFunction<A, ? super T, ? extends IntStream>,
IOBiFunction<A, ? super T, ? extends LongStream>,
IOBiFunction<A, ? super T, ? extends DoubleStream>,
IOToIntBiFunction<A, ? super T>,
IOToLongBiFunction<A, ? super T>,
IOToDoubleBiFunction<A, ? super T>> {//*E*

	private final Supplier<AutoCloseableStrategy<A, Stream<T>>> supplierAC;

	public RioStream(Supplier<A> allocator, Function<A, Stream<T>> converter, Consumer<A> releaser) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(
		  allocator,
		  converter,
		  (a, s) -> s.onClose(() -> releaser.accept(a)),
		  Function.identity())));
	}
	<OLD> RioStream(Supplier<AutoCloseableStrategy<A, OLD>> old, Function<OLD, Stream<T>> converter) {
		this(CachedSupplier.create(() -> new AutoCloseableStrategy<>(old, converter)));
	}
	private RioStream(Supplier<AutoCloseableStrategy<A, Stream<T>>> supplierAC) {
		super(supplierAC);
		this.supplierAC = supplierAC;
	}
	public A getCached() {
		return supplierAC.get().resource;
	}
	public @Override Stream<T> castToStream(AutoCloseableStrategy<A, Stream<T>> strategy) {
		return strategy.user;
	}
	public @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	public @Override RioStream<A, T> asSELF(Function<Stream<T>, Stream<T>> convert) {
		return new RioStream<>(supplierAC, convert);
	}
	public @Override RioIntStream<A> asIS(Function<Stream<T>, IntStream> convert) {
		return new RioIntStream<>(supplierAC, convert);
	}
	public @Override RioLongStream<A> asLS(Function<Stream<T>, LongStream> convert) {
		return new RioLongStream<>(supplierAC, convert);
	}
	public @Override RioDoubleStream<A> asDS(Function<Stream<T>, DoubleStream> convert) {
		return new RioDoubleStream<>(supplierAC, convert);
	}
	public @Override Function<? super T, ? extends IntStream> castToIntStream(
	  IOBiFunction<A, ? super T, ? extends IntStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	public @Override Function<? super T, ? extends LongStream> castToLongStream(
	  IOBiFunction<A, ? super T, ? extends LongStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	public @Override Function<? super T, ? extends DoubleStream> castToDoubleStream(
	  IOBiFunction<A, ? super T, ? extends DoubleStream> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	public @Override ToIntFunction<? super T> castToInt(IOToIntBiFunction<A, ? super T> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	public @Override ToLongFunction<? super T> castToLong(IOToLongBiFunction<A, ? super T> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	public @Override ToDoubleFunction<? super T> castToDouble(IOToDoubleBiFunction<A, ? super T> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	public @Override BinaryOperator<T> castToBinaryOperators(Function<A, IOBinaryOperator<T>> combiner) {
		return combiner.apply(getCached()).uncheck(classOfE());
	}
	public @Override Comparator<? super T> castToComparators(Function<A, Comparator<? super T>> comparator) {
		return comparator.apply(getCached());
	}
	public @Override Consumer<? super T> castToConsumers(IOBiConsumer<A, ? super T> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	public @Override Predicate<? super T> castToPredicates(IOBiPredicate<A, ? super T> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> IOStream<R> map(IOBiFunction<A, ? super T, ? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(Function<? super T, ? extends R> mapper, Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IOBiFunction<A, ? super T, ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
	  Function<? super T, ? extends Stream<? extends R>> mapper,
	  Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<K, ArrayList<T>> toMap(IOBiFunction<A, ? super T, ? extends K> classifier) throws IOException {
		return toMapInternal(castToClassifier(classifier));
	}
	public final @SafeVarargs <K> Map<K, ArrayList<T>> toMap(
	  Function<? super T, ? extends K> classifier,
	  Predicate<T>... allowed) throws IOException {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMapInternal(classifier);
		}
		return toMapInternal(classifier);
	}
	public <K, L, M> M toMultiMap(
	  IOBiFunction<A, ? super T, ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList) throws IOException {
		return toMultiMapInternal(castToClassifier(classifier), intoMap, intoList);
	}
	public final @SafeVarargs <K, L, M> M toMultiMap(
	  Function<? super T, ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList,
	  Predicate<T>... allowed) throws IOException {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMultiMapInternal(
			  classifier,
			  intoMap,
			  intoList);
		}
		return toMultiMapInternal(classifier, intoMap, intoList);
	}
	private <K> Function<? super T, ? extends K> castToClassifier(IOBiFunction<A, ? super T, ? extends K> classifier) {
		return t -> classifier.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<? super T, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IOBiFunction<A, ? super T, ? extends Stream<? extends R>> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<? super T, ? extends R> castToMapFunctions(IOBiFunction<A, ? super T, ? extends R> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<Stream<T>, Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}
