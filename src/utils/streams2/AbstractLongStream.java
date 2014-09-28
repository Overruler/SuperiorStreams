package utils.streams2;

import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import utils.lists.ArrayList;
import utils.lists.HashMap;
import utils.lists.HashSet;
import utils.streams.functions.BiConsumer;
import utils.streams.functions.BiFunction;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.LongBinaryOperator;
import utils.streams.functions.LongConsumer;
import utils.streams.functions.LongFunction;
import utils.streams.functions.LongPredicate;
import utils.streams.functions.LongToDoubleFunction;
import utils.streams.functions.LongToIntFunction;
import utils.streams.functions.LongUnaryOperator;
import utils.streams.functions.ObjLongConsumer;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToLongFunction;
import static utils.streams2.Collectors.*;

//*Q*
abstract class AbstractLongStream<E extends Exception, STREAM, OS, IS,
SELF extends AbstractLongStream<E, STREAM, OS, IS,
SELF,
DS, CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>,
DS, CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE> {//*E*
	protected final Supplier<java.util.stream.LongStream> supplier;

	public AbstractLongStream(Supplier<STREAM> supplier) {
		this.supplier = () -> castToStream(supplier.get());
	}
	protected <OLD> AbstractLongStream(Supplier<OLD> older, Function<OLD, STREAM> converter) {
		this.supplier = () -> castToStream(converter.apply(older.get()));
	}
	protected Supplier<java.util.stream.LongStream> maker() {
		return supplier;
	}
	protected abstract LongStream castToStream(STREAM stream);
	protected abstract LongConsumer castToConsumers(CONSUMER action);
	protected abstract LongPredicate castToPredicates(PREDICATE test);
	protected abstract LongBinaryOperator castToBinaryOperators(BINARY_OPERATOR combiner);
	protected abstract Function<? super Long, ? extends IntStream> castToIntStream(TO_IS mapper);
	protected abstract Function<? super Long, ? extends LongStream> castToLongStream(TO_LS mapper);
	protected abstract Function<? super Long, ? extends DoubleStream> castToDoubleStream(TO_DS mapper);
	protected abstract LongToIntFunction castToInt(TO_INT mapper);
	protected abstract LongUnaryOperator castToLong(TO_LONG mapper);
	protected abstract LongToDoubleFunction castToDouble(TO_DOUBLE mapper);
	protected abstract Class<E> classOfE();
	protected abstract OS asOS(Function<java.util.stream.LongStream, java.util.stream.Stream<Long>> convert);
	protected abstract IS asIS(Function<java.util.stream.LongStream, java.util.stream.IntStream> convert);
	protected abstract SELF asSELF(Function<java.util.stream.LongStream, java.util.stream.LongStream> convert);
	protected abstract DS asDS(Function<java.util.stream.LongStream, java.util.stream.DoubleStream> convert);
	protected static <T> long toLong(T t) {
		return t instanceof Long ? (int) t : t == null ? 0 : Long.parseLong(String.valueOf(t));
	}
	public SELF filter(PREDICATE allowed) {
		LongPredicate predicate = castToPredicates(allowed);
		return asSELF(s -> s.filter(predicate));
	}
	public final @SafeVarargs SELF filter(LongPredicate allow, LongPredicate... allowed) {
		for(LongPredicate predicate : allowed) {
			allow = allow.and(predicate);
		}
		LongPredicate predicate = allow;
		return asSELF(s -> s.filter(predicate));
	}
	protected static <R, RS> RS mapInternal(
		LongFunction<? extends R> mapper,
		Function<Function<java.util.stream.LongStream, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.mapToObj(mapper));
	}
	public IS mapToInt(TO_INT mapper) {
		LongToIntFunction mapper2 = castToInt(mapper);
		return asIS(s -> s.mapToInt(mapper2));
	}
	public SELF mapToLong(TO_LONG mapper) {
		LongUnaryOperator mapper2 = castToLong(mapper);
		return asSELF(s -> s.map(mapper2));
	}
	public DS mapToDouble(TO_DOUBLE mapper) {
		LongToDoubleFunction mapper2 = castToDouble(mapper);
		return asDS(s -> s.mapToDouble(mapper2));
	}
	protected static <R, RS> RS flatMapInternal(
		LongFunction<? extends java.util.stream.Stream<? extends R>> mapper,
		Function<Function<java.util.stream.LongStream, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.boxed().flatMap(mapper::apply));
	}
	public IS flatMapToInt(TO_IS mapper) {
		Function<? super Long, ? extends IntStream> mapper2 = castToIntStream(mapper);
		return asIS(s -> s.boxed().flatMapToInt(mapper2));
	}
	public SELF flatMapToLong(TO_LS mapper) {
		Function<? super Long, ? extends LongStream> mapper2 = castToLongStream(mapper);
		return asSELF(s -> s.boxed().flatMapToLong(mapper2));
	}
	public DS flatMapToDouble(TO_DS mapper) {
		Function<? super Long, ? extends DoubleStream> mapper2 = castToDoubleStream(mapper);
		return asDS(s -> s.boxed().flatMapToDouble(mapper2));
	}
	public SELF distinct() {
		return asSELF(s -> s.distinct());
	}
	public SELF sorted() {
		return asSELF(s -> s.sorted());
	}
	public SELF peek(CONSUMER action) {
		LongConsumer consumer = castToConsumers(action);
		return asSELF(s -> s.peek(consumer));
	}
	public SELF limit(long maxSize) {
		return asSELF(s -> s.limit(maxSize));
	}
	public SELF skip(long n) {
		return asSELF(s -> s.skip(n));
	}
	public void forEach(CONSUMER action) throws E {
		LongConsumer consumer = castToConsumers(action);
		terminal(s -> s.forEach(consumer), maker(), classOfE());
	}
	public void forEachOrdered(CONSUMER action) throws E {
		LongConsumer consumer = castToConsumers(action);
		terminal(s -> s.forEachOrdered(consumer), maker(), classOfE());
	}
	public long[] toArray() throws E {
		return terminalAsObj(s -> s.toArray(), maker(), classOfE());
	}
	public ArrayList<Long> toList() throws E {
		Collector<Long, ArrayList<Long>, ArrayList<Long>> collector = Collectors.toList();
		return terminalAsObj(s -> s.boxed().collect(collector), maker(), classOfE());
	}
	public HashSet<Long> toSet() throws E {
		Collector<Long, HashSet<Long>, HashSet<Long>> collector = Collectors.toSet();
		return terminalAsObj(s -> s.boxed().collect(collector), maker(), classOfE());
	}
	public <L> L toList(Function<long[], L> intoList) throws E {
		return intoList.apply(toArray());
	}
	protected <K, L, M, CLASSIFIER> M toMultiMapInternal(
		CLASSIFIER classifier,
		Function<CLASSIFIER, LongFunction<? extends K>> cast,
		Function<long[], L> intoList,
		Function<HashMap<K, L>, M> intoMap) throws E {
		return terminalAsMapToList(cast.apply(classifier), intoMap, intoList, maker(), classOfE());
	}
	protected <K, CLASSIFIER> HashMap<K, long[]> toMapInternal(
		CLASSIFIER classifier,
		Function<CLASSIFIER, LongFunction<? extends K>> cast) throws E {
		return terminalAsMapToList(
			cast.apply(classifier),
			Function.identity(),
			Function.identity(),
			maker(),
			classOfE());
	}
	public long reduce(long identity, BINARY_OPERATOR accumulator) throws E {
		LongBinaryOperator operator = castToBinaryOperators(accumulator);
		return terminalAsLong(s -> s.reduce(identity, operator), maker(), classOfE());
	}
	public OptionalLong reduce(BINARY_OPERATOR accumulator) throws E {
		LongBinaryOperator operator = castToBinaryOperators(accumulator);
		return terminalAsObj(s -> s.reduce(operator), maker(), classOfE());
	}
	public <U> U reduce(U identity, BiFunction<U, ? super Long, U> accumulator, BinaryOperator<U> combiner) throws E {
		return terminalAsObj(s -> s.boxed().reduce(identity, accumulator, combiner), maker(), classOfE());
	}
	public <R> R collect(Supplier<R> initial, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) throws E {
		return terminalAsObj(s -> s.collect(initial, accumulator, combiner), maker(), classOfE());
	}
	public long sum() throws E {
		return terminalAsLong(s -> s.sum(), maker(), classOfE());
	}
	public OptionalLong min() throws E {
		return terminalAsObj(s -> s.min(), maker(), classOfE());
	}
	public OptionalLong max() throws E {
		return terminalAsObj(s -> s.max(), maker(), classOfE());
	}
	public long count() throws E {
		return terminalAsLong(s -> s.count(), maker(), classOfE());
	}
	public OptionalDouble average() throws E {
		return terminalAsObj(s -> s.average(), maker(), classOfE());
	}
	public LongSummaryStatistics summaryStatistics() throws E {
		return terminalAsObj(s -> s.summaryStatistics(), maker(), classOfE());
	}
	public boolean anyMatch(PREDICATE test) throws E {
		LongPredicate castToPredicates = castToPredicates(test);
		return terminalAsBoolean(s -> s.anyMatch(castToPredicates), maker(), classOfE());
	}
	public boolean allMatch(PREDICATE test) throws E {
		LongPredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.allMatch(predicate), maker(), classOfE());
	}
	public boolean noneMatch(PREDICATE test) throws E {
		LongPredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.noneMatch(predicate), maker(), classOfE());
	}
	public OptionalLong findFirst() throws E {
		return terminalAsObj(s -> s.findFirst(), maker(), classOfE());
	}
	public OptionalLong findAny() throws E {
		return terminalAsObj(s -> s.findAny(), maker(), classOfE());
	}
	public IS asIntStream() {
		return asIS(s -> s.mapToInt(AbstractIntStream::toInt));
	}
	public SELF asLongStream() {
		return asSELF(Function.identity());
	}
	public DS asDoubleStream() {
		return asDS(s -> s.mapToDouble(AbstractLongStream::toLong));
	}
	public OS boxed() {
		return asOS(s -> s.boxed());
	}
	public SELF sequential() {
		return asSELF(s -> s.sequential());
	}
	public SELF parallel() {
		return asSELF(s -> s.parallel());
	}
	public SELF concat(SELF after) {
		Objects.requireNonNull(after);
		return asSELF(s -> LongStream.concat(s, after.maker().get()));
	}
	private static <E extends Exception> void terminal(
		Consumer<LongStream> consumption,
		Supplier<LongStream> supplier,
		Class<E> class1) throws E {
		try(LongStream stream = supplier.get()) {
			consumption.accept(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <R, E extends Exception> R terminalAsObj(
		Function<LongStream, R> consumption,
		Supplier<LongStream> supplier,
		Class<E> class1) throws E {
		try(LongStream stream = supplier.get()) {
			return consumption.apply(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> long terminalAsLong(
		ToLongFunction<LongStream> consumption,
		Supplier<LongStream> supplier,
		Class<E> class1) throws E {
		try(LongStream stream = supplier.get()) {
			return consumption.applyAsLong(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> boolean terminalAsBoolean(
		Predicate<LongStream> consumption,
		Supplier<LongStream> supplier,
		Class<E> class1) throws E {
		try(LongStream stream = supplier.get()) {
			return consumption.test(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <K, L, M, E extends Exception> M terminalAsMapToList(
		LongFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<long[], L> intoList,
		Supplier<LongStream> supplier,
		Class<E> classOfE) throws E {
		Supplier<long[][]> supplier2 = () -> new long[64][];
		BiConsumer<long[][], Long> biConsumer = (left, value) -> {
			long[] array2 = new long[] { value == null ? 0 : value };
			for(int i1 = 0, n = left.length; i1 < n; i1++) {
				long[] array1 = left[i1];
				if(array1 == null) {
					left[i1] = array2;
					break;
				}
				long[] array = new long[array1.length + array2.length];
				System.arraycopy(array1, 0, array, 0, array1.length);
				System.arraycopy(array2, 0, array, array1.length, array2.length);
				array2 = array;
				left[i1] = null;
			}
		};
		BinaryOperator<long[][]> binaryOperator = (left, right) -> {
			long[][] container2 = new long[left.length + right.length][];
			System.arraycopy(left, 0, container2, 0, left.length);
			System.arraycopy(right, 0, container2, left.length, right.length);
			return container2;
		};
		Collector<Long, long[][], long[][]> toContainer = Collector.of(supplier2, biConsumer, binaryOperator);
		Function<? super long[][], ? extends long[]> before = left -> {
			int count = 0;
			for(long[] is : left) {
				if(is != null) {
					count = Math.addExact(count, is.length);
				}
			}
			long[] result = new long[count];
			count = 0;
			for(int i1 = left.length - 1; i1 >= 0; i1--) {
				long[] array2 = left[i1];
				if(array2 != null) {
					System.arraycopy(array2, 0, result, count, array2.length);
					count += array2.length;
				}
			}
			return result;
		};
		Collector<Long, long[][], L> collectingAndThen = collectingAndThen(toContainer, intoList.compose(before));
		Collector<Long, ?, M> collectMap =
			collectingAndThen(groupingBy(classifier::apply, HashMap<K, L>::new, collectingAndThen), intoMap);
		try(LongStream s = supplier.get()) {
			return s.boxed().collect(collectMap);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	private static <E extends Exception> E unwrapCause(Class<E> classOfE, RuntimeException e) throws E {
		Throwable cause = e.getCause();
		if(classOfE.isInstance(cause) == false) {
			throw e;
		}
		throw classOfE.cast(cause);
	}
}
