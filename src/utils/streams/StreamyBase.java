package utils.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import static java.util.stream.Collector.*;
import static java.util.stream.Collectors.*;
import utils.streams.functions.ExConsumer;
import utils.streams.functions.ExFunction;
import utils.streams.functions.ExPredicate;
import utils.streams.functions.ExToLongFunction;

interface StreamyBase<T, E extends Exception> extends Streamable<T, E> {

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