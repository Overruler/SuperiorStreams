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

//*Q*
public class Stream2<T> extends AbstractStream<T, RuntimeException,
Stream<T>,
Stream2<T>,
IntStream2,
LongStream2,
DoubleStream2,
Consumer<? super T>,
Predicate<? super T>,
BinaryOperator<T>,
Comparator<? super T>,
Function<? super T, ? extends IntStream>,
Function<? super T, ? extends LongStream>,
Function<? super T, ? extends DoubleStream>,
ToIntFunction<? super T>,
ToLongFunction<? super T>,
ToDoubleFunction<? super T>> {//*E*

	public Stream2(Supplier<Stream<T>> supplier) {
		super(supplier);
	}
	<OLD> Stream2(Supplier<OLD> older, Function<OLD, Stream<T>> converter) {
		super(older, converter);
	}
	protected @Override Class<RuntimeException> classOfE() {
		return RuntimeException.class;
	}
	protected @Override Stream<T> castToStream(Stream<T> stream) {
		return stream;
	}
	protected @Override Stream2<T> asSELF(Function<Stream<T>, Stream<T>> func) {
		return new Stream2<>(supplier, func);
	}
	protected @Override IntStream2 asIS(Function<Stream<T>, IntStream> func) {
		return new IntStream2(supplier, func);
	}
	protected @Override LongStream2 asLS(Function<Stream<T>, LongStream> func) {
		return new LongStream2(supplier, func);
	}
	protected @Override DoubleStream2 asDS(Function<Stream<T>, DoubleStream> func) {
		return new DoubleStream2(supplier, func);
	}
	protected @Override Function<? super T, ? extends IntStream> castToIntStream(
	  Function<? super T, ? extends IntStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super T, ? extends LongStream> castToLongStream(
	  Function<? super T, ? extends LongStream> mapper) {
		return mapper;
	}
	protected @Override Function<? super T, ? extends DoubleStream> castToDoubleStream(
	  Function<? super T, ? extends DoubleStream> mapper) {
		return mapper;
	}
	protected @Override ToIntFunction<? super T> castToInt(ToIntFunction<? super T> mapper) {
		return mapper;
	}
	protected @Override ToLongFunction<? super T> castToLong(ToLongFunction<? super T> mapper) {
		return mapper;
	}
	protected @Override ToDoubleFunction<? super T> castToDouble(ToDoubleFunction<? super T> mapper) {
		return mapper;
	}
	protected @Override BinaryOperator<T> castToBinaryOperators(BinaryOperator<T> combiner) {
		return combiner;
	}
	protected @Override Comparator<? super T> castToComparators(Comparator<? super T> comparator) {
		return comparator;
	}
	protected @Override Consumer<? super T> castToConsumers(Consumer<? super T> action) {
		return action;
	}
	protected @Override Predicate<? super T> castToPredicates(Predicate<? super T> allowed) {
		return allowed;
	}
	public <R> Stream2<R> map(Function<? super T, ? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> Stream2<R> map(Function<? super T, ? extends R> mapper, Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> Stream2<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> Stream2<R> flatMap(
	  Function<? super T, ? extends Stream<? extends R>> mapper,
	  Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> HashMap<K, ArrayList<T>> toMap(Function<? super T, ? extends K> classifier) {
		return toMapInternal(castToClassifier(classifier));
	}
	public final @SafeVarargs <K> HashMap<K, ArrayList<T>> toMap(
	  Function<? super T, ? extends K> classifier,
	  Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMapInternal(classifier);
		}
		return toMapInternal(classifier);
	}
	public <K, L, M> M toMultiMap(
	  Function<? super T, ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList) {
		return toMultiMapInternal(castToClassifier(classifier), intoMap, intoList);
	}
	public final @SafeVarargs <K, L, M> M toMultiMap(
	  Function<? super T, ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList,
	  Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			return filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).toMultiMapInternal(
			  classifier,
			  intoMap,
			  intoList);
		}
		return toMultiMapInternal(classifier, intoMap, intoList);
	}
	private <K> Function<? super T, ? extends K> castToClassifier(Function<? super T, ? extends K> classifier) {
		return classifier;
	}
	private <R> Function<? super T, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  Function<? super T, ? extends Stream<? extends R>> mapper) {
		return mapper;
	}
	private <R> Function<? super T, ? extends R> castToMapFunctions(Function<? super T, ? extends R> mapping) {
		return mapping;
	}
	private <R> Function<Function<Stream<T>, Stream<R>>, Stream2<R>> cast() {
		return f -> new Stream2<>(supplier, f);
	}
}
