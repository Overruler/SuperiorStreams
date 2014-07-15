package utils.streams.functions;

import java.util.Comparator;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.BinaryOperator
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface ExBinaryOperator<T, E extends Exception> extends ExBiFunction<T, T, T, E> {
	static <T, E extends Exception> ExBinaryOperator<T, E> minBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) <= 0 ? a : b;
	}
	static <T, E extends Exception> ExBinaryOperator<T, E> maxBy(Comparator<? super T> comparator) {
		Objects.requireNonNull(comparator);
		return (a, b) -> comparator.compare(a, b) >= 0 ? a : b;
	}
	static <T, E extends Exception> ExBinaryOperator<T, E> recheck(
		java.util.function.BinaryOperator<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, T u) -> {
			try {
				return unchecked.apply(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default @Override java.util.function.BinaryOperator<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, T u) -> {
			try {
				return apply(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
