package utils.streams2;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import static java.util.stream.Collector.*;
import utils.lists.ArrayList;
import utils.lists.HashMap;
import utils.lists.HashSet;
import utils.streams.functions.BiConsumer;
import utils.streams.functions.BinaryOperator;
import utils.streams.functions.Consumer;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExSupplier;
import utils.streams.functions.ExToLongFunction;
import utils.streams.functions.Function;
import utils.streams.functions.IntFunction;
import utils.streams.functions.Predicate;
import utils.streams.functions.Supplier;
import utils.streams.functions.ToDoubleFunction;
import utils.streams.functions.ToIntFunction;
import utils.streams.functions.ToLongFunction;
import static utils.streams2.Collectors.*;

//*Q*
abstract class AbstractStream<T, E extends Exception, STREAM,
SELF extends AbstractStream<T, E, STREAM,
SELF,
IS, LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, COMPARATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>,
IS, LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, COMPARATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>
implements Streamable<T, E> {//*E*
	protected abstract Class<E> classOfE();
	public @Override java.util.stream.Stream<T> stream() throws E {
		return ExSupplier.recheck(maker(), classOfE()).get();
	}
	public @Override Iterable<T> iterable() throws E {
		Iterator<T> iterator = stream().iterator();
		return () -> iterator;
	}
	public SELF filter(PREDICATE allowed) {
		Predicate<? super T> predicate = castToPredicates(allowed);
		return asSELF(s -> s.filter(predicate));
	}
	public final @SafeVarargs SELF filter(Predicate<? super T> allow, Predicate<? super T>... allowed) {
		Predicate<T> allow2 = allow::test;
		for(Predicate<? super T> predicate : allowed) {
			allow2 = allow2.and(predicate);
		}
		Predicate<? super T> predicate = allow2;
		return asSELF(s -> s.filter(predicate));
	}
	public IS mapToInt(TO_INT mapper) {
		ToIntFunction<? super T> mapper2 = castToInt(mapper);
		return asIS(s -> s.mapToInt(mapper2));
	}
	public LS mapToLong(TO_LONG mapper) {
		ToLongFunction<? super T> mapper2 = castToLong(mapper);
		return asLS(s -> s.mapToLong(mapper2));
	}
	public DS mapToDouble(TO_DOUBLE mapper) {
		ToDoubleFunction<? super T> mapper2 = castToDouble(mapper);
		return asDS(s -> s.mapToDouble(mapper2));
	}
	public IS flatMapToInt(TO_IS mapper) {
		Function<? super T, ? extends IntStream> mapper2 = castToIntStream(mapper);
		return asIS(s -> s.flatMapToInt(mapper2));
	}
	public DS flatMapToDouble(TO_DS mapper) {
		Function<? super T, ? extends DoubleStream> mapper2 = castToDoubleStream(mapper);
		return asDS(s -> s.flatMapToDouble(mapper2));
	}
	public LS flatMapToLong(TO_LS mapper) {
		Function<? super T, ? extends LongStream> mapper2 = castToLongStream(mapper);
		return asLS(s -> s.flatMapToLong(mapper2));
	}
	public SELF distinct() {
		return asSELF(s -> s.distinct());
	}
	public SELF sorted() {
		return asSELF(s -> s.sorted());
	}
	public SELF limit(long maxSize) {
		return asSELF(s -> s.limit(maxSize));
	}
	public SELF skip(long n) {
		return asSELF(s -> s.skip(n));
	}
	public SELF peek(CONSUMER action) {
		Consumer<? super T> consumer = castToConsumers(action);
		return asSELF(s -> s.peek(consumer));
	}
	public final @SafeVarargs SELF peek(Consumer<? super T> action, Consumer<? super T>... actions) {
		Consumer<T> action2 = action::accept;
		for(Consumer<? super T> consumer : actions) {
			action2 = action2.andThen(consumer);
		}
		Consumer<? super T> consumer = action2;
		return asSELF(s -> s.peek(consumer));
	}
	public void forEach(CONSUMER action) throws E {
		Consumer<? super T> consumer = castToConsumers(action);
		terminal(s -> s.forEach(consumer), maker(), classOfE());
	}
	public final @SafeVarargs void forEach(Consumer<? super T> action, Consumer<? super T>... actions) throws E {
		Consumer<T> action2 = action::accept;
		for(Consumer<? super T> consumer : actions) {
			action2 = action2.andThen(consumer);
		}
		Consumer<? super T> action3 = action2;
		terminal(s -> s.forEach(action3), maker(), classOfE());
	}
	public void forEachOrdered(CONSUMER action) throws E {
		Consumer<? super T> consumer = castToConsumers(action);
		terminal(s -> s.forEachOrdered(consumer), maker(), classOfE());
	}
	public final @SafeVarargs void forEachOrdered(Consumer<? super T> action, Consumer<? super T>... actions) throws E {
		Consumer<T> action2 = action::accept;
		for(Consumer<? super T> consumer : actions) {
			action2 = action2.andThen(consumer);
		}
		Consumer<? super T> consumer = action2;
		terminal(s -> s.forEachOrdered(consumer), maker(), classOfE());
	}
	public IS asIntStream() {
		return asIS(s -> s.mapToInt(AbstractIntStream::toInt));
	}
	public LS asLongStream() {
		return asLS(s -> s.mapToLong(AbstractLongStream::toLong));
	}
	public DS asDoubleStream() {
		return asDS(s -> s.mapToDouble(AbstractDoubleStream::toDouble));
	}
	public SELF boxed() {
		return asSELF(Function.identity());
	}
	public SELF sequential() {
		return asSELF(s -> s.sequential());
	}
	public SELF parallel() {
		return asSELF(s -> s.parallel());
	}
	public Object[] toArray() throws E {
		return terminalAsObj(s -> s.toArray(), maker(), classOfE());
	}
	public T[] toArray(IntFunction<T[]> allocator) throws E {
		return terminalAsObj(s -> s.toArray(allocator), maker(), classOfE());
	}
	public ArrayList<T> toList() throws E {
		Collector<T, ?, ArrayList<T>> collection = Collectors.toList();
		return terminalAsObj(s -> s.collect(collection), maker(), classOfE());
	}
	public HashSet<T> toSet() throws E {
		Collector<T, ?, HashSet<T>> collection = Collectors.toSet();
		return terminalAsObj(s -> s.collect(collection), maker(), classOfE());
	}
	public long count() throws E {
		return terminalAsLong(s -> s.count(), maker(), classOfE());
	}
	public Optional<T> findFirst() throws E {
		return terminalAsObj(s -> s.findFirst(), maker(), classOfE());
	}
	public Optional<T> findAny() throws E {
		return terminalAsObj(s -> s.findAny(), maker(), classOfE());
	}
	public
		SELF
		concat(
			AbstractStream<T, E, STREAM, SELF, IS, LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, COMPARATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE> after) {
		Objects.requireNonNull(after);
		return asSELF(s -> java.util.stream.Stream.concat(s, after.maker().get()));
	}
	public HashMap<Boolean, ArrayList<T>> partition(PREDICATE allowed) throws E {
		Predicate<? super T> predicate = castToPredicates(allowed);
		Collector<T, Object, HashMap<Boolean, ArrayList<T>>> collector =
			collectingAndThen(partitioningBy(predicate, Collectors.toList()), m -> new HashMap<>(m));
		return terminalAsObj(s -> s.collect(collector), maker(), classOfE());
	}
	public final @SafeVarargs HashMap<Boolean, ArrayList<T>> partition(
		Predicate<? super T> allow,
		Predicate<? super T>... allowed) throws E {
		Predicate<T> allow2 = allow::test;
		for(Predicate<? super T> predicate : allowed) {
			allow2 = allow2.and(predicate);
		}
		Predicate<? super T> predicate = allow2;
		Collector<T, Object, HashMap<Boolean, ArrayList<T>>> collector =
			collectingAndThen(partitioningBy(predicate, Collectors.toList()), m -> new HashMap<>(m));
		return terminalAsObj(s -> s.collect(collector), maker(), classOfE());
	}
	public <R> R collect(Supplier<R> initial, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) throws E {
		return terminalAsObj(s -> s.collect(initial, accumulator, combiner), maker(), classOfE());
	}
	public <R, A> R collect(Collector<? super T, A, R> collector) throws E {
		return terminalAsObj(s -> s.collect(collector), maker(), classOfE());
	}
	public SELF sorted(COMPARATOR comparator) {
		Comparator<? super T> comparator2 = castToComparators(comparator);
		return asSELF(s -> s.sorted(comparator2));
	}
	public Optional<T> min(COMPARATOR comparator) throws E {
		Comparator<? super T> comparator2 = castToComparators(comparator);
		return terminalAsObj(s -> s.min(comparator2), maker(), classOfE());
	}
	public Optional<T> max(COMPARATOR comparator) throws E {
		Comparator<? super T> comparator2 = castToComparators(comparator);
		return terminalAsObj(s -> s.max(comparator2), maker(), classOfE());
	}
	public SummaryStatistics<T> summaryStatistics(TO_LONG attribute) throws E {
		ToLongFunction<? super T> attribute2 = castToLong(attribute);
		Collector<? super T, SummaryStatistics<T>, SummaryStatistics<T>> collector =
			SummaryStatistics.collector(attribute2);
		return terminalAsObj(s -> s.collect(collector), maker(), classOfE());
	}
	public OptionalDouble average(TO_DOUBLE attribute) throws E {
		ToDoubleFunction<? super T> mapper = castToDouble(attribute);
		return terminalAsObj(s -> s.mapToDouble(mapper).average(), maker(), classOfE());
	}
	protected <K> HashMap<K, ArrayList<T>> toMapInternal(Function<? super T, ? extends K> classifier) throws E {
		return terminalAsMapToList(classifier, Function.identity(), Function.identity(), maker(), classOfE());
	}
	protected <M, L, K> M toMultiMapInternal(
		Function<? super T, ? extends K> intoKey,
		Function<ArrayList<T>, L> intoList,
		Function<HashMap<K, L>, M> intoMap) throws E {
		return terminalAsMapToList(intoKey, intoMap, intoList, maker(), classOfE());
	}
	public T reduce(T start, BINARY_OPERATOR add) throws E {
		BinaryOperator<T> accumulator = castToBinaryOperators(add);
		return terminalAsObj(s -> s.reduce(start, accumulator), maker(), classOfE());
	}
	public final @SafeVarargs T reduce(T start, BinaryOperator<T> add, Function<T, T>... extras) throws E {
		for(Function<T, T> accumulator2 : extras) {
			add = add.andThen(accumulator2)::apply;
		}
		BinaryOperator<T> accumulator = add;
		return terminalAsObj(s -> s.reduce(start, accumulator), maker(), classOfE());
	}
	public Optional<T> reduce(BINARY_OPERATOR add) throws E {
		BinaryOperator<T> accumulator = castToBinaryOperators(add);
		return terminalAsObj(s -> s.reduce(accumulator), maker(), classOfE());
	}
	public final @SafeVarargs Optional<T> reduce(BinaryOperator<T> add, Function<T, T>... extras) throws E {
		for(Function<T, T> accumulator2 : extras) {
			add = add.andThen(accumulator2)::apply;
		}
		BinaryOperator<T> accumulator = add;
		return terminalAsObj(s -> s.reduce(accumulator), maker(), classOfE());
	}
	public boolean anyMatch(PREDICATE test) throws E {
		Predicate<? super T> predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.anyMatch(predicate), maker(), classOfE());
	}
	public final @SafeVarargs boolean anyMatch(Predicate<? super T> test, Predicate<? super T>... tests) throws E {
		Predicate<T> test2 = test::test;
		for(Predicate<? super T> predicate2 : tests) {
			test2 = test2.and(predicate2);
		}
		Predicate<? super T> predicate = test2;
		return terminalAsBoolean(s -> s.anyMatch(predicate), maker(), classOfE());
	}
	public boolean allMatch(PREDICATE test) throws E {
		Predicate<? super T> predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.allMatch(predicate), maker(), classOfE());
	}
	public final @SafeVarargs boolean allMatch(Predicate<? super T> test, Predicate<? super T>... tests) throws E {
		Predicate<T> test2 = test::test;
		for(Predicate<? super T> predicate2 : tests) {
			test2 = test2.and(predicate2);
		}
		Predicate<? super T> predicate = test2;
		return terminalAsBoolean(s -> s.allMatch(predicate), maker(), classOfE());
	}
	public boolean noneMatch(PREDICATE test) throws E {
		Predicate<? super T> predicate = castToPredicates(test);
		return terminalAsBoolean(s -> s.noneMatch(predicate), maker(), classOfE());
	}
	public final @SafeVarargs boolean noneMatch(Predicate<? super T> test, Predicate<? super T>... tests) throws E {
		Predicate<T> test2 = test::test;
		for(Predicate<? super T> predicate2 : tests) {
			test2 = test2.and(predicate2);
		}
		Predicate<? super T> predicate = test2;
		return terminalAsBoolean(s -> s.noneMatch(predicate), maker(), classOfE());
	}
	protected <R, RS> RS mapInternal(
		Function<? super T, ? extends R> mapper,
		Function<Function<java.util.stream.Stream<T>, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.map(mapper));
	}
	protected <R, RS> RS flatMapInternal(
		Function<? super T, ? extends java.util.stream.Stream<? extends R>> mapper,
		Function<Function<java.util.stream.Stream<T>, java.util.stream.Stream<R>>, RS> cast) {
		return cast.apply(s -> s.flatMap(mapper));
	}

	protected final Supplier<java.util.stream.Stream<T>> supplier;

	protected AbstractStream(Supplier<STREAM> supplier) {
		this.supplier = () -> castToStream(supplier.get());
	}
	protected <OLD> AbstractStream(Supplier<OLD> older, Function<OLD, STREAM> converter) {
		this.supplier = () -> castToStream(converter.apply(older.get()));
	}
	protected Supplier<java.util.stream.Stream<T>> maker() {
		return supplier;
	}
	protected abstract SELF asSELF(Function<java.util.stream.Stream<T>, java.util.stream.Stream<T>> convert);
	protected abstract IS asIS(Function<java.util.stream.Stream<T>, java.util.stream.IntStream> convert);
	protected abstract LS asLS(Function<java.util.stream.Stream<T>, java.util.stream.LongStream> convert);
	protected abstract DS asDS(Function<java.util.stream.Stream<T>, java.util.stream.DoubleStream> convert);
	protected abstract Comparator<? super T> castToComparators(COMPARATOR comparator);
	protected abstract Function<? super T, ? extends IntStream> castToIntStream(TO_IS mapper);
	protected abstract Function<? super T, ? extends DoubleStream> castToDoubleStream(TO_DS mapper);
	protected abstract ToIntFunction<? super T> castToInt(TO_INT mapper);
	protected abstract ToLongFunction<? super T> castToLong(TO_LONG mapper);
	protected abstract Function<? super T, ? extends LongStream> castToLongStream(TO_LS mapper);
	protected abstract ToDoubleFunction<? super T> castToDouble(TO_DOUBLE mapper);
	protected abstract java.util.stream.Stream<T> castToStream(STREAM stream);
	protected abstract Consumer<? super T> castToConsumers(CONSUMER action);
	protected abstract Predicate<? super T> castToPredicates(PREDICATE test);
	protected abstract BinaryOperator<T> castToBinaryOperators(BINARY_OPERATOR combiner);
	private static <T, E extends Exception> void terminal(
		ExConsumer<java.util.stream.Stream<T>, E> consumption,
		Supplier<java.util.stream.Stream<T>> supplier,
		Class<E> classOfE) throws E {
		try(java.util.stream.Stream<T> stream = supplier.get()) {
			consumption.accept(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	private static <T, E extends Exception, R> R terminalAsObj(
		ExFunction<java.util.stream.Stream<T>, R, E> consumption,
		Supplier<java.util.stream.Stream<T>> supplier,
		Class<E> classOfE) throws E {
		try(java.util.stream.Stream<T> stream = supplier.get()) {
			return consumption.apply(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	private static <T, E extends Exception> long terminalAsLong(
		ExToLongFunction<java.util.stream.Stream<T>, E> consumption,
		Supplier<java.util.stream.Stream<T>> supplier,
		Class<E> classOfE) throws E {
		try(java.util.stream.Stream<T> stream = supplier.get()) {
			return consumption.applyAsLong(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	private static <T, E extends Exception> boolean terminalAsBoolean(
		ExPredicate<java.util.stream.Stream<T>, E> consumption,
		Supplier<java.util.stream.Stream<T>> supplier,
		Class<E> classOfE) throws E {
		try(java.util.stream.Stream<T> stream = supplier.get()) {
			return consumption.test(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	private static <T, E extends Exception, K, L, M> M terminalAsMapToList(
		Function<? super T, ? extends K> classifier,
		Function<HashMap<K, L>, M> intoMap,
		Function<ArrayList<T>, L> intoList,
		Supplier<java.util.stream.Stream<T>> supplier,
		Class<E> classOfE) throws E {
		Supplier<HashMap<K, L>> supply1 = () -> new HashMap<>();
		Supplier<ArrayList<T>> supply2 = () -> new ArrayList<>();
		BiConsumer<ArrayList<T>, T> accumulator2 = (left, value) -> left.add(value);
		BinaryOperator<ArrayList<T>> combiner2 = (left, right) -> {
			left.addAll(right);
			return left;
		};
		Collector<T, ?, M> collectingAndThen =
			collectingAndThen(
				groupingBy(classifier, supply1, collectingAndThen(of(supply2, accumulator2, combiner2), intoList)),
				intoMap);
		try(java.util.stream.Stream<T> s = supplier.get()) {
			return s.collect(collectingAndThen);
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
