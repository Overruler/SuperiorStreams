package utils.streams;

import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

//*Q*
abstract class AbstractIntStream<E extends Exception, STREAM, OS,
SELF extends AbstractIntStream<E, STREAM, OS,
SELF,
LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>,
LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE> {//*E*

	protected final Supplier<IntStream> supplier;

	public AbstractIntStream(Supplier<STREAM> supplier) {
		this.supplier = () -> castToStream(supplier.get());
	}
	protected <OLD> AbstractIntStream(Supplier<OLD> older, Function<OLD, STREAM> converter) {
		this.supplier = () -> castToStream(converter.apply(older.get()));
	}
	public Supplier<IntStream> maker() {
		return supplier;
	}
	public abstract IntStream castToStream(STREAM stream);
	public abstract IntConsumer castToConsumers(CONSUMER action);
	public abstract IntPredicate castToPredicates(PREDICATE test);
	public abstract IntBinaryOperator castToBinaryOperators(BINARY_OPERATOR combiner);
	public abstract Function<? super Integer, ? extends IntStream> castToIntStream(TO_IS mapper);
	public abstract Function<? super Integer, ? extends LongStream> castToLongStream(TO_LS mapper);
	public abstract Function<? super Integer, ? extends DoubleStream> castToDoubleStream(TO_DS mapper);
	public abstract IntUnaryOperator castToInt(TO_INT mapper);
	public abstract IntToLongFunction castToLong(TO_LONG mapper);
	public abstract IntToDoubleFunction castToDouble(TO_DOUBLE mapper);
	public abstract Class<E> classOfE();
	public abstract OS asOS(Function<IntStream, Stream<Integer>> convert);
	public abstract SELF asSELF(Function<IntStream, IntStream> convert);
	public abstract LS asLS(Function<IntStream, LongStream> convert);
	public abstract DS asDS(Function<IntStream, DoubleStream> convert);
	public static <T> int toInt(T t) {
		try {
			return t instanceof Integer ? (int) t : t == null ? 0 : Integer.parseInt(String.valueOf(t));
		} catch(NumberFormatException ignored) {
			return 0;
		}
	}
	public SELF filter(PREDICATE allowed) {
		return filterInternal(castToPredicates(allowed));
	}
	public final @SafeVarargs SELF filter(IntPredicate allow, IntPredicate... allowed) {
		for(IntPredicate predicate : allowed) {
			allow = allow.and(predicate);
		}
		return filterInternal(allow);
	}
	protected SELF filterInternal(IntPredicate allowed) {
		return asSELF(s -> s.filter(allowed));
	}
	protected static <R, RS> RS mapInternal(
	  IntFunction<? extends R> mapper,
	  Function<Function<IntStream, Stream<R>>, RS> cast) {
		return cast.apply(s -> s.mapToObj(mapper));
	}
	public SELF mapToInt(TO_INT mapper) {
		return asSELF(s -> s.map(castToInt(mapper)));
	}
	public LS mapToLong(TO_LONG mapper) {
		return asLS(s -> s.mapToLong((IntToLongFunction) mapper));
	}
	public DS mapToDouble(TO_DOUBLE mapper) {
		return asDS(s -> s.mapToDouble(castToDouble(mapper)));
	}
	protected static <R, RS> RS flatMapInternal(
	  Function<? super Integer, ? extends Stream<? extends R>> mapper,
	  Function<Function<IntStream, Stream<R>>, RS> cast) {
		return cast.apply(s -> s.boxed().flatMap(mapper));
	}
	public SELF flatMapToInt(TO_IS mapper) {
		return asSELF(s -> s.boxed().flatMapToInt(castToIntStream(mapper)));
	}
	public LS flatMapToLong(TO_LS mapper) {
		return asLS(s -> s.boxed().flatMapToLong(castToLongStream(mapper)));
	}
	public DS flatMapToDouble(TO_DS mapper) {
		return asDS(s -> s.boxed().flatMapToDouble(castToDoubleStream(mapper)));
	}
	public SELF distinct() {
		return asSELF(s -> s.distinct());
	}
	public SELF sorted() {
		return asSELF(s -> s.sorted());
	}
	public SELF peek(CONSUMER action) {
		return asSELF(s -> s.peek(castToConsumers(action)));
	}
	public SELF limit(long maxSize) {
		return asSELF(s -> s.limit(maxSize));
	}
	public SELF skip(long n) {
		return asSELF(s -> s.skip(n));
	}
	public void forEach(CONSUMER action) throws E {
		terminal(s -> s.forEach(castToConsumers(action)), maker(), classOfE());
	}
	public void forEachOrdered(CONSUMER action) throws E {
		terminal(s -> s.forEachOrdered(castToConsumers(action)), maker(), classOfE());
	}
	public String toUnicodeString() throws E {
		int[] array = toArray();
		return new String(array, 0, array.length);
	}
	public int[] toArray() throws E {
		return terminalAsObj(s -> s.toArray(), maker(), classOfE());
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
	public <K, CLASSIFIER> Map<K, int[]> toMapInternal(
	  CLASSIFIER classifier,
	  Function<CLASSIFIER, IntFunction<? extends K>> cast) throws E {
		return terminalAsMapToList(cast.apply(classifier), Function.identity(), Function.identity(), maker(), classOfE());
	}
	public int reduce(int identity, BINARY_OPERATOR accumulator) throws E {
		return terminalAsInt(s -> s.reduce(identity, castToBinaryOperators(accumulator)), maker(), classOfE());
	}
	public OptionalInt reduce(BINARY_OPERATOR accumulator) throws E {
		return terminalAsObj(s -> s.reduce(castToBinaryOperators(accumulator)), maker(), classOfE());
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
	public boolean anyMatch(PREDICATE predicate) throws E {
		return terminalAsBoolean(s -> s.anyMatch(castToPredicates(predicate)), maker(), classOfE());
	}
	public boolean allMatch(PREDICATE predicate) throws E {
		return terminalAsBoolean(s -> s.allMatch(castToPredicates(predicate)), maker(), classOfE());
	}
	public boolean noneMatch(PREDICATE predicate) throws E {
		return terminalAsBoolean(s -> s.noneMatch(castToPredicates(predicate)), maker(), classOfE());
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
		return asSELF(s -> IntStream.concat(s, after.maker().get()));
	}
	public static <E extends Exception> void terminal(
	  Consumer<IntStream> consumption,
	  Supplier<IntStream> supplier,
	  Class<E> class1) throws E {
		try(IntStream stream = supplier.get()) {
			consumption.accept(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <R, E extends Exception> R terminalAsObj(
	  Function<IntStream, R> consumption,
	  Supplier<IntStream> supplier,
	  Class<E> class1) throws E {
		try(IntStream stream = supplier.get()) {
			return consumption.apply(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <E extends Exception> long terminalAsLong(
	  ToLongFunction<IntStream> consumption,
	  Supplier<IntStream> supplier,
	  Class<E> class1) throws E {
		try(IntStream stream = supplier.get()) {
			return consumption.applyAsLong(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <E extends Exception> int terminalAsInt(
	  ToIntFunction<IntStream> consumption,
	  Supplier<IntStream> supplier,
	  Class<E> class1) throws E {
		try(IntStream stream = supplier.get()) {
			return consumption.applyAsInt(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <E extends Exception> boolean terminalAsBoolean(
	  Predicate<IntStream> consumption,
	  Supplier<IntStream> supplier,
	  Class<E> class1) throws E {
		try(IntStream stream = supplier.get()) {
			return consumption.test(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <K, E extends Exception> Map<K, int[]> terminalAsMapToList(
	  IntFunction<? extends K> classifier,
	  Supplier<IntStream> maker,
	  Class<E> classOfE) throws E {
		return terminalAsMapToList(classifier, Function.identity(), Function.identity(), maker, classOfE);
	}
	public static <K, L, M, E extends Exception> M terminalAsMapToList(
	  IntFunction<? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<int[], L> intoList,
	  Supplier<IntStream> supplier,
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
		try(IntStream s = supplier.get()) {
			return s.boxed().collect(collectMap);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	public static <E extends Exception> E unwrapCause(Class<E> classOfE, RuntimeException e) throws E {
		Throwable cause = e.getCause();
		if(classOfE.isInstance(cause) == false) {
			throw e;
		}
		throw classOfE.cast(cause);
	}
}
