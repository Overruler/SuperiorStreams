package utils.streams2;

import java.util.DoubleSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import utils.lists.ArrayList;
import utils.lists.HashMap;
import utils.lists.HashSet;
import utils.streams.functions.BiConsumer;
import utils.streams.functions.BiFunction;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.DoubleBinaryOperator;
import utils.streams.functions.DoubleConsumer;
import utils.streams.functions.DoubleFunction;
import utils.streams.functions.DoublePredicate;
import utils.streams.functions.DoubleToIntFunction;
import utils.streams.functions.DoubleToLongFunction;
import utils.streams.functions.DoubleUnaryOperator;
import utils.streams.functions.Function;
import utils.streams.functions.ObjDoubleConsumer;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToLongFunction;
import static utils.streams2.Collectors.*;

//*Q*
abstract class AbstractDoubleStream<E extends Exception, STREAM, OS, IS, LS,
SELF extends AbstractDoubleStream<E, STREAM, OS, IS, LS,
SELF,
CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>,
CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE> {//*E*
	protected final Supplier<java.util.stream.DoubleStream> supplier;

	public AbstractDoubleStream(Supplier<STREAM> supplier) {
		this.supplier = () -> castToStream(supplier.get());
	}
	protected <OLD> AbstractDoubleStream(Supplier<OLD> older, Function<OLD, STREAM> converter) {
		this.supplier = () -> castToStream(converter.apply(older.get()));
	}
	protected Supplier<java.util.stream.DoubleStream> maker() {
		return supplier;
	}
	protected abstract java.util.stream.DoubleStream castToStream(STREAM stream);
	protected abstract DoubleConsumer castToConsumers(CONSUMER action);
	protected abstract DoublePredicate castToPredicates(PREDICATE test);
	protected abstract DoubleBinaryOperator castToBinaryOperators(BINARY_OPERATOR combiner);
	protected abstract Function<? super Double, ? extends java.util.stream.IntStream> castToIntStream(TO_IS mapper);
	protected abstract Function<? super Double, ? extends java.util.stream.LongStream> castToLongStream(TO_LS mapper);
	protected abstract Function<? super Double, ? extends java.util.stream.DoubleStream>
		castToDoubleStream(TO_DS mapper);
	protected abstract DoubleToIntFunction castToInt(TO_INT mapper);
	protected abstract DoubleToLongFunction castToLong(TO_LONG mapper);
	protected abstract DoubleUnaryOperator castToDouble(TO_DOUBLE mapper);
	protected abstract Class<E> classOfE();
	protected abstract OS asOS(Function<java.util.stream.DoubleStream, java.util.stream.Stream<Double>> convert);
	protected abstract IS asIS(Function<java.util.stream.DoubleStream, java.util.stream.IntStream> convert);
	protected abstract LS asLS(Function<java.util.stream.DoubleStream, java.util.stream.LongStream> convert);
	protected abstract SELF asSELF(Function<java.util.stream.DoubleStream, java.util.stream.DoubleStream> convert);
	protected static <T> double toDouble(T t) {
		try {
			return t instanceof Double ? (int) t : t == null ? 0 : Double.parseDouble(String.valueOf(t));
		} catch(NumberFormatException ignored) {
			return 0;
		}
	}
	public SELF filter(PREDICATE allowed) {
		DoublePredicate predicate = castToPredicates(allowed);
		return asSELF(s -> s.filter(predicate));
	}
	public final @SafeVarargs SELF filter(DoublePredicate allow, DoublePredicate... allowed) {
		for(DoublePredicate predicate : allowed) {
			allow = allow.and(predicate);
		}
		DoublePredicate predicate = allow;
		return asSELF(s -> s.filter(predicate));
	}
	protected static <R, RS> RS mapInternal(
		DoubleFunction<? extends R> mapper,
		Function<Function<java.util.stream.DoubleStream, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.mapToObj(mapper));
	}
	public IS mapToInt(TO_INT mapper) {
		DoubleToIntFunction mapper2 = castToInt(mapper);
		return asIS(s -> s.mapToInt(mapper2));
	}
	public LS mapToLong(TO_LONG mapper) {
		DoubleToLongFunction mapper2 = castToLong(mapper);
		return asLS(s -> s.mapToLong(mapper2));
	}
	public SELF mapToDouble(TO_DOUBLE mapper) {
		DoubleUnaryOperator mapper2 = castToDouble(mapper);
		return asSELF(s -> s.map(mapper2));
	}
	protected static <R, RS> RS flatMapInternal(
		DoubleFunction<? extends java.util.stream.Stream<? extends R>> mapper,
		Function<Function<java.util.stream.DoubleStream, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.boxed().flatMap(mapper::apply));
	}
	public IS flatMapToInt(TO_IS mapper) {
		Function<? super Double, ? extends java.util.stream.IntStream> mapper2 = castToIntStream(mapper);
		return asIS(s -> s.boxed().flatMapToInt(mapper2));
	}
	public LS flatMapToLong(TO_LS mapper) {
		Function<? super Double, ? extends java.util.stream.LongStream> mapper2 = castToLongStream(mapper);
		return asLS(s -> s.boxed().flatMapToLong(mapper2));
	}
	public SELF flatMapToDouble(TO_DS mapper) {
		Function<? super Double, ? extends java.util.stream.DoubleStream> mapper2 = castToDoubleStream(mapper);
		return asSELF(s -> s.boxed().flatMapToDouble(mapper2));
	}
	public SELF distinct() {
		return asSELF(s -> s.distinct());
	}
	public SELF sorted() {
		return asSELF(s -> s.sorted());
	}
	public SELF peek(CONSUMER action) {
		DoubleConsumer consumer = castToConsumers(action);
		return asSELF(s -> s.peek(consumer));
	}
	public SELF limit(long maxSize) {
		return asSELF(s -> s.limit(maxSize));
	}
	public SELF skip(long n) {
		return asSELF(s -> s.skip(n));
	}
	public void forEach(CONSUMER action) throws E {
		DoubleConsumer consumer = castToConsumers(action);
		terminal(s -> s.forEach(consumer), maker(), classOfE());
	}
	public void forEachOrdered(CONSUMER action) throws E {
		DoubleConsumer consumer = castToConsumers(action);
		terminal(s -> s.forEachOrdered(consumer), maker(), classOfE());
	}
	public double[] toArray() throws E {
		return terminalAsObj(s -> s.toArray(), maker(), classOfE());
	}
	public ArrayList<Double> toList() throws E {
		Collector<Double, ?, ArrayList<Double>> collector = Collectors.toList();
		return terminalAsObj(s -> s.boxed().collect(collector), maker(), classOfE());
	}
	public HashSet<Double> toSet() throws E {
		Collector<Double, ?, HashSet<Double>> collector = Collectors.toSet();
		return terminalAsObj(s -> s.boxed().collect(collector), maker(), classOfE());
	}
	public <L> L toList(Function<double[], L> intoList) throws E {
		return intoList.apply(toArray());
	}
	protected <K, L, M, CLASSIFIER> M toMultiMapInternal(
		CLASSIFIER classifier,
		Function<CLASSIFIER, DoubleFunction<? extends K>> cast,
		Function<double[], L> intoList,
		Function<HashMap<K, L>, M> intoMap) throws E {
		return terminalAsMapToList(cast.apply(classifier), intoMap, intoList, maker(), classOfE());
	}
	protected <K, CLASSIFIER> HashMap<K, double[]> toMapInternal(
		CLASSIFIER classifier,
		Function<CLASSIFIER, DoubleFunction<? extends K>> cast) throws E {
		return terminalAsMapToList(
			cast.apply(classifier),
			Function.identity(),
			Function.identity(),
			maker(),
			classOfE());
	}
	public double reduce(double identity, BINARY_OPERATOR accumulator) throws E {
		DoubleBinaryOperator operator = castToBinaryOperators(accumulator);
		return terminalAsDouble(s -> s.reduce(identity, operator), maker(), classOfE());
	}
	public OptionalDouble reduce(BINARY_OPERATOR accumulator) throws E {
		DoubleBinaryOperator operator = castToBinaryOperators(accumulator);
		return terminalAsObj(s -> s.reduce(operator), maker(), classOfE());
	}
	public <U> U reduce(U identity, BiFunction<U, ? super Double, U> accumulator, BinaryOperator<U> combiner) throws E {
		return terminalAsObj(s -> s.boxed().reduce(identity, accumulator, combiner), maker(), classOfE());
	}
	public <R> R collect(Supplier<R> initial, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) throws E {
		return terminalAsObj(s -> s.collect(initial, accumulator, combiner), maker(), classOfE());
	}
	public double sum() throws E {
		return terminalAsDouble(s -> s.sum(), maker(), classOfE());
	}
	public OptionalDouble min() throws E {
		return terminalAsObj(s -> s.min(), maker(), classOfE());
	}
	public OptionalDouble max() throws E {
		return terminalAsObj(s -> s.max(), maker(), classOfE());
	}
	public long count() throws E {
		return terminalAsLong(s -> s.count(), maker(), classOfE());
	}
	public OptionalDouble average() throws E {
		return terminalAsObj(s -> s.average(), maker(), classOfE());
	}
	public DoubleSummaryStatistics summaryStatistics() throws E {
		return terminalAsObj(s -> s.summaryStatistics(), maker(), classOfE());
	}
	public boolean anyMatch(PREDICATE test) throws E {
		DoublePredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.anyMatch(predicate), maker(), classOfE());
	}
	public boolean allMatch(PREDICATE test) throws E {
		DoublePredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.allMatch(predicate), maker(), classOfE());
	}
	public boolean noneMatch(PREDICATE test) throws E {
		DoublePredicate predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.noneMatch(predicate), maker(), classOfE());
	}
	public OptionalDouble findFirst() throws E {
		return terminalAsObj(s -> s.findFirst(), maker(), classOfE());
	}
	public OptionalDouble findAny() throws E {
		return terminalAsObj(s -> s.findAny(), maker(), classOfE());
	}
	public IS asIntStream() {
		return asIS(s -> s.mapToInt(AbstractIntStream::toInt));
	}
	public LS asLongStream() {
		return asLS(s -> s.mapToLong(AbstractLongStream::toLong));
	}
	public SELF asDoubleStream() {
		return asSELF(Function.identity());
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
		return asSELF(s -> java.util.stream.DoubleStream.concat(s, after.maker().get()));
	}
	private static <E extends Exception> void terminal(
		Consumer<java.util.stream.DoubleStream> consumption,
		Supplier<java.util.stream.DoubleStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.DoubleStream stream = supplier.get()) {
			consumption.accept(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <R, E extends Exception> R terminalAsObj(
		Function<java.util.stream.DoubleStream, R> consumption,
		Supplier<java.util.stream.DoubleStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.DoubleStream stream = supplier.get()) {
			return consumption.apply(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> long terminalAsLong(
		ToLongFunction<java.util.stream.DoubleStream> consumption,
		Supplier<java.util.stream.DoubleStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.DoubleStream stream = supplier.get()) {
			return consumption.applyAsLong(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> double terminalAsDouble(
		ToDoubleFunction<java.util.stream.DoubleStream> consumption,
		Supplier<java.util.stream.DoubleStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.DoubleStream stream = supplier.get()) {
			return consumption.applyAsDouble(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <E extends Exception> boolean terminalAsBoolean(
		Predicate<java.util.stream.DoubleStream> consumption,
		Supplier<java.util.stream.DoubleStream> supplier,
		Class<E> class1) throws E {
		try(java.util.stream.DoubleStream stream = supplier.get()) {
			return consumption.test(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	private static <K, L, M, E extends Exception> M terminalAsMapToList(
		DoubleFunction<? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<double[], L> intoList,
		Supplier<java.util.stream.DoubleStream> supplier,
		Class<E> classOfE) throws E {
		Supplier<double[][]> supplier2 = () -> new double[64][];
		BiConsumer<double[][], Double> biConsumer = (left, value) -> {
			double[] array2 = new double[] { value == null ? 0 : value };
			for(int i1 = 0, n = left.length; i1 < n; i1++) {
				double[] array1 = left[i1];
				if(array1 == null) {
					left[i1] = array2;
					break;
				}
				double[] array = new double[array1.length + array2.length];
				System.arraycopy(array1, 0, array, 0, array1.length);
				System.arraycopy(array2, 0, array, array1.length, array2.length);
				array2 = array;
				left[i1] = null;
			}
		};
		BinaryOperator<double[][]> binaryOperator = (left, right) -> {
			double[][] container2 = new double[left.length + right.length][];
			System.arraycopy(left, 0, container2, 0, left.length);
			System.arraycopy(right, 0, container2, left.length, right.length);
			return container2;
		};
		Collector<Double, double[][], double[][]> toContainer = Collector.of(supplier2, biConsumer, binaryOperator);
		Function<? super double[][], ? extends double[]> before = left -> {
			int count = 0;
			for(double[] is : left) {
				if(is != null) {
					count = Math.addExact(count, is.length);
				}
			}
			double[] result = new double[count];
			count = 0;
			for(int i1 = left.length - 1; i1 >= 0; i1--) {
				double[] array2 = left[i1];
				if(array2 != null) {
					System.arraycopy(array2, 0, result, count, array2.length);
					count += array2.length;
				}
			}
			return result;
		};
		Collector<Double, double[][], L> collectingAndThen = collectingAndThen(toContainer, intoList.compose(before));
		Collector<Double, ?, M> collectMap =
			collectingAndThen(groupingBy(classifier::apply, HashMap<K, L>::new, collectingAndThen), intoMap);
		try(java.util.stream.DoubleStream s = supplier.get()) {
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
