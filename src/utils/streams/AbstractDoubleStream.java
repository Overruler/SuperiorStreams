package utils.streams;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

//*Q*
abstract class AbstractDoubleStream<E extends Exception, STREAM, OS, IS, LS,
SELF extends AbstractDoubleStream<E, STREAM, OS, IS, LS,
SELF,
CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>,
CONSUMER, PREDICATE, BINARY_OPERATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE> {//*E*

	protected final Supplier<DoubleStream> supplier;

	public AbstractDoubleStream(Supplier<STREAM> supplier) {
		this.supplier = () -> castToStream(supplier.get());
	}
	protected <OLD> AbstractDoubleStream(Supplier<OLD> older, Function<OLD, STREAM> converter) {
		this.supplier = () -> castToStream(converter.apply(older.get()));
	}
	protected Supplier<DoubleStream> maker() {
		return supplier;
	}
	protected abstract DoubleStream castToStream(STREAM stream);
	protected abstract DoubleConsumer castToConsumers(CONSUMER action);
	protected abstract DoublePredicate castToPredicates(PREDICATE test);
	protected abstract DoubleBinaryOperator castToBinaryOperators(BINARY_OPERATOR combiner);
	protected abstract Function<? super Double, ? extends IntStream> castToIntStream(TO_IS mapper);
	protected abstract Function<? super Double, ? extends LongStream> castToLongStream(TO_LS mapper);
	protected abstract Function<? super Double, ? extends DoubleStream> castToDoubleStream(TO_DS mapper);
	protected abstract DoubleToIntFunction castToInt(TO_INT mapper);
	protected abstract DoubleToLongFunction castToLong(TO_LONG mapper);
	protected abstract DoubleUnaryOperator castToDouble(TO_DOUBLE mapper);
	protected abstract Class<E> classOfE();
	protected abstract OS asOS(Function<DoubleStream, Stream<Double>> convert);
	protected abstract IS asIS(Function<DoubleStream, IntStream> convert);
	protected abstract LS asLS(Function<DoubleStream, LongStream> convert);
	protected abstract SELF asSELF(Function<DoubleStream, DoubleStream> convert);
	protected static <T> double toDouble(T t) {
		try {
			return t instanceof Double ? (int) t : t == null ? 0 : Double.parseDouble(String.valueOf(t));
		} catch(NumberFormatException ignored) {
			return 0;
		}
	}
	public SELF filter(PREDICATE allowed) {
		return filterInternal(castToPredicates(allowed));
	}
	public final @SafeVarargs SELF filter(DoublePredicate allow, DoublePredicate... allowed) {
		for(DoublePredicate predicate : allowed) {
			allow = allow.and(predicate);
		}
		return filterInternal(allow);
	}
	protected SELF filterInternal(DoublePredicate allowed) {
		return asSELF(s -> s.filter(allowed));
	}
	protected static <R, RS> RS mapInternal(
	  DoubleFunction<? extends R> mapper,
	  Function<Function<DoubleStream, Stream<R>>, RS> cast) {
		return cast.apply(s -> s.mapToObj(mapper));
	}
	public IS mapToInt(TO_INT mapper) {
		return asIS(s -> s.mapToInt(castToInt(mapper)));
	}
	public LS mapToLong(TO_LONG mapper) {
		return asLS(s -> s.mapToLong(castToLong(mapper)));
	}
	public SELF mapToDouble(TO_DOUBLE mapper) {
		return asSELF(s -> s.map(castToDouble(mapper)));
	}
	protected static <R, RS> RS flatMapInternal(
	  Function<? super Double, ? extends Stream<? extends R>> mapper,
	  Function<Function<DoubleStream, Stream<R>>, RS> cast) {
		return cast.apply(s -> s.boxed().flatMap(mapper));
	}
	public IS flatMapToInt(TO_IS mapper) {
		return asIS(s -> s.boxed().flatMapToInt(castToIntStream(mapper)));
	}
	public LS flatMapToLong(TO_LS mapper) {
		return asLS(s -> s.boxed().flatMapToLong(castToLongStream(mapper)));
	}
	public SELF flatMapToDouble(TO_DS mapper) {
		return asSELF(s -> s.boxed().flatMapToDouble(castToDoubleStream(mapper)));
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
	public double[] toArray() throws E {
		return terminalAsObj(s -> s.toArray(), maker(), classOfE());
	}
	public ArrayList<Double> toList() throws E {
		return terminalAsObj(s -> s.boxed().collect(Collectors.toCollection(ArrayList::new)), maker(), classOfE());
	}
	public HashSet<Double> toSet() throws E {
		return terminalAsObj(s -> s.boxed().collect(Collectors.toCollection(HashSet::new)), maker(), classOfE());
	}
	public <L> L toList(Function<double[], L> intoList) throws E {
		return intoList.apply(toArray());
	}
	public <K, L, M, CLASSIFIER> M toMultiMapInternal(
	  CLASSIFIER classifier,
	  Function<CLASSIFIER, DoubleFunction<? extends K>> cast,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<double[], L> intoList) throws E {
		return terminalAsMapToList(cast.apply(classifier), intoMap, intoList, maker(), classOfE());
	}
	public <K, CLASSIFIER> HashMap<K, double[]> toMapInternal(
	  CLASSIFIER classifier,
	  Function<CLASSIFIER, DoubleFunction<? extends K>> cast) throws E {
		return terminalAsMapToList(cast.apply(classifier), Function.identity(), Function.identity(), maker(), classOfE());
	}
	public double reduce(double identity, BINARY_OPERATOR accumulator) throws E {
		return terminalAsDouble(s -> s.reduce(identity, castToBinaryOperators(accumulator)), maker(), classOfE());
	}
	public OptionalDouble reduce(BINARY_OPERATOR accumulator) throws E {
		return terminalAsObj(s -> s.reduce(castToBinaryOperators(accumulator)), maker(), classOfE());
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
	public boolean anyMatch(PREDICATE predicate) throws E {
		return terminalAsBoolean(s -> s.anyMatch(castToPredicates(predicate)), maker(), classOfE());
	}
	public boolean allMatch(PREDICATE predicate) throws E {
		return terminalAsBoolean(s -> s.allMatch(castToPredicates(predicate)), maker(), classOfE());
	}
	public boolean noneMatch(PREDICATE predicate) throws E {
		return terminalAsBoolean(s -> s.noneMatch(castToPredicates(predicate)), maker(), classOfE());
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
		return asSELF(s -> DoubleStream.concat(s, after.maker().get()));
	}
	public static <E extends Exception> void terminal(
	  Consumer<DoubleStream> consumption,
	  Supplier<DoubleStream> supplier,
	  Class<E> class1) throws E {
		try(DoubleStream stream = supplier.get()) {
			consumption.accept(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <R, E extends Exception> R terminalAsObj(
	  Function<DoubleStream, R> consumption,
	  Supplier<DoubleStream> supplier,
	  Class<E> class1) throws E {
		try(DoubleStream stream = supplier.get()) {
			return consumption.apply(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <E extends Exception> long terminalAsLong(
	  ToLongFunction<DoubleStream> consumption,
	  Supplier<DoubleStream> supplier,
	  Class<E> class1) throws E {
		try(DoubleStream stream = supplier.get()) {
			return consumption.applyAsLong(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <E extends Exception> int terminalAsInt(
	  ToIntFunction<DoubleStream> consumption,
	  Supplier<DoubleStream> supplier,
	  Class<E> class1) throws E {
		try(DoubleStream stream = supplier.get()) {
			return consumption.applyAsInt(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <E extends Exception> double terminalAsDouble(
	  ToDoubleFunction<DoubleStream> consumption,
	  Supplier<DoubleStream> supplier,
	  Class<E> class1) throws E {
		try(DoubleStream stream = supplier.get()) {
			return consumption.applyAsDouble(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <E extends Exception> boolean terminalAsBoolean(
	  Predicate<DoubleStream> consumption,
	  Supplier<DoubleStream> supplier,
	  Class<E> class1) throws E {
		try(DoubleStream stream = supplier.get()) {
			return consumption.test(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(class1, e);
		}
	}
	public static <K, E extends Exception> HashMap<K, double[]> terminalAsMapToList(
	  DoubleFunction<? extends K> classifier,
	  Supplier<DoubleStream> maker,
	  Class<E> classOfE) throws E {
		return terminalAsMapToList(classifier, Function.identity(), Function.identity(), maker, classOfE);
	}
	public static <K, L, M, E extends Exception> M terminalAsMapToList(
	  DoubleFunction<? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<double[], L> intoList,
	  Supplier<DoubleStream> supplier,
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
		try(DoubleStream s = supplier.get()) {
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
