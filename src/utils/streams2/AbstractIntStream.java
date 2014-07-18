package utils.streams2;

import java.util.IntSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import utils.lists2.ArrayList;
import utils.lists2.HashMap;
import utils.lists2.HashSet;
import utils.streams.functions.BiConsumer;
import utils.streams.functions.BiFunction;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.Function;
import utils.streams.functions.IntBinaryOperator;
import utils.streams.functions.IntConsumer;
import utils.streams.functions.IntFunction;
import utils.streams.functions.IntPredicate;
import utils.streams.functions.IntToDoubleFunction;
import utils.streams.functions.IntToLongFunction;
import utils.streams.functions.IntUnaryOperator;
import utils.streams.functions.ObjIntConsumer;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;
import static utils.streams2.Collectors.*;

//*Q*
abstract class AbstractIntStream<E extends Exception, STREAM, OS,
SELF extends AbstractIntStream<E, STREAM, OS,
SELF,
LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>,
LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE> {//*E*
	protected final Supplier<java.util.stream.IntStream> supplier;

	public AbstractIntStream(Supplier<STREAM> supplier) {
		this.supplier = () -> castToStream(supplier.get());
	}
	protected <OLD> AbstractIntStream(Supplier<OLD> older, Function<OLD, STREAM> converter) {
		this.supplier = () -> castToStream(converter.apply(older.get()));
	}
	protected Supplier<java.util.stream.IntStream> maker() {
		return supplier;
	}
	protected abstract IntStream castToStream(STREAM stream);
	protected abstract IntConsumer castToConsumers(CONSUMER action);
	protected abstract IntPredicate castToPredicates(PREDICATE test);
	protected abstract IntBinaryOperator castToBinaryOperators(BINARY_OPERATOR combiner);
	protected abstract Function<? super Integer, ? extends java.util.stream.IntStream> castToIntStream(TO_IS mapper);
	protected abstract Function<? super Integer, ? extends java.util.stream.LongStream> castToLongStream(TO_LS mapper);
	protected abstract Function<? super Integer, ? extends java.util.stream.DoubleStream> castToDoubleStream(
		TO_DS mapper);
	protected abstract IntUnaryOperator castToInt(TO_INT mapper);
	protected abstract IntToLongFunction castToLong(TO_LONG mapper);
	protected abstract IntToDoubleFunction castToDouble(TO_DOUBLE mapper);
	protected abstract Class<E> classOfE();
	protected abstract OS asOS(Function<java.util.stream.IntStream, java.util.stream.Stream<Integer>> convert);
	protected abstract SELF asSELF(Function<java.util.stream.IntStream, java.util.stream.IntStream> convert);
	protected abstract LS asLS(Function<java.util.stream.IntStream, java.util.stream.LongStream> convert);
	protected abstract DS asDS(Function<java.util.stream.IntStream, java.util.stream.DoubleStream> convert);
	protected static <T> int toInt(T t) {
		try {
			return t instanceof Integer ? (int) t : t == null ? 0 : Integer.parseInt(String.valueOf(t));
		} catch(NumberFormatException ignored) {
			return 0;
		}
	}
	public SELF filter(PREDICATE allowed) {
		IntPredicate predicate = castToPredicates(allowed);
		return asSELF(s -> s.filter(predicate));
	}
	public final @SafeVarargs SELF filter(IntPredicate allow, IntPredicate... allowed) {
		for(IntPredicate predicate : allowed) {
			allow = allow.and(predicate);
		}
		IntPredicate predicate = allow;
		return asSELF(s -> s.filter(predicate));
	}
	protected static <R, RS> RS mapInternal(
		IntFunction<? extends R> mapper,
		Function<Function<java.util.stream.IntStream, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.mapToObj(mapper));
	}
	public SELF mapToInt(TO_INT mapper) {
		IntUnaryOperator mapper2 = castToInt(mapper);
		return asSELF(s -> s.map(mapper2));
	}
	public LS mapToLong(TO_LONG mapper) {
		IntToLongFunction mapper2 = castToLong(mapper);
		return asLS(s -> s.mapToLong(mapper2));
	}
	public DS mapToDouble(TO_DOUBLE mapper) {
		IntToDoubleFunction mapper2 = castToDouble(mapper);
		return asDS(s -> s.mapToDouble(mapper2));
	}
	protected static <R, RS> RS flatMapInternal(
		IntFunction<? extends java.util.stream.Stream<? extends R>> mapper,
		Function<Function<java.util.stream.IntStream, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.boxed().flatMap(mapper::apply));
	}
	public SELF flatMapToInt(TO_IS mapper) {
		Function<? super Integer, ? extends java.util.stream.IntStream> mapper2 = castToIntStream(mapper);
		return asSELF(s -> s.boxed().flatMapToInt(mapper2));
	}
	public LS flatMapToLong(TO_LS mapper) {
		Function<? super Integer, ? extends java.util.stream.LongStream> mapper2 = castToLongStream(mapper);
		return asLS(s -> s.boxed().flatMapToLong(mapper2));
	}
	public DS flatMapToDouble(TO_DS mapper) {
		Function<? super Integer, ? extends java.util.stream.DoubleStream> mapper2 = castToDoubleStream(mapper);
		return asDS(s -> s.boxed().flatMapToDouble(mapper2));
	}
	public SELF distinct() {
		return asSELF(s -> s.distinct());
	}
	public SELF sorted() {
		return asSELF(s -> s.sorted());
	}
	public SELF peek(CONSUMER action) {
		IntConsumer consumer = castToConsumers(action);
		return asSELF(s -> s.peek(consumer));
	}
	public SELF limit(long maxSize) {
		return asSELF(s -> s.limit(maxSize));
	}
	public SELF skip(long n) {
		return asSELF(s -> s.skip(n));
	}
	public void forEach(CONSUMER action) throws E {
		IntConsumer consumer = castToConsumers(action);
		terminal(s -> s.forEach(consumer), maker(), classOfE());
	}
	public void forEachOrdered(CONSUMER action) throws E {
		IntConsumer consumer = castToConsumers(action);
		terminal(s -> s.forEachOrdered(consumer), maker(), classOfE());
	}
	public String toUnicodeString() throws E {
		int[] array = toArray();
		return new String(array, 0, array.length);
	}
	public int[] toArray() throws E {
		return terminalAsObj(s -> s.toArray(), maker(), classOfE());
	}
	public ArrayList<Integer> toList() throws E {
		Collector<Integer, ArrayList<Integer>, ArrayList<Integer>> collection =
			Collectors.<Integer, ArrayList<Integer>> toCollection(ArrayList::new);
		return terminalAsObj(s -> s.boxed().collect(collection), maker(), classOfE());
	}
	public HashSet<Integer> toSet() throws E {
		Collector<Integer, HashSet<Integer>, HashSet<Integer>> collection =
			Collectors.<Integer, HashSet<Integer>> toCollection(HashSet::new);
		return terminalAsObj(s -> s.boxed().collect(collection), maker(), classOfE());
	}
	public <L> L toList(Function<int[], L> intoList) throws E {
		return intoList.apply(toArray());
	}
	public <K, L, M, CLASSIFIER> M toMultiMapInternal(
		CLASSIFIER classifier,
		Function<CLASSIFIER, IntFunction<? extends K>> cast,
		Function<HashMap<K, L>, M> intoMap,
		Function<int[], L> intoList) throws E {
		return terminalAsMapToList(cast.apply(classifier), intoMap, intoList, maker(), classOfE());
	}
	public <K, CLASSIFIER> HashMap<K, int[]> toMapInternal(
		CLASSIFIER classifier,
		Function<CLASSIFIER, IntFunction<? extends K>> cast) throws E {
		return terminalAsMapToList(
			cast.apply(classifier),
			Function.identity(),
			Function.identity(),
			maker(),
			classOfE());
	}
	public int reduce(int identity, BINARY_OPERATOR accumulator) throws E {
		IntBinaryOperator operator = castToBinaryOperators(accumulator);
		return terminalAsInt(s -> s.reduce(identity, operator), maker(), classOfE());
	}
	public OptionalInt reduce(BINARY_OPERATOR accumulator) throws E {
		IntBinaryOperator operator = castToBinaryOperators(accumulator);
		return terminalAsObj(s -> s.reduce(operator), maker(), classOfE());
	}
	public <U> U reduce(U identity, BiFunction<U, ? super Integer, U> accumulator, BinaryOperator<U> combiner) throws E {
		return terminalAsObj(s -> s.boxed().reduce(identity, accumulator, combiner), maker(), classOfE());
	}
	public <R> R collect(Supplier<R> initial, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) throws E {
		return terminalAsObj(s -> s.collect(initial, accumulator, combiner), maker(), classOfE());
	}
	public int sum() throws E {
		return terminalAsInt(s -> s.sum(), maker(), classOfE());
	}
	public OptionalInt min() throws E {
		return terminalAsObj(s -> s.min(), maker(), classOfE());
	}
	public OptionalInt max() throws E {
		return terminalAsObj(s -> s.max(), maker(), classOfE());
	}
	public long count() throws E {
		return terminalAsLong(s -> s.count(), maker(), classOfE());
	}
	public OptionalDouble average() throws E {
		return terminalAsObj(s -> s.average(), maker(), classOfE());
	}
	public IntSummaryStatistics summaryStatistics() throws E {
		return terminalAsObj(s -> s.summaryStatistics(), maker(), classOfE());
	}
	public boolean anyMatch(PREDICATE test) throws E {
		IntPredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.anyMatch(predicate), maker(), classOfE());
	}
	public boolean allMatch(PREDICATE test) throws E {
		IntPredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.allMatch(predicate), maker(), classOfE());
	}
	public boolean noneMatch(PREDICATE test) throws E {
		IntPredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.noneMatch(predicate), maker(), classOfE());
	}
	public OptionalInt findFirst() throws E {
		return terminalAsObj(s -> s.findFirst(), maker(), classOfE());
	}
	public OptionalInt findAny() throws E {
		return terminalAsObj(s -> s.findAny(), maker(), classOfE());
	}
	public SELF asIntStream() {
		return asSELF(Function.identity());
	}
	public LS asLongStream() {
		return asLS(s -> s.mapToLong(AbstractLongStream::toLong));
	}
	public DS asDoubleStream() {
		return asDS(s -> s.mapToDouble(AbstractDoubleStream::toDouble));
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
		return asSELF(s -> java.util.stream.IntStream.concat(s, after.maker().get()));
	}
	private static <E extends Exception> void terminal(
		Consumer<java.util.stream.IntStream> consumption,
		Supplier<java.util.stream.IntStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.IntStream stream = supplier.get()) {
			consumption.accept(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <R, E extends Exception> R terminalAsObj(
		Function<java.util.stream.IntStream, R> consumption,
		Supplier<java.util.stream.IntStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.IntStream stream = supplier.get()) {
			return consumption.apply(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> long terminalAsLong(
		ToLongFunction<java.util.stream.IntStream> consumption,
		Supplier<java.util.stream.IntStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.IntStream stream = supplier.get()) {
			return consumption.applyAsLong(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> int terminalAsInt(
		ToIntFunction<IntStream> consumption,
		Supplier<IntStream> supplier,
		Class<E> class1) throws E {
		try(IntStream stream = supplier.get()) {
			return consumption.applyAsInt(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> boolean terminalAsBoolean(
		Predicate<IntStream> consumption,
		Supplier<IntStream> supplier,
		Class<E> class1) throws E {
		try(IntStream stream = supplier.get()) {
			return consumption.test(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <K, L, M, E extends Exception> M terminalAsMapToList(
		IntFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<int[], L> intoList,
		Supplier<java.util.stream.IntStream> supplier,
		Class<E> classOfE) throws E {
		Supplier<int[][]> supplier2 = () -> new int[64][];
		BiConsumer<int[][], Integer> biConsumer = (left, value) -> {
			int[] array2 = new int[] { value == null ? 0 : value };
			for(int i1 = 0, n = left.length; i1 < n; i1++) {
				int[] array1 = left[i1];
				if(array1 == null) {
					left[i1] = array2;
					break;
				}
				int[] array = new int[array1.length + array2.length];
				System.arraycopy(array1, 0, array, 0, array1.length);
				System.arraycopy(array2, 0, array, array1.length, array2.length);
				array2 = array;
				left[i1] = null;
			}
		};
		BinaryOperator<int[][]> binaryOperator = (left, right) -> {
			int[][] container2 = new int[left.length + right.length][];
			System.arraycopy(left, 0, container2, 0, left.length);
			System.arraycopy(right, 0, container2, left.length, right.length);
			return container2;
		};
		Collector<Integer, int[][], int[][]> toContainer = Collector.of(supplier2, biConsumer, binaryOperator);
		Function<? super int[][], ? extends int[]> before = left -> {
			int count = 0;
			for(int[] is : left) {
				if(is != null) {
					count = Math.addExact(count, is.length);
				}
			}
			int[] result = new int[count];
			count = 0;
			for(int i1 = left.length - 1; i1 >= 0; i1--) {
				int[] array2 = left[i1];
				if(array2 != null) {
					System.arraycopy(array2, 0, result, count, array2.length);
					count += array2.length;
				}
			}
			return result;
		};
		Collector<Integer, int[][], L> collectingAndThen = collectingAndThen(toContainer, intoList.compose(before));
		Collector<Integer, ?, M> collectMap =
			collectingAndThen(groupingBy(classifier::apply, HashMap<K, L>::new, collectingAndThen), intoMap);
		try(java.util.stream.IntStream s = supplier.get()) {
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
