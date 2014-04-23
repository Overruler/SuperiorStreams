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
import utils.streams.functions.ExBiConsumer;
import utils.streams.functions.ExBiFunction;
import utils.streams.functions.ExBiPredicate;
import utils.streams.functions.ExBinaryOperator;
import utils.streams.functions.ExToDoubleBiFunction;
import utils.streams.functions.ExToIntBiFunction;
import utils.streams.functions.ExToLongBiFunction;

//*Q*
public final class RioStream<A extends AutoCloseable, T> extends AbstractStream<T, IOException,
AutoCloseableStrategy<A, Stream<T>>,
RioStream<A, T>,
RioIntStream<A>,
RioLongStream<A>,
RioDoubleStream<A>,
ExBiConsumer<A, ? super T, IOException>,
ExBiPredicate<A, ? super T, IOException>, Function<A,
ExBinaryOperator<T, IOException>>,
Function<A, Comparator<? super T>>,
ExBiFunction<A, ? super T, ? extends IntStream, IOException>,
ExBiFunction<A, ? super T, ? extends LongStream, IOException>,
ExBiFunction<A, ? super T, ? extends DoubleStream, IOException>,
ExToIntBiFunction<A, ? super T, IOException>,
ExToLongBiFunction<A, ? super T, IOException>,
ExToDoubleBiFunction<A, ? super T, IOException>> {//*E*

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
	  ExBiFunction<A, ? super T, ? extends IntStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	public @Override Function<? super T, ? extends LongStream> castToLongStream(
	  ExBiFunction<A, ? super T, ? extends LongStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	public @Override Function<? super T, ? extends DoubleStream> castToDoubleStream(
	  ExBiFunction<A, ? super T, ? extends DoubleStream, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	public @Override ToIntFunction<? super T> castToInt(ExToIntBiFunction<A, ? super T, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsInt(getCached(), t);
	}
	public @Override ToLongFunction<? super T> castToLong(ExToLongBiFunction<A, ? super T, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsLong(getCached(), t);
	}
	public @Override ToDoubleFunction<? super T> castToDouble(ExToDoubleBiFunction<A, ? super T, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).applyAsDouble(getCached(), t);
	}
	public @Override BinaryOperator<T> castToBinaryOperators(Function<A, ExBinaryOperator<T, IOException>> combiner) {
  	return combiner.apply(getCached()).uncheck(classOfE());
  }
	public @Override Comparator<? super T> castToComparators(Function<A, Comparator<? super T>> comparator) {
  	return comparator.apply(getCached());
  }
	public @Override Consumer<? super T> castToConsumers(ExBiConsumer<A, ? super T, IOException> action) {
		return t -> action.uncheck(classOfE()).accept(getCached(), t);
	}
	public @Override Predicate<? super T> castToPredicates(ExBiPredicate<A, ? super T, IOException> test) {
		return t -> test.uncheck(classOfE()).test(getCached(), t);
	}
	public <R> IOStream<R> map(ExBiFunction<A, ? super T, ? extends R, IOException> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(Function<? super T, ? extends R> mapper, Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(ExBiFunction<A, ? super T, ? extends Stream<? extends R>, IOException> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
	  Function<? super T, ? extends Stream<? extends R>> mapper,
	  Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<K, ArrayList<T>> toMap(ExBiFunction<A, ? super T, ? extends K, IOException> classifier) throws IOException {
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
	  ExBiFunction<A, ? super T, ? extends K, IOException> classifier,
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
	private <K> Function<? super T, ? extends K> castToClassifier(ExBiFunction<A, ? super T, ? extends K, IOException> classifier) {
		return t -> classifier.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<? super T, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  ExBiFunction<A, ? super T, ? extends Stream<? extends R>, IOException> mapper) {
		return t -> mapper.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<? super T, ? extends R> castToMapFunctions(ExBiFunction<A, ? super T, ? extends R, IOException> mapping) {
		return t -> mapping.uncheck(classOfE()).apply(getCached(), t);
	}
	private <R> Function<Function<Stream<T>, Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}
