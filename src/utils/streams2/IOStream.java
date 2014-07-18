package utils.streams2;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import utils.lists2.ArrayList;
import utils.lists2.Arrays;
import utils.lists2.HashMap;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.IOBinaryOperator;
import utils.streams.functions.IOConsumer;
import utils.streams.functions.IOFunction;
import utils.streams.functions.IOPredicate;
import utils.streams.functions.IOToDoubleFunction;
import utils.streams.functions.IOToIntFunction;
import utils.streams.functions.IOToLongFunction;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;

//*Q*
public class IOStream<T> extends AbstractStream<T, IOException,
java.util.stream.Stream<T>, IOStream<T>,
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
	public IOStream(Supplier<java.util.stream.Stream<T>> supplier) {
		super(supplier);
	}
	<OLD> IOStream(Supplier<OLD> older, Function<OLD, java.util.stream.Stream<T>> converter) {
		super(older, converter);
	}
	protected @Override Class<IOException> classOfE() {
		return IOException.class;
	}
	protected @Override java.util.stream.Stream<T> castToStream(java.util.stream.Stream<T> stream) {
		return stream;
	}
	protected @Override IOStream<T> asSELF(Function<java.util.stream.Stream<T>, java.util.stream.Stream<T>> func) {
		return new IOStream<>(supplier, func);
	}
	protected @Override IOIntStream asIS(Function<java.util.stream.Stream<T>, java.util.stream.IntStream> func) {
		return new IOIntStream(supplier, func);
	}
	protected @Override IOLongStream asLS(Function<java.util.stream.Stream<T>, java.util.stream.LongStream> func) {
		return new IOLongStream(supplier, func);
	}
	protected @Override IODoubleStream asDS(Function<java.util.stream.Stream<T>, java.util.stream.DoubleStream> func) {
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
	public <R> IOStream<R> map(IOFunction<? super T, ? extends R> mapper) {
		return mapInternal(mapper.uncheck(), cast());
	}
	public final @SafeVarargs <R> IOStream<R> map(Function<? super T, ? extends R> mapper, Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			IOStream<T> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return mapInternal(mapper, stream.cast());
		}
		return mapInternal(mapper, cast());
	}
	public <R> IOStream<R> flatMap(IOFunction<? super T, ? extends IOStream<? extends R>> mapper) {
		return flatMapInternal(castToFlatMapFunctions(mapper.uncheck()), cast());
	}
	public final @SafeVarargs <R> IOStream<R> flatMap(
		Function<? super T, ? extends IOStream<? extends R>> mapper,
		Predicate<T>... allowed) {
		if(allowed != null && allowed.length > 0) {
			IOStream<T> stream = filter(allowed[0], Arrays.copyOfRange(allowed, 1, allowed.length));
			return flatMapInternal(castToFlatMapFunctions(mapper), stream.cast());
		}
		return flatMapInternal(castToFlatMapFunctions(mapper), cast());
	}
	public <K> HashMap<K, ArrayList<T>> toMap(IOFunction<? super T, ? extends K> classifier) throws IOException {
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
		Function<? super T, ? extends IOStream<? extends R>> mapper) {
		return t -> mapper.apply(t).maker().get();
	}
	private <R> Function<Function<java.util.stream.Stream<T>, java.util.stream.Stream<R>>, IOStream<R>> cast() {
		return f -> new IOStream<>(supplier, f);
	}
}
