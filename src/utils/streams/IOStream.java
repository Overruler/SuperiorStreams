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
import utils.streams.functions.IOBinaryOperator;
import utils.streams.functions.IOConsumer;
import utils.streams.functions.IOFunction;
import utils.streams.functions.IOPredicate;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;

//*Q*
public class IOStream<T> extends AbstractStream<T, IOException,
Stream<T>, IOStream<T>,
IOIntStream,
IOLongStream,
IODoubleStream,
IOConsumer<? super T>,
IOPredicate<? super T>,
IOBinaryOperator<T>,
Comparator<? super T>,
IOFunction<? super T, ? extends IntStream>,
IOFunction<? super T, ? extends LongStream>,
IOFunction<? super T, ? extends DoubleStream>,
IOToIntFunction<? super T>,
IOToLongFunction<? super T>,
IOToDoubleFunction<? super T>> {//*E*

	public IOStream(Supplier<Stream<T>> supplier) {
		super(supplier);
	}
	<OLD> IOStream(Supplier<OLD> older, Function<OLD, Stream<T>> converter) {
		super(older, converter);
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override Stream<T> castToStream(Stream<T> stream) {
		return stream;
	}
	protected @Override IOStream<T> asSELF(Function<Stream<T>, Stream<T>> func) {
		return new IOStream<>(supplier, func);
	}
	protected @Override IOIntStream asIS(Function<Stream<T>, IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	protected @Override IOLongStream asLS(Function<Stream<T>, LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	protected @Override IODoubleStream asDS(Function<Stream<T>, DoubleStream> func) {
		return new IODoubleStream(supplier, func);
	}
	protected @Override Function<? super T, ? extends IntStream> castToIntStream(
	  IOFunction<? super T, ? extends IntStream> mapper) {
		return mapper.uncheck(classOfE());
	}
	protected @Override Function<? super T, ? extends LongStream> castToLongStream(
	  IOFunction<? super T, ? extends LongStream> mapper) {
		return mapper.uncheck(classOfE());
	}
	protected @Override Function<? super T, ? extends DoubleStream> castToDoubleStream(
	  IOFunction<? super T, ? extends DoubleStream> mapper) {
		return mapper.uncheck(classOfE());
	}
	protected @Override ToIntFunction<? super T> castToInt(IOToIntFunction<? super T> mapper) {
		return mapper.uncheck(classOfE());
	}
	protected @Override ToLongFunction<? super T> castToLong(IOToLongFunction<? super T> mapper) {
		return mapper.uncheck(classOfE());
	}
	protected @Override ToDoubleFunction<? super T> castToDouble(IOToDoubleFunction<? super T> mapper) {
		return mapper.uncheck(classOfE());
	}
	protected @Override BinaryOperator<T> castToBinaryOperators(IOBinaryOperator<T> combiner) {
		return combiner.uncheck(classOfE());
	}
	protected @Override Comparator<? super T> castToComparators(Comparator<? super T> comparator) {
		return comparator;
	}
	protected @Override Consumer<? super T> castToConsumers(IOConsumer<? super T> action) {
		return action.uncheck(classOfE());
	}
	protected @Override Predicate<? super T> castToPredicates(IOPredicate<? super T> allowed) {
		return allowed.uncheck(classOfE());
	}
	public <R> IOStream<R> map(IOFunction<? super T, ? extends R> mapping) {
		return mapInternal(castToMapFunctions(mapping), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(Function<? super T, ? extends R> mapper, Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? mapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IOFunction<? super T, ? extends Stream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
	  Function<? super T, ? extends Stream<? extends R>> mapper,
	  Predicate<T>... allowed) {
		return allowed != null && allowed.length > 0 ? flatMapInternal(
		  mapper,
		  filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length)).cast()) : flatMapInternal(mapper, cast());
	}
	public <K> Map<K, ArrayList<T>> toMap(IOFunction<? super T, ? extends K> classifier) throws IOException {
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
	  IOFunction<? super T, ? extends K> classifier,
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
	private <K> Function<? super T, ? extends K> castToClassifier(IOFunction<? super T, ? extends K> classifier) {
		return classifier.uncheck(IOException.class);
	}
	private <R> Function<? super T, ? extends Stream<? extends R>> castToFlatMapFunctions(
	  IOFunction<? super T, ? extends Stream<? extends R>> mapper) {
		return mapper.uncheck(IOException.class);
	}
	private <R> Function<? super T, ? extends R> castToMapFunctions(IOFunction<? super T, ? extends R> mapping) {
		return mapping.uncheck(IOException.class);
	}
	private <R> Function<Function<Stream<T>, Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}
