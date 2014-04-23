package utils.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
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
import static java.util.stream.Collector.*;
import static java.util.stream.Collectors.*;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToLongFunction;

//*Q*
abstract class AbstractStream<T, E extends Exception, STREAM,
SELF extends AbstractStream<T, E, STREAM,
SELF,
IS, LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, COMPARATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>,
IS, LS, DS, CONSUMER, PREDICATE, BINARY_OPERATOR, COMPARATOR, TO_IS, TO_LS, TO_DS, TO_INT, TO_LONG, TO_DOUBLE>
implements StreamyBase<T, E, SELF, IS, LS, DS> {//*E*

	public SELF filter(PREDICATE allowed) {
		return filterInternal(castToPredicates(allowed));
	}
	public final @SafeVarargs SELF filter(Predicate<T> allow, Predicate<T>... allowed) {
		for(Predicate<T> predicate : allowed) {
			allow = allow.and(predicate);
		}
		return filterInternal(allow);
	}
	public IS mapToInt(TO_INT mapper) {
		return asIS(s -> s.mapToInt(castToInt(mapper)));
	}
	public LS mapToLong(TO_LONG mapper) {
		ToLongFunction<? super T> mapper2 = castToLong(mapper);
		return asLS(s -> s.mapToLong(mapper2));
	}
	public DS mapToDouble(TO_DOUBLE mapper) {
		return asDS(s -> s.mapToDouble(castToDouble(mapper)));
	}
	public IS flatMapToInt(TO_IS mapper) {
		return asIS(s -> s.flatMapToInt(castToIntStream(mapper)));
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
		return peek(castToConsumers(action));
	}
	public final @SafeVarargs SELF peek(Consumer<T> action, Consumer<? super T>... actions) {
		for(Consumer<? super T> consumer : actions) {
			action = action.andThen(consumer);
		}
		return peek(action);
	}
	public void forEach(CONSUMER action) throws E {
		forEach(castToConsumers(action));
	}
	public final @SafeVarargs void forEach(Consumer<T> action, Consumer<? super T>... actions) throws E {
		for(Consumer<? super T> consumer : actions) {
			action = action.andThen(consumer);
		}
		forEach(action);
	}
	public void forEachOrdered(CONSUMER action) throws E {
		forEachOrdered(castToConsumers(action));
	}
	public final @SafeVarargs void forEachOrdered(Consumer<T> action, Consumer<? super T>... actions) throws E {
		for(Consumer<? super T> consumer : actions) {
			action = action.andThen(consumer);
		}
		forEachOrdered(action);
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
		return StreamyBase.terminalAsObj(s -> s.toArray(), maker(), classOfE());
	}
	public T[] toArray(IntFunction<T[]> allocator) throws E {
		return StreamyBase.terminalAsObj(s -> s.toArray(allocator), maker(), classOfE());
	}
	public ArrayList<T> toList() throws E {
		return StreamyBase.terminalAsObj(
		  s -> s.collect(Collectors.<T, ArrayList<T>> toCollection(ArrayList<T>::new)),
		  maker(),
		  classOfE());
	}
	public long count() throws E {
		return StreamyBase.terminalAsLong(s -> s.count(), maker(), classOfE());
	}
	public Optional<T> findFirst() throws E {
		return StreamyBase.terminalAsObj(s -> s.findFirst(), maker(), classOfE());
	}
	public Optional<T> findAny() throws E {
		return StreamyBase.terminalAsObj(s -> s.findAny(), maker(), classOfE());
	}
	public SELF concat(StreamyBase<T, E, SELF, IS, LS, DS> after) {
		Objects.requireNonNull(after);
		return asSELF(s -> Stream.concat(s, after.maker().get()));
	}
	public <R> R collect(Supplier<R> initial, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) throws E {
		return StreamyBase.terminalAsObj(s -> s.collect(initial, accumulator, combiner), maker(), classOfE());
	}
	public <R, A> R collect(Collector<? super T, A, R> collector) throws E {
		return StreamyBase.terminalAsObj(s -> s.collect(collector), maker(), classOfE());
	}
	public SELF sorted(COMPARATOR comparator) {
		return asSELF(s -> s.sorted(castToComparators(comparator)));
	}
	public Optional<T> min(COMPARATOR comparator) throws E {
		return StreamyBase.terminalAsObj(s -> s.min(castToComparators(comparator)), maker(), classOfE());
	}
	public Optional<T> max(COMPARATOR comparator) throws E {
		return StreamyBase.terminalAsObj(s -> s.max(castToComparators(comparator)), maker(), classOfE());
	}
	public SummaryStatistics<T> summaryStatistics(TO_LONG attribute) throws E {
		ToLongFunction<? super T> attribute2 = castToLong(attribute);
		Collector<? super T, SummaryStatistics<T>, SummaryStatistics<T>> collector =
		  SummaryStatistics.collector(attribute2);
		return StreamyBase.terminalAsObj(s -> s.collect(collector), maker(), classOfE());
	}
	public OptionalDouble average(TO_DOUBLE attribute) throws E {
		return StreamyBase.terminalAsObj(s -> s.mapToDouble(castToDouble(attribute)).average(), maker(), classOfE());
	}
	protected <K> Map<K, ArrayList<T>> toMapInternal(Function<? super T, ? extends K> classifier) throws E {
		return StreamyBase.terminalAsMapToList(classifier, Function.identity(), Function.identity(), maker(), classOfE());
	}
	protected <M, L, K> M toMultiMapInternal(
	  Function<? super T, ? extends K> apply,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList) throws E {
		return StreamyBase.terminalAsMapToList(apply, intoMap, intoList, maker(), classOfE());
	}
	public T reduce(T start, BINARY_OPERATOR add) throws E {
		return reduce(start, castToBinaryOperators(add));
	}
	public final @SafeVarargs T reduce(T start, BinaryOperator<T> add, Function<T, T>... extras) throws E {
		for(Function<T, T> accumulator2 : extras) {
			add = add.andThen(accumulator2)::apply;
		}
		return reduce(start, add);
	}
	public Optional<T> reduce(BINARY_OPERATOR add) throws E {
		return reduceInternal(castToBinaryOperators(add));
	}
	public final @SafeVarargs Optional<T> reduce(BinaryOperator<T> add, Function<T, T>... extras) throws E {
		for(Function<T, T> accumulator2 : extras) {
			add = add.andThen(accumulator2)::apply;
		}
		return reduceInternal(add);
	}
	public boolean anyMatch(PREDICATE test) throws E {
		return anyMatch(castToPredicates(test));
	}
	public final @SafeVarargs boolean anyMatch(Predicate<T> test, Predicate<? super T>... tests) throws E {
		for(Predicate<? super T> test2 : tests) {
			test = test.and(test2);
		}
		return anyMatch(test);
	}
	public boolean allMatch(PREDICATE predicate) throws E {
		return allMatch(castToPredicates(predicate));
	}
	public final @SafeVarargs boolean allMatch(Predicate<T> test, Predicate<? super T>... tests) throws E {
		for(Predicate<? super T> test2 : tests) {
			test = test.and(test2);
		}
		return allMatch(test);
	}
	public boolean noneMatch(PREDICATE test) throws E {
		return noneMatch(castToPredicates(test));
	}
	public final @SafeVarargs boolean noneMatch(Predicate<T> test, Predicate<? super T>... tests) throws E {
		for(Predicate<? super T> predicate2 : tests) {
			test = test.and(predicate2);
		}
		return noneMatch(test);
	}
	protected <R, RS> RS mapInternal(
	  Function<? super T, ? extends R> mapper,
	  Function<Function<Stream<T>, Stream<R>>, RS> cast) {
		return cast.apply(s -> s.map(mapper));
	}
	protected <R, RS> RS flatMapInternal(
	  Function<? super T, ? extends Stream<? extends R>> mapper,
	  Function<Function<Stream<T>, Stream<R>>, RS> cast) {
		return cast.apply(s -> s.flatMap(mapper));
	}
	protected Optional<T> reduceInternal(BinaryOperator<T> accumulator) throws E {
		return StreamyBase.terminalAsObj(s -> s.reduce(accumulator), maker(), classOfE());
	}
	protected SELF filterInternal(Predicate<? super T> allowed) {
		return asSELF(s -> s.filter(allowed));
	}

	protected final Supplier<Stream<T>> supplier;

	protected AbstractStream(Supplier<STREAM> supplier) {
		this.supplier = () -> castToStream(supplier.get());
	}
	protected <OLD> AbstractStream(Supplier<OLD> older, Function<OLD, STREAM> converter) {
		this.supplier = () -> castToStream(converter.apply(older.get()));
	}
	public @Override Supplier<Stream<T>> maker() {
		return supplier;
	}
	protected abstract Comparator<? super T> castToComparators(COMPARATOR comparator);
	protected abstract Function<? super T, ? extends IntStream> castToIntStream(TO_IS mapper);
	protected abstract Function<? super T, ? extends DoubleStream> castToDoubleStream(TO_DS mapper);
	protected abstract ToIntFunction<? super T> castToInt(TO_INT mapper);
	protected abstract ToLongFunction<? super T> castToLong(TO_LONG mapper);
	protected abstract Function<? super T, ? extends LongStream> castToLongStream(TO_LS mapper);
	protected abstract ToDoubleFunction<? super T> castToDouble(TO_DOUBLE mapper);
	protected abstract Stream<T> castToStream(STREAM stream);
	protected abstract Consumer<? super T> castToConsumers(CONSUMER action);
	protected abstract Predicate<? super T> castToPredicates(PREDICATE test);
	protected abstract BinaryOperator<T> castToBinaryOperators(BINARY_OPERATOR combiner);
	static <T, E extends Exception> void terminal(
	  ExConsumer<Stream<T>, E> consumption,
	  Supplier<Stream<T>> supplier,
	  Class<E> classOfE) throws E {
		try(Stream<T> stream = supplier.get()) {
			consumption.accept(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	static <T, E extends Exception, R> R terminalAsObj(
	  ExFunction<Stream<T>, R, E> consumption,
	  Supplier<Stream<T>> supplier,
	  Class<E> classOfE) throws E {
		try(Stream<T> stream = supplier.get()) {
			return consumption.apply(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	static <T, E extends Exception> long terminalAsLong(
	  ExToLongFunction<Stream<T>, E> consumption,
	  Supplier<Stream<T>> supplier,
	  Class<E> classOfE) throws E {
		try(Stream<T> stream = supplier.get()) {
			return consumption.applyAsLong(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	static <T, E extends Exception> boolean terminalAsBoolean(
	  ExPredicate<Stream<T>, E> consumption,
	  Supplier<Stream<T>> supplier,
	  Class<E> classOfE) throws E {
		try(Stream<T> stream = supplier.get()) {
			return consumption.test(stream);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	static <T, E extends Exception, K, L, M> M terminalAsMapToList(
	  Function<? super T, ? extends K> classifier,
	  Function<HashMap<K, L>, M> intoMap,
	  Function<ArrayList<T>, L> intoList,
	  Supplier<Stream<T>> supplier,
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
		try(Stream<T> s = supplier.get()) {
			return s.collect(collectingAndThen);
		} catch(RuntimeException e) {
			throw unwrapCause(classOfE, e);
		}
	}
	static <E extends Exception> E unwrapCause(Class<E> classOfE, RuntimeException e) throws E {
		Throwable cause = e.getCause();
		if(classOfE.isInstance(cause) == false) {
			throw e;
		}
		throw classOfE.cast(cause);
	}
}
