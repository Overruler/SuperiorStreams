package utils.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
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

interface StreamyBase<T, E extends Exception,SELF, IS, LS, DS> extends Streamable<T, E> {
	default T reduce(T identity, BinaryOperator<T> accumulator) throws E {
		return StreamyBase.terminalAsObj(s -> s.reduce(identity, accumulator), maker(), classOfE());
	}
	default Optional<T> reduce(BinaryOperator<T> accumulator) throws E {
		return StreamyBase.terminalAsObj(s -> s.reduce(accumulator), maker(), classOfE());
	}
	default <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) throws E {
		return StreamyBase.terminalAsObj(s -> s.reduce(identity, accumulator, combiner), maker(), classOfE());
	}
	default SELF filter(Predicate<? super T> allowed) {
		return asSELF(s -> s.filter(allowed));
	}
	default boolean anyMatch(Predicate<? super T> predicate) throws E {
		return StreamyBase.terminalAsBoolean(s -> s.anyMatch(predicate), maker(), classOfE());
	}
	default boolean allMatch(Predicate<? super T> predicate) throws E {
		return StreamyBase.terminalAsBoolean(s -> s.allMatch(predicate), maker(), classOfE());
	}
	default boolean noneMatch(Predicate<? super T> predicate) throws E {
		return StreamyBase.terminalAsBoolean(s -> s.noneMatch(predicate), maker(), classOfE());
	}
	default SELF peek(Consumer<? super T> action) {
		return asSELF(s -> s.peek(action));
	}
	default void forEach(Consumer<? super T> action) throws E {
		StreamyBase.terminal(s -> s.forEach(action), maker(), classOfE());
	}
	default void forEachOrdered(Consumer<? super T> action) throws E {
		StreamyBase.terminal(s -> s.forEachOrdered(action), maker(), classOfE());
	}

	abstract SELF asSELF(Function<Stream<T>, Stream<T>> convert);
	abstract IS asIS(Function<Stream<T>, IntStream> convert);
	abstract LS asLS(Function<Stream<T>, LongStream> convert);
	abstract DS asDS(Function<Stream<T>, DoubleStream> convert);
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